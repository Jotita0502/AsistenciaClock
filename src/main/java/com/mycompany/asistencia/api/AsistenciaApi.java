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
import database.UsuarioDAO;
import database.ProyectoDAO;
import model.Proyecto;
import utils.JwtUtil;
import model.LoginResponse;
import static spark.Spark.before;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.JsonObject;
import routes.AuthRoutes;
import routes.UsuarioRoutes;
import routes.ProyectoRoutes;
import routes.AsistenciaRoutes;
import routes.TimerRoutes;

public class AsistenciaApi {

    public static void main(String[] args) {

    port(4567);

    System.out.println("🔥 API INICIADA 🔥");

    before((request, response) -> {

        String path = request.pathInfo();

        // rutas públicas
        if (path.equals("/login")
                || path.equals("/test")
                || path.equals("/test-conexion")
                ) {

            return;
        }

        String authHeader =
                request.headers("Authorization");

        if (authHeader == null
                || !authHeader.startsWith("Bearer ")) {

            halt(
                    401,
                    "{\"error\":\"Token requerido\"}"
            );
        }

        String token =
                authHeader.replace("Bearer ", "");

        boolean valido =
                JwtUtil.validarToken(token);
        if (!valido) {

            halt(
                    401,
                    "{\"error\":\"Token inválido\"}"
            );
        }
        
        DecodedJWT jwt =
                JwtUtil.obtenerTokenDecodificado(token);

        String rol =
                jwt.getClaim("rol").asString();

        request.attribute("rol", rol);

        int usuarioId =
                jwt.getClaim("id").asInt();

        request.attribute("rol", rol);
        
        request.attribute("usuario_id", usuarioId);

    });

    //TESTS
    
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

        AsistenciaRoutes.init();

        TimerRoutes.init();
    }
}


