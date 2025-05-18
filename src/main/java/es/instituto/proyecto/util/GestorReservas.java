package es.instituto.proyecto.util;

import es.instituto.proyecto.Mesa;
import es.instituto.proyecto.Reserva;
import es.instituto.proyecto.db.Conexion;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class GestorReservas {
    
    public boolean guardarReserva(Reserva reserva) {
        String sql = "INSERT INTO reservas (cliente_id, mesa_id, fecha, hora, num_personas) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = Conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, reserva.getCliente().getId());
            stmt.setInt(2, reserva.getMesa().getId());
            stmt.setDate(3, Date.valueOf(reserva.getFecha()));
            stmt.setTime(4, Time.valueOf(reserva.getHora()));
            stmt.setInt(5, reserva.getNumPersonas());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        reserva.setId(generatedKeys.getInt(1));
                        return true;
                    }
                }
            }
            return false;
            
        } catch (SQLException e) {
            System.out.println("Error al guardar la reserva: " + e.getMessage());
            return false;
        }
    }

    public List<Mesa> obtenerMesasDisponibles(LocalDate fecha) {
        // TODO: Implementar la lógica para obtener mesas disponibles
        return new ArrayList<>();
    }
} // Este es el cierre que faltaba