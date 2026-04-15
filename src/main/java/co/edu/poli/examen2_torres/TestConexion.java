package co.edu.poli.examen2_torres;

import co.edu.poli.examen2_torres.servicios.ConexionBD;

public class TestConexion {
    public static void main(String[] args) {
        try {
            ConexionBD.getInstancia().getConexion();
            System.out.println("FUNCIONA");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
