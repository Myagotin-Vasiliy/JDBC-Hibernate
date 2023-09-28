package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Igor", "Litvinov", (byte) 22);
        userService.saveUser("Oleg", "Nadych", (byte) 38);
        userService.saveUser("Vasiliy", "Lepert", (byte) 32);
        userService.saveUser("Olga", "Gromova", (byte) 37);

        List<User> userList = userService.getAllUsers();
        userList.forEach(System.out::println);

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
