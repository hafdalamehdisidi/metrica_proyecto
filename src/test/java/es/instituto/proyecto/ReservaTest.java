package es.instituto.proyecto;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.*;

public class ReservaTest {

    @Test
    void testCreacionReserva() {
        Cliente cliente = new Cliente("Juan", "Pérez", "123456789", "juan@email.com");
        Mesa mesa = new Mesa(1, 4);
        LocalDate fecha = LocalDate.of(2025, 5, 18);
        LocalTime hora = LocalTime.of(14, 0);
        
        Reserva reserva = new Reserva(cliente, mesa, fecha, hora, 4);
        
        assertAll("Reserva",
            () -> assertEquals(cliente, reserva.getCliente()),
            () -> assertEquals(mesa, reserva.getMesa()),
            () -> assertEquals(fecha, reserva.getFecha()),
            () -> assertEquals(hora, reserva.getHora()),
            () -> assertEquals(4, reserva.getNumPersonas()),
            () -> assertEquals("CONFIRMADA", reserva.getEstado())
        );
    }

    @Test
    void testSetId() {
        Cliente cliente = new Cliente("Juan", "Pérez", "123456789", "juan@email.com");
        Mesa mesa = new Mesa(1, 4);
        Reserva reserva = new Reserva(cliente, mesa, LocalDate.now(), LocalTime.now(), 4);
        
        reserva.setId(1);
        assertEquals(1, reserva.getId());
    }

    @Test
    void testCancelacionReserva() {
        Cliente cliente = new Cliente("Juan", "Pérez", "123456789", "juan@email.com");
        Mesa mesa = new Mesa(1, 4);
        Reserva reserva = new Reserva(cliente, mesa, LocalDate.now(), LocalTime.now(), 4);
        
        reserva.setEstado("CANCELADA");
        assertEquals("CANCELADA", reserva.getEstado());
    }

    @Test
    void testToString() {
        Cliente cliente = new Cliente("Juan", "Pérez", "123456789", "juan@email.com");
        Mesa mesa = new Mesa(1, 4);
        LocalDate fecha = LocalDate.of(2025, 5, 18);
        LocalTime hora = LocalTime.of(14, 0);
        
        Reserva reserva = new Reserva(cliente, mesa, fecha, hora, 4);
        
        String expected = "Mesa 1 - 14:00 - Juan Pérez - 4 personas";
        assertEquals(expected, reserva.toString());
    }
}