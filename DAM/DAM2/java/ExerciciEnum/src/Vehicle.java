public class Vehicle {
    private VehicleType tipus;
    private String identificador;
    private int anyFabricacio;

    public Vehicle(VehicleType tipus, String identificador, int anyFabricacio) {
        this.tipus = tipus;
        this.identificador = identificador;
        this.anyFabricacio = anyFabricacio;
    }

    public VehicleType getTipus() {
        return tipus;
    }

    public String getIdentificador() {
        return identificador;
    }

    public int getAnyFabricacio() {
        return anyFabricacio;
    }

    @Override
    public String toString() {
        return "Vehicle {" +
                "Tipus = " + tipus +
                ", Identificador = '" + identificador + '\'' +
                ", Any de fabricació = " + anyFabricacio +
                ", Rodes = " + tipus.getRodes() +
                ", Carnet requerit = '" + tipus.getCarnet() + '\'' +
                ", Càrrega màxima = " + tipus.getCarregaMaxima() +
                '}';
    }
}