import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Central service class for Organization X. Owns the employee collection
 * and the payment history, and implements FR-01 through FR-08.
 */
public class OrganizationManagement {

    // ---- Business constants (shared with Payment / Employee) ----
    public static final double STANDARD_HOURS = 160.0;   // hours/month
    public static final double OVERTIME_MULTIPLIER = 1.5;
    public static final double TAX_RATE = 0.10;
    public static final double INSURANCE_RATE = 0.05;
    public static final double MAX_OVERTIME_HOURS = 40.0;

    // employeeId -> Employee, insertion order preserved for predictable listing
    private final Map<String, Employee> employees = new LinkedHashMap<>();
    private final List<Payment> paymentHistory = new ArrayList<>();

    // ---------------- FR-01: Add Employee ----------------
    public void addEmployee(Employee employee) throws DuplicateEmployeeException {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null.");
        }
        String employeeId = normalizeId(employee.getEmployeeId());
        if (employees.containsKey(employeeId)) {
            throw new DuplicateEmployeeException(
                    "Employee ID '" + employeeId + "' already exists.");
        }
        employees.put(employeeId, employee);
    }

    // ---------------- FR-02: Update Employee ----------------
    public void updateEmployee(String employeeId, String name, String position,
                                double baseSalary, double hoursWorked, double overtimeHours)
            throws EmployeeNotFoundException, InvalidInputException {
        Employee existing = employees.get(normalizeId(employeeId));
        if (existing == null) {
            throw new EmployeeNotFoundException("No employee found with ID '" + employeeId + "'.");
        }
        Employee replacement = new Employee(existing.getEmployeeId(), name, position,
            baseSalary, hoursWorked, overtimeHours);
        existing.setName(replacement.getName());
        existing.setPosition(replacement.getPosition());
        existing.setBaseSalary(replacement.getBaseSalary());
        existing.setHoursWorked(replacement.getHoursWorked());
        existing.setOvertimeHours(replacement.getOvertimeHours());
    }

    // ---------------- FR-03: Delete Employee ----------------
    public void deleteEmployee(String employeeId) throws EmployeeNotFoundException {
        String normalizedId = normalizeId(employeeId);
        if (!employees.containsKey(normalizedId)) {
            throw new EmployeeNotFoundException("No employee found with ID '" + employeeId + "'.");
        }
        employees.remove(normalizedId);
    }

    // ---------------- FR-04: Search Employee ----------------
    public Employee searchById(String employeeId) throws EmployeeNotFoundException {
        Employee e = employees.get(normalizeId(employeeId));
        if (e == null) {
            throw new EmployeeNotFoundException("No employee found with ID '" + employeeId + "'.");
        }
        return e;
    }

    public List<Employee> searchByName(String namePart) {
        List<Employee> results = new ArrayList<>();
        if (namePart == null) return results;
        String needle = namePart.trim().toLowerCase(Locale.ROOT);
        for (Employee e : employees.values()) {
            if (e.getName().toLowerCase(Locale.ROOT).contains(needle)) {
                results.add(e);
            }
        }
        return results;
    }

    // ---------------- FR-05: List Employees ----------------
    public List<Employee> listEmployees() {
        return new ArrayList<>(employees.values());
    }

    // ---------------- FR-06: Process Payment ----------------
    public Payment processPayment(String employeeId) throws EmployeeNotFoundException {
        Employee e = searchById(employeeId);
        Payment payment = new Payment(e);
        paymentHistory.add(payment);
        return payment;
    }

    public List<Payment> getPaymentHistory() {
        return new ArrayList<>(paymentHistory);
    }

    // ---------------- FR-07 / FR-08: Reporting ----------------
    public String generateEmployeeReport() {
        return Report.employeeReport(listEmployees());
    }

    public String generatePaymentReport() {
        return Report.paymentReport(getPaymentHistory());
    }

    public String generateSummaryReport() {
        return Report.summaryReport(listEmployees(), getPaymentHistory());
    }

    public int employeeCount() {
        return employees.size();
    }

    private static String normalizeId(String employeeId) {
        return employeeId == null ? "" : employeeId.trim();
    }
}
