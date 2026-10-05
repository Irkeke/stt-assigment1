import java.time.LocalDate;

/**
 * Represents one payment/payslip run for a single employee (FR-06).
 * All money math lives here so it can be unit-tested independently of
 * the console menu and the employee-storage logic.
 */
public class Payment {

    private final Employee employee;
    private final double grossPay;
    private final double overtimePay;
    private final double taxDeduction;
    private final double insuranceDeduction;
    private final double netPay;
    private final LocalDate payDate;

    public Payment(Employee employee) {
        this.employee = employee;
        this.payDate = LocalDate.now();

        double hourlyRate = employee.getBaseSalary() / OrganizationManagement.STANDARD_HOURS;
        this.overtimePay = employee.getOvertimeHours() * hourlyRate
                * OrganizationManagement.OVERTIME_MULTIPLIER;

        // Gross pay = base salary (covers standard hours) + overtime pay.
        double gross = employee.getBaseSalary() + overtimePay;
        this.grossPay = gross;

        this.taxDeduction = gross * OrganizationManagement.TAX_RATE;
        this.insuranceDeduction = gross * OrganizationManagement.INSURANCE_RATE;
        this.netPay = gross - taxDeduction - insuranceDeduction;
    }

    public Employee getEmployee() { return employee; }
    public double getGrossPay() { return grossPay; }
    public double getOvertimePay() { return overtimePay; }
    public double getTaxDeduction() { return taxDeduction; }
    public double getInsuranceDeduction() { return insuranceDeduction; }
    public double getNetPay() { return netPay; }
    public LocalDate getPayDate() { return payDate; }

    /** FR-06: produces a formatted, human-readable payslip. */
    public String generatePayslip() {
        StringBuilder sb = new StringBuilder();
        sb.append("========== PAYSLIP ==========\n");
        sb.append("Pay Date       : ").append(payDate).append('\n');
        sb.append("Employee ID    : ").append(employee.getEmployeeId()).append('\n');
        sb.append("Name           : ").append(employee.getName()).append('\n');
        sb.append("Position       : ").append(employee.getPosition()).append('\n');
        sb.append(String.format("Base Salary    : $%.2f%n", employee.getBaseSalary()));
        sb.append(String.format("Overtime Hours : %.1f%n", employee.getOvertimeHours()));
        sb.append(String.format("Overtime Pay   : $%.2f%n", overtimePay));
        sb.append(String.format("Gross Pay      : $%.2f%n", grossPay));
        sb.append(String.format("Tax (10%%)      : -$%.2f%n", taxDeduction));
        sb.append(String.format("Insurance (5%%) : -$%.2f%n", insuranceDeduction));
        sb.append(String.format("NET PAY        : $%.2f%n", netPay));
        sb.append("==============================\n");
        return sb.toString();
    }
}
