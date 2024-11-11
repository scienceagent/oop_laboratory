package lab_1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public enum DomeniuStudii  {
    MATEMATICA,
    ECONOMIE,
    FIZICA,
    INGINERIE_SOFTWARE,
    INGINERIE_MECANICA,
    TEHNOLOGIE_ALIMENTARA,
    ARHITECTURA_URBANISM,
    MEDICINA_VETERINARA,
    INGINERIE_ELECTRICA
}

class Tools {
    public static String[] parseInput(String input) {
        return input.split("/");
    }

    public static Date parseDate(String dateStr) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.parse(dateStr);
    }

    public static String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }
}

class Student {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final Date enrollmentDate;
    private final Date dateOfBirth;
    private Boolean graduated;

    public Student(String firstName, String lastName, String email, Date enrollmentDate, Date dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.enrollmentDate = enrollmentDate;
        this.dateOfBirth = dateOfBirth;
        this.graduated = false;
    }

    public String getFullName() { return firstName + " " + lastName; }
    public void setGraduated(boolean graduated) { this.graduated = graduated; }
    public Boolean isGraduated() { return graduated; }
}

class Faculty {
    private final String name;
    private final String abbreviation;
    private final DomeniuStudii studyField;
    private final List<Student> students;

    public Faculty(String name, String abbreviation, DomeniuStudii studyField) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.studyField = studyField;
        this.students = new ArrayList<>();
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public DomeniuStudii getStudyField() {
        return studyField;
    }

    public String getName() {
        return name;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public List<Student> getStudents() {
        return students;
    }

    public boolean containsStudent(String firstName, String lastName) {
        return students.stream().anyMatch(s -> 
            s.getFullName().equalsIgnoreCase(firstName + " " + lastName)
        );
    }

    public Student findStudentByName(String firstName, String lastName) {
        return students.stream()
                .filter(s -> s.getFullName().equalsIgnoreCase(firstName + " " + lastName))
                .findFirst()
                .orElse(null);
    }
}

class University {
    private final List<Faculty> faculties = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    public void addFaculty() {
        System.out.println("Enter faculty details as <name>/<abbreviation>/<study field>");
        String[] details = Tools.parseInput(scanner.nextLine());
        try {
            Faculty faculty = new Faculty(details[0], details[1], DomeniuStudii .valueOf(details[2].toUpperCase()));
            faculties.add(faculty);
            System.out.println("Faculty " + details[0] + " created successfully.");
        } catch (Exception e) {
            System.out.println("Error: Invalid field or input format.");
        }
    }

    public Faculty findFacultyByCriteria(Predicate<Faculty> criteria){
        return faculties.stream().filter(criteria).findFirst().orElse(null);
    }
    public Faculty findFacultyByName(String facultyName) {
        return findFacultyByCriteria(faculty -> faculty.getName().equals(facultyName));
    }

    public Faculty findFacultyByAbbreviation(String facultyAbbreviation) {
        return findFacultyByCriteria((faculty -> faculty.getAbbreviation().equals(facultyAbbreviation)));
    }

    public Faculty getFacultyByAbbreviation(String abbreviation) {
        return faculties.stream()
            .filter(f -> f.getAbbreviation().equalsIgnoreCase(abbreviation))
            .findFirst()
            .orElse(null);
    }
    public void addStudentToFaculty() {
        System.out.println("Enter student details as <faculty abbreviation>/<firstname>/<lastname>/<email>/<enrollmentDate>/<dateOfBirth>");
        String[] details = Tools.parseInput(scanner.nextLine());

        Faculty faculty = getFacultyByAbbreviation(details[0]);
        if (faculty == null) {
            System.out.println("Error: Faculty not found.");
            return;
        }

        try {
            Date enrollmentDate = Tools.parseDate(details[4]);
            Date dateOfBirth = Tools.parseDate(details[5]);
            Student student = new Student(details[1], details[2], details[3], enrollmentDate, dateOfBirth);
            faculty.addStudent(student);
            System.out.println("Student " + details[1] + " " + details[2] + " added to " + faculty.getName());
        } catch (ParseException e) {
            System.out.println("Error: Invalid date format.");
        }
    }

    public void graduateStudent() {
        System.out.println("Enter student details as <faculty abbreviation>/<firstname>/<lastname>");
        String[] details = Tools.parseInput(scanner.nextLine());

        Faculty faculty = getFacultyByAbbreviation(details[0]);
        if (faculty == null) {
            System.out.println("Error: Faculty not found.");
            return;
        }

        Student student = faculty.findStudentByName(details[1], details[2]);
        if (student != null) {
            student.setGraduated(true);
            System.out.println("Student " + details[1] + " " + details[2] + " has graduated.");
        } else {
            System.out.println("Error: Student not found.");
        }
    }

    public void displayFaculties() {
        if (faculties.isEmpty()) {
            System.out.println("No faculties available.");
        } else {
            faculties.forEach(f -> System.out.println(f.getName() + " (" + f.getAbbreviation() + ")"));
        }
        
    }

    public void displayStudentsInFaculty() {
        System.out.println("Enter faculty abbreviation:");
        String abbreviation = scanner.nextLine().trim();

        Faculty faculty = getFacultyByAbbreviation(abbreviation);
        if (faculty == null) {
            System.out.println("Error: Faculty not found.");
        } else {
            faculty.getStudents().forEach(s -> System.out.println(s.getFullName() + " - Graduated: " + s.isGraduated()));
        }
    }

    public void checkStudentBelongsToFaculty() {
        System.out.println("Please enter the student details in the format <first name>/<last name>/<faculty abbreviation>");
        String studentData = scanner.nextLine();
        String[] studentDetails = Tools.parseInput(studentData);

        if (studentDetails.length != 3) {
            System.out.println("Invalid input format. Please use the format: <first name>/<last name>/<faculty abbreviation>");
            return;
        }

        String firstName = studentDetails[0];
        String lastName = studentDetails[1];
        String facultyAbbreviation = studentDetails[2];

        Faculty faculty = findFacultyByAbbreviation(facultyAbbreviation);
        if (faculty == null) {
            System.out.println("Faculty with abbreviation " + facultyAbbreviation + " not found.");
        } else {
            boolean studentBelongsToFaculty = faculty.containsStudent(firstName, lastName);
            if (studentBelongsToFaculty) {
                System.out.println(firstName + " " + lastName + " belongs to " + faculty.getName());
            } else {
                System.out.println(firstName + " " + lastName + " does not belong to " + faculty.getName());
            }
        }
    }
    
}
