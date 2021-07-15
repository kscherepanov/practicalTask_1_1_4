package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String statement = "CREATE TABLE IF NOT EXISTS `users` (" +
                "`id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT," +
                "`name` varchar(255) NOT NULL," +
                "`lastName` varchar(255) NOT NULL," +
                "`age` tinyint NOT NULL," +
                "PRIMARY KEY (`id`)" +
                ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;";

        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(statement)) {
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String statement = "DROP TABLE IF EXISTS users;";

        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(statement)) {
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (name, lastName, age)  VALUES(?, ?, ?)";

        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String query = "DELETE FROM `users` WHERE id=?";

        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM `users`;";

        try (Statement statement = Util.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return users;
    }

    public void cleanUsersTable() {
        String query = "TRUNCATE TABLE `users`;";

        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(query)) {
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
