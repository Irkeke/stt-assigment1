public class WB04Driver {
    public static void main(String[] args) throws Exception {
        OrganizationManagement mgr = new OrganizationManagement();
        Employee e1 = new Employee("E013", "Henry Ford", "Driver", 2800, 160, 0);
        mgr.addEmployee(e1); // first branch: containsKey == false -> added
        System.out.println("First add OK, count=" + mgr.employeeCount());

        Employee e2 = new Employee("E013", "Ivan Petrov", "Driver", 2900, 160, 0);
        try {
            mgr.addEmployee(e2); // second branch: containsKey == true -> exception
            System.out.println("UNEXPECTED: no exception thrown");
        } catch (DuplicateEmployeeException ex) {
            System.out.println("Caught expected exception: " + ex.getMessage());
        }
        System.out.println("count unchanged=" + mgr.employeeCount());
    }
}
