package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;

import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.UserTestData.USER_WITH_MEALS_MATCHER;

@ActiveProfiles(profiles = Profiles.DATAJPA)
public class DataJpaUserServiceTest extends AbstractUserServiceTest{
    @Test
    public void getWithMeals() {
        User user = service.getWithMeals(USER_ID);
        USER_WITH_MEALS_MATCHER.assertMatch(user, UserTestData.user);
    }
}
