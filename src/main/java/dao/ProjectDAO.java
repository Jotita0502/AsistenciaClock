package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.CallableStatement;
import java.util.List;

import db.Conexion;

import java.util.ArrayList;

import model.Project;

public class ProjectDAO {

    public List<Project> listarProyectos() {

        List<Project> lista = new ArrayList<>();

        try {

            Connection con = Conexion.conectar();

            String sql = "{CALL sp_listar_proyectos()}";

            CallableStatement cs = con.prepareCall(sql);

            ResultSet rs = cs.executeQuery();

            while (rs.next()) {

                Project p = new Project();

                p.id = rs.getInt("id");
                p.workspace_id = rs.getInt("workspace_id");
                p.nombre = rs.getString("nombre");
                p.color = rs.getString("color");
                p.billable = rs.getBoolean("billable");
                p.archivado = rs.getBoolean("archivado");

                lista.add(p);
            }

            rs.close();
            cs.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return lista;
    }

    public boolean crearProyecto(Project proyecto) {

        try {

            Connection con = Conexion.conectar();

            String sql = "{CALL sp_crear_proyecto(?, ?, ?, ?, ?, ?)}";

            CallableStatement cs = con.prepareCall(sql);

            cs.setInt(1, proyecto.workspace_id);

            // cliente_id temporal
            cs.setInt(2, 1);

            cs.setString(3, proyecto.nombre);

            cs.setString(4, proyecto.color);

            cs.setBoolean(5, proyecto.billable);

            cs.registerOutParameter(
                    6,
                    java.sql.Types.INTEGER);

            cs.execute();

            cs.close();
            con.close();

            return true;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarProyecto(int id, Project proyecto) {
        try {

            Connection con = Conexion.conectar();

            String sql = "{CALL sp_actualizar_proyecto(?, ?, ?, ?, ?)}";

            CallableStatement cs = con.prepareCall(sql);

            cs.setInt(1, id);

            // cliente_id temporal
            cs.setInt(2, 1);

            cs.setString(3, proyecto.nombre);

            cs.setString(4, proyecto.color);

            cs.setBoolean(5, proyecto.billable);

            int filas = cs.executeUpdate();

            cs.close();
            con.close();

            return filas > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarProyecto(int id) {

        try {

            Connection con = Conexion.conectar();

            String sql = "{CALL sp_eliminar_proyecto(?)}";

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