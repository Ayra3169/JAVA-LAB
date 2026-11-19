package JDBCLab;

import java.util.*;

public class LambdaEmployeeDemo {
    @FunctionalInterface
    interface EmployeeOperation {
        void display(Employee e);
    }
    static class Employee {
        int id;
        String name;
        double salary;
        Employee(int id, String name, double salary) {
            this.id = id;
            this.name = name;
            this.salary = salary;
        }
        void display() {
            System.out.println(id + "  " + name + "  " + salary);
        }
        static int compareByName(Employee e1, Employee e2) {
            return e1.name.compareTo(e2.name);
        }
        public String toString() {
            return id + "  " + name + "  " + salary;
        }
    }
    public static void main(String[] args) {
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(new Employee(101, "Rahul", 45000));
        employees.add(new Employee(102, "Aman", 35000));
        employees.add(new Employee(103, "Riya", 55000));
        employees.add(new Employee(104, "Simran", 40000));
        System.out.println("Employee Details:");
        employees.forEach(e -> e.display());
        employees.sort((e1, e2) ->
            Double.compare(e1.salary, e2.salary)
        );
        System.out.println("\nEmployees Sorted by Salary:");
        employees.forEach(System.out::println);
        employees.sort(Employee::compareByName);
        System.out.println("\nEmployees Sorted by Name:");
        employees.forEach(System.out::println);
        EmployeeOperation operation = e -> e.display();
        System.out.println("\nUsing Functional Interface:");
        operation.display(employees.get(0));
        EmployeeOperation operation2 = Employee::display;
        System.out.println("\nUsing Method Reference:");
        operation2.display(employees.get(1));
    }
}

