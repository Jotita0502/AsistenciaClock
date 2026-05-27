package routes;

import com.google.gson.Gson;

import dao.TagDAO;

import java.util.List;

import model.response.ErrorResponse;
import model.response.SuccessResponse;
import model.Tag;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

public class TagRoutes {

    public static void init() {

        // CREAR
        post("/tags", (req, res) -> {

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

                Tag tag = gson.fromJson(
                        req.body(),
                        Tag.class);

                if (tag.workspace_id <= 0) {

                    res.status(400);

                    return gson.toJson(
                            new ErrorResponse(
                                    "workspace_id inválido"));
                }

                if (tag.nombre == null
                        || tag.nombre.trim().isEmpty()) {

                    res.status(400);

                    return gson.toJson(
                            new ErrorResponse(
                                    "El nombre es obligatorio"));
                }

                if (tag.color == null
                        || tag.color.trim().isEmpty()) {

                    tag.color = "#1D9E75";
                }
                if (!tag.color.matches("^#([A-Fa-f0-9]{6})$")) {

                    res.status(400);

                    return gson.toJson(
                            new ErrorResponse(
                                    "El color debe tener formato hexadecimal. Ejemplo: #1D9E75"));
                }

                TagDAO dao = new TagDAO();

                boolean creado = dao.crearTag(tag);

                if (creado) {

                    res.status(201);

                    return gson.toJson(
                            new SuccessResponse(
                                    "Etiqueta creada correctamente"));
                }

                res.status(500);

                return gson.toJson(
                        new ErrorResponse(
                                "No se pudo crear etiqueta"));

            } catch (Exception e) {

                e.printStackTrace();

                res.status(500);

                return gson.toJson(
                        new ErrorResponse(
                                "Error en servidor"));
            }
        });

        // LISTAR
        get("/workspaces/:id/tags", (req, res) -> {

            res.type("application/json");

            Gson gson = new Gson();

            try {

                int workspaceId;

                try {

                    workspaceId = Integer.parseInt(req.params(":id"));

                } catch (NumberFormatException e) {

                    res.status(400);

                    return gson.toJson(
                            new ErrorResponse("El ID debe ser numérico"));
                }

                if (workspaceId <= 0) {

                    res.status(400);

                    return gson.toJson(
                            new ErrorResponse("ID de workspace inválido"));
                }
                TagDAO dao = new TagDAO(); 
                List<Tag> lista = dao.listarTags(workspaceId); 
                return gson.toJson(lista);

            } catch (Exception e) {

                e.printStackTrace();

                res.status(500);

                return gson.toJson(
                        new ErrorResponse(
                                "Error al listar etiquetas"));
            }
        });

        // OBTENER
        get("/tags/:id", (req, res) -> {

            res.type("application/json");

            Gson gson = new Gson();

            try {

               int id;

                try {

                    id = Integer.parseInt(req.params(":id"));

                } catch (NumberFormatException e) {

                    res.status(400);

                    return gson.toJson(
                            new ErrorResponse("El ID debe ser numérico"));
                }

                if (id <= 0) {

                    res.status(400);

                    return gson.toJson(
                            new ErrorResponse("ID de etiqueta inválido"));
                }

                TagDAO dao = new TagDAO();

                Tag tag = dao.obtenerTag(id);

                if (tag == null) {

                    res.status(404);

                    return gson.toJson(
                            new ErrorResponse(
                                    "Etiqueta no encontrada"));
                }

                return gson.toJson(tag);

            } catch (Exception e) {

                e.printStackTrace();

                res.status(500);

                return gson.toJson(
                        new ErrorResponse(
                                "Error en servidor"));
            }
        });

        // ACTUALIZAR
        put("/tags/:id", (req, res) -> {

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

            int id;

            try {

                id = Integer.parseInt(
                        req.params(":id"));

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
                                "ID de etiqueta inválido"));
            }

            if (req.body() == null || req.body().trim().isEmpty()) {

                res.status(400);

                return gson.toJson(
                        new ErrorResponse(
                                "El body no puede estar vacío"));
            }

