package models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase que representa una operación bancaria (depósito o retiro).
 */
public class Operacion {
    private String tipo;
    private double cantidad;
    private String fecha;
    private Divisa divisa;

    /**
     * Constructor de la operación.
     * @param tipo Tipo de operación (Depósito o Retiro)
     * @param cantidad Monto de la operación
     * @param divisa Divisa en la que se realizó la operación
     */
    public Operacion(String tipo, double cantidad, Divisa divisa) {
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.divisa = divisa;
        this.fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String getTipo() {
        return tipo;
    }

    public double getCantidad() {
        return cantidad;
    }

    public String getFecha() {
        return fecha;
    }

    public Divisa getDivisa() {
        return divisa;
    }

    @Override
    public String toString() {
        return String.format("%s - %s: %.2f %s", 
            fecha, 
            tipo, 
            cantidad, 
            divisa.getSimbolo());
    }
}