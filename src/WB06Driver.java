public class WB06Driver {
    public static void main(String[] args) throws Exception {
        // Path A: overtimeHours = 0 -> overtimePay branch computes 0
        Employee noOt = new Employee("E015", "Kevin Brooks", "Support", 3000, 160, 0);
        Payment p1 = new Payment(noOt);
        System.out.println("Path A (no overtime):");
        System.out.println(p1.generatePayslip());

        // Path B: overtimeHours > 0 -> overtimePay branch computes a positive value
        Employee withOt = new Employee("E016", "Laura Chen", "Support", 3000, 160, 20);
        Payment p2 = new Payment(withOt);
        System.out.println("Path B (with overtime):");
        System.out.println(p2.generatePayslip());
    }
}
