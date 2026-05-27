package routes;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import java.util.List;

import com.google.gson.Gson;

import dao.WorkspaceDAO;
import model.Workspace;
import model.response.ErrorResponse;
import model.response.SuccessResponse;

public class WorkspaceRoutes {

        public static void init() {

                // LISTAR
                get("/workspaces", (req, res) -> {

                        res.type("application/json");

                        Gson gson = new Gson();

                        try {

                                WorkspaceDAO dao = new WorkspaceDAO();

                                List<Workspace> lista = dao.listarWorkspaces();

                                return gson.toJson(lista);

                        } catch (Exception e) {

                                e.printStackTrace();

                                res.status(500);

                                return gson.toJson(
                                                new ErrorResponse(
                                                                "Error al listar workspaces"));
                        }
                });

                // CREAR
                post("/workspaces", (req, res) -> {

                        res.type("application/json");

                        Gson gson = new Gson();

                        String rol = req.attribute("rol");

                        if (rol == null
                                        || (!rol.equals("ADMIN")
                                                        && !rol.equals("MANAGER"))) {

                                res.status(403);

                                return gson.toJson(
                                                new ErrorResponse(
                                                                "Acceso denegado"));
                        }

                        try {

                                Workspace workspace = gson.fromJson(
                                                req.body(),
                                                Workspace.class);

                                // VALIDACIONES

                                if (workspace.nombre == null
                                                || workspace.nombre.trim().isEmpty()) {

                                        res.status(400);

                                        return gson.toJson(
                                                        new ErrorResponse(
                                                                        "El nombre del workspace es obligatorio"));
                                }

                                if (workspace.descripcion == null
                                                || workspace.descripcion.trim().isEmpty()) {

                                        res.status(400);

                                        return gson.toJson(
                                                        new ErrorResponse(
                                                                        "La descripción del workspace es obligatoria"));
                                }

                                if (workspace.owner_id <= 0) {

                                        res.status(400);

                                        return gson.toJson(
                                                        new ErrorResponse(
                                                                        "owner_id inválido"));
                                }

                                WorkspaceDAO dao = new WorkspaceDAO();

                                boolean creado = dao.crearWorkspace(workspace);

                                if (creado) {

                                        res.status(201);

                                        return gson.toJson(
                                                        new SuccessResponse(
                                                                        "Workspace creado correctamente"));

                                } else {

                                        res.status(500);

                                        return gson.toJson(
                                                        new ErrorResponse(
                                                                        "No se pudo crear workspace"));
                                }

                        } catch (Exception e) {

                                e.printStackTrace();

                                res.status(500);

                                return gson.toJson(
                                                new ErrorResponse(
                                                                "Error en servidor"));
                        }
                });

                // ACTUALIZAR
                put("/workspaces/:id", (req, res) -> {

                        res.type("application/json");

                        Gson gson = new Gson();

                        String rol = req.attribute("rol");

                        if (rol == null
                                        || (!rol.equals("ADMIN")
                                                        && !rol.equals("MANAGER"))) {

                                res.status(403);

                                return gson.toJson(
                                                new ErrorResponse(
                                                                "Acceso denegado"));
                        }

                        try {

                                int id = Integer.parseInt(
                                                req.params(":id"));

                                Workspace workspace = gson.fromJson(
                                                req.body(),
                                                Workspace.class);

                                // VALIDACIONES

                                if (workspace.nombre == null
                                                || workspace.nombre.trim().isEmpty()) {

                                        res.status(400);

                                        return gson.toJson(
                                                        new ErrorResponse(
                                                                        "El nombre del workspace es obligatorio"));
                                }

                                if (workspace.descripcion == null
                                                || workspace.descripcion.trim().isEmpty()) {

                                        res.status(400);

                                        return gson.toJson(
                                                        new ErrorResponse(
                                                                        "La descripción del workspace es obligatoria"));
                                }

                                if (workspace.owner_id <= 0) {

                                        res.status(400);

                                        return gson.toJson(
                                                        new ErrorResponse(
                                                                        "owner_id inválido"));
                                }

                                WorkspaceDAO dao = new WorkspaceDAO();

                                boolean actualizado = dao.actualizarWorkspace(
                                                id,
                                                workspace);

                                if (actualizado) {

                                        return gson.toJson(
                                                        new SuccessResponse(
                                                                        "Workspace actualizado correctamente"));

                                } else {

                                        res.status(500);

                                        return gson.toJson(
                                                        new ErrorResponse(
                                                                        "No se pudo actualizar workspace"));
                                }

                        } catch (NumberFormatException e) {

                                res.status(400);

                                return gson.toJson(
                                                new ErrorResponse(
                                                                "ID inválido"));

                        } catch (Exception e) {

                                e.printStackTrace();

                                res.status(500);

                                return gson.toJson(
                                                new ErrorResponse(
                                                                "Error en servidor"));
                        }
                });

                // ELIMINAR
                delete("/workspaces/:id", (req, res) -> {

                        res.type("application/json");

                        Gson gson = new Gson();

                        String rol = req.attribute("rol");

                        if (rol == null
                                        || !rol.equals("ADMIN")) {

                                res.status(403);

                                return gson.toJson(
                                                new ErrorResponse(
                                                                "Acceso denegado"));
                        }

                        try {

                                int id = Integer.parseInt(
                                                req.params(":id"));

                                WorkspaceDAO dao = new WorkspaceDAO();

                                boolean eliminado = dao.eliminarWorkspace(id);

                                if (eliminado) {

                                        return gson.toJson(
                                                        new SuccessResponse(
                                                                        "Workspace eliminado correctamente"));

                                } else {

                                        res.status(500);

                                        return gson.toJson(
                                                        new ErrorResponse(
                                                                        "No se pudo eliminar workspace"));
                                }

                        } catch (NumberFormatException e) {

                                res.status(400);

                                return gson.toJson(
                                                new ErrorResponse(
                                                                "ID inválido"));

                        } catch (Exception e) {

                                e.printStackTrace();

                                res.status(500);

                                return gson.toJson(
                                                new ErrorResponse(
                                                                "Error en servidor"));
                        }
                });
        }
}