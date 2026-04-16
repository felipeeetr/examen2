package co.edu.poli.examen2_torres.integracion;

import java.sql.Connection;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import co.edu.poli.examen2_torres.modelo.asegurado;
import co.edu.poli.examen2_torres.modelo.seguro;
import co.edu.poli.examen2_torres.modelo.seguroVehiculo;
import co.edu.poli.examen2_torres.modelo.seguroVida;
import co.edu.poli.examen2_torres.servicios.ConexionBD;
import co.edu.poli.examen2_torres.servicios.DAOseguro;

public class TestDAOseguro {

    DAOseguro dao = new DAOseguro();

    // =========================
    // LIMPIEZA ANTES DE CADA TEST
    // =========================
    @BeforeEach
    void clean() throws Exception {

        Connection con = ConexionBD.getInstancia().getConexion();

        con.setAutoCommit(false);

        Statement st = con.createStatement();

        st.executeUpdate("DELETE FROM seguro_vida");
        st.executeUpdate("DELETE FROM seguro_vehiculo");
        st.executeUpdate("DELETE FROM seguro");

        con.setAutoCommit(true);
    }

    // =========================
    // CREATE + READ SEGURO VIDA
    // =========================
    @Test
    void create_seguroVida_y_readone() throws Exception {

        asegurado a = new asegurado("A001", "Pipe Torres");

        seguroVida vida = new seguroVida(
                "VIDA_001",
                "2026-12-25",
                true,
                a,
                "Maria Torres"
        );

        String result = dao.create(vida);

        assertTrue(result.contains("guardado"));

        seguro s = dao.readone("VIDA_001");

        assertNotNull(s);
        assertTrue(s instanceof seguroVida);

        seguroVida v = (seguroVida) s;

        assertEquals("Maria Torres", v.getBeneficiario());
    }

    // =========================
    // CREATE + READ SEGURO VEHICULO
    // =========================
    @Test
    void create_seguroVehiculo_y_readone() throws Exception {

        asegurado a = new asegurado("A002", "Carlos Ruiz");

        seguroVehiculo vehiculo = new seguroVehiculo(
                "VEH_001",
                "2026-12-25",
                true,
                a,
                "Toyota"
        );

        String result = dao.create(vehiculo);

        assertTrue(result.contains("guardado"));

        seguro s = dao.readone("VEH_001");

        assertNotNull(s);
        assertTrue(s instanceof seguroVehiculo);

        seguroVehiculo v = (seguroVehiculo) s;

        assertEquals("Toyota", v.getMarca());
    }

    // =========================
    // READONE NO EXISTE
    // =========================
    @Test
    void readone_noExiste() throws Exception {

        seguro s = dao.readone("XXXX");

        assertNull(s);
    }
}