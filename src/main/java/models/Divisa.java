package models;

/**
 * Enumeración que representa las diferentes divisas disponibles en el sistema.
 * Incluye métodos para conversión entre divisas y manejo de símbolos monetarios.
 * 
 * @author Yago
 * @version 1.0
 */
public enum Divisa {
    /** Euro - Divisa base del sistema */
    EUR("€", 1.0),      // Euro (moneda base)
    /** Dólar estadounidense */
    USD("$", 1.09),     // 1 EUR = 1.09 USD
    /** Libra esterlina */
    GBP("£", 1.16);     // Cambiado a 1.16 porque 1 GBP = 1.16 EUR

    private final String simbolo;
    private final double tasaConversionEUR;

    /**
     * Constructor privado para las divisas.
     * 
     * @param simbolo Símbolo de la divisa
     * @param tasaConversionEUR Tasa de conversión respecto al euro
     */
    private Divisa(String simbolo, double tasaConversionEUR) {
        this.simbolo = simbolo;
        this.tasaConversionEUR = tasaConversionEUR;
    }

    /**
     * Obtiene el símbolo de la divisa.
     * @return String con el símbolo monetario
     */
    public String getSimbolo() {
        return simbolo;
    }

    /**
     * Convierte una cantidad desde euros a esta divisa.
     * 
     * @param cantidadEUR Cantidad en euros a convertir
     * @return Cantidad convertida a la divisa actual
     */
    public double convertirDesdeEUR(double cantidadEUR) {
        return cantidadEUR / tasaConversionEUR;
    }

    /**
     * Convierte una cantidad desde esta divisa a euros.
     * 
     * @param cantidad Cantidad a convertir a euros
     * @return Cantidad convertida a euros
     */
    public double convertirAEUR(double cantidad) {
        return cantidad * tasaConversionEUR;
    }
}
