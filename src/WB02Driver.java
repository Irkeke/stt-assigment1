public class WB02Driver {
    public static void main(String[] args) throws Exception {
        Employee e = new Employee("E011", "Frank Moss", "Clerk", 2500, 160, 0);
        try {
            e.setBaseSalary(0); // boundary value: exactly 0, should hit the throw branch
            System.out.println("UNEXPECTED: no exception thrown");
        } catch (InvalidInputException ex) {
            System.out.println("Caught expected exception: " + ex.getMessage());
        }
        System.out.println("baseSalary unchanged=" + e.getBaseSalary());
    }
}
