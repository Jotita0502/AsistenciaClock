/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package routes;

import com.google.gson.Gson;

import dao.AsistenciaDAO;

import java.util.List;
import model.Asistencia;
import model.ErrorResponse;
import model.SuccessResponse;
import static spark.Spark.get;
import static spark.Spark.post;

/**
 *
 * @author USUARIO
 */
public class TimerRoutes {
    
    public static void init(){
        
        
           
            post("/timer/iniciar", (req, res) -> {

            res.type("application/json");

            Gson gson = new Gson();

            Asistencia asistencia = gson.fromJson(
                req.body(),
                Asistencia.class
            );

            int usuarioId =
                    req.attribute("usuario_id");

            asistencia.usuario_id = usuarioId;

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

            int usuarioId =
                    req.attribute("usuario_id");

            AsistenciaDAO dao = new AsistenciaDAO();

            boolean detenido =
                    dao.detenerTimer(usuarioId);

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

            int usuarioId =
                    req.attribute("usuario_id");

            AsistenciaDAO dao = new AsistenciaDAO();

            List<Asistencia> lista =
                    dao.historialTimers(usuarioId);

            return gson.toJson(lista);
        });
            get("/timer/activo", (req, res) -> {

            res.type("application/json");

            Gson gson = new Gson();

            int usuarioId =
                    req.attribute("usuario_id");

            AsistenciaDAO dao = new AsistenciaDAO();

            Asistencia timer =
                    dao.obtenerTimerActivo(usuarioId);

            if (timer == null) {

                return gson.toJson(
                        new ErrorResponse(
                                "No hay timer activo"
                        )
                );
            }

            return gson.toJson(timer);
        });
    }
}
