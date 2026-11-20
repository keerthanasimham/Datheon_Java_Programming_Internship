import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        GradeManager manager = new GradeManager();

        // Load students from CSV file
        CSVLoader.loadStudents("sample_student_grades.csv", manager);

        int choice = 0;

        do {

            System.out.println("\n====================================");
            System.out.println(" STUDENT GRADE MANAGEMENT SYSTEM");
            System.out.println("====================================");

            System.out.println("1. Add Student");
            System.out.println("2. Record Marks");
            System.out.println("3. Display All Students");
            System.out.println("4. Calculate Class Average");
            System.out.println("5. Calculate Class Median");
            System.out.println("6. Generate Report Card");
            System.out.println("7. Exit");

            System.out.print("\nEnter your choice: ");

            if (!sc.hasNextInt()) {

                System.out.println("Please enter a valid number.");

                sc.nextLine();

                continue;
            }

            choice = sc.nextInt();

            switch (choice) {

                // Add Student
                case 1:

                    System.out.print("Enter Student ID: ");
                    int id = sc.nextInt();

                    sc.nextLine();

                    System.out.print("Enter Student Name: ");
                    String name = sc.nextLine();

                    try {

                        Student student = new Student(id, name);

                        manager.addStudent(student);

                    } catch (IllegalArgumentException e) {

                        System.out.println(e.getMessage());
                    }

                    break;

                // Record Marks
                case 2:

                    System.out.print("Enter Student ID: ");
                    int studentId = sc.nextInt();

                    sc.nextLine();

                    System.out.print("Enter Subject: ");
                    String subject = sc.nextLine();

                    System.out.print("Enter Marks: ");
                    int marks = sc.nextInt();

                    manager.recordMarks(studentId, subject, marks);

                    break;

                // Display Students
                case 3:

                    manager.displayStudents();

                    break;

                // Class Average
                case 4:

                    manager.calculateClassAverage();

                    break;

                // Class Median
                case 5:

                    manager.calculateMedian();

                    break;

                // Individual Report Card
                case 6:

                    System.out.print("Enter Student ID: ");

                    int reportId = sc.nextInt();

                    manager.generateReportCard(reportId);

                    break;

                // Exit
                case 7:

                    manager.saveStudentsToCSV(
                        "sample_student_grades.csv"
                    );

                    System.out.println(
                        "Thank you for using Student Grade Management System!"
                    );

                    break;

                default:

                    System.out.println("Invalid choice!");
            }

        } while (choice != 7);

        sc.close();
    }
}