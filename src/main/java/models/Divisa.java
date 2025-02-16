package models;

/**
 * Enum que representa las diferentes divisas soportadas por el sistema
 * con sus tasas de conversión respecto al euro.
 */
public enum Divisa {
    EUR("€", 1.0),      // Euro (moneda base)
    USD("$", 1.09),     // 1 EUR = 1.09 USD
    GBP("£", 0.86);     // 1 EUR = 0.86 GBP

    private final String simbolo;
    private final double tasaConversion;

    /**
     * Constructor para cada divisa.
     * @param simbolo El símbolo de la moneda
     * @param tasaConversion La tasa de conversión respecto al euro
     */
    Divisa(String simbolo, double tasaConversion) {
        this.simbolo = simbolo;
        this.tasaConversion = tasaConversion;
    }

    /**
     * Obtiene el símbolo de la divisa.
     * @return El símbolo de la moneda (€, $, £)
     */
    public String getSimbolo() {
        return simbolo;
    }

    /**
     * Convierte una cantidad desde euros a esta divisa.
     * @param cantidad La cantidad en euros a convertir
     * @return La cantidad convertida a la divisa actual
     */
    public double convertirDesdeEUR(double cantidad) {
        return cantidad * tasaConversion;
    }

    /**
     * Convierte una cantidad desde esta divisa a euros.
     * @param cantidad La cantidad a convertir a euros
     * @return La cantidad convertida a euros
     */
    public double convertirAEUR(double cantidad) {
        return cantidad / tasaConversion;
    }
}
