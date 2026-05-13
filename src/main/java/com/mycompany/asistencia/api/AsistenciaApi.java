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
                    || (path.equals("/usuarios")
                    && request.requestMethod().equals("POST"))) {

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
        });

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

                    String token = JwtUtil.generarToken(
                            user.id,
                            user.correo
                    );

                    LoginResponse response =
                            new LoginResponse(token, user);

                    return gson.toJson(response);

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

                AsistenciaDAO dao = new AsistenciaDAO();

                List<Asistencia> lista = dao.listarAsistencias(
                        idUsuarioInt,
                        fecha,
                        idProyecto,
                        limitInt,
                        offsetInt
                );

                res.type("application/json");
                return gson.toJson(lista);

            } catch (Exception e) {
                e.printStackTrace();
                res.status(500);
                return new Gson().toJson(new ErrorResponse("Error al listar"));
            }
        });
                get("/usuarios", (req, res) -> {

            Gson gson = new Gson();

            try {

                UsuarioDAO dao = new UsuarioDAO();

                List<Usuario> lista = dao.listarUsuarios();

                res.type("application/json");

                return gson.toJson(lista);

            } catch (Exception e) {

                e.printStackTrace();

                res.status(500);

                return gson.toJson(
                        new ErrorResponse("Error al listar usuarios")
                );
            }
        });
                post("/usuarios", (req, res) -> {

            Gson gson = new Gson();

            try {

                Usuario u = gson.fromJson(req.body(), Usuario.class);

                if (u.nombre == null || u.nombre.isEmpty()
                        || u.correo == null || u.correo.isEmpty()
                        || u.password == null || u.password.isEmpty()
                        || u.rol == null || u.rol.isEmpty()) {

                    res.status(400);

                    return gson.toJson(
                            new ErrorResponse("Faltan datos del usuario")
                    );
                }

                UsuarioDAO dao = new UsuarioDAO();

                boolean creado = dao.crearUsuario(u);

                if (creado) {

                    res.status(201);

                    return gson.toJson(
                            new SuccessResponse("Usuario creado correctamente")
                    );

                } else {

                    res.status(500);

                    return gson.toJson(
                            new ErrorResponse("No se pudo crear usuario")
                    );
                }

            } catch (Exception e) {

                e.printStackTrace();

                res.status(500);

                return gson.toJson(
                        new ErrorResponse("Error en servidor")
                );
            }
        });
                        put("/usuarios/:id", (req, res) -> {

             res.type("application/json");

             int id = Integer.parseInt(req.params(":id"));

             Gson gson = new Gson();

             Usuario usuario = gson.fromJson(req.body(), Usuario.class);

             UsuarioDAO dao = new UsuarioDAO();

             boolean actualizado = dao.actualizarUsuario(id, usuario);

             if (actualizado) {

                 return gson.toJson(
                         new SuccessResponse(
                                 "Usuario actualizado correctamente"
                         )
                 );

             } else {

                 res.status(500);

                 return gson.toJson(
                         new ErrorResponse(
                                 "No se pudo actualizar usuario"
                         )
                 );
             }
         });
                    delete("/usuarios/:id", (req, res) -> {

            res.type("application/json");

            int id = Integer.parseInt(req.params(":id"));

            UsuarioDAO dao = new UsuarioDAO();

            boolean eliminado = dao.eliminarUsuario(id);

            Gson gson = new Gson();

            if (eliminado) {

                return gson.toJson(
                        new SuccessResponse(
                                "Usuario eliminado correctamente"
                        )
                );

            } else {

                res.status(500);

                return gson.toJson(
                        new ErrorResponse(
                                "No se pudo eliminar usuario"
                        )
                );
            }
        });
                get("/proyectos", (req, res) -> {

            Gson gson = new Gson();

            try {

                ProyectoDAO dao = new ProyectoDAO();

                List<Proyecto> lista = dao.listarProyectos();

                res.type("application/json");

                return gson.toJson(lista);

            } catch (Exception e) {

                e.printStackTrace();

                res.status(500);

                return gson.toJson(
                        new ErrorResponse("Error al listar proyectos")
                );
            }
        });
                    post("/proyectos", (req, res) -> {

            res.type("application/json");

            Gson gson = new Gson();

            Proyecto proyecto = gson.fromJson(req.body(), Proyecto.class);

            ProyectoDAO dao = new ProyectoDAO();

            boolean creado = dao.crearProyecto(proyecto);

            if (creado) {

                return gson.toJson(
                        new SuccessResponse(
                                "Proyecto creado correctamente"
                        )
                );

            } else {

                res.status(500);

                return gson.toJson(
                        new ErrorResponse(
                                "No se pudo crear proyecto"
                        )
                );
            }
        });
                put("/proyectos/:id", (req, res) -> {

            res.type("application/json");

            int id = Integer.parseInt(req.params(":id"));

            Gson gson = new Gson();

            Proyecto proyecto =
                    gson.fromJson(req.body(), Proyecto.class);

            ProyectoDAO dao = new ProyectoDAO();

            boolean actualizado =
                    dao.actualizarProyecto(id, proyecto);

            if (actualizado) {

                return gson.toJson(
                        new SuccessResponse(
                                "Proyecto actualizado correctamente"
                        )
                );

            } else {

                res.status(500);

                return gson.toJson(
                        new ErrorResponse(
                                "No se pudo actualizar proyecto"
                        )
                );
            }
        });
                delete("/proyectos/:id", (req, res) -> {

            res.type("application/json");

            int id = Integer.parseInt(req.params(":id"));

            ProyectoDAO dao = new ProyectoDAO();

            boolean eliminado = dao.eliminarProyecto(id);

            Gson gson = new Gson();

            if (eliminado) {

                return gson.toJson(
                        new SuccessResponse(
                                "Proyecto eliminado correctamente"
                        )
                );

            } else {

                res.status(500);

                return gson.toJson(
                        new ErrorResponse(
                                "No se pudo eliminar proyecto"
                        )
                );
            }
        });
        awaitInitialization();
    }
}


