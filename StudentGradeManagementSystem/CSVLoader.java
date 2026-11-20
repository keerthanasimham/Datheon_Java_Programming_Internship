import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVLoader {

    public static void loadStudents(
            String filename,
            GradeManager manager) {

        try (BufferedReader br = new BufferedReader(
                new FileReader(filename))) {

            String line;

            // Skip CSV header
            br.readLine();

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",", -1);

                if (data.length != 6) {
                    System.out.println("Invalid CSV row: " + line);
                    continue;
                }

                try {

                    int id = Integer.parseInt(data[0].trim());

                    String name = data[1].trim();

                    Student student = new Student(id, name);

                    student.addGrade(
                        "Math",
                        Integer.parseInt(data[2].trim())
                    );

                    student.addGrade(
                        "Science",
                        Integer.parseInt(data[3].trim())
                    );

                    student.addGrade(
                        "English",
                        Integer.parseInt(data[4].trim())
                    );

                    student.addGrade(
                        "Computer",
                        Integer.parseInt(data[5].trim())
                    );

                    manager.addStudent(student);

                } catch (IllegalArgumentException e) {

                    System.out.println(
                        "Invalid student data: " + e.getMessage()
                    );
                }
            }

            System.out.println("CSV data loaded successfully!");

        } catch (IOException e) {

            System.out.println(
                "Error reading CSV: " + e.getMessage()
            );
        }
    }
}