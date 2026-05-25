/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package routes;

import com.google.gson.Gson;

import dao.UserDAO;

import java.util.List;

import model.User;
import model.response.ErrorResponse;
import model.response.SuccessResponse;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

/**
 *
 * @author USUARIO
 */
public class UserRoutes {

        public static void init() {

                get("/usuarios", (req, res) -> {

                        Gson gson = new Gson();

                        try {

                                UserDAO dao = new UserDAO();

                                List<User> lista = dao.listarUsuarios();

                                res.type("application/json");

                                return gson.toJson(lista);

                        } catch (Exception e) {

                                e.printStackTrace();

                                res.status(500);

                                return gson.toJson(
                                                new ErrorResponse("Error al listar usuarios"));
                        }
                });
                post("/usuarios", (req, res) -> {

                        Gson gson = new Gson();
                        String rol = req.attribute("rol");

                        if (!rol.equals("ADMIN")) {

                                res.status(403);

                                return gson.toJson(
                                                new ErrorResponse(
                                                                "Acceso denegado"));
                        }
                        try {

                                User u = gson.fromJson(req.body(), User.class);

                                // VALIDAR NOMBRE

                                if (u.nombre == null
                                                || u.nombre.trim().isEmpty()) {

                                        res.status(400);

                                        return gson.toJson(
                                                        new ErrorResponse(
                                                                        "El nombre es obligatorio"));
                                }

                                // VALIDAR CORREO

                                if (u.correo == null
                                                || u.correo.trim().isEmpty()) {

                                        res.status(400);

                                        return gson.toJson(
                                                        new ErrorResponse(
                                                                        "El correo es obligatorio"));
                                }

                                // VALIDAR PASSWORD

                                if (u.password == null
                                                || u.password.trim().isEmpty()) {

                                        res.status(400);

                                        return gson.toJson(
                                                        new ErrorResponse(
                                                                        "La contraseña es obligatoria"));
                                }

                                // VALIDAR LONGITUD PASSWORD

                                if (u.password.length() < 6) {

                                        res.status(400);

                                        return gson.toJson(
                                                        new ErrorResponse(
                                                                        "La contraseña debe tener mínimo 6 caracteres"));
                                }

                                // VALIDAR ROL

                                if (!u.rol.equals("ADMIN")
                                                && !u.rol.equals("MANAGER")
                                                && !u.rol.equals("EMPLEADO")) {

                                        res.status(400);

                                        return gson.toJson(
                                                        new ErrorResponse(
                                                                        "Rol inválido"));
                                }

                                UserDAO dao = new UserDAO();

                                boolean creado = dao.crearUsuario(u);

                                if (creado) {

                                        res.status(201);

                                        return gson.toJson(
                                                        new SuccessResponse("Usuario creado correctamente"));

                                } else {

                                        res.status(500);

                                        return gson.toJson(
                                                        new ErrorResponse("No se pudo crear usuario"));
                                }

                        } catch (Exception e) {

                                e.printStackTrace();

                                res.status(500);

                                return gson.toJson(
                                                new ErrorResponse("Error en servidor"));
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
                                                                "Acceso denegado"));
                        }

                        int id = Integer.parseInt(req.params(":id"));

                        User usuario = gson.fromJson(req.body(), User.class);

                        // VALIDACIONES

                        if (usuario.nombre == null
                                        || usuario.nombre.trim().isEmpty()) {

                                res.status(400);

                                return gson.toJson(
                                                new ErrorResponse(
                                                                "El nombre es obligatorio"));
                        }

                        if (usuario.correo == null
                                        || usuario.correo.trim().isEmpty()) {

                                res.status(400);

                                return gson.toJson(
                                                new ErrorResponse(
                                                                "El correo es obligatorio"));
                        }
                        if (usuario.password == null
                                        || usuario.password.trim().isEmpty()) {

                                res.status(400);

                                return gson.toJson(
                                                new ErrorResponse(
                                                                "La contraseña es obligatoria"));
                        }

                        if (usuario.password.length() < 6) {

                                res.status(400);

                                return gson.toJson(
                                                new ErrorResponse(
                                                                "La contraseña debe tener mínimo 6 caracteres"));
                        }
                        if (!usuario.rol.equals("ADMIN")
                                        && !usuario.rol.equals("MANAGER")
                                        && !usuario.rol.equals("EMPLEADO")) {

                                res.status(400);

                                return gson.toJson(
                                                new ErrorResponse(
                                                                "Rol inválido"));
                        }

                        UserDAO dao = new UserDAO();

                        boolean actualizado = dao.actualizarUsuario(id, usuario);

                        if (actualizado) {

                                return gson.toJson(
                                                new SuccessResponse(
                                                                "Usuario actualizado correctamente"));

                        } else {

                                res.status(500);

                                return gson.toJson(
                                                new ErrorResponse(
                                                                "No se pudo actualizar usuario"));
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
                                                                "Acceso denegado"));
                        }

                        int id = Integer.parseInt(req.params(":id"));

                        UserDAO dao = new UserDAO();

                        boolean eliminado = dao.eliminarUsuario(id);

                        if (eliminado) {

                                return gson.toJson(
                                                new SuccessResponse(
                                                                "Usuario eliminado correctamente"));

                        } else {

                                res.status(500);

                                return gson.toJson(
                                                new ErrorResponse(
                                                                "No se pudo eliminar usuario"));
                        }
                });

        }
}
