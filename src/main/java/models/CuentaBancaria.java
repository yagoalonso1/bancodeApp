package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa una cuenta bancaria con capacidad de manejar diferentes divisas
 * y aplicar comisiones en retiros grandes.
 * 
 * @author Yago
 * @version 1.0
 */
public class CuentaBancaria {
    private double saldoEnEUR;
    private List<Operacion> historial;
    private Divisa divisaActual;
    /** Umbral a partir del cual se aplica comisión en los retiros (en EUR) */
    private static final double UMBRAL_COMISION = 200.0;
    /** Porcentaje de comisión aplicado a retiros grandes (2%) */
    private static final double PORCENTAJE_COMISION = 0.02;

    /**
     * Constructor que inicializa una nueva cuenta bancaria.
     * @param saldoInicial Saldo inicial de la cuenta en euros
     */
    public CuentaBancaria(double saldoInicial) {
        this.saldoEnEUR = saldoInicial;
        this.historial = new ArrayList<>();
        this.divisaActual = Divisa.EUR;
    }

    /**
     * Obtiene el saldo actual en la divisa seleccionada.
     * @return Saldo convertido a la divisa actual
     */
    public double getSaldo() {
        return divisaActual.convertirDesdeEUR(saldoEnEUR);
    }

    /**
     * Obtiene el saldo formateado con el símbolo de la divisa actual.
     * @return String con el saldo formateado (ej: "100.00 €")
     */
    public String getSaldoFormateado() {
        return String.format("%.2f %s", getSaldo(), divisaActual.getSimbolo());
    }

    /**
     * Obtiene la divisa actual de la cuenta.
     * @return Divisa actual
     */
    public Divisa getDivisaActual() {
        return divisaActual;
    }

    /**
     * Cambia la divisa actual de la cuenta.
     * @param nuevaDivisa Nueva divisa a utilizar
     */
    public void cambiarDivisa(Divisa nuevaDivisa) {
        this.divisaActual = nuevaDivisa;
    }

    /**
     * Realiza un retiro de la cuenta, aplicando comisión si corresponde.
     * Se aplica una comisión del 2% para retiros de 200€ o más.
     * 
     * @param cantidad Cantidad a retirar en la divisa actual
     * @return true si el retiro fue exitoso, false si no hay saldo suficiente
     */
    public boolean retirar(double cantidad) {
        // Convertir la cantidad a EUR para comparar con el umbral
        double cantidadEnEUR = divisaActual.convertirAEUR(cantidad);
        double comision = 0;
        double totalRetiroEUR;

        // Verificar si la cantidad es igual o superior al umbral y calcular comisión
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
     * Realiza un depósito en la cuenta.
     *
     * @param cantidad Cantidad a depositar en la divisa actual
     * @return
     */
    public boolean depositar(double cantidad) {
        double cantidadEnEUR = divisaActual.convertirAEUR(cantidad);
        saldoEnEUR += cantidadEnEUR;
        historial.add(new Operacion("Depósito", cantidad, divisaActual));
        return false;
    }

    /**
     * Obtiene el historial de operaciones de la cuenta.
     * @return Lista de operaciones realizadas
     */
    public List<Operacion> getHistorial() {
        return historial;
    }
}