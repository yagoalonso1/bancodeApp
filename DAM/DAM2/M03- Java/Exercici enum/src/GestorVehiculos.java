import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Clase que gestiona la lista de vehículos.
 */
public class GestorVehiculos {
    private final ArrayList<Vehicle> vehiculos = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Inicia el menú de gestión de vehículos.
     */
    public void iniciar() {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opción del menú:");
            switch (opcion) {
                case 1:
                    agregarVehiculo();
                    break;
                case 2:
                    listarVehiculos();
                    break;
                case 3:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida, intente de nuevo.");
            }
        } while (opcion != 3);
    }

    /**
     * Muestra el menú principal.
     */
    private void mostrarMenu() {
        System.out.println("\n--- Menú de Gestión de Vehículos ---");
        System.out.println("1. Agregar vehículo");
        System.out.println("2. Listar vehículos");
        System.out.println("3. Salir");
    }

    /**
     * Lee un número entero de la entrada estándar con validación.
     * @param mensaje Mensaje a mostrar al usuario.
     * @return Entero introducido por el usuario.
     */
    private int leerEntero(String mensaje) {
        int numero = -1;
        boolean valido;
        do {
            System.out.print(mensaje + " ");
            try {
                numero = scanner.nextInt();
                valido = true;
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número entero.");
                valido = false;
            } finally {
                scanner.nextLine(); // Limpiar buffer
            }
        } while (!valido);
        return numero;
    }

    /**
     * Lee una cadena de texto de la entrada estándar.
     * @param mensaje Mensaje a mostrar al usuario.
     * @return Cadena introducida por el usuario.
     */
    private String leerCadena(String mensaje) {
        System.out.print(mensaje + " ");
        return scanner.nextLine();
    }

    /**
     * Valida si la matrícula cumple con el formato 4 números y 3 letras.
     * @param matricula Matrícula a validar.
     * @return true si es válida, false en caso contrario.
     */
    private boolean validarMatricula(String matricula) {
        String regex = "^[0-9]{4}[A-Z]{3}$";
        return Pattern.matches(regex, matricula);
    }

    /**
     * Agrega un vehículo a la lista.
     */
    private void agregarVehiculo() {
        System.out.println("\n--- Agregar Vehículo ---");
        System.out.println("Tipos disponibles:");
        for (TipoVehiculo tipo : TipoVehiculo.values()) {
            System.out.println("- " + tipo);
        }

        TipoVehiculo tipo = null;
        boolean valido;
        do {
            try {
                String tipoInput = leerCadena("Ingrese el tipo de vehículo:");
                tipo = TipoVehiculo.valueOf(tipoInput.toUpperCase());
                valido = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: Tipo de vehículo no válido.");
                valido = false;
            }
        } while (!valido);

        String matricula;
        do {
            matricula = leerCadena("Ingrese la matrícula del vehículo (4 números y 3 letras):");
            if (!validarMatricula(matricula)) {
                System.out.println("Error: La matrícula no cumple el formato (4 números y 3 letras).");
            }
        } while (!validarMatricula(matricula));

        vehiculos.add(new Vehicle(tipo, matricula));
        System.out.println("Vehículo agregado con éxito.");
    }

    /**
     * Lista todos los vehículos en la colección.
     */
    private void listarVehiculos() {
        System.out.println("\n--- Listado de Vehículos ---");
        if (vehiculos.isEmpty()) {
            System.out.println("No hay vehículos registrados.");
        } else {
            for (Vehicle vehiculo : vehiculos) {
                System.out.println(vehiculo);
            }
        }
    }
}