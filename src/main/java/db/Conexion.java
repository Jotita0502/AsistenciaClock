package db;

import java.sql.Connection;
import java.sql.DriverManager;
import io.github.cdimascio.dotenv.Dotenv;

public class Conexion {

    public static Connection conectar() {

        Connection con = null;

        try {
            Dotenv dotenv = Dotenv.load();
            
            String HOST = dotenv.get("DB_HOST"); 
            String PORT = dotenv.get("DB_PORT");
            String DATABASE = dotenv.get("DB_NAME");
            String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE + "?useSSL=true&sslMode=REQUIRED&serverTimezone=UTC";
            String USER = dotenv.get("DB_USER");
            String PASSWORD = dotenv.get("DB_PASSWORD");

            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (Exception e) {
            System.out.println("Error de conexion");
            e.printStackTrace();
        }

        return con;
    }
}