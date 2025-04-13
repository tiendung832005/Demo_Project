package business.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/jdbc_practice";
    private static final String USER = "root";
    private static final String PASS = "12345678";

    private static Connection connection = null;
    
    public static Connection getConnection(){
        try{
            if(connection == null || connection.isClosed()){
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(DB_URL, USER, PASS);
            }
            return connection;
        }catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
            throw new RuntimeException("Kết nối database thất bại: " + e.getMessage());
        }
    }

    public static void closeConnection(){
        try{
            if(connection != null && !connection.isClosed()){
                connection.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
