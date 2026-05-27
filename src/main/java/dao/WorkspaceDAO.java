package dao;

import db.Conexion;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

import model.Workspace;

public class WorkspaceDAO {

    // LISTAR
    public List<Workspace> listarWorkspaces() {

        List<Workspace> lista = new ArrayList<>();

        try {

            Connection con = Conexion.conectar();

            String sql = "{CALL sp_listar_workspaces()}";

            CallableStatement cs = con.prepareCall(sql);

            ResultSet rs = cs.executeQuery();

            while (rs.next()) {

                Workspace w = new Workspace();

                w.id = rs.getInt("id");
                w.nombre = rs.getString("nombre");
                w.descripcion = rs.getString("descripcion");
                w.owner_id = rs.getInt("owner_id");

                lista.add(w);
            }

            rs.close();
            cs.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return lista;
    }

    // CREAR
    public boolean crearWorkspace(Workspace workspace) {

        try {

            Connection con = Conexion.conectar();

            String sql = "{CALL sp_crear_workspace(?, ?, ?, ?)}";

            CallableStatement cs = con.prepareCall(sql);

            cs.setString(1, workspace.nombre);

            cs.setString(2, workspace.descripcion);

            cs.setInt(3, workspace.owner_id);

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

    // ACTUALIZAR
    public boolean actualizarWorkspace(
            int id,
            Workspace workspace) {

        try {

            Connection con = Conexion.conectar();

            String sql = "{CALL sp_actualizar_workspace(?, ?, ?, ?)}";

            CallableStatement cs = con.prepareCall(sql);

            cs.setInt(1, id);

            cs.setString(2, workspace.nombre);

            cs.setString(3, workspace.descripcion);

            cs.setInt(4, workspace.owner_id);

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
    public boolean eliminarWorkspace(int id) {

        try {

            Connection con = Conexion.conectar();

            String sql = "{CALL sp_eliminar_workspace(?)}";

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
}