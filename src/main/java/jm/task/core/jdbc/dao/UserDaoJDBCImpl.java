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
        String request = "CREATE TABLE `users` (\n" +
                "  `id` int NOT NULL AUTO_INCREMENT,\n" +
                "  `name` varchar(45) NOT NULL,\n" +
                "  `lastName` varchar(45) NOT NULL,\n" +
                "  `age` int DEFAULT NULL,\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ";
        try (Statement statement = Util.getConnect().createStatement()) {
            statement.execute(request);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        String request = "drop table users";
        try (Statement statement = Util.getConnect().createStatement()) {
            statement.execute(request);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String request = "insert into users(name,lastName,age) values(?,?,?);";
        try (PreparedStatement preparedStatement = Util.getConnect().prepareStatement(request)) {
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,lastName);
            preparedStatement.setByte(3,age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String request = "delete from users where id = ?";
        try (PreparedStatement preparedStatement = Util.getConnect().prepareStatement(request)){
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        String request = "select * from users;";
        try (Statement statement = Util.getConnect().createStatement()) {
            ResultSet resultSet = statement.executeQuery(request);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                allUsers.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allUsers;
    }

    public void cleanUsersTable() {
        String request = "truncate users;";
        try (Statement statement = Util.getConnect().createStatement()){
            statement.execute(request);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
