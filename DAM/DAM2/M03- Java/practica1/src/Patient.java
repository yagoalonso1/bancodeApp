public class Patient {
    private String name;
    private int age;
    private String reasonForVisit;
    private boolean survived;
    private int priority; // 1: Baixa, 2: Mitjana, 3: Alta, 4: Molt Alta

    public Patient(String name, int age, String reasonForVisit, boolean survived, int priority) {
        this.name = name;
        this.age = age;
        this.reasonForVisit = reasonForVisit;
        this.survived = survived;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getReasonForVisit() {
        return reasonForVisit;
    }

    public boolean hasSurvived() {
        return survived;
    }

    public void setSurvived(boolean survived) {
        this.survived = survived;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        String priorityString = switch (priority) {
            case 1 -> "Baixa";
            case 2 -> "Mitjana";
            case 3 -> "Alta";
            case 4 -> "Molt Alta";
            default -> "Desconeguda";
        };
        return "Patient{name='" + name + "', age=" + age +
                ", reasonForVisit='" + reasonForVisit + "', priority=" + priorityString +
                ", survived=" + survived + "}";
    }

    public String toCSV() {
        return name + "," + age + "," + reasonForVisit + "," + survived + "," + priority;
    }

    public static Patient fromCSV(String csvLine) {
        String[] fields = csvLine.split(",");
        String name = fields[0];
        int age = Integer.parseInt(fields[1]);
        String reason = fields[2];
        boolean survived = Boolean.parseBoolean(fields[3]);
        int priority = Integer.parseInt(fields[4]);
        return new Patient(name, age, reason, survived, priority);
    }
}