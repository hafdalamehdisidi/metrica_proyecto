package es.instituto.proyecto;

public class Mesa {
    private int id;
    private int numero;
    private int capacidad;
    private boolean ocupada;

    public Mesa(int numero, int capacidad) {
        this.numero = numero;
        this.capacidad = capacidad;
        this.ocupada = false;
    }

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }
    public int getCapacidad() { return capacidad; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }
    public boolean isOcupada() { return ocupada; }
    public void setOcupada(boolean ocupada) { this.ocupada = ocupada; }

    @Override
    public String toString() {
        return "Mesa #" + numero + " (Capacidad: " + capacidad + " personas)";
    }
}