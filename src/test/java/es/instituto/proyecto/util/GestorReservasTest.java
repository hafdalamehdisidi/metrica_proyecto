package es.instituto.proyecto.util;

import es.instituto.proyecto.Cliente;
import es.instituto.proyecto.Mesa;
import es.instituto.proyecto.Reserva;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.*;

public class GestorReservasTest {
    private GestorReservas gestorReservas;
    private GestorClientes gestorClientes;
    private GestorMesas gestorMesas;

    @BeforeEach
    void setUp() {
        gestorReservas = new GestorReservas();
        gestorClientes = new GestorClientes();
        gestorMesas = new GestorMesas();
    }

    @Test
    void testGuardarReserva() {
        // Crear y guardar el cliente
        Cliente cliente = new Cliente("Juan", "Pérez", "123456789", "juan@email.com");
        Cliente clienteGuardado = gestorClientes.guardarCliente(cliente);
        assertTrue(clienteGuardado != null && clienteGuardado.getId() > 0, "Error al guardar el cliente");
        cliente = clienteGuardado;
        
        // Crear y guardar la mesa
        Mesa mesa = new Mesa(1, 4);
        mesa.setEstado("DISPONIBLE");
        int mesaId = gestorMesas.guardarMesa(mesa);
        assertTrue(mesaId > 0, "Error al guardar la mesa");
        mesa.setId(mesaId);
        
        // Crear y guardar la reserva
        LocalDate fecha = LocalDate.now();
        LocalTime hora = LocalTime.of(14, 0);
        
        Reserva reserva = new Reserva(cliente, mesa, fecha, hora, 4);
        reserva.setEstado("CONFIRMADA");
        
        boolean resultado = gestorReservas.guardarReserva(reserva);
        assertTrue(resultado, "Error al guardar la reserva");
    }
}