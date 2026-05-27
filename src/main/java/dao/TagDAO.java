package dao;

import db.Conexion;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

import model.Tag;

public class TagDAO {

    // CREAR
    public boolean crearTag(Tag tag) {

        try {

            Connection con = Conexion.conectar();

            String sql = "{CALL sp_crear_etiqueta(?, ?, ?, ?)}";

            CallableStatement cs = con.prepareCall(sql);

            cs.setInt(1, tag.workspace_id);

            cs.setString(2, tag.nombre);

            cs.setString(3, tag.color);

            cs.registerOutParameter(
                    4,
                    java.sql.Types.INTEGER);

            cs.execute();

            int nuevoId = cs.getInt(4);

            cs.close();
            con.close();

            return nuevoId > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

    // LISTAR
    public List<Tag> listarTags(int workspaceId) {

        List<Tag> lista = new ArrayList<>();

        try {

            Connection con = Conexion.conectar();

            String sql = "{CALL sp_listar_etiquetas(?)}";

            CallableStatement cs = con.prepareCall(sql);

            cs.setInt(1, workspaceId);

            ResultSet rs = cs.executeQuery();

            while (rs.next()) {

                Tag t = new Tag();

                t.id = rs.getInt("id");

                t.workspace_id = rs.getInt("workspace_id");

                t.nombre = rs.getString("nombre");

                t.color = rs.getString("color");

                lista.add(t);
            }

            rs.close();
            cs.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return lista;
    }

    // OBTENER
    public Tag obtenerTag(int id) {

        try {

            Connection con = Conexion.conectar();

            String sql = "{CALL sp_obtener_etiqueta(?)}";

            CallableStatement cs = con.prepareCall(sql);

            cs.setInt(1, id);

            ResultSet rs = cs.executeQuery();

            Tag t = null;

            if (rs.next()) {

                t = new Tag();

                t.id = rs.getInt("id");

                t.workspace_id = rs.getInt("workspace_id");

                t.nombre = rs.getString("nombre");

                t.color = rs.getString("color");
            }

            rs.close();
            cs.close();
            con.close();

            return t;

        } catch (Exception e) {

            e.printStackTrace();

            return null;
        }
    }

    // ACTUALIZAR
    public boolean actualizarTag(
            int id,
            Tag tag) {

        try {

            Connection con = Conexion.conectar();

            String sql = "{CALL sp_actualizar_etiqueta(?, ?, ?)}";

            CallableStatement cs = con.prepareCall(sql);

            cs.setInt(1, id);

            cs.setString(2, tag.nombre);

            cs.setString(3, tag.color);

            int filas = cs.executeUpdate();

            cs.close();
            con.close();

            return filas > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

    // ELIMINAR
    public boolean eliminarTag(int id) {

        try {

            Connection con = Conexion.conectar();

            String sql = "{CALL sp_eliminar_etiqueta(?)}";

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

    // AGREGAR ETIQUETA A REGISTRO
    public boolean agregarEtiquetaRegistro(
            int registroId,
            int etiquetaId) {

        try {

            Connection con = Conexion.conectar();

            String sql = "{CALL sp_agregar_etiqueta_registro(?, ?)}";

            CallableStatement cs = con.prepareCall(sql);

            cs.setInt(1, registroId);

            cs.setInt(2, etiquetaId);

            cs.execute();

            cs.close();
            con.close();

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

    // QUITAR ETIQUETA
    public boolean quitarEtiquetaRegistro(
            int registroId,
            int etiquetaId) {

        try {

            Connection con = Conexion.conectar();

            String sql = "{CALL sp_quitar_etiqueta_registro(?, ?)}";

            CallableStatement cs = con.prepareCall(sql);

            cs.setInt(1, registroId);

            cs.setInt(2, etiquetaId);
            
            cs.execute();

            cs.close();
            con.close();
            return true;
        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

    // LISTAR ETIQUETAS POR REGISTRO
    public List<Tag> etiquetasPorRegistro(
            int registroId) {

        List<Tag> lista = new ArrayList<>();

        try {

            Connection con = Conexion.conectar();

            String sql = "{CALL sp_etiquetas_por_registro(?)}";

            CallableStatement cs = con.prepareCall(sql);

            cs.setInt(1, registroId);

            ResultSet rs = cs.executeQuery();

            while (rs.next()) {

                Tag t = new Tag();

                t.id = rs.getInt("id");

                t.workspace_id = rs.getInt("workspace_id");

                t.nombre = rs.getString("nombre");

                t.color = rs.getString("color");

                lista.add(t);
            }

            rs.close();
            cs.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return lista;
    }
}