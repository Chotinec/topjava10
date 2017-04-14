package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.List;
import java.util.*;

/**
 * Created by Artiom on 10.04.2017.
 */
public class UsersUtil {

    public static final List<User> USERS = Arrays.asList(
            new User("user", "user@gmail.com", "user", Role.ROLE_USER),
            new User("admin", "admin@gmail.com", "admin", Role.ROLE_ADMIN)
    );
}
