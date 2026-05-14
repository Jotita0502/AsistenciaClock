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
            public List<Asistencia> listarAsistencias() {

        List<Asistencia> lista = new ArrayList<>();

        try {

            Connection con = Conexion.conectar();

            String sql = "SELECT * FROM registros_tiempo "
                    + "ORDER BY inicio DESC";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Asistencia a = new Asistencia();

                a.id = rs.getInt("id");

                a.usuario_id = rs.getInt("usuario_id");

                a.workspace_id = rs.getInt("workspace_id");

                a.proyecto_id = rs.getInt("proyecto_id");

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
            public boolean iniciarTimer(Asistencia a) {

            try {

                Connection con = Conexion.conectar();

                String verificar = "SELECT id FROM registros_tiempo "
                        + "WHERE usuario_id = ? AND fin IS NULL";

                PreparedStatement psVerificar = con.prepareStatement(verificar);

                psVerificar.setInt(1, a.usuario_id);

                ResultSet rs = psVerificar.executeQuery();

                if (rs.next()) {

                    rs.close();
                    psVerificar.close();
                    con.close();

                    return false;
                }

                rs.close();
                psVerificar.close();

                String sql = "INSERT INTO registros_tiempo "
                        + "(usuario_id, workspace_id, proyecto_id, "
                        + "descripcion, inicio, billable) "
                        + "VALUES (?, ?, ?, ?, NOW(), ?)";

                PreparedStatement ps = con.prepareStatement(sql);

                ps.setInt(1, a.usuario_id);
                ps.setInt(2, a.workspace_id);
                ps.setInt(3, a.proyecto_id);
                ps.setString(4, a.descripcion);
                ps.setBoolean(5, a.billable);

                int filas = ps.executeUpdate();

                ps.close();
                con.close();

                return filas > 0;

            } catch (Exception e) {

                e.printStackTrace();
                return false;
            }
        }
        public boolean detenerTimer(int usuarioId) {

        try {

            Connection con = Conexion.conectar();

            String sql = "UPDATE registros_tiempo "
                    + "SET fin = NOW() "
                    + "WHERE usuario_id = ? "
                    + "AND fin IS NULL";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, usuarioId);

            int filas = ps.executeUpdate();

            ps.close();
            con.close();

            return filas > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
        }    
                public List<Asistencia> historialTimers(int usuarioId) {

            List<Asistencia> lista = new ArrayList<>();

            try {

                Connection con = Conexion.conectar();

                String sql = "SELECT * FROM registros_tiempo "
                        + "WHERE usuario_id = ? "
                        + "ORDER BY inicio DESC";

                PreparedStatement ps = con.prepareStatement(sql);

                ps.setInt(1, usuarioId);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    Asistencia a = new Asistencia();

                    a.id = rs.getInt("id");

                    a.usuario_id = rs.getInt("usuario_id");

                    a.workspace_id = rs.getInt("workspace_id");

                    a.proyecto_id = rs.getInt("proyecto_id");

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
    

