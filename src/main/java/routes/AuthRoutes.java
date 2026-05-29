/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package routes;

import com.google.gson.Gson;

import dao.LoginDAO;
import model.User;
import model.response.ErrorResponse;
import model.response.LoginRequest;
import model.response.LoginResponse;

import static spark.Spark.*;
import utils.JwtUtil;

/**
 *
 * @author USUARIO
 */
public class AuthRoutes {
    public static void init() {
        post("/login", (req, res) -> {

            try {
                Gson gson = new Gson();
                res.type("application/json");
                if (req.body() == null || req.body().trim().isEmpty()) {

                    res.status(400);

                    return gson.toJson(
                            new ErrorResponse(
                                    "El body no puede estar vacío"));
                }
                LoginRequest data = gson.fromJson(req.body(), LoginRequest.class);

                if (data == null
                        || data.correo == null
                        || data.correo.trim().isEmpty()) {

                    res.status(400);

                    return gson.toJson(
                            new ErrorResponse(
                                    "El correo es obligatorio"));
                }

                if (data.password == null
                        || data.password.trim().isEmpty()) {

                    res.status(400);

                    return gson.toJson(
                            new ErrorResponse(
                                    "La contraseña es obligatoria"));
                }
                
                if (!data.correo.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

                    res.status(400);

                    return gson.toJson(
                            new ErrorResponse(
                                    "Formato de correo inválido"));
                }
                User user = LoginDAO.login(data.correo, data.password);

                if (user != null) {
                    String token = JwtUtil.generarToken(
                            user.id,
                            user.correo,
                            user.rol);
                    LoginResponse response = new LoginResponse(token, user);

                    return gson.toJson(response);
                } else {
                    res.status(401);
                    return gson.toJson(new ErrorResponse("Credenciales incorrectas"));
                }

            } catch (Exception e) {
                e.printStackTrace();
                res.status(500);
                return new Gson().toJson(new ErrorResponse("Error en servidor"));
            }
        });
    }
}
