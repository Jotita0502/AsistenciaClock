package database;

import java.sql.Connection;
import java.sql.DriverManager;
import io.github.cdimascio.dotenv.Dotenv;

public class Conexion {

    public static Connection conectar() {

        Connection con = null;

        try {
            Dotenv dotenv = Dotenv.load();

            String url = dotenv.get("DB_URL"); 
            String user = dotenv.get("DB_USER");
            String password = dotenv.get("DB_PASS");

            con = DriverManager.getConnection(url, user, password);

            System.out.println("Conexion exitosa a MySQL nube");

        } catch (Exception e) {
            System.out.println("Error de conexion");
            e.printStackTrace();
        }

        return con;
    }
}