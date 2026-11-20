import java.util.HashMap;
import java.util.Map;

public class Student {

    private int id;
    private String name;
    private HashMap<String, Integer> grades;

    public Student(int id, String name) {

        if (id <= 0) {
            throw new IllegalArgumentException("Invalid student ID");
        }

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        this.id = id;
        this.name = name;
        this.grades = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Map<String, Integer> getGrades() {
        return new HashMap<>(grades);
    }

    public void setName(String name) {

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid name");
        }

        this.name = name;
    }

    public void addGrade(String subject, int score) {

        if (subject == null || subject.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid subject");
        }

        if (score < 0 || score > 100) {
            throw new IllegalArgumentException(
                "Marks must be between 0 and 100"
            );
        }

        grades.put(subject, score);
    }

    public double calculateAverage() {

        if (grades.isEmpty()) {
            return 0;
        }

        int total = 0;

        for (int score : grades.values()) {
            total += score;
        }

        return (double) total / grades.size();
    }

    public double calculateGPA() {
        return calculateAverage() / 25.0;
    }
}