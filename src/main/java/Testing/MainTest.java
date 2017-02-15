package Testing;

/**
 * Created by Kaempe on 15-02-2017.
 */
public class MainTest
{
    public static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
    public static final String MYSQL_URL = "jdbc:mysql://80.255.6.114:3306/AirshipOne?"
            + "user=AirshipOneUser&password=123456";
    public static void main(String[] args) throws Exception  {

        Test dao = new Test(MYSQL_DRIVER,MYSQL_URL);
        dao.readData();
    }
}
