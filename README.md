Organization X Management System — Homework Submission
=========================================================

Contents
--------
1. Organization_X_Test_Report.docx
   The full write-up: functional requirements, implementation overview,
   test case summary table, all 13 detailed test-case reports (filled
   out on the supplied template), automated-testing notes, and the
   final summary/conclusion.

2. java-source/
   Complete, compiled-and-run Java source:
     - Employee.java, Payment.java, Report.java,
       OrganizationManagement.java, Main.java
     - EmployeeNotFoundException.java, DuplicateEmployeeException.java,
       InvalidInputException.java
     - OrganizationManagementTest.java  (JUnit 5 test class)
     - WB01Driver.java ... WB06Driver.java
       (small standalone programs used to exercise the same compiled
       classes for the white-box test cases, since a plain JUnit
       runner was not available offline in the build environment)

How to run it yourself
-----------------------
    cd java-source
    javac *.java
    java Main            # interactive console menu
    java WB01Driver       # (through WB06Driver) individual white-box checks

To run the JUnit 5 tests you'll need the junit-jupiter dependency on
your classpath (e.g. via Maven/Gradle) — see Section 6 of the report
for details.

Note: 1 defect was intentionally left in and documented (Incident
INC-001, found by test case BB-07) rather than "fixed away" — see the
report for the full discrepancy description and recommendation.
