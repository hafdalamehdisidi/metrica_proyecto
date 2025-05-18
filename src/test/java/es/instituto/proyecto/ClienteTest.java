package es.instituto.proyecto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClienteTest {

    @Test
    void testCreacionCliente() {
        Cliente cliente = new Cliente("Juan", "Pérez", "123456789", "juan@email.com");
        
        assertAll("Cliente",
            () -> assertEquals("Juan", cliente.getNombre()),
            () -> assertEquals("Pérez", cliente.getApellidos()),
            () -> assertEquals("123456789", cliente.getTelefono()),
            () -> assertEquals("juan@email.com", cliente.getEmail())
        );
    }

    @Test
    void testSetId() {
        Cliente cliente = new Cliente("Juan", "Pérez", "123456789", "juan@email.com");
        cliente.setId(1);
        assertEquals(1, cliente.getId());
    }

    @Test
    void testToString() {
        Cliente cliente = new Cliente("Juan", "Pérez", "123456789", "juan@email.com");
        String expected = "Juan Pérez - Tel: 123456789 - Email: juan@email.com";
        assertEquals(expected, cliente.toString());
    }
}