import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Custom exception for handling marks greater than 100
class InvalidMarkException extends Exception {
    public InvalidMarkException(String message) {
        super(message);
    }
}

// Class to represent a student
class Student {
    private String name;
    private List<String> subjects;
    private List<Integer> marks;

    public Student(String name) {
        this.name = name;
        this.subjects = new ArrayList<>();
        this.marks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public List<Integer> getMarks() {
        return marks;
    }

    public void addSubjectAndMark(String subject, int mark) throws InvalidMarkException {
        if (mark > 100) {
            throw new InvalidMarkException("Mark for " + subject + " cannot be greater than 100.  Entered mark was: " + mark);
        }
        this.subjects.add(subject);
        this.marks.add(mark);
    }

    public double calculateAverage() {
        if (marks.isEmpty()) {
            return 0.0;
        }
        int total = 0;
        for (int mark : marks) {
            total += mark;
        }
        return (double) total / marks.size();
    }

    public String getSubjectAndMarks() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < subjects.size(); i++) {
            sb.append(subjects.get(i)).append(":").append(marks.get(i)).append(" ");
        }
        return sb.toString();
    }


    @Override
    public String toString() {
        return "Name: " + name + ", Subjects and Marks: " + getSubjectAndMarks() + ", Average Score: " + calculateAverage();
    }
}

// Main class for the application
public class StudentManagementpp {
    private static ArrayList<Student> students = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Main application loop
        while (true) {
            displayMenu();
            int choice = getUserChoice();
            processChoice(choice);
        }
    }

    // Method to display the main menu
    private static void displayMenu() {
        System.out.println("\nStudent Management Application");
        System.out.println("1. Add Student");
        System.out.println("2. Add Subject and Marks for a Student");
        System.out.println("3. View All Students");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }

    // Method to get the user's menu choice
    private static int getUserChoice() {
        while (true) {
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 1 && choice <= 4) {
                    return choice;
                } else {
                    System.out.println("Invalid input. Please enter a number between 1 and 4.");
                    System.out.print("Enter your choice: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }

    // Method to process the user's menu choice
    private static void processChoice(int choice) {
        switch (choice) {
            case 1:
                addStudent();
                break;
            case 2:
                addSubjectAndMarks();
                break;
            case 3:
                viewAllStudents();
                break;
            case 4:
                System.out.println("Exiting application.");
                System.exit(0);
                break; // Added break here, although it's not strictly necessary since System.exit(0) terminates the program.
            default:
                System.out.println("Invalid choice. Please try again."); //This part will never be reached
        }
    }

    // Method to add a new student
    private static void addStudent() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        Student student = new Student(name);
        students.add(student);
        System.out.println("Student " + name + " added successfully.");
    }

    // Method to add subject and marks for a student
    private static void addSubjectAndMarks() {
        if (students.isEmpty()) {
            System.out.println("No students available. Please add a student first.");
            return;
        }

        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        Student student = findStudentByName(name);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        while (true) {
            System.out.print("Enter subject (or 'done' to finish): ");
            String subject = scanner.nextLine();
            if (subject.equalsIgnoreCase("done")) {
                break;
            }

            System.out.print("Enter marks for " + subject + ": ");
            try {
                int mark = Integer.parseInt(scanner.nextLine());
                student.addSubjectAndMark(subject, mark);
                System.out.println("Subject and marks added for " + student.getName());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input for marks. Please enter a valid number.");
            } catch (InvalidMarkException e) {
                System.err.println("Error: " + e.getMessage());
            }        }
    }

    // Method to view all students
    private static void viewAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students available.");
            return;
        }
        System.out.println("\n--- Student Records ---");
        for (Student student : students) {
            System.out.println(student);
        }
    }

    // Method to find a student by their name
    private static Student findStudentByName(String name) {
        for (Student student : students) {
            if (student.getName().equalsIgnoreCase(name)) {
                return student;
            }
        }
        return null;
    }
}
