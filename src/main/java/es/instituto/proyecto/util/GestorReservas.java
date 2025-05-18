package es.instituto.proyecto.util;

import es.instituto.proyecto.Mesa;
import es.instituto.proyecto.Reserva;
import es.instituto.proyecto.Cliente;
import es.instituto.proyecto.db.Conexion;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;

public class GestorReservas {
    
    public List<Mesa> obtenerMesasDisponibles(LocalDate fecha, LocalTime hora, int capacidadRequerida) {
        List<Mesa> mesas = new ArrayList<>();
        String sql = """
            SELECT DISTINCT m.* FROM mesas m 
            WHERE m.capacidad >= ? 
            AND m.estado = 'DISPONIBLE'
            AND m.numero NOT IN (
                SELECT COALESCE(r.mesa_id, 0) 
                FROM reservas r 
                WHERE r.fecha = ? 
                AND r.hora = ? 
                AND r.estado = 'CONFIRMADA'
            )
            ORDER BY m.capacidad ASC
        """;
        
        try (Connection conn = Conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, capacidadRequerida);
            stmt.setDate(2, Date.valueOf(fecha));
            stmt.setTime(3, Time.valueOf(hora));
            
            System.out.println("Ejecutando consulta para fecha: " + fecha + ", hora: " + hora + ", capacidad: " + capacidadRequerida);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Mesa mesa = new Mesa(
                    rs.getInt("numero"),
                    rs.getInt("capacidad")
                );
                mesa.setId(rs.getInt("id"));
                mesa.setUbicacion(rs.getString("ubicacion"));
                mesa.setEstado(rs.getString("estado"));
                mesas.add(mesa);
                
                System.out.println("Mesa encontrada: " + mesa);
            }
            
            if (mesas.isEmpty()) {
                System.out.println("No se encontraron mesas disponibles que cumplan con los criterios");
            }
            
        } catch (SQLException e) {
            System.out.println("Error al buscar mesas disponibles: " + e.getMessage());
            e.printStackTrace();
        }
        
        return mesas;
    }
    
    public boolean guardarReserva(Reserva reserva) {
        String sql = "INSERT INTO reservas (cliente_id, mesa_id, fecha, hora, num_personas, estado) VALUES (?, ?, ?, ?, ?, 'CONFIRMADA')";
        
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
            
        } catch (SQLException e) {
            System.out.println("Error al guardar la reserva: " + e.getMessage());
        }
        return false;
    }

    public List<Reserva> obtenerReservasDelDia(LocalDate fecha) {
        List<Reserva> reservas = new ArrayList<>();
        String sql = """
            SELECT r.*, c.nombre, c.apellidos, c.telefono, c.email, m.numero, m.capacidad 
            FROM reservas r 
            JOIN clientes c ON r.cliente_id = c.id 
            JOIN mesas m ON r.mesa_id = m.id 
            WHERE r.fecha = ? 
            ORDER BY r.hora ASC
        """;
        
        try (Connection conn = Conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDate(1, Date.valueOf(fecha));
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Cliente cliente = new Cliente(
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getString("telefono"),
                    rs.getString("email")
                );
                cliente.setId(rs.getInt("cliente_id"));
                
                Mesa mesa = new Mesa(
                    rs.getInt("numero"),
                    rs.getInt("capacidad")
                );
                mesa.setId(rs.getInt("mesa_id"));
                
                Reserva reserva = new Reserva(
                    cliente,
                    mesa,
                    rs.getDate("fecha").toLocalDate(),
                    rs.getTime("hora").toLocalTime(),
                    rs.getInt("num_personas")
                );
                reserva.setId(rs.getInt("id"));
                reserva.setEstado(rs.getString("estado"));
                
                reservas.add(reserva);
            }
            
        } catch (SQLException e) {
            System.out.println("Error al obtener las reservas: " + e.getMessage());
        }
        
        return reservas;
    }

    public boolean cancelarReserva(int reservaId) {
        String sql = "UPDATE reservas SET estado = 'CANCELADA' WHERE id = ?";
        
        try (Connection conn = Conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, reservaId);
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.out.println("Error al cancelar la reserva: " + e.getMessage());
            return false;
        }
    }
}