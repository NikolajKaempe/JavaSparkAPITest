package Testing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Kaempe on 15-02-2017.
 */
public class Test {
    enum TestTableColumns{
        id,name,email
    }
    private final String jdbcDriverStr;
    private final String jdbcURL;

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    public Test(String jdbcDriverStr, String jdbcURL){
        this.jdbcDriverStr = jdbcDriverStr;
        this.jdbcURL = jdbcURL;
    }
    public void readData() throws Exception {
        try {
            Class.forName(jdbcDriverStr);
            connection = DriverManager.getConnection(jdbcURL);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from AirshipOne.User;");
            getResultSet(resultSet);
            preparedStatement = connection.prepareStatement("insert into AirshipOne.User values (default,?,?)");
            preparedStatement.setString(1,"test from java");
            preparedStatement.setString(2,"test@javatest.dk");
            preparedStatement.executeUpdate();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from AirshipOne.User;");
        }finally{
            close();
        }
    }

    private void getResultSet(ResultSet resultSet) throws Exception {
        while(resultSet.next()){
            Integer id = resultSet.getInt(TestTableColumns.id.toString());
            String name = resultSet.getString(TestTableColumns.name.toString());
            String email = resultSet.getString(TestTableColumns.email.toString());
            System.out.println("id: "+id);
            System.out.println("name: "+name);
            System.out.println("email: "+email);
        }
    }

    private void close(){
        try {
            if(resultSet!=null) resultSet.close();
            if(statement!=null) statement.close();
            if(connection!=null) connection.close();
        } catch(Exception e){}
    }
}
