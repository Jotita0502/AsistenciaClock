/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import model.Usuario;

/**
 *
 * @author USUARIO
 */
public class loginDAO {
    public static Usuario login(String correo, String password) {
        Usuario user = null;

        try {
            Connection con = Conexion.conectar();

            String sql = "{CALL sp_login_usuario(?, ?)}";
            CallableStatement cs = con.prepareCall(sql);

            cs.setString(1, correo);
            cs.setString(2, password);

            ResultSet rs = cs.executeQuery();

            if (rs.next()) {
                user = new Usuario();

                user.id = rs.getInt("id_usuario");
                user.nombre = rs.getString("nombre_usuario");
                user.correo = rs.getString("correo");
                user.rol = rs.getString("rol");

                System.out.println("DEBUG:");
                System.out.println("ID: " + user.id);
                System.out.println("Nombre: " + user.nombre);
                System.out.println("Correo: " + user.correo);
                System.out.println("Rol: " + user.rol);

                System.out.println("Login correcto");
            } else {
                System.out.println("Usuario o clave incorrectos");
            }
            con.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }
}
