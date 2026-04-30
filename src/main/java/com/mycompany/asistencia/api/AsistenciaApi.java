/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.asistencia.api;

import static spark.Spark.*;
import database.Conexion;
import database.loginDAO;
import database.AsistenciaDAO;
import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.PreparedStatement;
import model.MarcacionRequest;
import model.Usuario;
import model.LoginRequest;
import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSet;
import model.Asistencia;
import model.ErrorResponse;
import model.SuccessResponse;

public class AsistenciaApi {

    public static void main(String[] args) {

        port(4567);

        System.out.println("🔥 API INICIADA 🔥");

        get("/test", (req, res) -> {
            return "API funcionando";
        });

        get("/test-conexion", (req, res) -> {
            if (Conexion.conectar() != null) {
                return "Conexion OK";
            } else {
                return "Error de conexion";
            }
        });
        
        post("/login", (req, res) -> {

            try {
                Gson gson = new Gson();
                LoginRequest data = gson.fromJson(req.body(), LoginRequest.class);

                // 🔥 VALIDACIÓN
                if (data == null || data.correo == null || data.password == null
                        || data.correo.isEmpty() || data.password.isEmpty()) {

                    res.status(400);
                    return gson.toJson(new ErrorResponse("Faltan datos de login"));
                }

                Usuario user = loginDAO.login(data.correo, data.password);

                res.type("application/json");

                if (user != null) {
                    return gson.toJson(user);
                } else {
                    res.status(401);
                    return gson.toJson(new ErrorResponse("Credenciales incorrectas"));
                }

            } catch (Exception e) {
                e.printStackTrace();
                res.status(500);
                return new Gson().toJson(new ErrorResponse("Error en servidor"));
            }
        });


        post("/marcar", (req, res) -> {

            try {
                Gson gson = new Gson();
                MarcacionRequest data = gson.fromJson(req.body(), MarcacionRequest.class);

                // 🔥 VALIDACIÓN
                if (data == null || data.id_usuario <= 0 || data.id_proyecto <= 0
                        || data.tipo == null || data.tipo.isEmpty()) {

                    res.status(400);
                    return gson.toJson(new ErrorResponse("Datos incompletos para marcar asistencia"));
                }

                System.out.println("Marcando asistencia...");
                System.out.println("Usuario: " + data.id_usuario);
                System.out.println("Proyecto: " + data.id_proyecto);
                System.out.println("Tipo: " + data.tipo);

                AsistenciaDAO dao = new AsistenciaDAO();
                dao.marcar(data.id_usuario, data.id_proyecto, data.tipo);

                res.type("application/json");
                return gson.toJson(new SuccessResponse("Marcación registrada correctamente"));

            } catch (Exception e) {
                e.printStackTrace();
                res.status(500);
                return new Gson().toJson(new ErrorResponse("Error en servidor"));
            }
        });
        get("/asistencias", (req, res) -> {

            Gson gson = new Gson();

            try {
                String idUsuario = req.queryParams("id_usuario");
                String fecha = req.queryParams("fecha");
                String idProyecto = req.queryParams("id_proyecto");
                String limit = req.queryParams("limit");
                String offset = req.queryParams("offset");

                int limitInt = 10;  // por defecto
                int offsetInt = 0;

                if (limit != null && !limit.trim().isEmpty()) {
                    limitInt = Integer.parseInt(limit.trim());
                }

                if (offset != null && !offset.trim().isEmpty()) {
                    offsetInt = Integer.parseInt(offset.trim());
                }

                if (idUsuario == null || idUsuario.isEmpty()) {
                    res.status(400);
                    return gson.toJson(new ErrorResponse("Falta id_usuario"));
                }

                int idUsuarioInt;

                try {
                    idUsuarioInt = Integer.parseInt(idUsuario);
                } catch (NumberFormatException e) {
                    res.status(400);
                    return gson.toJson(new ErrorResponse("id_usuario debe ser un número"));
                }

                Connection con = Conexion.conectar();

                if (con == null) {
                    res.status(500);
                    return gson.toJson(new ErrorResponse("Error de conexión a BD"));
                }

                PreparedStatement ps;

                if (fecha != null && !fecha.isEmpty() && idProyecto != null && !idProyecto.isEmpty()) {

                    String sql = "SELECT * FROM registros_tiempo WHERE id_usuario = ? AND fecha = ? AND id_proyecto = ? ORDER BY fecha DESC LIMIT ? OFFSET ?";
                    ps = con.prepareStatement(sql);

                    ps.setInt(1, idUsuarioInt);
                    ps.setString(2, fecha);
                    ps.setInt(3, Integer.parseInt(idProyecto));
                    ps.setInt(4, limitInt);
                    ps.setInt(5, offsetInt);

                } else if (fecha != null && !fecha.isEmpty()) {

                    String sql = "SELECT * FROM registros_tiempo WHERE id_usuario = ? AND fecha = ? ORDER BY fecha DESC LIMIT ? OFFSET ?";
                    ps = con.prepareStatement(sql);

                    ps.setInt(1, idUsuarioInt);
                    ps.setString(2, fecha);
                    ps.setInt(3, limitInt);
                    ps.setInt(4, offsetInt);

                } else if (idProyecto != null && !idProyecto.isEmpty()) {

                    String sql = "SELECT * FROM registros_tiempo WHERE id_usuario = ? AND id_proyecto = ? ORDER BY fecha DESC LIMIT ? OFFSET ?";
                    ps = con.prepareStatement(sql);

                    ps.setInt(1, idUsuarioInt);
                    ps.setInt(2, Integer.parseInt(idProyecto));
                    ps.setInt(3, limitInt);
                    ps.setInt(4, offsetInt);

                } else {

                    String sql = "SELECT * FROM registros_tiempo WHERE id_usuario = ? ORDER BY fecha DESC LIMIT ? OFFSET ?";
                    ps = con.prepareStatement(sql);

                    ps.setInt(1, idUsuarioInt);
                    ps.setInt(2, limitInt);
                    ps.setInt(3, offsetInt);
                }

                ResultSet rs = ps.executeQuery();

                List<Asistencia> lista = new ArrayList<>();

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

                res.type("application/json");
                return gson.toJson(lista);

            } catch (Exception e) {
                e.printStackTrace();
                res.status(500);
                return new Gson().toJson(new ErrorResponse("Error al listar"));
            }
        });


        awaitInitialization();
    }
}


