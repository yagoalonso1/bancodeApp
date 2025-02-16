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
    private static final double UMBRAL_COMISION = 200.0;
    private static final double PORCENTAJE_COMISION = 0.02; // 2%

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
        // Convertir la cantidad a EUR para comparar con el umbral
        double cantidadEnEUR = divisaActual.convertirAEUR(cantidad);
        double comision = 0;
        double totalRetiroEUR;

        // Verificar si la cantidad supera el umbral y calcular comisión
        if (cantidadEnEUR >= UMBRAL_COMISION) {
            comision = cantidadEnEUR * PORCENTAJE_COMISION;
            totalRetiroEUR = cantidadEnEUR + comision;
            System.out.println("Cantidad en EUR: " + cantidadEnEUR);
            System.out.println("Comisión en EUR: " + comision);
            System.out.println("Total a retirar en EUR: " + totalRetiroEUR);
        } else {
            totalRetiroEUR = cantidadEnEUR;
        }

        // Verificar si hay saldo suficiente
        if (totalRetiroEUR > saldoEnEUR) {
            return false;
        }

        // Realizar el retiro
        saldoEnEUR -= totalRetiroEUR;

        // Registrar la operación con detalles de comisión si aplica
        if (comision > 0) {
            double comisionEnDivisaActual = divisaActual.convertirDesdeEUR(comision);
            String descripcion = String.format("Retiro: %.2f %s + Comisión (2%%): %.2f %s = Total: %.2f %s",
                    cantidad,
                    divisaActual.getSimbolo(),
                    comisionEnDivisaActual,
                    divisaActual.getSimbolo(),
                    cantidad + comisionEnDivisaActual,
                    divisaActual.getSimbolo());
            historial.add(new Operacion(descripcion, cantidad + comisionEnDivisaActual, divisaActual));
        } else {
            historial.add(new Operacion("Retiro", cantidad, divisaActual));
        }

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