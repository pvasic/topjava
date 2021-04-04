package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class JdbcUserRepository implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(@NotNull User user) {
        BeanPropertySqlParameterSource parameterSourceUser = new BeanPropertySqlParameterSource(user);
        Integer key;
        if (user.isNew()) {
            key = (Integer) insertUser.executeAndReturnKey(parameterSourceUser);
            user.setId(key);
        } else if (namedParameterJdbcTemplate.update("""
                   UPDATE users SET name=:name, email=:email, password=:password, 
                   registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id
                """, parameterSourceUser) == 0) {
            return null;
        } else {
            key = user.getId();
        }
        jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=?", key);
        List<Role> roles = new ArrayList<>(user.getRoles());
        jdbcTemplate.batchUpdate("insert into user_roles (user_id, role) values(?,?)",
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, key);
                        ps.setString(2, String.valueOf(roles.get(i)));
                    }

                    public int getBatchSize() {
                        return roles.size();
                    }

                });
        return user;
    }

    @Override
    @Transactional
    public boolean delete(@NotNull int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(@NotNull int id) {
        List<User> users = jdbcTemplate.query("SELECT u.*, ur.role as roles " +
                "FROM users u LEFT JOIN" +
                "    (SELECT * FROM user_roles) ur ON u.id=ur.user_id " +
                "WHERE id=?;", ROW_MAPPER, id);
        if (users.isEmpty()) {
            return null;
        }
        User user = new User(users.get(0));
        user.setRoles(users.stream().map(u-> u.getRoles().iterator().next()).collect(Collectors.toList()));
        return user;
    }

    @Override
    public User getByEmail(@Email String email) {
        List<User> users = jdbcTemplate.query("SELECT u.*, ur.role as roles " +
                "FROM users u LEFT JOIN" +
                "    (SELECT * FROM user_roles) ur ON u.id=ur.user_id " +
                "WHERE email=?;", ROW_MAPPER, email);
        User user = new User(users.get(0));
        user.setRoles(users.stream().map(u-> u.getRoles().iterator().next()).collect(Collectors.toList()));
        return user;
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM users ORDER BY name, email", ROW_MAPPER);
    }
}
