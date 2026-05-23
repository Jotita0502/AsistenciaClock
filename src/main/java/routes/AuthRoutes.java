/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package routes;

import com.google.gson.Gson;

import dao.LoginDAO;
import model.ErrorResponse;
import model.LoginRequest;
import model.LoginResponse;
import model.Usuario;
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
                LoginRequest data = gson.fromJson(req.body(), LoginRequest.class);

                if (data == null || data.correo == null || data.password == null
                        || data.correo.isEmpty() || data.password.isEmpty()) {
                    res.status(400);
                    return gson.toJson(new ErrorResponse("Faltan datos de login"));
                }
                Usuario user = LoginDAO.login(data.correo, data.password);

                res.type("application/json");

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
