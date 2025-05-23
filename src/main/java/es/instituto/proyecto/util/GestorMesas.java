package es.instituto.proyecto.util;

import es.instituto.proyecto.Mesa;
import es.instituto.proyecto.db.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestorMesas {
    
    public List<Mesa> obtenerMesasDisponibles() {
        List<Mesa> mesasDisponibles = new ArrayList<>();
        String sql = "SELECT * FROM mesas WHERE estado = 'DISPONIBLE' ORDER BY numero";
        
        try (Connection conn = Conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Mesa mesa = new Mesa(
                    rs.getInt("numero"),
                    rs.getInt("capacidad")
                );
                mesa.setId(rs.getInt("id"));
                mesa.setEstado(rs.getString("estado"));
                if (rs.getString("ubicacion") != null) {
                    mesa.setUbicacion(rs.getString("ubicacion"));
                }
                mesasDisponibles.add(mesa);
            }
            
        } catch (SQLException e) {
            System.out.println("Error al obtener las mesas disponibles: " + e.getMessage());
            e.printStackTrace();
        }
        
        return mesasDisponibles;
    }
    
public List<Mesa> obtenerMesasDisponiblesPorCapacidad(int capacidadMinima) {
    List<Mesa> mesasDisponibles = new ArrayList<>();
    String sql = "SELECT * FROM mesas WHERE estado = 'DISPONIBLE' AND capacidad >= ? ORDER BY capacidad";
    
    try (Connection conn = Conexion.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setInt(1, capacidadMinima);
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next()) {
            Mesa mesa = new Mesa(
                rs.getInt("numero"),
                rs.getInt("capacidad")
            );
            mesa.setId(rs.getInt("id"));
            mesa.setEstado(rs.getString("estado"));
            if (rs.getString("ubicacion") != null) {
                mesa.setUbicacion(rs.getString("ubicacion"));
            }
            mesasDisponibles.add(mesa);
        }
        
    } catch (SQLException e) {
        System.out.println("Error al obtener las mesas disponibles: " + e.getMessage());
        e.printStackTrace();
    }
    
    return mesasDisponibles;
}
    
    public int guardarMesa(Mesa mesa) {
        // Método existente guardarMesa se mantiene igual
        // ... código anterior ...
        // Primero verificamos si la mesa ya existe
        String checkSql = "SELECT id FROM mesas WHERE numero = ?";
        String insertSql = "INSERT INTO mesas (numero, capacidad, estado) VALUES (?, ?, ?)";
        
        try (Connection conn = Conexion.conectar()) {
            // Verificar si la mesa existe
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setInt(1, mesa.getNumero());
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt("id"); // Retorna el ID de la mesa existente
                }
            }
            
            // Si no existe, insertarla
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                insertStmt.setInt(1, mesa.getNumero());
                insertStmt.setInt(2, mesa.getCapacidad());
                insertStmt.setString(3, mesa.getEstado());
                
                insertStmt.executeUpdate();
                
                try (ResultSet rs = insertStmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int mesaId = rs.getInt(1);
                        mesa.setId(mesaId); // Actualizamos el ID de la mesa
                        return mesaId;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}