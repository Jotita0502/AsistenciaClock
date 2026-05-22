package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.CallableStatement;

import java.util.List;
import java.util.ArrayList;
import org.mindrot.jbcrypt.BCrypt;

import db.Conexion;
import model.Usuario;

public class UsuarioDAO {

        public List<Usuario> listarUsuarios() {

         List<Usuario> lista = new ArrayList<>();

         try {

             Connection con = Conexion.conectar();

             String sql = "{CALL sp_listar_usuarios()}";

             CallableStatement cs =
                     con.prepareCall(sql);

             ResultSet rs = cs.executeQuery();

             while (rs.next()) {

                 Usuario u = new Usuario();

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
        public boolean crearUsuario(Usuario u) {

            try {

                Connection con = Conexion.conectar();

                String sql = "{CALL sp_crear_usuario(?, ?, ?, ?, ?)}";

                CallableStatement cs =
                        con.prepareCall(sql);

                String hash =
                        BCrypt.hashpw(
                                u.password,
                                BCrypt.gensalt()
                        );

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
        public boolean actualizarUsuario(int id, Usuario usuario) {

            try {

                Connection con = Conexion.conectar();

                String sql =
                        "{CALL sp_actualizar_usuario(?, ?, ?, ?, ?)}";

                CallableStatement cs =
                        con.prepareCall(sql);

                String hash =
                        BCrypt.hashpw(
                                usuario.password,
                                BCrypt.gensalt()
                        );

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

                String sql =
                        "{CALL sp_eliminar_usuario(?)}";

                CallableStatement cs =
                        con.prepareCall(sql);

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
    }