            try {

                Tag tag = gson.fromJson(
                        req.body(),
                        Tag.class);

                if (tag.nombre == null
                        || tag.nombre.trim().isEmpty()) {

                    res.status(400);

                    return gson.toJson(
                            new ErrorResponse(
                                    "El nombre es obligatorio"));
                }

                if (tag.color == null
                        || tag.color.trim().isEmpty()) {

                    res.status(400);

                    return gson.toJson(
                            new ErrorResponse(
                                    "El color es obligatorio"));
                }

                if (!tag.color.matches("^#([A-Fa-f0-9]{6})$")) {

                    res.status(400);

                    return gson.toJson(
                            new ErrorResponse(
                                    "El color debe tener formato hexadecimal. Ejemplo: #1D9E75"));
                }

                TagDAO dao = new TagDAO();

                boolean actualizado = dao.actualizarTag(
                        id,
                        tag);

                if (actualizado) {

                    return gson.toJson(
                            new SuccessResponse(
                                    "Etiqueta actualizada correctamente"));
                }

                res.status(500);

                return gson.toJson(
                        new ErrorResponse(
                                "No se pudo actualizar etiqueta"));

            } catch (Exception e) {

                e.printStackTrace();

                res.status(500);

                return gson.toJson(
                        new ErrorResponse(
                                "Error en servidor"));
            }
        });

        // ELIMINAR
        delete("/tags/:id", (req, res) -> {

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

            int id;

            try {

                id = Integer.parseInt(
                        req.params(":id"));

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
                                "ID de etiqueta inválido"));
            }

            try {

                TagDAO dao = new TagDAO();

                boolean eliminado = dao.eliminarTag(id);

                if (eliminado) {

                    return gson.toJson(
                            new SuccessResponse(
                                    "Etiqueta eliminada correctamente"));
                }

                res.status(500);

                return gson.toJson(
                        new ErrorResponse(
                                "No se pudo eliminar etiqueta"));

            } catch (Exception e) {

                e.printStackTrace();

                res.status(500);

                return gson.toJson(
                        new ErrorResponse(
                                "Error en servidor"));
            }
        });

        // AGREGAR ETIQUETA A REGISTRO
        post("/registros/:registroId/tags/:tagId", (req, res) -> {

            res.type("application/json");

            Gson gson = new Gson();

            try {

                int registroId = Integer.parseInt(
                        req.params(":registroId"));

                int tagId = Integer.parseInt(
                        req.params(":tagId"));

                TagDAO dao = new TagDAO();

                boolean agregado = dao.agregarEtiquetaRegistro(
                        registroId,
                        tagId);

                if (agregado) {

                    return gson.toJson(
                            new SuccessResponse(
                                    "Etiqueta agregada al registro"));
                }

                res.status(500);

                return gson.toJson(
                        new ErrorResponse(
                                "No se pudo agregar etiqueta"));

            } catch (Exception e) {

                e.printStackTrace();

                res.status(500);

                return gson.toJson(
                        new ErrorResponse(
                                "Error en servidor"));
            }
        });

        // QUITAR ETIQUETA
        delete("/registros/:registroId/tags/:tagId", (req, res) -> {

                res.type("application/json");

                Gson gson = new Gson();

                int registroId;

                try {

                    registroId = Integer.parseInt(
                            req.params(":registroId"));

                } catch (NumberFormatException e) {

                    res.status(400);

                    return gson.toJson(
                            new ErrorResponse(
                                    "El ID del registro debe ser numérico"));
                }

                if (registroId <= 0) {

                    res.status(400);

                    return gson.toJson(
                            new ErrorResponse(
                                    "ID de registro inválido"));
                }

                int tagId;

                try {

                    tagId = Integer.parseInt(
                            req.params(":tagId"));

                } catch (NumberFormatException e) {

                    res.status(400);

                    return gson.toJson(
                            new ErrorResponse(
                                    "El ID de etiqueta debe ser numérico"));
                }

                if (tagId <= 0) {

                    res.status(400);

                    return gson.toJson(
                            new ErrorResponse(
                                    "ID de etiqueta inválido"));
                }

                try {

                    TagDAO dao = new TagDAO();

                    boolean eliminado = dao.quitarEtiquetaRegistro(
                            registroId,
                            tagId);

                    if (eliminado) {

                        return gson.toJson(
                                new SuccessResponse(
                                        "Etiqueta removida del registro"));
                    }

                    res.status(500);

                    return gson.toJson(
                            new ErrorResponse(
                                    "No se pudo remover etiqueta"));

                } catch (Exception e) {

                    e.printStackTrace();

                    res.status(500);

                    return gson.toJson(
                            new ErrorResponse(
                                    "Error en servidor"));
                }
            });

        // ETIQUETAS POR REGISTRO
        get("/registros/:id/tags", (req, res) -> {

            res.type("application/json");

            Gson gson = new Gson();

            try {

                int registroId;

                try {

                    registroId = Integer.parseInt(req.params(":id"));

                } catch (NumberFormatException e) {

                    res.status(400);

                    return gson.toJson(
                            new ErrorResponse("El ID debe ser numérico"));
                }

                if (registroId <= 0) {

                    res.status(400);

                    return gson.toJson(
                            new ErrorResponse("ID de registro inválido"));
                }

                TagDAO dao = new TagDAO();

                List<Tag> lista = dao.etiquetasPorRegistro(
                        registroId);

                return gson.toJson(lista);

            } catch (Exception e) {

                e.printStackTrace();

                res.status(500);

                return gson.toJson(
                        new ErrorResponse(
                                "Error al listar etiquetas"));
            }
        });
    }
}