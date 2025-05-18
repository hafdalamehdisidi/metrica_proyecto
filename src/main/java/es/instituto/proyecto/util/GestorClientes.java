package es.instituto.proyecto.util;

import es.instituto.proyecto.Cliente;
import es.instituto.proyecto.db.Conexion;
import java.sql.*;
import java.util.Optional;

public class GestorClientes {
    
    public Optional<Cliente> buscarClientePorTelefono(String telefono) {
        String sql = "SELECT * FROM clientes WHERE telefono = ?";
        
        try (Connection conn = Conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, telefono);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Cliente cliente = new Cliente(
                    rs.getString("nombre"),
                    rs.getString("telefono"),
                    rs.getString("email")
                );
                cliente.setId(rs.getInt("id"));
                return Optional.of(cliente);
            }
            
        } catch (SQLException e) {
            System.out.println("Error al buscar cliente: " + e.getMessage());
        }
        return Optional.empty();
    }

    public boolean guardarCliente(Cliente cliente) {
        String sql = "INSERT INTO clientes (nombre, telefono, email) VALUES (?, ?, ?)";
        
        try (Connection conn = Conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getTelefono());
            stmt.setString(3, cliente.getEmail());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        cliente.setId(generatedKeys.getInt(1));
                        return true;
                    }
                }
            }
            return false;
            
        } catch (SQLException e) {
            System.out.println("Error al guardar cliente: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarCliente(Cliente cliente) {
        String sql = "UPDATE clientes SET nombre = ?, telefono = ?, email = ? WHERE id = ?";
        
        try (Connection conn = Conexion.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getTelefono());
            stmt.setString(3, cliente.getEmail());
            stmt.setInt(4, cliente.getId());
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.out.println("Error al actualizar cliente: " + e.getMessage());
            return false;
        }
    }
}