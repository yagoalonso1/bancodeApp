import java.io.*;
import java.util.*;

public class ClinicManagementApp {
    private Deque<Patient> waitingQueue;
    private List<Patient> operatedPatients;
    private Scanner scanner;

    public ClinicManagementApp() {
        this.waitingQueue = new ArrayDeque<>();
        this.operatedPatients = readPatientsFromFile();
        this.scanner = new Scanner(System.in);
    }

    private String getValidName(String prompt) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (input.matches("[a-zA-ZÀ-ÿ\\s]+")) {
                break;
            } else {
                System.out.println("Nom no vàlid. Només lletres, si us plau.");
            }
        }
        return input;
    }

    private int getValidAge(String prompt) {
        int age;
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                age = scanner.nextInt();
                scanner.nextLine();
                if (age > 0 && age <= 120) {
                    break;
                } else {
                    System.out.println("L’edat ha de ser entre 1 i 120.");
                }
            } else {
                System.out.println("Entrada no vàlida. Introduïu un número.");
                scanner.nextLine();
            }
        }
        return age;
    }

    private String getValidString(String prompt) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                break;
            } else {
                System.out.println("Entrada no vàlida. Introduïu text.");
            }
        }
        return input;
    }

    private int getValidPriority(String prompt) {
        int priority;
        while (true) {
            System.out.print(prompt + " (1: Baixa, 2: Mitjana, 3: Alta, 4: Molt Alta): ");
            if (scanner.hasNextInt()) {
                priority = scanner.nextInt();
                scanner.nextLine();
                if (priority >= 1 && priority <= 4) {
                    break;
                } else {
                    System.out.println("Prioritat no vàlida. Seleccioneu 1, 2, 3 o 4.");
                }
            } else {
                System.out.println("Entrada no vàlida. Introduïu un número.");
                scanner.nextLine();
            }
        }
        return priority;
    }

    public void addPatient() {
        String name = getValidName("Nom del pacient: ");
        int age = getValidAge("Edat del pacient: ");
        String reason = getValidString("Motiu de la visita: ");
        int priority = getValidPriority("Seleccioneu la prioritat");

        Patient newPatient = new Patient(name, age, reason, false, priority);

        if (priority == 4) {
            waitingQueue.addFirst(newPatient); // Prioridad muy alta al frente
        } else if (priority == 3) {
            waitingQueue.addFirst(newPatient);
        } else {
            waitingQueue.addLast(newPatient);
        }

        System.out.println("Pacient afegit amb prioritat " + priority + ".");
    }

    public void sendToOperatingRoom() {
        if (waitingQueue.isEmpty()) {
            System.out.println("No hi ha pacients en llista d'espera.");
            return;
        }

        Optional<Patient> highPriorityPatient = waitingQueue.stream()
                .filter(patient -> patient.getPriority() == 4)
                .findFirst();

        Patient selectedPatient;

        if (highPriorityPatient.isPresent()) {
            selectedPatient = highPriorityPatient.get();
            waitingQueue.remove(selectedPatient);
            System.out.println("Pacient amb prioritat molt alta enviat al quiròfan: " + selectedPatient.getName());
        } else {
            System.out.println("Llista de pacients disponibles:");
            int index = 1;
            for (Patient patient : waitingQueue) {
                System.out.println(index + ". " + patient);
                index++;
            }
            System.out.print("Seleccioneu el número del pacient a enviar al quiròfan: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice >= 1 && choice <= waitingQueue.size()) {
                selectedPatient = new ArrayList<>(waitingQueue).get(choice - 1);
                waitingQueue.remove(selectedPatient);
            } else {
                System.out.println("Selecció no vàlida.");
                return;
            }
        }

        boolean survived = determineSurvival(selectedPatient.getPriority());
        selectedPatient.setSurvived(survived);
        operatedPatients.add(selectedPatient);

        System.out.println(selectedPatient.getName() +
                (survived ? " ha sobreviscut." : " no ha sobreviscut."));
        writePatientsToFile(operatedPatients);
    }

    private boolean determineSurvival(int priority) {
        Random random = new Random();
        int chance = random.nextInt(100);

        switch (priority) {
            case 1:
                return chance >= 10;
            case 2:
                return chance >= 30;
            case 3:
                return chance >= 50;
            case 4:
                return chance >= 90;
            default:
                throw new IllegalArgumentException("Prioritat desconeguda.");
        }
    }

    public void displayWaitingQueue() {
        if (waitingQueue.isEmpty()) {
            System.out.println("No hi ha pacients en la llista d'espera.");
        } else {
            System.out.println("Pacients en llista d'espera:");
            for (Patient patient : waitingQueue) {
                System.out.println(patient);
            }
        }
    }

    public void displayStatistics() {
        long survivedCount = operatedPatients.stream().filter(Patient::hasSurvived).count();
        int totalPatients = operatedPatients.size();
        double survivalRate = totalPatients == 0 ? 0 : (survivedCount * 100.0) / totalPatients;

        System.out.println("Pacients operats: " + totalPatients);
        System.out.println("Pacients que han sobreviscut: " + survivedCount);
        System.out.printf("Percentatge de supervivència: %.2f%%\n", survivalRate);
    }

    private void writePatientsToFile(List<Patient> patients) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("patients.csv"))) {
            for (Patient patient : patients) {
                writer.write(patient.toCSV());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error escrivint al fitxer: " + e.getMessage());
        }
    }

    private List<Patient> readPatientsFromFile() {
        List<Patient> patients = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("patients.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                patients.add(Patient.fromCSV(line));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fitxer no trobat. Es crearà un de nou en guardar.");
        } catch (IOException e) {
            System.out.println("Error llegint el fitxer: " + e.getMessage());
        }
        return patients;
    }

    public void run() {
        while (true) {
            System.out.println("\n--- Menú Principal ---");
            System.out.println("1. Afegir pacient");
            System.out.println("2. Enviar pacient al quiròfan");
            System.out.println("3. Consultar pacients en llista d'espera");
            System.out.println("4. Consultar estadístiques");
            System.out.println("5. Sortir");
            int option = getValidAge("Seleccioneu una opció: ");

            switch (option) {
                case 1 -> addPatient();
                case 2 -> sendToOperatingRoom();
                case 3 -> displayWaitingQueue();
                case 4 -> displayStatistics();
                case 5 -> {
                    System.out.println("Sortint de l'aplicació.");
                    return;
                }
                default -> System.out.println("Opció no vàlida.");
            }
        }
    }
}