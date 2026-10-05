public class WB01Driver {
    public static void main(String[] args) throws Exception {
        Employee e = new Employee("E010", "Dana Lee", "HR Officer", 3500, 160, 0);
        System.out.println("Constructed OK: " + e);
        System.out.println("employeeId=" + e.getEmployeeId());
        System.out.println("name=" + e.getName());
        System.out.println("position=" + e.getPosition());
        System.out.println("baseSalary=" + e.getBaseSalary());
        System.out.println("hoursWorked=" + e.getHoursWorked());
        System.out.println("overtimeHours=" + e.getOvertimeHours());
    }
}
