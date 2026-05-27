package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.mindrot.jbcrypt.BCrypt;

import db.Conexion;
import model.User;

public class LoginDAO {

    public static User login(String correo, String password) {
        User user = null;

        try (Connection con = Conexion.conectar();
                PreparedStatement ps = con.prepareStatement("SELECT * FROM usuarios WHERE email = ?")) {

            ps.setString(1, correo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String hashBD = rs.getString("password_hash");

                    if (BCrypt.checkpw(password.trim(), hashBD)) {
                        user = new User();
                        user.id = rs.getInt("id");
                        user.nombre = rs.getString("nombre");
                        user.correo = rs.getString("email");
                        user.rol = rs.getString("rol");
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error en loginDAO: " + e.getMessage());
        }

        return user;
    }
}