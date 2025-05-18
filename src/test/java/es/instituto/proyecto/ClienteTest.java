package es.instituto.proyecto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClienteTest {
    
    @Test
    public void testCrearCliente() {
        Cliente cliente = new Cliente("Juan Pérez", "666555444", "juan@email.com");
        
        assertEquals("Juan Pérez", cliente.getNombre());
        assertEquals("666555444", cliente.getTelefono());
        assertEquals("juan@email.com", cliente.getEmail());
    }
}