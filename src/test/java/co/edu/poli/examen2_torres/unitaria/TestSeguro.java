package co.edu.poli.examen2_torres.unitaria;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import co.edu.poli.examen2_torres.modelo.asegurado;
import co.edu.poli.examen2_torres.modelo.seguro;
import co.edu.poli.examen2_torres.modelo.seguroVida;

public class TestSeguro {

    @Test
    void cancelar_cambiaEstadoAFalso() {
        
        asegurado asegurado = new asegurado("A001", "Test");
        
        seguro s = new seguroVida("S123", "2025-12-25", true, asegurado, "Maria");

        String mensaje = s.cancelar();

        assertFalse(s.isEstado());
        assertTrue(mensaje.contains("CANCELADO"));
        
    }

    @Test
    void activar_cambiaEstadoAVerdadero() {

        asegurado asegurado = new asegurado("A001", "Test");

        seguro s = new seguroVida("S123", "2025-12-25", false, asegurado, "Maria");

        String mensaje = s.activar();

        assertTrue(s.isEstado());
        assertTrue(mensaje.contains("ACTIVADO"));
    }

    @Test
    void getters_retornaValoresCorrectos() {

        asegurado asegurado = new asegurado("A001", "Test");

        seguro s = new seguroVida("S123", "2025-12-25", true, asegurado, "Maria");

        assertEquals("S123", s.getNumero());
        assertEquals("2025-12-25", s.getFechaExp());
        assertTrue(s.isEstado());
        assertEquals(asegurado, s.getasegurado());
    }

    @Test
    void setters_modificanValores() {

        asegurado asegurado = new asegurado("A001", "Test");
        asegurado nuevo = new asegurado("A002", "Nuevo");

        seguro s = new seguroVida("S123", "2025-12-25", true, asegurado, "Maria");

        s.setNumero("S999");
        s.setFechaExp("2030-01-01");
        s.setEstado(false);
        s.setasegurado(nuevo);

        assertEquals("S999", s.getNumero());
        assertEquals("2030-01-01", s.getFechaExp());
        assertFalse(s.isEstado());
        assertEquals(nuevo, s.getasegurado());
        
    }

    @Test
    void toString_contieneDatos() {

     asegurado asegurado = new asegurado("A001", "Test");

        seguro s = new seguroVida("S123", "2025-12-25", true, asegurado, "Maria");

        String texto = s.toString();

        assertTrue(texto.contains("S123"));
        assertTrue(texto.contains("2025-12-25"));
}

}

    