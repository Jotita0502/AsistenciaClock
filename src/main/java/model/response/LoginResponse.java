package model.response;

import model.User;

public class LoginResponse {

    public String token;
    public User usuario;

    public LoginResponse(String token, User usuario) {
        this.token = token;
        this.usuario = usuario;
    }
}