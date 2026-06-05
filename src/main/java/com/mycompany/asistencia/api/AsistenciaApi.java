/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.asistencia.api;

import static spark.Spark.*;

import utils.JwtUtil;

import com.auth0.jwt.interfaces.DecodedJWT;

import db.Conexion;
import routes.AuthRoutes;
import routes.UserRoutes;
import routes.ProjectRoutes;
import routes.TimerRoutes;
import routes.WorkspaceRoutes;
import routes.TaskRoutes;
import routes.TagRoutes;

public class AsistenciaApi {

    public static void main(String[] args) {
        port(4567);
        before((request, response) -> {

            response.header("Access-Control-Allow-Origin", "http://localhost:4200");
            response.header("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
            response.header("Access-Control-Allow-Headers", "Content-Type,Authorization");

            if (request.requestMethod().equals("OPTIONS")) {
                halt(200);
            }

            String path = request.pathInfo();

            if (path.equals("/login") || path.equals("/test") || path.equals("/test-conexion")) {
                return;
            }

            String authHeader = request.headers("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                halt(401, "{\"error\":\"Token requerido\"}");
            }

            String token = authHeader.replace("Bearer ", "");
            boolean valido = JwtUtil.validarToken(token);

            if (!valido) {
                halt(401, "{\"error\":\"Token inválido\"}");
            }

            DecodedJWT jwt = JwtUtil.obtenerTokenDecodificado(token);

            String rol = jwt.getClaim("rol").asString();
            int usuarioId = jwt.getClaim("id").asInt();

            request.attribute("rol", rol);
            request.attribute("usuario_id", usuarioId);
        });

        options("/*", (req, res) -> {
            res.header("Access-Control-Allow-Origin", "http://localhost:4200");
            res.header("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
            res.header("Access-Control-Allow-Headers", "Content-Type,Authorization");
            res.status(200);
            return "OK";
        });


        System.out.println("🔥 API INICIADA 🔥");

        before((request, response) -> {

            if (request.requestMethod().equals("OPTIONS")) {
                return;
            }

            // 2. EXCLUIR LAS RUTAS PÚBLICAS (Login y Tests)
            String path = request.pathInfo();
            if (path.equals("/login") || path.equals("/test") || path.equals("/test-conexion")) {
                return; // 🟢 Rompe el filtro aquí y deja pasar la petición sin pedir token
            }

            // 3. VALIDACIÓN DE TOKEN PARA EL RESTO DE RUTAS
            String authHeader = request.headers("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                halt(401, "{\"error\":\"Token requerido\"}");
            }

            String token = authHeader.replace("Bearer ", "");
            boolean valido = JwtUtil.validarToken(token);

            if (!valido) {
                halt(401, "{\"error\":\"Token inválido\"}");
            }

            DecodedJWT jwt = JwtUtil.obtenerTokenDecodificado(token);

            String rol = jwt.getClaim("rol").asString();

            request.attribute("rol", rol);

            int usuarioId = jwt.getClaim("id").asInt();

            request.attribute("usuario_id", usuarioId);

        });

        // TESTS

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

        awaitInitialization();

        AuthRoutes.init();

        UserRoutes.init();

        ProjectRoutes.init();

        WorkspaceRoutes.init();

        TaskRoutes.init();

        TimerRoutes.init();

        TagRoutes.init();
    }
}
