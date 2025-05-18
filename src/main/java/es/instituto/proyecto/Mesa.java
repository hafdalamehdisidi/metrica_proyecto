package es.instituto.proyecto;

public class Mesa {
    private int id;
    private int numero;
    private int capacidad;
    private String ubicacion;
    private String estado;

    public Mesa(int numero, int capacidad) {
        this.numero = numero;
        this.capacidad = capacidad;
        this.estado = "DISPONIBLE";
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }
    public int getCapacidad() { return capacidad; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return String.format("Mesa %d (Cap: %d) - %s - %s", 
            numero, capacidad, 
            ubicacion != null ? ubicacion : "Sin ubicación", 
            estado);
    }
}