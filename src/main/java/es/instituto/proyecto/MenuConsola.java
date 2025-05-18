package es.instituto.proyecto;

import es.instituto.proyecto.util.GestorReservas;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MenuConsola {
    private Scanner scanner;
    private GestorReservas gestor;

    public MenuConsola() {
        scanner = new Scanner(System.in);
        gestor = new GestorReservas();
    }

    public void mostrarMenu() {
        while (true) {
            System.out.println("\n=== SISTEMA DE RESERVAS EL RESTAURANTE ===");
            System.out.println("1. Nueva Reserva");
            System.out.println("2. Ver Reservas");
            System.out.println("3. Cancelar Reserva");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

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
                    System.out.println("Opción no válida");
            }
        }
    }

    private void crearNuevaReserva() {
        System.out.println("\n=== NUEVA RESERVA ===");
        // Implementar lógica de creación de reserva
        // Por ahora solo mostraremos un mensaje
        System.out.println("Funcionalidad en desarrollo...");
    }

    private void verReservas() {
        System.out.println("\n=== VER RESERVAS ===");
        // Implementar lógica para ver reservas
        System.out.println("Funcionalidad en desarrollo...");
    }

    private void cancelarReserva() {
        System.out.println("\n=== CANCELAR RESERVA ===");
        // Implementar lógica de cancelación
        System.out.println("Funcionalidad en desarrollo...");
    }
}