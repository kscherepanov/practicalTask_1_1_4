package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        // 1. Создание таблицы User(ов)
        userService.createUsersTable();

        // 2. Добавление 4 User(ов) в таблицу с данными на свой выбор.
        // После каждого добавления должен быть вывод в консоль ( User с именем – name добавлен в базу данных )
        userService.saveUser("Ivan", "Ivanov", (byte) 10);
        userService.saveUser("Petr", "Petrov", (byte) 20);
        userService.saveUser("Oleg", "Olegov", (byte) 30);
        userService.saveUser("Kirill", "Kirillov", (byte) 20);

        // 3. Получение всех User из базы и вывод в консоль ( должен быть переопределен toString в классе User)
        List<User> users = userService.getAllUsers();

        for (User user : users) {
            System.out.println(user);
        }

        userService.removeUserById(1);

        // 4. Очистка таблицы User(ов)
        userService.cleanUsersTable();

        // 5. Удаление таблицы
        userService.dropUsersTable();
    }
}
