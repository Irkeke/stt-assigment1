public class WB03Driver {
    public static void main(String[] args) throws Exception {
        Employee e = new Employee("E012", "Grace Kim", "Technician", 3200, 160, 0);
        try {
            e.setOvertimeHours(41); // just above MAX_OVERTIME_HOURS boundary (40)
            System.out.println("UNEXPECTED: no exception thrown");
        } catch (InvalidInputException ex) {
            System.out.println("Caught expected exception: " + ex.getMessage());
        }
        // boundary: exactly at the cap should succeed
        e.setOvertimeHours(40);
        System.out.println("overtimeHours at cap=" + e.getOvertimeHours());
    }
}
