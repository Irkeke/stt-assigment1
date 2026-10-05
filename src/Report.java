import java.util.List;

/**
 * Pure formatting helpers for FR-07/FR-08 (reporting). Kept stateless and
 * static so it can be unit-tested with plain lists, independent of
 * OrganizationManagement's storage.
 */
public class Report {

    public static String employeeReport(List<Employee> employees) {
        StringBuilder sb = new StringBuilder();
        sb.append("===== EMPLOYEE REPORT =====\n");
        if (employees.isEmpty()) {
            sb.append("No employees on record.\n");
        } else {
            sb.append(String.format("%-8s %-20s %-15s %-11s %-6s %-6s%n",
                    "ID", "Name", "Position", "Salary", "Hrs", "OT"));
            for (Employee e : employees) {
                sb.append(e.toString()).append('\n');
            }
        }
        sb.append("Total employees: ").append(employees.size()).append('\n');
        return sb.toString();
    }

    public static String paymentReport(List<Payment> payments) {
        StringBuilder sb = new StringBuilder();
        sb.append("===== PAYMENT REPORT =====\n");
        if (payments.isEmpty()) {
            sb.append("No payments processed.\n");
        } else {
            double totalNet = 0;
            for (Payment p : payments) {
                sb.append(String.format("%-8s %-20s Gross: $%-10.2f Net: $%-10.2f%n",
                        p.getEmployee().getEmployeeId(), p.getEmployee().getName(),
                        p.getGrossPay(), p.getNetPay()));
                totalNet += p.getNetPay();
            }
            sb.append(String.format("Total payments: %d   Total net paid: $%.2f%n",
                    payments.size(), totalNet));
        }
        return sb.toString();
    }

    public static String summaryReport(List<Employee> employees, List<Payment> payments) {
        StringBuilder sb = new StringBuilder();
        sb.append("===== SUMMARY REPORT =====\n");
        sb.append("Total employees   : ").append(employees.size()).append('\n');
        sb.append("Total payments run: ").append(payments.size()).append('\n');
        double totalGross = 0, totalNet = 0;
        for (Payment p : payments) {
            totalGross += p.getGrossPay();
            totalNet += p.getNetPay();
        }
        sb.append(String.format("Total gross paid  : $%.2f%n", totalGross));
        sb.append(String.format("Total net paid    : $%.2f%n", totalNet));
        return sb.toString();
    }
}
