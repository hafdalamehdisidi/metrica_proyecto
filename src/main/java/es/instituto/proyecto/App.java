package es.instituto.proyecto;

import es.instituto.proyecto.db.Conexion;
import java.sql.Connection;

public class App {
    public static void main(String[] args) {
        System.out.println("Iniciando El Restaurante - Sistema de Reservas");
        
        // Probar la conexión a la base de datos
        Connection conn = Conexion.conectar();
        if (conn != null) {
            System.out.println("Sistema iniciado correctamente");
        } else {
            System.out.println("Error al iniciar el sistema");
        }
    }
}