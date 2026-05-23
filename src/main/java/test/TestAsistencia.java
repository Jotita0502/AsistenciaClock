/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

import dao.TimerDAO;

/**
 *
 * @author USUARIO
 */
public class TestAsistencia {
    public static void main(String[] args) {
        TimerDAO dao = new TimerDAO();

        dao.marcar(1, 1, "entrada");
        dao.marcar(1, 1, "salida");
    }
}
