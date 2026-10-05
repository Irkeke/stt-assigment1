import java.util.List;
import java.util.Scanner;

/**
 * Console front-end for the Organization X Management System.
 * Delegates all business logic to OrganizationManagement; this class is
 * only responsible for menu I/O.
 */
public class Main {

    private static final OrganizationManagement manager = new OrganizationManagement();
    private static Scanner scanner;

    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            scanner = input;
            runMenu();
        }
    }

    private static void runMenu() {
        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> addEmployeeFlow();
                    case "2" -> updateEmployeeFlow();
                    case "3" -> deleteEmployeeFlow();
                    case "4" -> searchEmployeeFlow();
                    case "5" -> listEmployeesFlow();
                    case "6" -> processPaymentFlow();
                    case "7" -> System.out.println(manager.generateEmployeeReport());
                    case "8" -> System.out.println(manager.generatePaymentReport());
                    case "9" -> System.out.println(manager.generateSummaryReport());
                    case "0" -> {
                        running = false;
                        System.out.println("Goodbye.");
                    }
                    default -> System.out.println("Invalid option. Please choose 0-9.");
                }
            } catch (InvalidInputException | EmployeeNotFoundException
                     | DuplicateEmployeeException ex) {
                System.out.println("ERROR: " + ex.getMessage());
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n===== Organization X Management System =====");
        System.out.println("1. Add Employee");
        System.out.println("2. Update Employee");
        System.out.println("3. Delete Employee");
        System.out.println("4. Search Employee");
        System.out.println("5. List Employees");
        System.out.println("6. Process Payment");
        System.out.println("7. Employee Report");
        System.out.println("8. Payment Report");
        System.out.println("9. Summary Report");
        System.out.println("0. Exit");
        System.out.print("Choose an option: ");
    }

    private static void addEmployeeFlow() throws InvalidInputException, DuplicateEmployeeException {
        System.out.print("Employee ID: ");
        String id = scanner.nextLine();
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Position: ");
        String position = scanner.nextLine();
        System.out.print("Base Salary: ");
        double salary = parseDouble(scanner.nextLine());
        System.out.print("Hours Worked: ");
        double hours = parseDouble(scanner.nextLine());
        System.out.print("Overtime Hours: ");
        double ot = parseDouble(scanner.nextLine());

        Employee e = new Employee(id, name, position, salary, hours, ot);
        manager.addEmployee(e);
        System.out.println("Employee added successfully.");
    }

    private static void updateEmployeeFlow() throws InvalidInputException, EmployeeNotFoundException {
        System.out.print("Employee ID to update: ");
        String id = scanner.nextLine();
        System.out.print("New Name: ");
        String name = scanner.nextLine();
        System.out.print("New Position: ");
        String position = scanner.nextLine();
        System.out.print("New Base Salary: ");
        double salary = parseDouble(scanner.nextLine());
        System.out.print("New Hours Worked: ");
        double hours = parseDouble(scanner.nextLine());
        System.out.print("New Overtime Hours: ");
        double ot = parseDouble(scanner.nextLine());

        manager.updateEmployee(id, name, position, salary, hours, ot);
        System.out.println("Employee updated successfully.");
    }

    private static void deleteEmployeeFlow() throws EmployeeNotFoundException {
        System.out.print("Employee ID to delete: ");
        String id = scanner.nextLine();
        manager.deleteEmployee(id);
        System.out.println("Employee deleted successfully.");
    }

    private static void searchEmployeeFlow() throws EmployeeNotFoundException {
        System.out.print("Search by (1) ID or (2) Name: ");
        String mode = scanner.nextLine().trim();
        if (mode.equals("1")) {
            System.out.print("Employee ID: ");
            Employee e = manager.searchById(scanner.nextLine());
            System.out.println(e);
        } else {
            System.out.print("Name (or partial name): ");
            List<Employee> results = manager.searchByName(scanner.nextLine());
            if (results.isEmpty()) {
                System.out.println("No matching employees found.");
            } else {
                results.forEach(System.out::println);
            }
        }
    }

    private static void listEmployeesFlow() {
        List<Employee> all = manager.listEmployees();
        if (all.isEmpty()) {
            System.out.println("No employees on record.");
        } else {
            all.forEach(System.out::println);
        }
    }

    private static void processPaymentFlow() throws EmployeeNotFoundException {
        System.out.print("Employee ID to pay: ");
        String id = scanner.nextLine();
        Payment payment = manager.processPayment(id);
        System.out.println(payment.generatePayslip());
    }

    private static double parseDouble(String s) throws InvalidInputException {
        try {
            return Double.parseDouble(s.trim());
        } catch (NumberFormatException ex) {
            throw new InvalidInputException("Expected a numeric value but got '" + s + "'.");
        }
    }
}
