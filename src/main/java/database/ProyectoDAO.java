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
}