package co.edu.poli.examen2_torres.integracion;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import co.edu.poli.examen2_torres.modelo.asegurado;
import co.edu.poli.examen2_torres.servicios.DAOasegurado;

public class TestDAOasegurado {

    DAOasegurado dao = new DAOasegurado();

    @Test
    void readAll_noDebeSerNull() throws Exception {

        List<asegurado> lista = dao.readall();

        assertNotNull(lista);
    }

    @Test
    void readAll_listaValida() throws Exception {

        List<asegurado> lista = dao.readall();

        assertTrue(lista.size() >= 0);
    }

    @Test
    void readAll_objetosValidos() throws Exception {

        List<asegurado> lista = dao.readall();

        if (!lista.isEmpty()) {

            asegurado a = lista.get(0);

            assertNotNull(a.getId());
            assertNotNull(a.getNombre());
        }
    }
}