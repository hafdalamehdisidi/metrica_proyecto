package es.instituto.proyecto;

import java.util.regex.Pattern;

public class Cliente {
    private int id;
    private String nombre;
    private String telefono;
    private String email;

    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern TELEFONO_PATTERN = 
        Pattern.compile("^[0-9]{9}$");

    public Cliente(String nombre, String telefono, String email) {
        setNombre(nombre);
        setTelefono(telefono);
        setEmail(email);
    }

    // Getters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getTelefono() { return telefono; }
    public String getEmail() { return email; }

    // Setters con validación
    public void setId(int id) { 
        this.id = id; 
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (nombre.length() > 100) {
            throw new IllegalArgumentException("El nombre no puede tener más de 100 caracteres");
        }
        this.nombre = nombre.trim();
    }

    public void setTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) {
            throw new IllegalArgumentException("El teléfono no puede estar vacío");
        }
        if (!TELEFONO_PATTERN.matcher(telefono).matches()) {
            throw new IllegalArgumentException("El teléfono debe tener 9 dígitos");
        }
        this.telefono = telefono;
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email no puede estar vacío");
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Formato de email inválido");
        }
        if (email.length() > 100) {
            throw new IllegalArgumentException("El email no puede tener más de 100 caracteres");
        }
        this.email = email.trim().toLowerCase();
    }

    @Override
    public String toString() {
        return "Cliente: " + nombre + " (Tel: " + telefono + ", Email: " + email + ")";
    }
}