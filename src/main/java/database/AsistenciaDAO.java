/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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


}
