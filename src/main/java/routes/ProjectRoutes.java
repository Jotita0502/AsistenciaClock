/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package routes;

import com.google.gson.Gson;

import dao.ProjectDAO;

import java.util.List;

import model.Project;
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
public class ProjectRoutes {

        public static void init() {

                get("/proyectos", (req, res) -> {

                        Gson gson = new Gson();

                        try {

                                ProjectDAO dao = new ProjectDAO();

                                List<Project> lista = dao.listarProyectos();

                                res.type("application/json");

                                return gson.toJson(lista);

                        } catch (Exception e) {

                                e.printStackTrace();

                                res.status(500);

                                return gson.toJson(
                                                new ErrorResponse("Error al listar proyectos"));
                        }
                });
                get("/proyectos/archivados", (req, res) -> {

                    Gson gson = new Gson();

                    try {

                        ProjectDAO dao = new ProjectDAO();

                        List<Project> lista =
                                dao.listarProyectosArchivados();

                        res.type("application/json");

                        return gson.toJson(lista);

                    } catch (Exception e) {

                        e.printStackTrace();

                        res.status(500);

                        return gson.toJson(
                                new ErrorResponse(
                                        "Error al listar proyectos archivados"));
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
                                                                "Acceso denegado"));
                        }
                        if (req.body() == null || req.body().trim().isEmpty()) {

                                res.status(400);

                                return gson.toJson(
                                                new ErrorResponse(
                                                                "El body no puede estar vacío"));
                        }
                        try {

                                Project proyecto = gson.fromJson(req.body(), Project.class);

                                // VALIDACIONES

                                if (proyecto.nombre == null
                                                || proyecto.nombre.trim().isEmpty()) {

                                        res.status(400);

                                        return gson.toJson(
                                                        new ErrorResponse(
                                                                        "El nombre del proyecto es obligatorio"));
                                }

                                if (proyecto.color == null
                                                || proyecto.color.trim().isEmpty()) {

                                        res.status(400);

                                        return gson.toJson(
                                                        new ErrorResponse(
                                                                        "El color es obligatorio"));
                                }

                                if (!proyecto.color.matches("^#([A-Fa-f0-9]{6})$")) {

                                        res.status(400);

                                        return gson.toJson(
                                                        new ErrorResponse(
                                                                        "El color debe tener formato hexadecimal. Ejemplo: #FF5733"));
                                }

                                if (proyecto.workspace_id <= 0) {

                                        res.status(400);

                                        return gson.toJson(
                                                        new ErrorResponse(
                                                                        "workspace_id inválido"));
                                }
                                if (proyecto.billable == null) {

                                        res.status(400);

                                        return gson.toJson(
                                                        new ErrorResponse(
                                                                        "El campo billable es obligatorio"));
                                }

                                ProjectDAO dao = new ProjectDAO();

                                boolean creado = dao.crearProyecto(proyecto);

                                if (creado) {

                                        return gson.toJson(
                                                        new SuccessResponse(
                                                                        "Proyecto creado correctamente"));

                                } else {

                                        res.status(500);

                                        return gson.toJson(
                                                        new ErrorResponse(
                                                                        "No se pudo crear proyecto"));
                                }

                        } catch (Exception e) {

                                e.printStackTrace();

                                res.status(500);

                                return gson.toJson(
                                                new ErrorResponse(
                                                                "Error en servidor"));
                        }
                });
                put("/proyectos/:id/restaurar", (req, res) -> {
                    res.type("application/json");

                    Gson gson = new Gson();

                    String rol = req.attribute("rol");

                    if (!rol.equals("ADMIN") && !rol.equals("MANAGER")) {
                        res.status(403);
                        return gson.toJson(new ErrorResponse("Acceso denegado"));
                    }

                    int id;

                    try {
                        id = Integer.parseInt(req.params(":id"));
                    } catch (NumberFormatException e) {
                        res.status(400);
                        return gson.toJson(new ErrorResponse("El ID debe ser numérico"));
                    }

                    if (id <= 0) {
                        res.status(400);
                        return gson.toJson(new ErrorResponse("ID de proyecto inválido"));
                    }

                    ProjectDAO dao = new ProjectDAO();

                    boolean restaurado = dao.restaurarProyecto(id);

                    if (restaurado) {
                        return gson.toJson(
                            new SuccessResponse("Proyecto restaurado correctamente")
                        );
                    }

                    res.status(500);
                    return gson.toJson(new ErrorResponse("No se pudo restaurar proyecto"));
                });
                put("/proyectos/:id", (req, res) -> {

                        res.type("application/json");
                        
                        Gson gson = new Gson();
                        int id = Integer.parseInt(req.params(":id"));
                        
                        if (id <= 0) {

                                res.status(400);

                                return gson.toJson(
                                                new ErrorResponse(
                                                                "ID de proyecto inválido"));
                        }

                        
                        String rol = req.attribute("rol");

                        if (!rol.equals("ADMIN")
                                        && !rol.equals("MANAGER")) {

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
                        
                        Project proyecto = gson.fromJson(req.body(), Project.class);

                        if (proyecto.nombre == null
                                        || proyecto.nombre.trim().isEmpty()) {

                                res.status(400);

                                return gson.toJson(
                                                new ErrorResponse(
                                                                "El nombre del proyecto es obligatorio"));
                        }

                        if (proyecto.color == null
                                        || proyecto.color.trim().isEmpty()) {

                                res.status(400);

                                return gson.toJson(
                                                new ErrorResponse(
                                                                "El color es obligatorio"));
                        }

                        if (!proyecto.color.matches("^#([A-Fa-f0-9]{6})$")) {

                                res.status(400);

                                return gson.toJson(
                                                new ErrorResponse(
                                                                "El color debe tener formato hexadecimal. Ejemplo: #FF5733"));
                        }
                        if (proyecto.billable == null) {

                                res.status(400);

                                return gson.toJson(
                                                new ErrorResponse(
                                                                "El campo billable es obligatorio"));
                        }
                        

                        ProjectDAO dao = new ProjectDAO();

                        boolean actualizado = dao.actualizarProyecto(id, proyecto);

                        if (actualizado) {

                                return gson.toJson(
                                                new SuccessResponse(
                                                                "Proyecto actualizado correctamente"));

                        } else {

                                res.status(500);

                                return gson.toJson(
                                                new ErrorResponse(
                                                                "No se pudo actualizar proyecto"));
                        }
                });
                delete("/proyectos/:id", (req, res) -> {

                        res.type("application/json");

                        Gson gson = new Gson();

                        String rol = req.attribute("rol");

                        if (!rol.equals("ADMIN") && !rol.equals("MANAGER")) {

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
                                                                "ID de proyecto inválido"));
                        }

                        ProjectDAO dao = new ProjectDAO();

                        boolean eliminado = dao.eliminarProyecto(id);

                        if (eliminado) {

                                return gson.toJson(
                                                new SuccessResponse(
                                                                "Proyecto archivado correctamente"));

                        } else {

                                res.status(500);

                                return gson.toJson(
                                                new ErrorResponse(
                                                                "No se pudo eliminar proyecto"));
                        }
                });
        }
}
