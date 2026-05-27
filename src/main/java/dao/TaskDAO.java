package dao;

import db.Conexion;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

import model.Task;

public class TaskDAO {

    // CREAR
    public boolean crearTarea(Task tarea) {

        try {

            Connection con = Conexion.conectar();

            String sql = "{CALL sp_crear_tarea(?, ?, ?)}";

            CallableStatement cs = con.prepareCall(sql);

            cs.setInt(1, tarea.proyecto_id);

            cs.setString(2, tarea.nombre);

            cs.registerOutParameter(
                    3,
                    java.sql.Types.INTEGER);

            cs.execute();

            int nuevoId = cs.getInt(3);

            cs.close();
            con.close();

            return nuevoId > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

    // LISTAR POR PROYECTO
    public List<Task> listarPorProyecto(
            int proyectoId,
            boolean incluirArchivadas) {

        List<Task> lista = new ArrayList<>();

        try {

            Connection con = Conexion.conectar();

            String sql = "{CALL sp_tareas_por_proyecto(?, ?)}";

            CallableStatement cs = con.prepareCall(sql);

            cs.setInt(1, proyectoId);

            cs.setBoolean(2, incluirArchivadas);

            ResultSet rs = cs.executeQuery();

            while (rs.next()) {

                Task t = new Task();

                t.id = rs.getInt("id");

                t.proyecto_id = rs.getInt("proyecto_id");

                t.nombre = rs.getString("nombre");

                t.archivado = rs.getBoolean("archivado");

                t.created_at = rs.getString("created_at");

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

    // LISTAR TAREAS TOTAL
    public List<Task> listarTareas() {

        List<Task> lista = new ArrayList<>();

        try {

            Connection con = Conexion.conectar();

            String sql = "{CALL sp_obtener_tareas_totales()}";

            CallableStatement cs = con.prepareCall(sql);

            ResultSet rs = cs.executeQuery();

            while (rs.next()) {

                Task t = new Task();

                t.id = rs.getInt("id");

                t.proyecto_id = rs.getInt("proyecto_id");

                t.nombre = rs.getString("nombre");

                t.archivado = rs.getBoolean("archivado");

                t.created_at = rs.getString("created_at");

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
    public Task obtenerTarea(int id) {

        try {

            Connection con = Conexion.conectar();

            String sql = "{CALL sp_obtener_tarea(?)}";

            CallableStatement cs = con.prepareCall(sql);

            cs.setInt(1, id);

            ResultSet rs = cs.executeQuery();

            Task t = null;

            if (rs.next()) {

                t = new Task();

                t.id = rs.getInt("id");

                t.proyecto_id = rs.getInt("proyecto_id");

                t.nombre = rs.getString("nombre");

                t.archivado = rs.getBoolean("archivado");

                t.created_at = rs.getString("created_at");
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
    public boolean actualizarTarea(
            int id,
            Task tarea) {

        try {

            Connection con = Conexion.conectar();

            String sql = "{CALL sp_actualizar_tarea(?, ?, ?)}";

            CallableStatement cs = con.prepareCall(sql);

            cs.setInt(1, id);

            cs.setInt(2, tarea.proyecto_id);

            cs.setString(3, tarea.nombre);

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
    public boolean eliminarTarea(int id) {

        try {

            Connection con = Conexion.conectar();

            String sql = "{CALL sp_eliminar_tarea(?)}";

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

    // ARCHIVAR
    public boolean archivarTarea(
            int id,
            boolean archivado) {

        try {

            Connection con = Conexion.conectar();

            String sql = "{CALL sp_archivar_tarea(?, ?)}";

            CallableStatement cs = con.prepareCall(sql);

            cs.setInt(1, id);

            cs.setBoolean(2, archivado);

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