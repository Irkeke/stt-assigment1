
/**
 * Executable tests corresponding to test cases BB-01..BB-07 and WB-01..WB-06.
 * Each test method name references the test case ID it covers so the mapping
 * to the test-case reports is unambiguous.
 */
class OrganizationManagementTest {

    private OrganizationManagement manager;

    void setUp() {
        manager = new OrganizationManagement();
    }

    public static void main(String[] args) throws Exception {
        OrganizationManagementTest tests = new OrganizationManagementTest();
        tests.setUp();
        tests.bb01_addValidEmployee_succeedsAndIsListed();
        tests.setUp();
        tests.bb02_addDuplicateEmployee_throwsDuplicateEmployeeException();
        tests.setUp();
        tests.bb03_negativeBaseSalary_throwsInvalidInputException();
        tests.setUp();
        tests.bb04_searchById_returnsCorrectEmployee();
        tests.setUp();
        tests.bb05_deleteNonExistentEmployee_throwsEmployeeNotFoundException();
        tests.setUp();
        tests.bb06_processPayment_withOvertime_calculatesExpectedNetPay();
        tests.setUp();
        tests.bb07_hoursAboveStandardWithZeroOvertime_isAcceptedWithoutWarning();
        tests.setUp();
        tests.wb01_validConstruction_allFieldsSet();
        tests.setUp();
        tests.wb02_setBaseSalaryToZero_throwsAndLeavesFieldUnchanged();
        tests.setUp();
        tests.wb03_overtimeAboveCap_throwsButAtCapSucceeds();
        tests.setUp();
        tests.wb04_addEmployee_duplicateBranch();
        tests.setUp();
        tests.wb05_updateEmployee_notFoundThenFoundPaths();
        tests.setUp();
        tests.wb06_paymentCalculation_bothOvertimePaths();
        System.out.println("All OrganizationManagement tests passed.");
    }

    // ---- BB-01: add a valid employee ----
    void bb01_addValidEmployee_succeedsAndIsListed() throws Exception {
        Employee e = new Employee("E001", "Alice Johnson", "Software Engineer", 4000, 160, 10);
        manager.addEmployee(e);
        assertEquals(1, manager.employeeCount());
        assertEquals("Alice Johnson", manager.searchById("E001").getName());
    }

    // ---- BB-02: reject duplicate employee ID ----
    void bb02_addDuplicateEmployee_throwsDuplicateEmployeeException() throws Exception {
        manager.addEmployee(new Employee("E001", "Alice Johnson", "Software Engineer", 4000, 160, 10));
        Employee duplicate = new Employee("E001", "Bob Smith", "Analyst", 3000, 160, 0);
        assertThrows(DuplicateEmployeeException.class, () -> manager.addEmployee(duplicate));
        assertEquals(1, manager.employeeCount());
    }

    // ---- BB-03: reject negative base salary ----
    void bb03_negativeBaseSalary_throwsInvalidInputException() throws Exception {
        assertThrows(InvalidInputException.class,
                () -> new Employee("E002", "Carla Diaz", "Accountant", -500, 160, 0));
    }

    // ---- BB-04: search by valid ID ----
    void bb04_searchById_returnsCorrectEmployee() throws Exception {
        manager.addEmployee(new Employee("E001", "Alice Johnson", "Software Engineer", 4000, 160, 10));
        Employee found = manager.searchById("E001");
        assertEquals("Software Engineer", found.getPosition());
    }

    // ---- BB-05: delete non-existent employee ----
    void bb05_deleteNonExistentEmployee_throwsEmployeeNotFoundException() throws Exception {
        assertThrows(EmployeeNotFoundException.class, () -> manager.deleteEmployee("E999"));
    }

    // ---- BB-06: process payment with overtime ----
    void bb06_processPayment_withOvertime_calculatesExpectedNetPay() throws Exception {
        manager.addEmployee(new Employee("E001", "Alice Johnson", "Software Engineer", 4000, 160, 10));
        Payment payment = manager.processPayment("E001");
        assertEquals(4375.00, payment.getGrossPay(), 0.01);
        assertEquals(3718.75, payment.getNetPay(), 0.01);
    }

    // ---- BB-07: hoursWorked > standard hours with zero overtime (known gap, expected FAIL) ----
    void bb07_hoursAboveStandardWithZeroOvertime_isAcceptedWithoutWarning() throws Exception {
        // Documents the current (unvalidated) behavior: the system does not
        // cross-check hoursWorked against overtimeHours, so 40 unpaid hours
        // are silently accepted. See Incident INC-001 in the report.
        Employee e = new Employee("E020", "Marcus Webb", "Field Technician", 3800, 200, 0);
        manager.addEmployee(e);
        assertEquals(200, manager.searchById("E020").getHoursWorked(), 0.0);
        assertEquals(0, manager.searchById("E020").getOvertimeHours(), 0.0);
        // A correct implementation would be expected to reject or flag this;
        // this assertion documents the observed (defective) behavior.
    }

