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
        DecodedJWT jwt =
        JwtUtil.obtenerTokenDecodificado(token);

        String rol =
                jwt.getClaim("rol").asString();

        request.attribute("rol", rol);

        int usuarioId =
                jwt.getClaim("id").asInt();

        request.attribute("usuario_id", usuarioId);
        if (!valido) {

            halt(
                    401,
                    "{\"error\":\"Token inválido\"}"
            );
        }
    });

    // AQUÍ empiezan tus rutas
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
                            user.correo,
                            user.rol
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

                List<Asistencia> lista = dao.listarAsistencias();

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
            String rol = req.attribute("rol");

            if (!rol.equals("ADMIN")) {

                res.status(403);

                return gson.toJson(
                        new ErrorResponse(
                                "Acceso denegado"
                        )
                );
            }
            try {

                Usuario u = gson.fromJson(req.body(), Usuario.class);

                            // VALIDAR NOMBRE

            if (u.nombre == null
                    || u.nombre.trim().isEmpty()) {

                res.status(400);

                return gson.toJson(
                        new ErrorResponse(
                                "El nombre es obligatorio"
                        )
                );
            }

            // VALIDAR CORREO

            if (u.correo == null
                    || u.correo.trim().isEmpty()) {

                res.status(400);

                return gson.toJson(
                        new ErrorResponse(
                                "El correo es obligatorio"
                        )
                );
            }

            // VALIDAR PASSWORD

            if (u.password == null
                    || u.password.trim().isEmpty()) {

                res.status(400);

                return gson.toJson(
                        new ErrorResponse(
                                "La contraseña es obligatoria"
                        )
                );
            }

            // VALIDAR LONGITUD PASSWORD

            if (u.password.length() < 6) {

                res.status(400);

                return gson.toJson(
                        new ErrorResponse(
                                "La contraseña debe tener mínimo 6 caracteres"
                        )
                );
            }

            // VALIDAR ROL

            if (!u.rol.equals("ADMIN")
                    && !u.rol.equals("MANAGER")
                    && !u.rol.equals("EMPLEADO")) {

                res.status(400);

                return gson.toJson(
                        new ErrorResponse(
                                "Rol inválido"
                        )
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

    Gson gson = new Gson();

    String rol = req.attribute("rol");

    if (!rol.equals("ADMIN")) {

        res.status(403);

        return gson.toJson(
                new ErrorResponse(
                        "Acceso denegado"
                )
        );
    }

    int id = Integer.parseInt(req.params(":id"));

    Usuario usuario = gson.fromJson(req.body(), Usuario.class);

    // VALIDACIONES

    if (usuario.nombre == null
            || usuario.nombre.trim().isEmpty()) {

        res.status(400);

        return gson.toJson(
                new ErrorResponse(
                        "El nombre es obligatorio"
                )
        );
    }

    if (usuario.correo == null
            || usuario.correo.trim().isEmpty()) {

        res.status(400);

        return gson.toJson(
                new ErrorResponse(
                        "El correo es obligatorio"
                )
        );
    }

    if (!usuario.rol.equals("ADMIN")
            && !usuario.rol.equals("MANAGER")
            && !usuario.rol.equals("EMPLEADO")) {

        res.status(400);

        return gson.toJson(
                new ErrorResponse(
                        "Rol inválido"
                )
        );
    }

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

            Gson gson = new Gson();

            String rol = req.attribute("rol");

            if (!rol.equals("ADMIN")) {

                res.status(403);

                return gson.toJson(
                        new ErrorResponse(
                                "Acceso denegado"
                        )
                );
            }

            int id = Integer.parseInt(req.params(":id"));

            UsuarioDAO dao = new UsuarioDAO();

            boolean eliminado = dao.eliminarUsuario(id);

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
            String rol = req.attribute("rol");

            if (!rol.equals("ADMIN")
                    && !rol.equals("MANAGER")) {

                res.status(403);

                return gson.toJson(
                        new ErrorResponse(
                                "Acceso denegado"
                        )
                );
            }
            try {

                Proyecto proyecto =
                        gson.fromJson(req.body(), Proyecto.class);

                // VALIDACIONES

                if (proyecto.nombre == null
                        || proyecto.nombre.trim().isEmpty()) {

                    res.status(400);

                    return gson.toJson(
                            new ErrorResponse(
                                    "El nombre del proyecto es obligatorio"
                            )
                    );
                }

                if (proyecto.color == null
                        || proyecto.color.trim().isEmpty()) {

                    res.status(400);

                    return gson.toJson(
                            new ErrorResponse(
                                    "El color es obligatorio"
                            )
                    );
                }

                if (proyecto.workspace_id <= 0) {

                    res.status(400);

                    return gson.toJson(
                            new ErrorResponse(
                                    "workspace_id inválido"
                            )
                    );
                }

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

            } catch (Exception e) {

                e.printStackTrace();

                res.status(500);

                return gson.toJson(
                        new ErrorResponse(
                                "Error en servidor"
                        )
                );
            }
        });
                put("/proyectos/:id", (req, res) -> {

            res.type("application/json");

            int id = Integer.parseInt(req.params(":id"));

            Gson gson = new Gson();
            String rol = req.attribute("rol");

            if (!rol.equals("ADMIN")
                    && !rol.equals("MANAGER")) {

                res.status(403);

                return gson.toJson(
                        new ErrorResponse(
                                "Acceso denegado"
                        )
                );
            }
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

            Gson gson = new Gson();

            String rol = req.attribute("rol");

            if (!rol.equals("ADMIN")) {

                res.status(403);

                return gson.toJson(
                        new ErrorResponse(
                                "Acceso denegado"
                        )
                );
            }

            int id = Integer.parseInt(req.params(":id"));

            ProyectoDAO dao = new ProyectoDAO();

            boolean eliminado = dao.eliminarProyecto(id);

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
                post("/timer/iniciar", (req, res) -> {

            res.type("application/json");

            Gson gson = new Gson();

            Asistencia asistencia = gson.fromJson(
                    req.body(),
                    Asistencia.class
            );

            AsistenciaDAO dao = new AsistenciaDAO();

            boolean iniciado = dao.iniciarTimer(asistencia);

            if (iniciado) {

                return gson.toJson(
                        new SuccessResponse(
                                "Timer iniciado correctamente"
                        )
                );

            } else {

                res.status(400);

                return gson.toJson(
                        new ErrorResponse(
                                "Ya existe un timer activo"
                        )
                );
            }
        }); 
                post("/timer/detener", (req, res) -> {

            res.type("application/json");

            Gson gson = new Gson();

            Asistencia asistencia = gson.fromJson(
                    req.body(),
                    Asistencia.class
            );

            AsistenciaDAO dao = new AsistenciaDAO();

            boolean detenido = dao.detenerTimer(
                    asistencia.usuario_id
            );

            if (detenido) {

                return gson.toJson(
                        new SuccessResponse(
                                "Timer detenido correctamente"
                        )
                );

            } else {

                res.status(400);

                return gson.toJson(
                        new ErrorResponse(
                                "No existe timer activo"
                        )
                );
            }
        });
            get("/timer/historial", (req, res) -> {

            res.type("application/json");

            Gson gson = new Gson();

            String idUsuario = req.queryParams("id_usuario");

            if (idUsuario == null || idUsuario.isEmpty()) {

                res.status(400);

                return gson.toJson(
                        new ErrorResponse(
                                "Falta id_usuario"
                        )
                );
            }

            AsistenciaDAO dao = new AsistenciaDAO();

            List<Asistencia> lista = dao.historialTimers(
                    Integer.parseInt(idUsuario)
            );

            return gson.toJson(lista);
        });
                get("/timer/activo", (req, res) -> {

            res.type("application/json");

            Gson gson = new Gson();

            String authHeader =
                    req.headers("Authorization");

            String token =
                    authHeader.replace("Bearer ", "");

            DecodedJWT jwt =
                    JwtUtil.obtenerTokenDecodificado(token);

            int usuarioId =
                    jwt.getClaim("id").asInt();

            AsistenciaDAO dao = new AsistenciaDAO();

            Asistencia timer = dao.obtenerTimerActivo(usuarioId);

            if (timer == null) {

                return gson.toJson(
                        new ErrorResponse("No hay timer activo")
                );
            }

            return gson.toJson(timer);
        });
        awaitInitialization();
    }
}


