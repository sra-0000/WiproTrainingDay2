package wiprotrainingday2;

import java.util.*;

public class EmployeePayrollSystem {
    public static void main(String[] args) {
        Payroll payroll = new Payroll();
        Scanner scanner = new Scanner(System.in);  // Create scanner once

        while (true) {
            System.out.println("\nEmployee Payroll System");
            System.out.println("1. Add Permanent Employee");
            System.out.println("2. Add Contractual Employee");
            System.out.println("3. View Employees");
            System.out.println("4. Calculate Salaries");
            System.out.println("5. Generate Department Report");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> payroll.addPermanentEmployee(scanner);
                case 2 -> payroll.addContractualEmployee(scanner);
                case 3 -> payroll.viewEmployees();
                case 4 -> payroll.calculateSalaries();
                case 5 -> payroll.generateDepartmentReport();
                case 6 -> {
                    System.out.println("Exiting the system...");
                    scanner.close();  // Close scanner before exit
                    return;
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}

// Base Employee class
class Employee {
    private final int id;
    private final String name;
    private final String department;
    private final double baseSalary;

    public Employee(int id, String name, String department, double baseSalary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.baseSalary = baseSalary;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    public double calculateSalary() {
        return baseSalary;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Department: " + department + ", Base Salary: " + baseSalary;
    }
}

// Permanent Employee class
class PermanentEmployee extends Employee {
    private final double bonus;

    public PermanentEmployee(int id, String name, String department, double baseSalary, double bonus) {
        super(id, name, department, baseSalary);
        this.bonus = bonus;
    }

    @Override
    public double calculateSalary() {
        return getBaseSalary() + bonus;
    }

    @Override
    public String toString() {
        return super.toString() + ", Bonus: " + bonus;
    }
}

// Contractual Employee class
class ContractualEmployee extends Employee {
    private final int hoursWorked;
    private final double hourlyRate;

    public ContractualEmployee(int id, String name, String department, double hourlyRate, int hoursWorked) {
        super(id, name, department, hourlyRate * hoursWorked);  // calculate base salary here
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }

    @Override
    public double calculateSalary() {
        return hourlyRate * hoursWorked;  // Correct calculation of salary
    }

    @Override
    public String toString() {
        return super.toString() + ", Hours Worked: " + hoursWorked + ", Hourly Rate: " + hourlyRate;
    }
}

// Payroll class for employee management
class Payroll {
    private final List<Employee> employees = new ArrayList<>();

    public void addPermanentEmployee(Scanner scanner) {
        System.out.print("Enter Employee ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Department: ");
        String department = scanner.nextLine();
        System.out.print("Enter Base Salary: ");
        double baseSalary = scanner.nextDouble();
        System.out.print("Enter Bonus: ");
        double bonus = scanner.nextDouble();

        employees.add(new PermanentEmployee(id, name, department, baseSalary, bonus));
        System.out.println("Permanent employee added successfully!");
    }

    public void addContractualEmployee(Scanner scanner) {
        System.out.print("Enter Employee ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Department: ");
        String department = scanner.nextLine();
        System.out.print("Enter Hourly Rate: ");
        double hourlyRate = scanner.nextDouble();
        System.out.print("Enter Hours Worked: ");
        int hoursWorked = scanner.nextInt();

        employees.add(new ContractualEmployee(id, name, department, hourlyRate, hoursWorked));
        System.out.println("Contractual employee added successfully!");
    }

    public void viewEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees available.");
        } else {
            System.out.println("Employees:");
            employees.forEach(System.out::println);
        }
    }

    public void calculateSalaries() {
        if (employees.isEmpty()) {
            System.out.println("No employees available.");
            return;
        }

        System.out.println("Salaries:");
        for (Employee employee : employees) {
            System.out.printf("Employee ID: %d, Name: %s, Salary: %.2f%n",
                    employee.getId(), employee.getName(), employee.calculateSalary());
        }
    }

    public void generateDepartmentReport() {
        if (employees.isEmpty()) {
            System.out.println("No employees available.");
            return;
        }

        Map<String, List<Employee>> departmentMap = new HashMap<>();
        for (Employee employee : employees) {
            departmentMap.computeIfAbsent(employee.getDepartment(), k -> new ArrayList<>()).add(employee);
        }

        for (var entry : departmentMap.entrySet()) {
            System.out.println("\nDepartment: " + entry.getKey());
            for (Employee employee : entry.getValue()) {
                System.out.println(employee);
            }
        }
    }
}
