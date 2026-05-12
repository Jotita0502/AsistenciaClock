package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;
import java.util.ArrayList;

import model.Proyecto;

public class ProyectoDAO {

    public List<Proyecto> listarProyectos() {

        List<Proyecto> lista = new ArrayList<>();

        try {

            Connection con = Conexion.conectar();

            String sql = "SELECT id, nombre, color, archivado FROM proyectos";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Proyecto p = new Proyecto();

                p.id = rs.getInt("id");
                p.nombre = rs.getString("nombre");
                p.color = rs.getString("color");
                boolean archivado = rs.getBoolean("archivado");

                p.estado = archivado ? "archivado" : "activo";
                
                lista.add(p);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
        public boolean crearProyecto(Proyecto proyecto) {

        try {

            Connection con = Conexion.conectar();

            String sql = "INSERT INTO proyectos "
                    + "(workspace_id, nombre, color, billable, archivado) "
                    + "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, 1);
            ps.setString(2, proyecto.nombre);
            ps.setString(3, proyecto.color);
            ps.setBoolean(4, true);
            ps.setBoolean(5, false);

            ps.executeUpdate();

            ps.close();
            con.close();

            return true;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }
        public boolean actualizarProyecto(int id, Proyecto proyecto) {

        try {

            Connection con = Conexion.conectar();

            String sql = "UPDATE proyectos "
                    + "SET nombre = ?, "
                    + "color = ?, "
                    + "archivado = ? "
                    + "WHERE id = ?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, proyecto.nombre);
            ps.setString(2, proyecto.color);

            boolean archivado =
                    proyecto.estado.equalsIgnoreCase("archivado");

            ps.setBoolean(3, archivado);

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
            public boolean eliminarProyecto(int id) {

         try {

             Connection con = Conexion.conectar();

             String sql = "DELETE FROM proyectos WHERE id = ?";

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