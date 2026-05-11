/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;

import java.util.List;
import java.util.ArrayList;

import model.Asistencia;
/**
 *
 * @author USUARIO
 */
public class AsistenciaDAO {
    
    public void marcar(int idUsuario, int idProyecto, String tipo) {

        try {
            Connection con = Conexion.conectar();

            String sql = "{CALL sp_marcar_asistencia(?, ?, ?)}";
            CallableStatement cs = con.prepareCall(sql);

            cs.setInt(1, idUsuario);
            cs.setInt(2, idProyecto);
            cs.setString(3, tipo);

            cs.execute();

            System.out.println("Marcación registrada correctamente");

            con.close();

        } catch (Exception e) {
            System.out.println("Error en marcación");
            e.printStackTrace();
        }
    }
    public ResultSet listar() {
        try {
            Connection con = Conexion.conectar();

            String sql = "SELECT * FROM registros_tiempo";
            Statement st = con.createStatement();

            return st.executeQuery(sql);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
        public List<Asistencia> listarAsistencias(
            int idUsuario,
            String fecha,
            String idProyecto,
            int limit,
            int offset) {

        List<Asistencia> lista = new ArrayList<>();

        try {

            Connection con = Conexion.conectar();

            PreparedStatement ps;

            if (fecha != null && !fecha.isEmpty()
                    && idProyecto != null && !idProyecto.isEmpty()) {

                String sql = "SELECT * FROM registros_tiempo "
                        + "WHERE id_usuario = ? AND fecha = ? "
                        + "AND id_proyecto = ? "
                        + "ORDER BY fecha DESC LIMIT ? OFFSET ?";

                ps = con.prepareStatement(sql);

                ps.setInt(1, idUsuario);
                ps.setString(2, fecha);
                ps.setInt(3, Integer.parseInt(idProyecto));
                ps.setInt(4, limit);
                ps.setInt(5, offset);

            } else if (fecha != null && !fecha.isEmpty()) {

                String sql = "SELECT * FROM registros_tiempo "
                        + "WHERE id_usuario = ? AND fecha = ? "
                        + "ORDER BY fecha DESC LIMIT ? OFFSET ?";

                ps = con.prepareStatement(sql);

                ps.setInt(1, idUsuario);
                ps.setString(2, fecha);
                ps.setInt(3, limit);
                ps.setInt(4, offset);

            } else if (idProyecto != null && !idProyecto.isEmpty()) {

                String sql = "SELECT * FROM registros_tiempo "
                        + "WHERE id_usuario = ? AND id_proyecto = ? "
                        + "ORDER BY fecha DESC LIMIT ? OFFSET ?";

                ps = con.prepareStatement(sql);

                ps.setInt(1, idUsuario);
                ps.setInt(2, Integer.parseInt(idProyecto));
                ps.setInt(3, limit);
                ps.setInt(4, offset);

            } else {

                String sql = "SELECT * FROM registros_tiempo "
                        + "WHERE id_usuario = ? "
                        + "ORDER BY fecha DESC LIMIT ? OFFSET ?";

                ps = con.prepareStatement(sql);

                ps.setInt(1, idUsuario);
                ps.setInt(2, limit);
                ps.setInt(3, offset);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Asistencia a = new Asistencia();

                a.id = rs.getInt("id_registro");
                a.id_usuario = rs.getInt("id_usuario");
                a.id_proyecto = rs.getInt("id_proyecto");

                String desc = rs.getString("descripcion");
                a.tipo = (desc != null) ? desc : "Sin descripción";

                a.fecha = rs.getString("fecha");

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
