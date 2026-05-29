package routes;

import com.google.gson.Gson;

import dao.TimerDAO;

import java.util.List;

import model.Timer;
import model.response.ErrorResponse;
import model.response.SuccessResponse;

import static spark.Spark.get;
import static spark.Spark.post;

public class TimerRoutes {

        public static void init() {

                // INICIAR TIMER
                post("/timer/start", (req, res) -> {

                        res.type("application/json");

                        Gson gson = new Gson();

                        try {
                                if (req.body() == null || req.body().trim().isEmpty()) {

                                        res.status(400);

                                        return gson.toJson(
                                                        new ErrorResponse(
                                                                        "El body no puede estar vacío"));
                                }
                                Timer asistencia = gson.fromJson(
                                                req.body(),
                                                Timer.class);
                                if (asistencia.workspace_id <= 0) {

                                        res.status(400);

                                        return gson.toJson(
                                                        new ErrorResponse(
                                                                        "workspace_id inválido"));
                                }

                                if (asistencia.proyecto_id <= 0) {

                                        res.status(400);

                                        return gson.toJson(
                                                        new ErrorResponse(
                                                                        "proyecto_id inválido"));
                                }

                                if (asistencia.descripcion == null
                                                || asistencia.descripcion.trim().isEmpty()) {

                                        res.status(400);

                                        return gson.toJson(
                                                        new ErrorResponse(
                                                                        "La descripción es obligatoria"));
                                }
                                int usuarioId = req.attribute("usuario_id");

                                asistencia.usuario_id = usuarioId;

                                TimerDAO dao = new TimerDAO();

                                boolean iniciado = dao.iniciarTimer(asistencia);

                                if (iniciado) {

                                        return gson.toJson(
                                                        new SuccessResponse(
                                                                        "Timer iniciado correctamente"));

                                } else {

                                        res.status(400);

                                        return gson.toJson(
                                                        new ErrorResponse(
                                                                        "No se pudo iniciar timer"));
                                }

                        } catch (Exception e) {

                                e.printStackTrace();

                                res.status(500);

                                return gson.toJson(
                                                new ErrorResponse(
                                                                "Error en servidor"));
                        }
                });

                // DETENER TIMER
                post("/timer/stop", (req, res) -> {

                        res.type("application/json");

                        Gson gson = new Gson();

                        try {

                                int usuarioId = req.attribute("usuario_id");

                                TimerDAO dao = new TimerDAO();

                                boolean detenido = dao.detenerTimer(usuarioId);

                                if (detenido) {

                                        return gson.toJson(
                                                        new SuccessResponse(
                                                                        "Timer detenido correctamente"));

                                } else {

                                        res.status(400);

                                        return gson.toJson(
                                                        new ErrorResponse(
                                                                        "No existe timer activo"));
                                }

                        } catch (Exception e) {

                                e.printStackTrace();

                                res.status(500);

                                return gson.toJson(
                                                new ErrorResponse(
                                                                "Error en servidor"));
                        }
                });

                // HISTORIAL
                get("/timer/historial", (req, res) -> {

                        res.type("application/json");

                        Gson gson = new Gson();

                        try {

                                int usuarioId = req.attribute("usuario_id");

                                TimerDAO dao = new TimerDAO();

                                List<Timer> lista = dao.historialTimers(usuarioId);

                                return gson.toJson(lista);

                        } catch (Exception e) {

                                e.printStackTrace();

                                res.status(500);

                                return gson.toJson(
                                                new ErrorResponse(
                                                                "Error al obtener historial"));
                        }
                });

                // TIMER ACTIVO
                get("/timer/active", (req, res) -> {

                        res.type("application/json");

                        Gson gson = new Gson();

                        try {

                                int usuarioId = req.attribute("usuario_id");

                                TimerDAO dao = new TimerDAO();

                                Timer timer = dao.obtenerTimerActivo(usuarioId);

                                if (timer == null) {

                                        res.status(404);

                                        return gson.toJson(
                                                        new ErrorResponse(
                                                                        "No hay timer activo"));
                                }

                                return gson.toJson(timer);

                        } catch (Exception e) {

                                e.printStackTrace();

                                res.status(500);

                                return gson.toJson(
                                                new ErrorResponse(
                                                                "Error al obtener timer activo"));
                        }
                });
        }
}