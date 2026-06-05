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
import org.mindrot.jbcrypt.BCrypt;
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

                        res.type("application/json");

                        String rol = req.attribute("rol");

                        if (!rol.equals("ADMIN")) {

                                res.status(403);

                                return gson.toJson(
                                                new ErrorResponse(
                                                                "Acceso denegado"));
                        }

                        try {

                                UserDAO dao = new UserDAO();

                                List<User> lista = dao.listarUsuarios();

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
                        if (req.body() == null || req.body().trim().isEmpty()) {

                                res.status(400);

                                return gson.toJson(
                                                new ErrorResponse(
                                                                "El body no puede estar vacío"));
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
                                if (!u.correo.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {

                                        res.status(400);

                                        return gson.toJson(
                                                        new ErrorResponse(
                                                                        "Formato de correo inválido"));
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
                                if (u.password.contains(" ")) {

                                        res.status(400);

                                        return gson.toJson(
                                                        new ErrorResponse(
                                                                        "La contraseña no puede contener espacios"));
                                }

                                // VALIDAR ROL

                                if (u.rol == null
                                || (!u.rol.equals("ADMIN")
                                                && !u.rol.equals("MANAGER")
                                                && !u.rol.equals("EMPLEADO"))) {

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

                        int id;

                        try {

                                id = Integer.parseInt(req.params(":id"));

                        } catch (NumberFormatException e) {

                                res.status(400);

                                return gson.toJson(
                                                new ErrorResponse(
                                                                "El ID debe ser numérico"));
                        }

                        if (id <= 0) {

                                res.status(400);

                                return gson.toJson(
                                                new ErrorResponse(
                                                                "ID de usuario inválido"));
                        }
                        if (req.body() == null || req.body().trim().isEmpty()) {

                                res.status(400);

                                return gson.toJson(
                                                new ErrorResponse(
                                                                "El body no puede estar vacío"));
                        }

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
                        if (!usuario.correo.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {

                                res.status(400);

                                return gson.toJson(
                                                new ErrorResponse(
                                                                "Formato de correo inválido"));
                        }
                        
                        if (usuario.rol == null
                                        || (!usuario.rol.equals("ADMIN")
                                        && !usuario.rol.equals("MANAGER")
                                        && !usuario.rol.equals("EMPLEADO"))) {

                                res.status(400);

                                return gson.toJson(
                                                new ErrorResponse(
                                                                "Rol inválido"));
                        }

                        UserDAO dao = new UserDAO();

                        boolean actualizado = dao.actualizarUsuarioDatos(id, usuario);

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
                put("/me", (req, res) -> {

                    res.type("application/json");

                    Gson gson = new Gson();

                    int usuarioId = req.attribute("usuario_id");

                    if (req.body() == null || req.body().trim().isEmpty()) {

                        res.status(400);

                        return gson.toJson(
                                new ErrorResponse("El body no puede estar vacío"));
                    }

                    User usuario = gson.fromJson(req.body(), User.class);

                    if (usuario.nombre == null || usuario.nombre.trim().isEmpty()) {

                        res.status(400);

                        return gson.toJson(
                                new ErrorResponse("El nombre es obligatorio"));
                    }

                    if (usuario.correo == null || usuario.correo.trim().isEmpty()) {

                        res.status(400);

                        return gson.toJson(
                                new ErrorResponse("El correo es obligatorio"));
                    }

                    if (!usuario.correo.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {

                        res.status(400);

                        return gson.toJson(
                                new ErrorResponse("Formato de correo inválido"));
                    }

                    UserDAO dao = new UserDAO();

                    boolean actualizado = dao.actualizarMiPerfil(usuarioId, usuario);

                    if (actualizado) {

                        return gson.toJson(
                                new SuccessResponse("Perfil actualizado correctamente"));
                    }

                    res.status(500);

                    return gson.toJson(
                            new ErrorResponse("No se pudo actualizar el perfil"));
                });
                put("/me/password", (req, res) -> {

    res.type("application/json");

    Gson gson = new Gson();

    int usuarioId = req.attribute("usuario_id");

    if (req.body() == null || req.body().trim().isEmpty()) {
        res.status(400);
        return gson.toJson(new ErrorResponse("El body no puede estar vacío"));
    }

    try {
        com.google.gson.JsonObject data =
                gson.fromJson(req.body(), com.google.gson.JsonObject.class);

        String passwordActual = data.get("passwordActual").getAsString();
        String nuevaPassword = data.get("nuevaPassword").getAsString();
        String confirmarPassword = data.get("confirmarPassword").getAsString();

        if (passwordActual == null || passwordActual.trim().isEmpty()) {
            res.status(400);
            return gson.toJson(new ErrorResponse("La contraseña actual es obligatoria"));
        }

        if (nuevaPassword == null || nuevaPassword.trim().isEmpty()) {
            res.status(400);
            return gson.toJson(new ErrorResponse("La nueva contraseña es obligatoria"));
        }

        if (nuevaPassword.length() < 6) {
            res.status(400);
            return gson.toJson(new ErrorResponse("La nueva contraseña debe tener mínimo 6 caracteres"));
        }

        if (nuevaPassword.contains(" ")) {
            res.status(400);
            return gson.toJson(new ErrorResponse("La nueva contraseña no puede contener espacios"));
        }

        if (!nuevaPassword.equals(confirmarPassword)) {
            res.status(400);
            return gson.toJson(new ErrorResponse("La confirmación no coincide"));
        }

        UserDAO dao = new UserDAO();

        String hashActual = dao.obtenerPasswordHash(usuarioId);

        if (hashActual == null || !BCrypt.checkpw(passwordActual.trim(), hashActual)) {
            res.status(400);
            return gson.toJson(new ErrorResponse("La contraseña actual es incorrecta"));
        }

        boolean actualizado = dao.actualizarMiPassword(usuarioId, nuevaPassword);

        if (actualizado) {
            return gson.toJson(new SuccessResponse("Contraseña actualizada correctamente"));
        }

        res.status(500);
        return gson.toJson(new ErrorResponse("No se pudo actualizar la contraseña"));

    } catch (Exception e) {
        e.printStackTrace();
        res.status(500);
        return gson.toJson(new ErrorResponse("Error en servidor"));
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

                        int id;

                        try {

                                id = Integer.parseInt(req.params(":id"));

                        } catch (NumberFormatException e) {

                                res.status(400);

                                return gson.toJson(
                                                new ErrorResponse(
                                                                "El ID debe ser numérico"));
                        }

                        if (id <= 0) {

                                res.status(400);

                                return gson.toJson(
                                                new ErrorResponse(
                                                                "ID de usuario inválido"));
                        }

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
