package routes;

import com.google.gson.Gson;

import dao.TaskDAO;

import java.util.List;

import model.response.ErrorResponse;
import model.response.SuccessResponse;
import model.Task;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

public class TaskRoutes {

    public static void init() {

        // CREAR
        post("/tareas", (req, res) -> {

            res.type("application/json");

            Gson gson = new Gson();

            try {

                Task tarea = gson.fromJson(
                        req.body(),
                        Task.class);

                if (tarea.proyecto_id <= 0) {

                    res.status(400);

                    return gson.toJson(
                            new ErrorResponse(
                                    "proyecto_id inválido"));
                }

                if (tarea.nombre == null
                        || tarea.nombre.trim().isEmpty()) {

                    res.status(400);

                    return gson.toJson(
                            new ErrorResponse(
                                    "El nombre es obligatorio"));
                }

                TaskDAO dao = new TaskDAO();

                boolean creado = dao.crearTarea(tarea);

                if (creado) {

                    res.status(201);

                    return gson.toJson(
                            new SuccessResponse(
                                    "Task creada correctamente"));
                }

                res.status(500);

                return gson.toJson(
                        new ErrorResponse(
                                "No se pudo crear Task"));

            } catch (Exception e) {

                e.printStackTrace();

                res.status(500);

                return gson.toJson(
                        new ErrorResponse(
                                "Error en servidor"));
            }
        });

        // LISTAR POR PROYECTO
        get("/proyectos/:id/tareas", (req, res) -> {

            res.type("application/json");

            Gson gson = new Gson();

            try {

                int proyectoId = Integer.parseInt(
                        req.params(":id"));

                boolean archivadas = Boolean.parseBoolean(
                        req.queryParams("archivadas"));

                TaskDAO dao = new TaskDAO();

                List<Task> lista = dao.listarPorProyecto(
                        proyectoId,
                        archivadas);

                return gson.toJson(lista);

            } catch (Exception e) {

                e.printStackTrace();

                res.status(500);

                return gson.toJson(
                        new ErrorResponse(
                                "Error al listar Tasks"));
            }
        });

        get("/tareas", (req, res) -> {
            res.type("application/json");

            Gson gson = new Gson();

            try {

                TaskDAO dao = new TaskDAO();

                List<Task> lista = dao.listarTareas();

                return gson.toJson(lista);

            } catch (Exception e) {

                e.printStackTrace();

                res.status(500);

                return gson.toJson(
                        new ErrorResponse(
                                "Error al obtener historial"));
            }
        });

        // OBTENER
        get("/tareas/:id", (req, res) -> {

            res.type("application/json");

            Gson gson = new Gson();

            try {

                int id = Integer.parseInt(
                        req.params(":id"));

                TaskDAO dao = new TaskDAO();

                Task tarea = dao.obtenerTarea(id);

                if (tarea == null) {

                    res.status(404);

                    return gson.toJson(
                            new ErrorResponse(
                                    "Task no encontrada"));
                }

                return gson.toJson(tarea);

            } catch (Exception e) {

                e.printStackTrace();

                res.status(500);

                return gson.toJson(
                        new ErrorResponse(
                                "Error en servidor"));
            }
        });

        // ACTUALIZAR
        put("/tareas/:id", (req, res) -> {

            res.type("application/json");

            Gson gson = new Gson();

            try {

                int id = Integer.parseInt(
                        req.params(":id"));

                Task tarea = gson.fromJson(
                        req.body(),
                        Task.class);

                TaskDAO dao = new TaskDAO();

                boolean actualizado = dao.actualizarTarea(
                        id,
                        tarea);

                if (actualizado) {

                    return gson.toJson(
                            new SuccessResponse(
                                    "Task actualizada correctamente"));
                }

                res.status(500);

                return gson.toJson(
                        new ErrorResponse(
                                "No se pudo actualizar Task"));

            } catch (Exception e) {

                e.printStackTrace();

                res.status(500);

                return gson.toJson(
                        new ErrorResponse(
                                "Error en servidor"));
            }
        });

        // ELIMINAR
        delete("/tareas/:id", (req, res) -> {

            res.type("application/json");

            Gson gson = new Gson();

            try {

                int id = Integer.parseInt(
                        req.params(":id"));

                TaskDAO dao = new TaskDAO();

                boolean eliminado = dao.eliminarTarea(id);

                if (eliminado) {

                    return gson.toJson(
                            new SuccessResponse(
                                    "Task eliminada correctamente"));
                }

                res.status(500);

                return gson.toJson(
                        new ErrorResponse(
                                "No se pudo eliminar Task"));

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