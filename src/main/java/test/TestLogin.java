/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

import dao.LoginDAO;

/**
 *
 * @author USUARIO
 */
public class TestLogin {
    public static void main(String[] args) {

        String correo = "joaquin@email.com";
        String password = "1234";

        LoginDAO.login(correo, password);
    }
}
