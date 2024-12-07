/**
 * Enum que representa los tipos de vehículos.
 * Define características específicas como el número de ruedas, 
 * tipo de carnet y carga máxima.
 */
public enum TipoVehiculo {
    MOTOCICLETA(2, "A", 150),
    COCHE(4, "B", 500),
    CAMION(6, "C", 2000);

    private final int numRuedas;
    private final String carnetRequerido;
    private final int cargaMaxima;

    TipoVehiculo(int numRuedas, String carnetRequerido, int cargaMaxima) {
        this.numRuedas = numRuedas;
        this.carnetRequerido = carnetRequerido;
        this.cargaMaxima = cargaMaxima;
    }

    /**
     * Obtiene el número de ruedas del tipo de vehículo.
     * @return número de ruedas.
     */
    public int getNumRuedas() {
        return numRuedas;
    }

    /**
     * Obtiene el tipo de carnet requerido para conducir el vehículo.
     * @return carnet requerido.
     */
    public String getCarnetRequerido() {
        return carnetRequerido;
    }

    /**
     * Obtiene la carga máxima permitida del vehículo.
     * @return carga máxima.
     */
    public int getCargaMaxima() {
        return cargaMaxima;
    }
}