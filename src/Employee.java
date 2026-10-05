/**
 * Represents a single employee record held by Organization X.
 *
 * FR-01/FR-02 (add/update) construct or mutate instances of this class;
 * FR-06 (payment processing) reads baseSalary/hoursWorked/overtimeHours
 * from it.
 */
public class Employee {

    private String employeeId;
    private String name;
    private String position;
    private double baseSalary;     // monthly base salary, must be > 0
    private double hoursWorked;    // hours worked in the pay period, must be >= 0
    private double overtimeHours;  // overtime hours in the pay period, must be >= 0

    public Employee(String employeeId, String name, String position,
                     double baseSalary, double hoursWorked, double overtimeHours)
            throws InvalidInputException {
        setEmployeeId(employeeId);
        setName(name);
        setPosition(position);
        setBaseSalary(baseSalary);
        setHoursWorked(hoursWorked);
        setOvertimeHours(overtimeHours);
    }

    // ---------- Getters ----------
    public String getEmployeeId() { return employeeId; }
    public String getName() { return name; }
    public String getPosition() { return position; }
    public double getBaseSalary() { return baseSalary; }
    public double getHoursWorked() { return hoursWorked; }
    public double getOvertimeHours() { return overtimeHours; }

    // ---------- Setters (each validates its own field) ----------
    public final void setEmployeeId(String employeeId) throws InvalidInputException {
        if (employeeId == null || employeeId.trim().isEmpty()) {
            throw new InvalidInputException("Employee ID cannot be blank.");
        }
        this.employeeId = employeeId.trim();
    }

    public final void setName(String name) throws InvalidInputException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("Employee name cannot be blank.");
        }
        this.name = name.trim();
    }

    public final void setPosition(String position) throws InvalidInputException {
        if (position == null || position.trim().isEmpty()) {
            throw new InvalidInputException("Position cannot be blank.");
        }
        this.position = position.trim();
    }

    public final void setBaseSalary(double baseSalary) throws InvalidInputException {
        if (!Double.isFinite(baseSalary) || baseSalary <= 0) {
            throw new InvalidInputException("Base salary must be greater than 0.");
        }
        this.baseSalary = baseSalary;
    }

    public final void setHoursWorked(double hoursWorked) throws InvalidInputException {
        if (!Double.isFinite(hoursWorked) || hoursWorked < 0) {
            throw new InvalidInputException("Hours worked cannot be negative.");
        }
        this.hoursWorked = hoursWorked;
    }

    public final void setOvertimeHours(double overtimeHours) throws InvalidInputException {
        if (!Double.isFinite(overtimeHours) || overtimeHours < 0) {
            throw new InvalidInputException("Overtime hours cannot be negative.");
        }
        if (overtimeHours > OrganizationManagement.MAX_OVERTIME_HOURS) {
            throw new InvalidInputException("Overtime hours cannot exceed "
                    + OrganizationManagement.MAX_OVERTIME_HOURS + " per pay period.");
        }
        this.overtimeHours = overtimeHours;
    }

    @Override
    public String toString() {
        return String.format("%-8s %-20s %-15s $%-10.2f %-6.1f %-6.1f",
                employeeId, name, position, baseSalary, hoursWorked, overtimeHours);
    }
}
