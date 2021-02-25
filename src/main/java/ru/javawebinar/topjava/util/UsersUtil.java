package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

public class UsersUtil {
    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;
    public static final User USER = new User(USER_ID, "userName", "userEmail@gmail.com", "password", Role.USER);
    public static final User ADMIN = new User(ADMIN_ID, "adminName", "adminEmail@gmail.com", "password", Role.USER, Role.ADMIN);
}
