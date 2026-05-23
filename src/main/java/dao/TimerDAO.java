package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

import db.Conexion;

import model.Timer;

public class TimerDAO {

    // INICIAR TIMER
    public boolean iniciarTimer(Timer a) {

        try {

            Connection con = Conexion.conectar();

            String sql = "{CALL sp_iniciar_timer(?, ?, ?, ?, ?, ?, ?)}";

            CallableStatement cs = con.prepareCall(sql);

            cs.setInt(1, a.usuario_id);

            cs.setInt(2, a.workspace_id);

            // proyecto_id puede ser null
            if (a.proyecto_id > 0) {

                cs.setInt(3, a.proyecto_id);

            } else {

                cs.setNull(3, java.sql.Types.INTEGER);
            }

            // tarea_id puede ser null
            if (a.tarea_id > 0) {

                cs.setInt(4, a.tarea_id);

            } else {

                cs.setNull(4, java.sql.Types.INTEGER);
            }

            cs.setString(5, a.descripcion);

            cs.setBoolean(6, a.billable);

            cs.registerOutParameter(
                    7,
                    java.sql.Types.INTEGER);

            cs.execute();

            int nuevoId = cs.getInt(7);

            cs.close();
            con.close();

            return nuevoId > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

    // DETENER TIMER
    public boolean detenerTimer(int usuarioId) {

        try {

            Connection con = Conexion.conectar();

            String sql = "{CALL sp_detener_timer(?)}";

            CallableStatement cs = con.prepareCall(sql);

            cs.setInt(1, usuarioId);

            int filas = cs.executeUpdate();

            cs.close();
            con.close();

            return filas > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

    // TIMER ACTIVO
    public Timer obtenerTimerActivo(int usuarioId) {

        try {

            Connection con = Conexion.conectar();

            String sql = "{CALL sp_timer_activo(?)}";

            CallableStatement cs = con.prepareCall(sql);

            cs.setInt(1, usuarioId);

            ResultSet rs = cs.executeQuery();

            Timer a = null;

            if (rs.next()) {

                a = new Timer();

                a.id = rs.getInt("id");

                a.usuario_id = rs.getInt("usuario_id");

                a.workspace_id = rs.getInt("workspace_id");

                a.proyecto_id = rs.getInt("proyecto_id");

                a.tarea_id = rs.getInt("tarea_id");

                a.descripcion = rs.getString("descripcion");

                a.inicio = rs.getString("inicio");

                a.fin = rs.getString("fin");

                a.duracion_seg = rs.getInt("duracion_seg");

                a.billable = rs.getBoolean("billable");
            }

            rs.close();
            cs.close();
            con.close();

            return a;

        } catch (Exception e) {

            e.printStackTrace();

            return null;
        }
    }

    // HISTORIAL
    public List<Timer> historialTimers(int usuarioId) {

        List<Timer> lista = new ArrayList<>();

        try {

            Connection con = Conexion.conectar();

            String sql = "{CALL sp_historial_timers(?)}";

            CallableStatement cs = con.prepareCall(sql);

            cs.setInt(1, usuarioId);

            ResultSet rs = cs.executeQuery();

            while (rs.next()) {

                Timer a = new Timer();

                a.id = rs.getInt("id");

                a.usuario_id = rs.getInt("usuario_id");

                a.workspace_id = rs.getInt("workspace_id");

                a.proyecto_id = rs.getInt("proyecto_id");

                a.tarea_id = rs.getInt("tarea_id");

                a.descripcion = rs.getString("descripcion");

                a.inicio = rs.getString("inicio");

                a.fin = rs.getString("fin");

                a.duracion_seg = rs.getInt("duracion_seg");

                a.billable = rs.getBoolean("billable");

                lista.add(a);
            }

            rs.close();
            cs.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return lista;
    }

    // LISTADO SIMPLE
    public List<Timer> listar() {

        List<Timer> lista = new ArrayList<>();

        try {

            Connection con = Conexion.conectar();

            String sql = "SELECT * FROM registros_tiempo";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Timer a = new Timer();

                a.id = rs.getInt("id");

                a.usuario_id = rs.getInt("usuario_id");

                a.workspace_id = rs.getInt("workspace_id");

                a.proyecto_id = rs.getInt("proyecto_id");

                a.tarea_id = rs.getInt("tarea_id");

                a.descripcion = rs.getString("descripcion");

                a.inicio = rs.getString("inicio");

                a.fin = rs.getString("fin");

                a.duracion_seg = rs.getInt("duracion_seg");

                a.billable = rs.getBoolean("billable");

                lista.add(a);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return lista;
    }
}