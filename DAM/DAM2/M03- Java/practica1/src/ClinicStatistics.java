import java.util.List;

public class ClinicStatistics {
    private List<Patient> operatedPatients;

    public ClinicStatistics(List<Patient> operatedPatients) {
        this.operatedPatients = operatedPatients;
    }

    public int getTotalPatients() {
        return operatedPatients.size();
    }

    public long getSurvivedCount() {
        return operatedPatients.stream().filter(Patient::hasSurvived).count();
    }

    public double getSurvivalRate() {
        if (operatedPatients.isEmpty()) return 0;
        return (getSurvivedCount() * 100.0) / getTotalPatients();
    }
}