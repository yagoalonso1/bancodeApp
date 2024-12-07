import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private static final String FILE_NAME = "patients.csv";

    public static void writePatientsToFile(List<Patient> patients) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Patient patient : patients) {
                writer.write(patient.toCSV());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error escrivint al fitxer: " + e.getMessage());
        }
    }

    public static List<Patient> readPatientsFromFile() {
        List<Patient> patients = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
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
}