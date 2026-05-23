/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.asistencia.api;

import static spark.Spark.*;

import utils.JwtUtil;
import static spark.Spark.before;
import com.auth0.jwt.interfaces.DecodedJWT;

import db.Conexion;
import routes.AuthRoutes;
import routes.UsuarioRoutes;
import routes.ProyectoRoutes;
import routes.TimerRoutes;
import routes.WorkspaceRoutes;

public class AsistenciaApi {

    public static void main(String[] args) {
        String hashNativo = org.mindrot.jbcrypt.BCrypt.hashpw("admin123", org.mindrot.jbcrypt.BCrypt.gensalt(10));
        System.out.println("🎯 TU HASH PERFECTO ES: " + hashNativo);

        port(4567);

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

            request.attribute("rol", rol);

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

        UsuarioRoutes.init();

        ProyectoRoutes.init();

        WorkspaceRoutes.init();

        TimerRoutes.init();
    }
}
