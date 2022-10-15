package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection coonect = Util.getConnect();
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        final String request = "CREATE TABLE if not exists `users` (\n" +
                "  `id` int NOT NULL AUTO_INCREMENT,\n" +
                "  `name` varchar(45) NOT NULL,\n" +
                "  `lastName` varchar(45) NOT NULL,\n" +
                "  `age` int NOT NULL,\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ";
        try (Statement statement = coonect.createStatement()) {
            statement.execute(request);

        } catch (SQLException e) { }
    }

    public void dropUsersTable() {
        final String request = "drop table if exists users";
        try (Statement statement = coonect.createStatement()) {
            statement.execute(request);
        } catch (SQLException e) { }
    }

    public void saveUser(String name, String lastName, byte age) {
        final String request = "insert into users(name,lastName,age) values(?,?,?);";
        try (PreparedStatement preparedStatement = coonect.prepareStatement(request)) {
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,lastName);
            preparedStatement.setByte(3,age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) { }
    }

    public void removeUserById(long id) {
        final String request = "delete from users where id = ?";
        try (PreparedStatement preparedStatement = coonect.prepareStatement(request)){
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) { }
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        final String request = "select * from users;";
        try (Statement statement = coonect.createStatement()) {
            ResultSet resultSet = statement.executeQuery(request);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                allUsers.add(user);
            }
        } catch (SQLException e) { }
        return allUsers;
    }

    public void cleanUsersTable() {
        final String request = "truncate users;";
        try (Statement statement = coonect.createStatement()){
            statement.execute(request);
        } catch (SQLException e) { }
    }
}
