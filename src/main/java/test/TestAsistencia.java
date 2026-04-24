/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

import database.AsistenciaDAO;
/**
 *
 * @author USUARIO
 */
public class TestAsistencia {
    public static void main(String[] args){
        AsistenciaDAO dao = new AsistenciaDAO();
        
        dao.marcar(1, 1, "entrada");
        dao.marcar(1, 1, "salida");
    }
}
