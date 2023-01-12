package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (PreparedStatement ps = Util.getConnection().prepareStatement(
                "CREATE TABLE IF NOT EXISTS `mydbtest`.`users` " +
                        "(`id` INT NOT NULL AUTO_INCREMENT," +
                        "`name` VARCHAR(45)," +
                        "`lastName` VARCHAR(45)," +
                        "`age` INT," +
                        "PRIMARY KEY (`id`))")) {
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (PreparedStatement ps = Util.getConnection().prepareStatement("DROP TABLE IF EXISTS users")) {
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement ps = Util.getConnection().prepareStatement("INSERT INTO users (name, lastName, age)" +
                " VALUES (?, ?, ?)")) {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement ps = Util.getConnection().prepareStatement("DELETE FROM users WHERE id = ?")) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();

        try (PreparedStatement ps = Util.getConnection().prepareStatement("SELECT * FROM users")) {
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                usersList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usersList;
    }

    public void cleanUsersTable() {
        try (PreparedStatement ps = Util.getConnection().prepareStatement("DELETE FROM users")) {
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
