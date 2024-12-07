/**
 * Clase que representa un vehículo con un tipo y una matrícula.
 */
public class Vehicle {
    private TipoVehiculo tipo;
    private String matricula;

    /**
     * Constructor de la clase Vehicle.
     * @param tipo Tipo de vehículo.
     * @param matricula Matrícula del vehículo.
     */
    public Vehicle(TipoVehiculo tipo, String matricula) {
        this.tipo = tipo;
        this.matricula = matricula;
    }

    /**
     * Obtiene el tipo del vehículo.
     * @return tipo de vehículo.
     */
    public TipoVehiculo getTipo() {
        return tipo;
    }

    /**
     * Obtiene la matrícula del vehículo.
     * @return matrícula del vehículo.
     */
    public String getMatricula() {
        return matricula;
    }

    @Override
    public String toString() {
        return String.format("Matrícula: %s, Tipo: %s, Ruedas: %d, Carnet: %s, Carga Máxima: %d kg",
                matricula,
                tipo,
                tipo.getNumRuedas(),
                tipo.getCarnetRequerido(),
                tipo.getCargaMaxima());
    }
}