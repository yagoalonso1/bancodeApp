package models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase que representa una operación bancaria (depósito o retiro).
 * Almacena información sobre el tipo de operación, cantidad, fecha y divisa.
 * 
 * @author Yago
 * @version 1.0
 */
public class Operacion {
    private String tipo;
    private double cantidad;
    private String fecha;
    private Divisa divisa;

    /**
     * Constructor de la operación bancaria.
     * 
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

    /**
     * Obtiene el tipo de operación.
     * @return String con el tipo de operación
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Obtiene la cantidad de la operación.
     * @return double con el monto de la operación
     */
    public double getCantidad() {
        return cantidad;
    }

    /**
     * Obtiene la fecha de la operación.
     * @return String con la fecha formateada
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * Obtiene la divisa de la operación.
     * @return Divisa utilizada en la operación
     */
    public Divisa getDivisa() {
        return divisa;
    }

    /**
     * Genera una representación en texto de la operación.
     * @return String con los detalles de la operación formateados
     */
    @Override
    public String toString() {
        return String.format("%s - %s: %.2f %s", 
            fecha, 
            tipo, 
            cantidad, 
            divisa.getSimbolo());
    }
}