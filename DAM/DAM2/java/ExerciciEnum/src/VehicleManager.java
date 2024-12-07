import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class VehicleManager {
    private final ArrayList<Vehicle> vehicles = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    public void iniciar() {
        int opcio;
        do {
            mostrarMenu();
            opcio = llegirEnter("Selecciona una opció: ");
            switch (opcio) {
                case 1:
                    afegirVehicle();
                    break;

                case 2: llistarVehicles();
                break;
                case 3: System.out.println("Sortint del programa..."); break;
                default:System.out.println("Opció no vàlida. Intenta-ho de nou."); break;
            }
        } while (opcio != 3);
    }

    private void mostrarMenu() {
        System.out.println("\n--- Gestió de Vehicles ---");
        System.out.println("1. Afegir vehicle");
        System.out.println("2. Llistar vehicles");
        System.out.println("3. Sortir");
    }

    private void afegirVehicle() {
        System.out.println("\n--- Afegir Vehicle ---");
        for (VehicleType tipus : VehicleType.values()) {
            System.out.printf("%d. %s%n", tipus.ordinal() + 1, tipus);
        }
        int tipusIndex = llegirEnter("Selecciona el tipus de vehicle: ") - 1;
        if (tipusIndex < 0 || tipusIndex >= VehicleType.values().length) {
            System.out.println("Tipus no vàlid. Tornant al menú principal.");
            return;
        }

        VehicleType tipus = VehicleType.values()[tipusIndex];
        scanner.nextLine(); // Netegem el buffer
        String identificador = llegirText("Introdueix el nom o identificador del vehicle: ");
        int anyFabricacio = llegirEnter("Introdueix l'any de fabricació: ");
        vehicles.add(new Vehicle(tipus, identificador, anyFabricacio));
        System.out.println("Vehicle afegit correctament.");
    }

    private void llistarVehicles() {
        System.out.println("\n--- Llistat de Vehicles ---");
        if (vehicles.isEmpty()) {
            System.out.println("No hi ha vehicles registrats.");
        } else {
            for (Vehicle vehicle : vehicles) {
                System.out.println(vehicle);
            }
        }
    }

    private int llegirEnter(String missatge) {
        int valor = -1;
        boolean valid = false;
        while (!valid) {
            System.out.print(missatge);
            try {
                valor = scanner.nextInt();
                valid = true;
            } catch (InputMismatchException e) {
                System.out.println("Entrada no vàlida. Introdueix un número.");
                scanner.next(); // Netegem el buffer
            }
        }
        return valor;
    }

    private String llegirText(String missatge) {
        System.out.print(missatge);
        return scanner.nextLine();
    }

    public static void main(String[] args) {
        VehicleManager manager = new VehicleManager();
        manager.iniciar();
    }
}