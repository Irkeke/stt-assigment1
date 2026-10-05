public class WB05Driver {
    public static void main(String[] args) throws Exception {
        OrganizationManagement mgr = new OrganizationManagement();
        // employees map is empty -> existing == null path
        try {
            mgr.updateEmployee("E999", "No One", "Ghost", 1000, 160, 0);
            System.out.println("UNEXPECTED: no exception thrown");
        } catch (EmployeeNotFoundException ex) {
            System.out.println("Caught expected exception: " + ex.getMessage());
        }

        // now the found path
        Employee e = new Employee("E014", "Julia Ross", "Planner", 3300, 160, 0);
        mgr.addEmployee(e);
        mgr.updateEmployee("E014", "Julia Ross-Baker", "Senior Planner", 3600, 160, 5);
        Employee updated = mgr.searchById("E014");
        System.out.println("Updated employee: " + updated);
    }
}
