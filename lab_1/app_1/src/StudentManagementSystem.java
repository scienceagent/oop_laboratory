import java.io.*;
import java.util.*;

class Student implements Serializable {
    private String email;
    private String name;
    private String dateOfBirth;

    public Student(String email, String name, String dateOfBirth) {
        this.email = email;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Student{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                '}';
    }
}

class Faculty implements Serializable {
    private String name;
    private String abbreviation;
    private String field;
    private List<Student> enrolledStudents = new ArrayList<>();
    private List<Student> graduatedStudents = new ArrayList<>();

    public Faculty(String name, String abbreviation, String field) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.field = field;
    }

    public String getName() {
        return name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getField() {
        return field;
    }

    public void enrollStudent(Student student) {
        enrolledStudents.add(student);
    }

    public void graduateStudent(String email) throws NoSuchElementException {
        Optional<Student> student = enrolledStudents.stream()
                .filter(s -> s.getEmail().equals(email))
                .findFirst();
        if (student.isPresent()) {
            enrolledStudents.remove(student.get());
            graduatedStudents.add(student.get());
        } else {
            throw new NoSuchElementException("Student not found");
        }
    }

    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public List<Student> getGraduatedStudents() {
        return graduatedStudents;
    }

    public boolean hasStudent(String email) {
        return enrolledStudents.stream().anyMatch(s -> s.getEmail().equals(email)) ||
                graduatedStudents.stream().anyMatch(s -> s.getEmail().equals(email));
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "name='" + name + '\'' +
                ", abbreviation='" + abbreviation + '\'' +
                ", field='" + field + '\'' +
                '}';
    }
}

class University implements Serializable {
    private List<Faculty> faculties = new ArrayList<>();

    public void addFaculty(String name, String abbreviation, String field) {
        faculties.add(new Faculty(name, abbreviation, field));
    }

    public Faculty getFacultyByAbbreviation(String abbreviation) {
        return faculties.stream()
                .filter(f -> f.getAbbreviation().equals(abbreviation))
                .findFirst()
                .orElse(null);
    }

    public Faculty findFacultyByStudentEmail(String email) {
        return faculties.stream()
                .filter(f -> f.hasStudent(email))
                .findFirst()
                .orElse(null);
    }

    public List<Faculty> getAllFaculties() {
        return faculties;
    }

    public List<Faculty> getFacultiesByField(String field) {
        return faculties.stream()
                .filter(f -> f.getField().equals(field))
                .toList();
    }

    public void enrollStudent(String facultyAbbr, Student student) {
        Faculty faculty = getFacultyByAbbreviation(facultyAbbr);
        if (faculty != null) {
            faculty.enrollStudent(student);
        }
    }

    public void graduateStudent(String email) throws NoSuchElementException {
        Faculty faculty = findFacultyByStudentEmail(email);
        if (faculty != null) {
            faculty.graduateStudent(email);
        } else {
            throw new NoSuchElementException("Student not found");
        }
    }
}

class SaveManager {
    private static final String FILE_NAME = "university.ser";

    public static void save(University university) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(university);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static University load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (University) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new University();
        }
    }
}

public class StudentManagementSystem {

