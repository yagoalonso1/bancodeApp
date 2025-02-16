package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa una cuenta bancaria con saldo y un historial de operaciones.
 */
public class CuentaBancaria {
    private double saldoEnEUR;
    private List<Operacion> historial;
    private Divisa divisaActual;

    public CuentaBancaria(double saldoInicial) {
        this.saldoEnEUR = saldoInicial;
        this.historial = new ArrayList<>();
        this.divisaActual = Divisa.EUR; // Divisa por defecto
    }

    public double getSaldo() {
        return divisaActual.convertirDesdeEUR(saldoEnEUR);
    }

    public String getSaldoFormateado() {
        return String.format("%.2f %s", getSaldo(), divisaActual.getSimbolo());
    }

    public Divisa getDivisaActual() {
        return divisaActual;
    }

    public void cambiarDivisa(Divisa nuevaDivisa) {
        this.divisaActual = nuevaDivisa;
    }

    /**
     * Realiza un retiro de dinero de la cuenta si hay saldo suficiente.
     * @param cantidad Monto a retirar.
     * @return true si el retiro fue exitoso, false si no hay suficiente saldo.
     */
    public boolean retirar(double cantidad) {
        double cantidadEnEUR = divisaActual.convertirAEUR(cantidad);
        if (cantidadEnEUR > saldoEnEUR) return false;
        
        saldoEnEUR -= cantidadEnEUR;
        historial.add(new Operacion("Retiro", cantidad, divisaActual));
        return true;
    }

    /**
     * Realiza un depósito de dinero en la cuenta.
     * @param cantidad Monto a depositar.
     */
    public void depositar(double cantidad) {
        double cantidadEnEUR = divisaActual.convertirAEUR(cantidad);
        saldoEnEUR += cantidadEnEUR;
        historial.add(new Operacion("Depósito", cantidad, divisaActual));
    }

    /**
     * Obtiene el historial de operaciones de la cuenta.
     * @return Lista de operaciones realizadas.
     */
    public List<Operacion> getHistorial() {
        return historial;
    }
}