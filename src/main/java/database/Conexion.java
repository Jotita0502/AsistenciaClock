/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

    public static Connection conectar() {

        Connection con = null;

        try {

            String url = "URL_BASE";
            String user = "USER_BASE";
            String password = "CONTRA_BASE";

            con = DriverManager.getConnection(url, user, password);

            System.out.println("Conexion exitosa a MySQL nube");

        } catch (Exception e) {

            System.out.println("Error de conexion");
            e.printStackTrace();

        }

        return con;
    }
}

