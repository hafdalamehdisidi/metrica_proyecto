package es.instituto.proyecto;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reserva {
    private int id;
    private Cliente cliente;
    private Mesa mesa;
    private LocalDate fecha;
    private LocalTime hora;
    private int numPersonas;

    public Reserva(Cliente cliente, Mesa mesa, LocalDate fecha, LocalTime hora, int numPersonas) {
        this.cliente = cliente;
        this.mesa = mesa;
        this.fecha = fecha;
        this.hora = hora;
        this.numPersonas = numPersonas;
    }

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public Mesa getMesa() { return mesa; }
    public void setMesa(Mesa mesa) { this.mesa = mesa; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }
    public int getNumPersonas() { return numPersonas; }
    public void setNumPersonas(int numPersonas) { this.numPersonas = numPersonas; }

    @Override
    public String toString() {
        return "Reserva para " + cliente.getNombre() + 
               " - Fecha: " + fecha + 
               " Hora: " + hora +
               " - Mesa: " + mesa.getNumero() +
               " - Personas: " + numPersonas;
    }
}