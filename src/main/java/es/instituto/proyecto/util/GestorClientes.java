package es.instituto.proyecto.util;

import es.instituto.proyecto.Cliente;
import es.instituto.proyecto.db.Conexion;
import java.sql.*;

public class GestorClientes {
    
    public Cliente guardarCliente(Cliente cliente) {
        String sql = "INSERT INTO clientes (nombre, apellidos, telefono, email) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = Conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellidos());
            stmt.setString(3, cliente.getTelefono());
            stmt.setString(4, cliente.getEmail());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        cliente.setId(generatedKeys.getInt(1));
                        return cliente;
                    }
                }
            }
            
        } catch (SQLException e) {
            System.out.println("Error al guardar el cliente: " + e.getMessage());
        }
        return null;
    }

    public Cliente buscarClientePorTelefono(String telefono) {
        String sql = "SELECT * FROM clientes WHERE telefono = ?";
        
        try (Connection conn = Conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, telefono);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Cliente cliente = new Cliente(
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getString("telefono"),
                    rs.getString("email")
                );
                cliente.setId(rs.getInt("id"));
                return cliente;
            }
            
        } catch (SQLException e) {
            System.out.println("Error al buscar el cliente: " + e.getMessage());
        }
        return null;
    }
}