    private static University university = SaveManager.load();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            showMainMenu();
            String input = scanner.nextLine().trim().toLowerCase();
            switch (input) {
                case "g":
                    handleGeneralOperations();
                    break;
                case "f":
                    handleFacultyOperations();
                    break;
                case "s":
                    // handleStudentOperations(); // Placeholder for student operations
                    break;
                case "q":
                    SaveManager.save(university);
                    System.out.println("Program exited.");
                    return;
                default:
                    System.out.println("Invalid input. Please try again.");
            }
        }
    }

    private static void showMainMenu() {
        System.out.println("Welcome to TUM's student management system!");
        System.out.println("What do you want to do?");
        System.out.println("g - General operations");
        System.out.println("f - Faculty operations");
        System.out.println("s - Student operations");
        System.out.println("q - Quit Program");
        System.out.print("your input> ");
    }

    private static void handleGeneralOperations() {
        while (true) {
            System.out.println("\nGeneral operations");
            System.out.println("What do you want to do?");
            System.out.println("nf/<faculty name>/<faculty abbreviation>/<field> - create faculty");
            System.out.println("ss/<student email> - search student and show faculty");
            System.out.println("df - display faculties");
            System.out.println("df/<field> - display all faculties of a field");
            System.out.println("b - Back");
            System.out.println("q - Quit Program");
            System.out.print("your input> ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("b")) {
                break;
            } else if (input.equals("q")) {
                SaveManager.save(university);
                System.out.println("Program exited.");
                System.exit(0);
            } else if (input.startsWith("nf/")) {
                String[] parts = input.substring(3).split("/");
                if (parts.length == 3) {
                    university.addFaculty(parts[0], parts[1], parts[2]);
                    System.out.println("Faculty created.");
                } else {
                    System.out.println("Invalid input format.");
                }
            } else if (input.startsWith("ss/")) {
                String email = input.substring(3);
                Faculty faculty = university.findFacultyByStudentEmail(email);
                if (faculty != null) {
                    System.out.println("Student found in faculty: " + faculty.getName());
                } else {
                    System.out.println("Student not found.");
                }
            } else if (input.equals("df")) {
                for (Faculty faculty : university.getAllFaculties()) {
                    System.out.println(faculty.getName());
                }
            } else if (input.startsWith("df/")) {
                String field = input.substring(3);
                for (Faculty faculty : university.getFacultiesByField(field)) {
                    System.out.println(faculty.getName());
                }
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }

    private static void handleFacultyOperations() {
        while (true) {
            System.out.println("\nFaculty operations");
            System.out.println("What do you want to do?");
            System.out.println("ns/<faculty abbreviation>/<first name>/<last name>/<email>/<day>/<month>/<year> - create student");
            System.out.println("gs/<email> - (g)raduate (s)tudent");
            System.out.println("ds/<faculty abbreviation> - (d)isplay enrolled (s)tudents");
            System.out.println("dg/<faculty abbreviation> - (d)isplay (g)raduated students");
            System.out.println("bf/<faculty abbreviation>/<email> - check if student (b)elongs to (f)aculty");
            System.out.println("b - Back");
            System.out.println("q - Quit Program");
            System.out.print("your input> ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("b")) {
                break;
            } else if (input.equals("q")) {
                SaveManager.save(university);
                System.out.println("Program exited.");
                System.exit(0);
            } else if (input.startsWith("ns/")) {
                String[] parts = input.substring(3).split("/");
                if (parts.length == 7) {
                    String facultyAbbr = parts[0];
                    String firstName = parts[1];
                    String lastName = parts[2];
                    String email = parts[3];
                    String day = parts[4];
                    String month = parts[5];
                    String year = parts[6];
                    Student student = new Student(email, firstName + " " + lastName, day + "/" + month + "/" + year);
                    university.enrollStudent(facultyAbbr, student);
                    System.out.println("Student created.");
                } else {
                    System.out.println("Invalid input format.");
                }
            } else if (input.startsWith("gs/")) {
                String email = input.substring(3);
                try {
                    university.graduateStudent(email);
                    System.out.println("Student graduated.");
                } catch (NoSuchElementException e) {
                    System.out.println("Cannot graduate student: " + e.getMessage());
                }
            } else if (input.startsWith("ds/")) {
                String facultyAbbr = input.substring(3);
                Faculty faculty = university.getFacultyByAbbreviation(facultyAbbr);
                if (faculty != null) {
                    for (Student student : faculty.getEnrolledStudents()) {
                        System.out.println(student);
                    }
                } else {
                    System.out.println("Faculty not found.");
                }
            } else if (input.startsWith("dg/")) {
                String facultyAbbr = input.substring(3);
                Faculty faculty = university.getFacultyByAbbreviation(facultyAbbr);
                if (faculty != null) {
                    for (Student student : faculty.getGraduatedStudents()) {
                        System.out.println(student);
                    }
                } else {
                    System.out.println("Faculty not found.");
                }
            } else if (input.startsWith("bf/")) {
                String[] parts = input.substring(3).split("/");
                if (parts.length == 2) {
                    String facultyAbbr = parts[0];
                    String email = parts[1];
                    Faculty faculty = university.getFacultyByAbbreviation(facultyAbbr);
                    if (faculty != null && faculty.hasStudent(email)) {
                        System.out.println("Student belongs to faculty.");
                    } else {
                        System.out.println("Student does not belong to faculty.");
                    }
                } else {
                    System.out.println("Invalid input format.");
                }
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }
}
