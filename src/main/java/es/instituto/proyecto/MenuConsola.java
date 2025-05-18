package es.instituto.proyecto;

import es.instituto.proyecto.util.GestorReservas;
import es.instituto.proyecto.util.GestorClientes;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class MenuConsola {
    private Scanner scanner;
    private GestorReservas gestorReservas;
    private GestorClientes gestorClientes;
    private DateTimeFormatter dateFormatter;
    private DateTimeFormatter timeFormatter;

    public MenuConsola() {
        scanner = new Scanner(System.in);
        gestorReservas = new GestorReservas();
        gestorClientes = new GestorClientes();
        dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    }

    public void iniciar() {
        while (true) {
            try {
                mostrarMenuPrincipal();
                int opcion = Integer.parseInt(scanner.nextLine().trim());
                
                switch (opcion) {
                    case 1 -> crearReserva();
                    case 2 -> verReservas();
                    case 3 -> cancelarReserva();
                    case 4 -> {
                        System.out.println("¡Gracias por usar el sistema!");
                        return;
                    }
                    default -> System.out.println("Opción no válida");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void mostrarMenuPrincipal() {
        System.out.println("\n=== SISTEMA DE RESERVAS DEL RESTAURANTE ===");
        System.out.println("1. Nueva Reserva");
        System.out.println("2. Ver Reservas");
        System.out.println("3. Cancelar Reserva");
        System.out.println("4. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private void crearReserva() {
        Cliente cliente = solicitarDatosCliente();
        if (cliente == null) return;

        LocalDate fecha = solicitarFecha();
        if (fecha == null) return;

        LocalTime hora = solicitarHora();
        if (hora == null) return;

        System.out.print("Número de personas: ");
        int numPersonas;
        try {
            numPersonas = Integer.parseInt(scanner.nextLine().trim());
            if (numPersonas <= 0) throw new IllegalArgumentException("El número debe ser positivo");
        } catch (NumberFormatException e) {
            System.out.println("Por favor, ingrese un número válido");
            return;
        }

        List<Mesa> mesasDisponibles = gestorReservas.obtenerMesasDisponibles(fecha, hora, numPersonas);
        if (mesasDisponibles.isEmpty()) {
            System.out.println("No hay mesas disponibles para esa fecha y hora");
            return;
        }

        System.out.println("\nMesas disponibles:");
        for (Mesa mesa : mesasDisponibles) {
            System.out.printf("Mesa %d (Capacidad: %d, Ubicación: %s)\n", 
                            mesa.getNumero(), mesa.getCapacidad(), mesa.getUbicacion());
        }

        Mesa mesaSeleccionada = seleccionarMesa(mesasDisponibles);
        if (mesaSeleccionada == null) return;

        Reserva reserva = new Reserva(cliente, mesaSeleccionada, fecha, hora, numPersonas);
        if (gestorReservas.guardarReserva(reserva)) {
            System.out.println("¡Reserva creada con éxito!");
        } else {
            System.out.println("Error al crear la reserva");
        }
    }

    private Cliente solicitarDatosCliente() {
        System.out.print("Teléfono del cliente: ");
        String telefono = scanner.nextLine().trim();
        
        Cliente cliente = gestorClientes.buscarClientePorTelefono(telefono);
        if (cliente != null) {
            System.out.println("Cliente encontrado: " + cliente.getNombre() + " " + cliente.getApellidos());
            return cliente;
        }

        System.out.println("Cliente nuevo - Complete los datos:");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Apellidos: ");
        String apellidos = scanner.nextLine().trim();
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        try {
            cliente = new Cliente(nombre, apellidos, telefono, email);
            return gestorClientes.guardarCliente(cliente);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    private LocalDate solicitarFecha() {
        System.out.print("Fecha (dd/MM/yyyy): ");
        try {
            String fechaStr = scanner.nextLine().trim();
            LocalDate fecha = LocalDate.parse(fechaStr, dateFormatter);
            if (fecha.isBefore(LocalDate.now())) {
                System.out.println("La fecha no puede ser anterior a hoy");
                return null;
            }
            return fecha;
        } catch (DateTimeParseException e) {
            System.out.println("Formato de fecha inválido");
            return null;
        }
    }

    private LocalTime solicitarHora() {
        System.out.print("Hora (HH:mm): ");
        try {
            String horaStr = scanner.nextLine().trim();
            LocalTime hora = LocalTime.parse(horaStr, timeFormatter);
            if (hora.isBefore(LocalTime.of(12, 0)) || hora.isAfter(LocalTime.of(23, 0))) {
                System.out.println("El horario de reservas es de 12:00 a 23:00");
                return null;
            }
            return hora;
        } catch (DateTimeParseException e) {
            System.out.println("Formato de hora inválido");
            return null;
        }
    }

    private Mesa seleccionarMesa(List<Mesa> mesas) {
        System.out.print("Seleccione el número de mesa: ");
        try {
            int numeroMesa = Integer.parseInt(scanner.nextLine().trim());
            return mesas.stream()
                       .filter(m -> m.getNumero() == numeroMesa)
                       .findFirst()
                       .orElse(null);
        } catch (NumberFormatException e) {
            System.out.println("Número de mesa inválido");
            return null;
        }
    }

    private void verReservas() {
        LocalDate fecha = solicitarFecha();
        if (fecha == null) return;

        List<Reserva> reservas = gestorReservas.obtenerReservasDelDia(fecha);
        if (reservas.isEmpty()) {
            System.out.println("No hay reservas para esta fecha");
            return;
        }

        System.out.println("\nReservas para el " + fecha.format(dateFormatter) + ":");
        for (Reserva reserva : reservas) {
            System.out.println("ID: " + reserva.getId() + " - " + reserva);
        }
    }

    private void cancelarReserva() {
        System.out.print("ID de la reserva a cancelar: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            if (gestorReservas.cancelarReserva(id)) {
                System.out.println("Reserva cancelada con éxito");
            } else {
                System.out.println("No se pudo cancelar la reserva");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID inválido");
        }
    }
}