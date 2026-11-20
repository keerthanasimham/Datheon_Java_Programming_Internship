import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GradeManager {

    private ArrayList<Student> students = new ArrayList<>();

    // Add Student
    public void addStudent(Student student) {

        if (findStudent(student.getId()) != null) {
            System.out.println("Student ID already exists!");
            return;
        }

        students.add(student);
        System.out.println("Student added successfully!");
    }

    // Find Student
    public Student findStudent(int id) {

        for (Student student : students) {

            if (student.getId() == id) {
                return student;
            }
        }

        return null;
    }

    // Record Marks
    public void recordMarks(int id, String subject, int marks) {

        Student student = findStudent(id);

        if (student == null) {
            System.out.println("Student not found!");
            return;
        }

        try {
            student.addGrade(subject, marks);
            System.out.println("Marks recorded successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // Display All Students
    public void displayStudents() {

        System.out.println("\n========== STUDENT REPORT ==========");

        System.out.printf(
            "%-8s %-20s %-12s %-10s%n",
            "ID", "NAME", "AVERAGE", "GPA"
        );

        for (Student student : students) {

            System.out.printf(
                "%-8d %-20s %-12.2f %-10.2f%n",
                student.getId(),
                student.getName(),
                student.calculateAverage(),
                student.calculateGPA()
            );
        }
    }

    // Calculate Class Average
    public void calculateClassAverage() {

        if (students.isEmpty()) {
            System.out.println("No student records!");
            return;
        }

        double total = 0;

        for (Student student : students) {
            total += student.calculateAverage();
        }

        System.out.printf(
            "Class Average: %.2f%n",
            total / students.size()
        );
    }

    // Calculate Class Median
    public void calculateMedian() {

        if (students.isEmpty()) {
            System.out.println("No student records!");
            return;
        }

        List<Double> averages = new ArrayList<>();

        for (Student student : students) {
            averages.add(student.calculateAverage());
        }

        averages.sort(Comparator.naturalOrder());

        int size = averages.size();

        double median;

        if (size % 2 == 0) {

            median = (
                averages.get(size / 2 - 1)
                + averages.get(size / 2)
            ) / 2;

        } else {

            median = averages.get(size / 2);
        }

        System.out.printf("Class Median: %.2f%n", median);
    }

    // Generate Individual Report Card
    public void generateReportCard(int id) {

        Student student = findStudent(id);

        if (student == null) {
            System.out.println("Student not found!");
            return;
        }

        System.out.println("\n========== REPORT CARD ==========");

        System.out.println("Student ID: " + student.getId());
        System.out.println("Student Name: " + student.getName());

        System.out.println("\nSubject             Marks");

        for (String subject : student.getGrades().keySet()) {

            System.out.printf(
                "%-20s %d%n",
                subject,
                student.getGrades().get(subject)
            );
        }

        System.out.println("---------------------------------");

        System.out.printf(
            "Average: %.2f%n",
            student.calculateAverage()
        );

        System.out.printf(
            "GPA: %.2f / 4.0%n",
            student.calculateGPA()
        );

        System.out.println("=================================");
    }

    // Save Students to CSV
    public void saveStudentsToCSV(String filename) {

        try (FileWriter writer = new FileWriter(filename)) {

            writer.write(
                "student_id,name,math_score,science_score,english_score,computer_score\n"
            );

            for (Student student : students) {

                var grades = student.getGrades();

                writer.write(
                    student.getId() + "," +
                    student.getName() + "," +
                    grades.getOrDefault("Math", 0) + "," +
                    grades.getOrDefault("Science", 0) + "," +
                    grades.getOrDefault("English", 0) + "," +
                    grades.getOrDefault("Computer", 0) + "\n"
                );
            }

            System.out.println("Student records saved successfully!");

        } catch (IOException e) {

            System.out.println("Error saving CSV: " + e.getMessage());
        }
    }
}