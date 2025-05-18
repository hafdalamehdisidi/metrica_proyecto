package es.instituto.proyecto.util;

import es.instituto.proyecto.Mesa;
import es.instituto.proyecto.Reserva;
import es.instituto.proyecto.db.Conexion;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;

public class GestorReservas {
    
    public List<Mesa> obtenerMesasDisponibles(int capacidadRequerida) {
        List<Mesa> mesas = new ArrayList<>();
        String sql = "SELECT id, numero, capacidad FROM mesas WHERE capacidad >= ? ORDER BY capacidad ASC";
        
        try (Connection conn = Conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, capacidadRequerida);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Mesa mesa = new Mesa(
                    rs.getInt("numero"),
                    rs.getInt("capacidad")
                );
                mesa.setId(rs.getInt("id"));
                mesas.add(mesa);
            }
            
        } catch (SQLException e) {
            System.out.println("Error al buscar mesas por capacidad: " + e.getMessage());
        }
        
        return mesas;
    }

    // ... resto de métodos existentes ...
}