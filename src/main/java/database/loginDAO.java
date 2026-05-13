package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.mindrot.jbcrypt.BCrypt;

import model.Usuario;

public class loginDAO {

    public static Usuario login(String correo, String password) {

        Usuario user = null;

        try {

            Connection con = Conexion.conectar();

            String sql =
                    "SELECT * FROM usuarios WHERE email = ?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, correo);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String hash =
                        rs.getString("password_hash");

                boolean passwordCorrecta =
                        BCrypt.checkpw(password, hash);

                if (passwordCorrecta) {

                    user = new Usuario();

                    user.id = rs.getInt("id");
                    user.nombre = rs.getString("nombre");
                    user.correo = rs.getString("email");
                    user.rol = rs.getString("rol");
                }
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }
}