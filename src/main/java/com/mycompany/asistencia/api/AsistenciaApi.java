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

                Usuario user = loginDAO.login(data.correo, data.password);

                res.type("application/json");

                if (user != null) {
                    return gson.toJson(user); // 🔥 devuelve usuario real
                } else {
                    return "{\"error\":\"Credenciales incorrectas\"}";
                }

            } catch (Exception e) {
                e.printStackTrace();
                return "{\"error\":\"Error en servidor\"}";
            }
        });

        post("/marcar", (req, res) -> {

            try {
                Gson gson = new Gson();

                MarcacionRequest data = gson.fromJson(req.body(), MarcacionRequest.class);

                System.out.println("Marcando asistencia...");
                System.out.println("Usuario: " + data.id_usuario);
                System.out.println("Proyecto: " + data.id_proyecto);
                System.out.println("Tipo: " + data.tipo);

                AsistenciaDAO dao = new AsistenciaDAO();
                dao.marcar(data.id_usuario, data.id_proyecto, data.tipo);

                res.type("application/json");
                return "{\"mensaje\":\"Marcación registrada correctamente\"}";

            } catch (Exception e) {
                e.printStackTrace();
                return "{\"error\":\"Error en servidor\"}";
            }
        });
        get("/asistencias", (req, res) -> {

        try {
            String idUsuario = req.queryParams("id_usuario");

            Gson gson = new Gson();
            Connection con = Conexion.conectar();

            String sql = "SELECT * FROM registros_tiempo WHERE id_usuario = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(idUsuario));

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

            res.type("application/json");
            return gson.toJson(lista);

        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\":\"Error al listar\"}";
        }
    });

        awaitInitialization();
    }
}


