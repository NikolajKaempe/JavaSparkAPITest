import Controllers.UserController;
import Models.User;
import Services.UserService;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        String databaseUrl = "jdbc:mysql://80.255.6.114:3306/AirshipOne";
        ConnectionSource connectionSource = null;
        Dao<User,Integer> userDao = null;
        try {
            connectionSource = new JdbcConnectionSource(databaseUrl);
            ((JdbcConnectionSource)connectionSource).setUsername("AirshipOneUser");
            ((JdbcConnectionSource)connectionSource).setPassword("123456");

            userDao = DaoManager.createDao(connectionSource, User.class);

        } catch (SQLException e) {
            throw new IllegalArgumentException("Could not create database manager");
        }

        new UserController(new UserService(userDao));
    }
}