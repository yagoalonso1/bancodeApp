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
            opcio = llegirEnter("Selecciona una opció (1-3): ", 1, 3);
            switch (opcio) {
                case 1 -> afegirVehicle();
                case 2 -> llistarVehicles();
                case 3 -> System.out.println("Sortint del programa...");
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
            System.out.printf("%d. %s (Rodes: %d, Carnet: %s, Càrrega: %d)%n",
                    tipus.ordinal() + 1, tipus, tipus.getRodes(), tipus.getCarnet(), tipus.getCarregaMaxima());
        }

        int tipusIndex = llegirEnter("Selecciona el tipus de vehicle (1-3): ", 1, VehicleType.values().length) - 1;
        VehicleType tipus = VehicleType.values()[tipusIndex];

        scanner.nextLine(); // Netegem el buffer
        String identificador = llegirText("Introdueix el nom o identificador del vehicle: ");
        int anyFabricacio = llegirEnter("Introdueix l'any de fabricació (1900-2100): ", 1900, 2100);

        vehicles.add(new Vehicle(tipus, identificador, anyFabricacio));
        System.out.println("Vehicle afegit correctament.");
    }

    private void llistarVehicles() {
        System.out.println("\n--- Llistat de Vehicles ---");
        if (vehicles.isEmpty()) {
            System.out.println("No hi ha vehicles registrats.");
        } else {
            for (int i = 0; i < vehicles.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, vehicles.get(i));
            }
        }
    }

    private int llegirEnter(String missatge, int min, int max) {
        int valor = -1;
        boolean valid = false;
        while (!valid) {
            System.out.print(missatge);
            try {
                valor = scanner.nextInt();
                if (valor >= min && valor <= max) {
                    valid = true;
                } else {
                    System.out.printf("El número ha d'estar entre %d i %d.%n", min, max);
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada no vàlida. Introdueix un número.");
                scanner.next(); // Netegem el buffer
            }
        }
        return valor;
    }

    private String llegirText(String missatge) {
        System.out.print(missatge);
        return scanner.nextLine().trim();
    }

    public static void main(String[] args) {
        VehicleManager manager = new VehicleManager();
        manager.iniciar();
    }
}