    // ---- WB-01: Employee constructor, statement coverage ----
    void wb01_validConstruction_allFieldsSet() throws Exception {
        Employee e = new Employee("E010", "Dana Lee", "HR Officer", 3500, 160, 0);
        assertEquals("E010", e.getEmployeeId());
        assertEquals(3500, e.getBaseSalary(), 0.0);
    }

    // ---- WB-02: setBaseSalary, branch coverage (<= 0) ----
    void wb02_setBaseSalaryToZero_throwsAndLeavesFieldUnchanged() throws Exception {
        Employee e = new Employee("E011", "Frank Moss", "Clerk", 2500, 160, 0);
        assertThrows(InvalidInputException.class, () -> e.setBaseSalary(0));
        assertEquals(2500, e.getBaseSalary(), 0.0);
    }

    // ---- WB-03: setOvertimeHours, branch coverage (cap boundary) ----
    void wb03_overtimeAboveCap_throwsButAtCapSucceeds() throws Exception {
        Employee e = new Employee("E012", "Grace Kim", "Technician", 3200, 160, 0);
        assertThrows(InvalidInputException.class, () -> e.setOvertimeHours(41));
        e.setOvertimeHours(40);
        assertEquals(40, e.getOvertimeHours(), 0.0);
    }

    // ---- WB-04: addEmployee duplicate branch ----
    void wb04_addEmployee_duplicateBranch() throws Exception {
        manager.addEmployee(new Employee("E013", "Henry Ford", "Driver", 2800, 160, 0));
        Employee dup = new Employee("E013", "Ivan Petrov", "Driver", 2900, 160, 0);
        assertThrows(DuplicateEmployeeException.class, () -> manager.addEmployee(dup));
        assertEquals(1, manager.employeeCount());
    }

    // ---- WB-05: updateEmployee not-found path then found path ----
    void wb05_updateEmployee_notFoundThenFoundPaths() throws Exception {
        assertThrows(EmployeeNotFoundException.class,
                () -> manager.updateEmployee("E999", "No One", "Ghost", 1000, 160, 0));

        manager.addEmployee(new Employee("E014", "Julia Ross", "Planner", 3300, 160, 0));
        manager.updateEmployee("E014", "Julia Ross-Baker", "Senior Planner", 3600, 160, 5);
        Employee updated = manager.searchById("E014");
        assertEquals("Senior Planner", updated.getPosition());
        assertEquals(3600, updated.getBaseSalary(), 0.0);
    }

    // ---- WB-06: Payment computation paths (no overtime vs. overtime) ----
    void wb06_paymentCalculation_bothOvertimePaths() throws Exception {
        Employee noOt = new Employee("E015", "Kevin Brooks", "Support", 3000, 160, 0);
        Payment p1 = new Payment(noOt);
        assertEquals(0.0, p1.getOvertimePay(), 0.0);
        assertEquals(2550.00, p1.getNetPay(), 0.01);

        Employee withOt = new Employee("E016", "Laura Chen", "Support", 3000, 160, 20);
        Payment p2 = new Payment(withOt);
        assertEquals(562.50, p2.getOvertimePay(), 0.01);
        assertEquals(3028.13, p2.getNetPay(), 0.01);
    }

    private static void assertEquals(Object expected, Object actual) {
        if (expected == null ? actual != null : !expected.equals(actual)) {
            throw new AssertionError("Expected " + expected + " but was " + actual);
        }
    }

    private static void assertEquals(int expected, int actual) {
        if (expected != actual) {
            throw new AssertionError("Expected " + expected + " but was " + actual);
        }
    }

    private static void assertEquals(double expected, double actual, double delta) {
        if (Double.isNaN(actual) || Math.abs(expected - actual) > delta) {
            throw new AssertionError("Expected " + expected + " but was " + actual);
        }
    }

    private interface ThrowingRunnable {
        void run() throws Exception;
    }

    private static <T extends Throwable> void assertThrows(
            Class<T> expectedType, ThrowingRunnable action) {
        try {
            action.run();
        } catch (Exception actual) {
            if (expectedType.isInstance(actual)) {
                return;
            }
            throw new AssertionError("Expected " + expectedType.getSimpleName()
                    + " but was " + actual.getClass().getSimpleName(), actual);
        }
        throw new AssertionError("Expected " + expectedType.getSimpleName()
                + " to be thrown.");
    }
}
