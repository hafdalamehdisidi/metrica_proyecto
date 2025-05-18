package es.instituto.proyecto;

import java.util.Scanner;
import es.instituto.proyecto.util.GestorReservas;
import es.instituto.proyecto.util.GestorClientes;
import java.util.List;

public class MenuConsola {
    private Scanner scanner;
    private GestorReservas gestor;
    private GestorClientes gestorClientes;

    public MenuConsola() {
        scanner = new Scanner(System.in);
        gestor = new GestorReservas();
        gestorClientes = new GestorClientes();
    }

    public void mostrarMenu() {
        while (true) {
            try {
                System.out.println("\n=== SISTEMA DE RESERVAS EL RESTAURANTE ===");
                System.out.println("1. Nueva Reserva");
                System.out.println("2. Ver Reservas");
                System.out.println("3. Cancelar Reserva");
                System.out.println("4. Salir");
                System.out.print("Seleccione una opción: ");

                int opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        crearNuevaReserva();
                        break;
                    case 2:
                        verReservas();
                        break;
                    case 3:
                        cancelarReserva();
                        break;
                    case 4:
                        System.out.println("¡Gracias por usar el sistema!");
                        return;
                    default:
                        System.out.println("Opción no válida. Por favor, intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor, ingrese un número válido.");
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
            }
        }
    }

    private void crearNuevaReserva() {
        System.out.println("\n=== NUEVA RESERVA ===");
        Cliente cliente = solicitarDatosCliente();
        if (cliente == null) {
            System.out.println("No se pudo completar el proceso de reserva.");
            return;
        }
        System.out.println("Funcionalidad en desarrollo...");
    }

    private void verReservas() {
        System.out.println("\n=== VER RESERVAS ===");
        System.out.println("Funcionalidad en desarrollo...");
    }

    private void cancelarReserva() {
        System.out.println("\n=== CANCELAR RESERVA ===");
        System.out.println("Funcionalidad en desarrollo...");
    }

    private Cliente solicitarDatosCliente() {
        while (true) {
            try {
                System.out.println("\n=== DATOS DEL CLIENTE ===");
                System.out.print("Nombre completo: ");
                String nombre = scanner.nextLine().trim();

                System.out.print("Teléfono (9 dígitos): ");
                String telefono = scanner.nextLine().trim();

                System.out.print("Email: ");
                String email = scanner.nextLine().trim();

                return new Cliente(nombre, telefono, email);

            } catch (IllegalArgumentException e) {
                System.out.println("\nError: " + e.getMessage());
                System.out.print("¿Desea intentar nuevamente? (S/N): ");
                if (!scanner.nextLine().trim().equalsIgnoreCase("S")) {
                    return null;
                }
            }
        }
    }
	
    private void mostrarMesasDisponibles() {
        try {
            System.out.print("\nIngrese el número de personas: ");
            int numPersonas = Integer.parseInt(scanner.nextLine());
            
            if (numPersonas <= 0) {
                System.out.println("El número de personas debe ser mayor que 0");
                return;
            }
            
            List<Mesa> mesasDisponibles = gestor.obtenerMesasDisponibles(numPersonas);
            
            if (mesasDisponibles.isEmpty()) {
                System.out.println("No hay mesas disponibles para " + numPersonas + " personas.");
                return;
            }
            
            System.out.println("\nMesas disponibles para " + numPersonas + " personas:");
            for (Mesa mesa : mesasDisponibles) {
                System.out.println("Mesa " + mesa.getNumero() + 
                                 " (Capacidad: " + mesa.getCapacidad() + " personas)");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Error: Por favor ingrese un número válido.");
        }
    }
} // Este es el cierre que faltaba