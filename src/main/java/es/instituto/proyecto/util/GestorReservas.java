package es.instituto.proyecto.util;

import es.instituto.proyecto.db.Conexion;
import es.instituto.proyecto.Reserva;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class GestorReservas {
    
    public boolean guardarReserva(Reserva reserva) {
        String sql = "INSERT INTO reservas (cliente_id, mesa_id, fecha, hora, num_personas) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = Conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, reserva.getCliente().getId());
            stmt.setInt(2, reserva.getMesa().getId());
            stmt.setDate(3, Date.valueOf(reserva.getFecha()));
            stmt.setTime(4, Time.valueOf(reserva.getHora()));
            stmt.setInt(5, reserva.getNumPersonas());
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.out.println("Error al guardar la reserva: " + e.getMessage());
            return false;
        }
    }

    public boolean isMesaDisponible(int mesaId, LocalDate fecha, LocalTime hora) {
        String sql = "SELECT COUNT(*) FROM reservas WHERE mesa_id = ? AND fecha = ? AND hora = ?";
        
        try (Connection conn = Conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, mesaId);
            stmt.setDate(2, Date.valueOf(fecha));
            stmt.setTime(3, Time.valueOf(hora));
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0;
            }
            
        } catch (SQLException e) {
            System.out.println("Error al verificar disponibilidad: " + e.getMessage());
        }
        return false;
    }
}