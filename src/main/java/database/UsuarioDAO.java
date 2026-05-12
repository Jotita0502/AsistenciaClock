package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;
import java.util.ArrayList;

import model.Usuario;

public class UsuarioDAO {

    public List<Usuario> listarUsuarios() {

        List<Usuario> lista = new ArrayList<>();

        try {

            Connection con = Conexion.conectar();

            String sql = "SELECT id, nombre, email, rol FROM usuarios";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Usuario u = new Usuario();

                u.id = rs.getInt("id");
                u.nombre = rs.getString("nombre");
                u.correo = rs.getString("email");
                u.rol = rs.getString("rol");
                lista.add(u);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
        public boolean crearUsuario(Usuario u) {

        try {

            Connection con = Conexion.conectar();

            String sql = "INSERT INTO usuarios "
                    + "(nombre, email, password_hash, rol) "
                    + "VALUES (?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, u.nombre);
            ps.setString(2, u.correo);
            ps.setString(3, u.password);
            ps.setString(4, u.rol);

            int filas = ps.executeUpdate();

            ps.close();
            con.close();

            return filas > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        
    }    
        public boolean actualizarUsuario(int id, Usuario usuario) {

        try {

            Connection con = Conexion.conectar();

            String sql = "UPDATE usuarios "
                    + "SET nombre = ?, "
                    + "email = ?, "
                    + "rol = ? "
                    + "WHERE id = ?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, usuario.nombre);
            ps.setString(2, usuario.correo);
            ps.setString(3, usuario.rol);
            ps.setInt(4, id);

            int filas = ps.executeUpdate();

            ps.close();
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

            String sql = "DELETE FROM usuarios WHERE id = ?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            int filas = ps.executeUpdate();

            ps.close();
            con.close();

            return filas > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }
}