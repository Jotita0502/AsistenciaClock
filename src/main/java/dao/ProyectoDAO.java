package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.CallableStatement;
import java.util.List;

import db.Conexion;

import java.util.ArrayList;

import model.Proyecto;

public class ProyectoDAO {

        public List<Proyecto> listarProyectos() {

        List<Proyecto> lista = new ArrayList<>();

        try {

            Connection con = Conexion.conectar();

            String sql =
                    "{CALL sp_listar_proyectos(?, ?)}";

            CallableStatement cs =
                    con.prepareCall(sql);

            // workspace fijo por ahora
            cs.setInt(1, 2);

            // no incluir archivados
            cs.setBoolean(2, false);

            ResultSet rs = cs.executeQuery();

            while (rs.next()) {

                Proyecto p = new Proyecto();

                p.id = rs.getInt("id");
                p.workspace_id = rs.getInt("workspace_id");
                p.nombre = rs.getString("nombre");
                p.color = rs.getString("color");
                p.billable =rs.getBoolean("billable");
                p.archivado =rs.getBoolean("archivado");

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
        public boolean crearProyecto(Proyecto proyecto) {

            try {

                Connection con = Conexion.conectar();

                String sql =
                        "{CALL sp_crear_proyecto(?, ?, ?, ?, ?, ?)}";

                CallableStatement cs =
                        con.prepareCall(sql);

                cs.setInt(1, proyecto.workspace_id);

                // cliente_id temporal
                cs.setInt(2, 1);

                cs.setString(3, proyecto.nombre);

                cs.setString(4, proyecto.color);

                cs.setBoolean(5, proyecto.billable);

                cs.registerOutParameter(
                        6,
                        java.sql.Types.INTEGER
                );

                cs.execute();

                int nuevoId = cs.getInt(6);

                cs.close();
                con.close();

                return nuevoId > 0;

            } catch (Exception e) {

                e.printStackTrace();
                return false;
            }
        }
        public boolean actualizarProyecto(
                int id,
                Proyecto proyecto
        ) {

            try {

                Connection con = Conexion.conectar();

                String sql =
                        "{CALL sp_actualizar_proyecto(?, ?, ?, ?)}";

                CallableStatement cs =
                        con.prepareCall(sql);

                cs.setInt(1, id);

                cs.setString(2, proyecto.nombre);

                cs.setString(3, proyecto.color);

                cs.setBoolean(4, proyecto.archivado);

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

            String sql =
                    "{CALL sp_eliminar_proyecto(?)}";

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