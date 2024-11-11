package lab_1;

import java.util.Scanner;

public class UniversityManagementSystem {
    private final University university = new University();
    private final Scanner scanner = new Scanner(System.in);

    public void run() {
        while (true) {
            displayMenu();
            switch (scanner.nextLine().trim().toLowerCase()) {
                case "1" -> university.addFaculty();
                case "2" -> university.displayFaculties();
                case "3" -> university.addStudentToFaculty();
                case "4" -> university.graduateStudent();
                case "5" -> university.displayStudentsInFaculty();
                case "q" -> {
                    System.out.println("Exiting program.");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void displayMenu() {
        System.out.println("""
                
                1 - Add Faculty
                2 - View Faculties
                3 - Add Student to Faculty
                4 - Graduate Student
                5 - View Students in Faculty
                q - Quit Program
                
                Select an option:""");
    }

    public static void main(String[] args) {
        new UniversityManagementSystem().run();
    }
}