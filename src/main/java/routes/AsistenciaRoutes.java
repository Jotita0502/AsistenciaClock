/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package routes;

import com.google.gson.Gson;
import database.AsistenciaDAO;
import java.util.List;
import model.Asistencia;
import model.ErrorResponse;
import model.MarcacionRequest;
import model.SuccessResponse;
import static spark.Spark.get;
import static spark.Spark.post;

/**
 *
 * @author USUARIO
 */
public class AsistenciaRoutes {
    
    public static void init(){
        
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

                int usuarioId =
                        req.attribute("usuario_id");

                String fecha =
                        req.queryParams("fecha");

                String idProyecto =
                        req.queryParams("id_proyecto");

                String limit =
                        req.queryParams("limit");

                String offset =
                        req.queryParams("offset");

                int limitInt = 10;

                int offsetInt = 0;

                if (limit != null
                        && !limit.trim().isEmpty()) {

                    limitInt =
                            Integer.parseInt(limit.trim());
                }

                if (offset != null
                        && !offset.trim().isEmpty()) {

                    offsetInt =
                            Integer.parseInt(offset.trim());
                }

                Integer proyectoIdInt = null;

                if (idProyecto != null
                        && !idProyecto.trim().isEmpty()) {

                    proyectoIdInt =
                            Integer.parseInt(idProyecto);
                }

                AsistenciaDAO dao =
                        new AsistenciaDAO();

                List<Asistencia> lista =
                        dao.listarAsistencias(
                                usuarioId,
                                fecha,
                                proyectoIdInt,
                                limitInt,
                                offsetInt
                        );

                res.type("application/json");

                return gson.toJson(lista);

            } catch (Exception e) {

                e.printStackTrace();

                res.status(500);

                return new Gson().toJson(
                        new ErrorResponse(
                                "Error al listar"
                        )
                );
            }
        });
            
    }
}
