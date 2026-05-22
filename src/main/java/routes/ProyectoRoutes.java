/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package routes;

import com.google.gson.Gson;

import dao.ProyectoDAO;

import java.util.List;
import model.ErrorResponse;
import model.Proyecto;
import model.SuccessResponse;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

/**
 *
 * @author USUARIO
 */
public class ProyectoRoutes {
    
    public static void init(){
        
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
    }
}
