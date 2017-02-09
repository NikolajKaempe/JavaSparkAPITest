import Controllers.UserController;
import Services.UserService;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

public class Main {
    public static void main(String[] args) {
        String databaseUrl = "jdbc:mysql://80.255.6.114:3306/AirshipOne";
        ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl);
        ((JdbcConnectionSource)connectionSource).setUsername("AirshipOneUser");
        ((JdbcConnectionSource)connectionSource).setPassword("123456");

        new UserController(new UserService(connectionSource));
    }
}