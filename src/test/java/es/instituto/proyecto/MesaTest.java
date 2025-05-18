package es.instituto.proyecto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MesaTest {

    @Test
    void testCreacionMesa() {
        Mesa mesa = new Mesa(1, 4);
        
        assertAll("Mesa",
            () -> assertEquals(1, mesa.getNumero()),
            () -> assertEquals(4, mesa.getCapacidad()),
            () -> assertEquals("DISPONIBLE", mesa.getEstado())
        );
    }

    @Test
    void testSetUbicacion() {
        Mesa mesa = new Mesa(1, 4);
        mesa.setUbicacion("Terraza");
        assertEquals("Terraza", mesa.getUbicacion());
    }

    @Test
    void testSetEstado() {
        Mesa mesa = new Mesa(1, 4);
        mesa.setEstado("NO_DISPONIBLE");
        assertEquals("NO_DISPONIBLE", mesa.getEstado());
    }

    @Test
    void testToString() {
        Mesa mesa = new Mesa(1, 4);
        mesa.setUbicacion("Terraza");
        String expected = "Mesa 1 (Cap: 4) - Terraza - DISPONIBLE";
        assertEquals(expected, mesa.toString());
    }
}