package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.CallableStatement;

import java.util.List;
import java.util.ArrayList;
import org.mindrot.jbcrypt.BCrypt;

import db.Conexion;
import model.User;

public class UserDAO {

    public List<User> listarUsuarios() {

        List<User> lista = new ArrayList<>();

        try {

            Connection con = Conexion.conectar();

            String sql = "{CALL sp_listar_usuarios()}";

            CallableStatement cs = con.prepareCall(sql);

            ResultSet rs = cs.executeQuery();

            while (rs.next()) {

                User u = new User();

                u.id = rs.getInt("id");
                u.nombre = rs.getString("nombre");
                u.correo = rs.getString("email");
                u.rol = rs.getString("rol");

                lista.add(u);
            }

            rs.close();
            cs.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return lista;
    }

    public boolean crearUsuario(User u) {

        try {

            Connection con = Conexion.conectar();

            String sql = "{CALL sp_crear_usuario(?, ?, ?, ?, ?)}";

            CallableStatement cs = con.prepareCall(sql);

            String hash = BCrypt.hashpw(
                    u.password,
                    BCrypt.gensalt());

            cs.setString(1, u.nombre);
            cs.setString(2, u.correo);
            cs.setString(3, hash);
            cs.setString(4, u.rol);

            // OUT PARAM
            cs.registerOutParameter(5, java.sql.Types.INTEGER);

            cs.execute();

            int nuevoId = cs.getInt(5);

            cs.close();
            con.close();

            return nuevoId > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarUsuario(int id, User usuario) {

        try {

            Connection con = Conexion.conectar();

            String sql = "{CALL sp_actualizar_usuario(?, ?, ?, ?, ?)}";

            CallableStatement cs = con.prepareCall(sql);

            String hash = BCrypt.hashpw(
                    usuario.password,
                    BCrypt.gensalt());

            cs.setInt(1, id);
            cs.setString(2, usuario.nombre);
            cs.setString(3, usuario.correo);
            cs.setString(4, hash);
            cs.setString(5, usuario.rol);

            int filas = cs.executeUpdate();

            cs.close();
            con.close();

            return filas > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarUsuario(int id) {

        try {

            Connection con = Conexion.conectar();

            String sql = "{CALL sp_eliminar_usuario(?)}";

            CallableStatement cs = con.prepareCall(sql);

            cs.setInt(1, id);

            int filas = cs.executeUpdate();

            cs.close();
            con.close();

            return filas > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }
    public boolean actualizarMiPerfil(int id, User usuario) {

        try {

            Connection con = Conexion.conectar();

            String sql = "{CALL sp_actualizar_mi_perfil(?, ?, ?)}";

            CallableStatement cs = con.prepareCall(sql);

            cs.setInt(1, id);
            cs.setString(2, usuario.nombre);
            cs.setString(3, usuario.correo);

            int filas = cs.executeUpdate();

            cs.close();
            con.close();

            return filas > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }
    public String obtenerPasswordHash(int id) {

        try {

            Connection con = Conexion.conectar();

            String sql = "SELECT password_hash FROM usuarios WHERE id = ?";

            java.sql.PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            String hash = null;

            if (rs.next()) {
                hash = rs.getString("password_hash");
            }

            rs.close();
            ps.close();
            con.close();

            return hash;

        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }
    public boolean actualizarMiPassword(int id, String nuevaPassword) {

        try {

            Connection con = Conexion.conectar();

            String sql = "{CALL sp_actualizar_mi_password(?, ?)}";

            CallableStatement cs = con.prepareCall(sql);

            String hash = BCrypt.hashpw(
                    nuevaPassword,
                    BCrypt.gensalt());

            cs.setInt(1, id);
            cs.setString(2, hash);

            int filas = cs.executeUpdate();

            cs.close();
            con.close();

            return filas > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }
    public boolean actualizarUsuarioDatos(int id, User usuario) {

        try {

            Connection con = Conexion.conectar();

            String sql = "{CALL sp_actualizar_usuario_datos(?, ?, ?, ?)}";

            CallableStatement cs = con.prepareCall(sql);

            cs.setInt(1, id);
            cs.setString(2, usuario.nombre);
            cs.setString(3, usuario.correo);
            cs.setString(4, usuario.rol);

            int filas = cs.executeUpdate();

            cs.close();
            con.close();

            return filas > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }    
}
