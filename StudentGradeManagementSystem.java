package wiprotrainingday2;

import java.util.*;

public class StudentGradeManagementSystem {
    public static void main(String[] args) {
        GradeManagement gradeManagement = new GradeManagement();
        Scanner scanner = new Scanner(System.in);  // Create scanner once

        while (true) {
            System.out.println("\nStudent Grade Management System");
            System.out.println("1. Add Student");
            System.out.println("2. Add Course");
            System.out.println("3. Assign Grade");
            System.out.println("4. Calculate GPA");
            System.out.println("5. View Students");
            System.out.println("6. View Courses");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> gradeManagement.addStudent(scanner);
                case 2 -> gradeManagement.addCourse(scanner);
                case 3 -> gradeManagement.assignGrade(scanner);
                case 4 -> gradeManagement.calculateGPA(scanner);
                case 5 -> gradeManagement.viewStudents();
                case 6 -> gradeManagement.viewCourses();
                case 7 -> {
                    System.out.println("Exiting the system...");
                    scanner.close();  // Close scanner before exit
                    return;
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}

// Class to manage students, courses, and grades
class GradeManagement {
    private final List<Student> students = new ArrayList<>();
    private final List<Course> courses = new ArrayList<>();
    private final Map<Integer, List<Grade>> grades = new HashMap<>(); // Student ID -> Grades

    // Add a new student
    public void addStudent(Scanner scanner) {
        System.out.print("Enter Student ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();

        students.add(new Student(id, name));
        System.out.println("Student added successfully!");
    }

    // Add a new course
    public void addCourse(Scanner scanner) {
        System.out.print("Enter Course ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Course Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Credits: ");
        int credits = scanner.nextInt();

        courses.add(new Course(id, name, credits));
        System.out.println("Course added successfully!");
    }

    // Assign a grade to a student for a course
    public void assignGrade(Scanner scanner) {
        System.out.print("Enter Student ID: ");
        int studentId = scanner.nextInt();
        System.out.print("Enter Course ID: ");
        int courseId = scanner.nextInt();
        System.out.print("Enter Grade (0.0 to 4.0): ");
        double gradeValue = scanner.nextDouble();

        // Validate grade
        if (gradeValue < 0.0 || gradeValue > 4.0) {
            System.out.println("Invalid grade. Grade should be between 0.0 and 4.0.");
            return;
        }

        Student student = findStudentById(studentId);
        Course course = findCourseById(courseId);

        if (student != null && course != null) {
            grades.computeIfAbsent(studentId, k -> new ArrayList<>())
                  .add(new Grade(course, gradeValue));
            System.out.println("Grade assigned successfully!");
        } else {
            System.out.println("Student or Course not found!");
        }
    }

    // Calculate GPA for a student
    public void calculateGPA(Scanner scanner) {
        System.out.print("Enter Student ID: ");
        int studentId = scanner.nextInt();

        Student student = findStudentById(studentId);
        if (student != null) {
            List<Grade> studentGrades = grades.get(studentId);
            if (studentGrades == null || studentGrades.isEmpty()) {
                System.out.println("No grades assigned to this student yet.");
                return;
            }

            double totalGradePoints = 0.0;
            int totalCredits = 0;

            for (Grade grade : studentGrades) {
                totalGradePoints += grade.getGrade() * grade.getCourse().getCredits();
                totalCredits += grade.getCourse().getCredits();
            }

            double gpa = totalGradePoints / totalCredits;
            System.out.printf("GPA of %s: %.2f%n", student.getName(), gpa);
        } else {
            System.out.println("Student not found!");
        }
    }

    // View all students
    public void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No students available.");
        } else {
            System.out.println("Students:");
            students.forEach(System.out::println);
        }
    }

    // View all courses
    public void viewCourses() {
        if (courses.isEmpty()) {
            System.out.println("No courses available.");
        } else {
            System.out.println("Courses:");
            courses.forEach(System.out::println);
        }
    }

    // Helper methods to find student and course by ID
    private Student findStudentById(int id) {
        return students.stream().filter(student -> student.getId() == id).findFirst().orElse(null);
    }

    private Course findCourseById(int id) {
        return courses.stream().filter(course -> course.getId() == id).findFirst().orElse(null);
    }
}

// Student class
class Student {
    private final int id;
    private final String name;

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name;
    }
}

// Course class
class Course {
    private final int id;
    private final String name;
    private final int credits;

    public Course(int id, String name, int credits) {
        this.id = id;
        this.name = name;
        this.credits = credits;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCredits() {
        return credits;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Credits: " + credits;
    }
}

// Grade class
class Grade {
    private final Course course;
    private final double grade;

    public Grade(Course course, double grade) {
        this.course = course;
        this.grade = grade;
    }

    public Course getCourse() {
        return course;
    }

    public double getGrade() {
        return grade;
    }

    @Override
    public String toString() {
        return "Course: " + course.getName() + ", Grade: " + grade;
    }
}
