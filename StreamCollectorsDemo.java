import java.util.*;
import java.util.stream.Collectors;

public class StreamCollectorsDemo {
    static class Employee {
        int id;
        String name;
        String department;
        int salary;
        Employee(int id, String name, String department, int salary) {
            this.id = id;
            this.name = name;
            this.department = department;
            this.salary = salary;
        }
        public String toString() {
            return id + " " + name + " " + department + " " + salary;
        }
    }
    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
            new Employee(101, "Aman", "IT", 50000),
            new Employee(102, "Riya", "HR", 35000),
            new Employee(103, "Rahul", "IT", 60000),
            new Employee(104, "Simran", "Sales", 40000),
            new Employee(105, "Karan", "HR", 30000),
            new Employee(106, "Neha", "Sales", 55000),
            new Employee(107, "Arjun", "IT", 70000)
        );
        System.out.println("===== ALL EMPLOYEES =====");
        employees.forEach(System.out::println);
        Map<String, List<Employee>> grouped =
            employees.stream()
                .collect(Collectors.groupingBy(e -> e.department));
        System.out.println("\n===== GROUPING BY DEPARTMENT =====");
        grouped.forEach((department, list) -> {
            System.out.println(department + ": " + list);
        });
        Map<String, Long> count =
            employees.stream()
                .collect(Collectors.groupingBy(
                    e -> e.department,
                    Collectors.counting()
                ));
        System.out.println("\n===== EMPLOYEE COUNT =====");
        count.forEach((department, total) ->
            System.out.println(department + ": " + total)
        );
        Map<Boolean, List<Employee>> partition =
            employees.stream()
                .collect(Collectors.partitioningBy(
                    e -> e.salary >= 50000
                ));
        System.out.println("\n===== PARTITION BY SALARY =====");
        System.out.println("Salary >= 50000:");
        partition.get(true).forEach(System.out::println);
        System.out.println("Salary < 50000:");
        partition.get(false).forEach(System.out::println);
        IntSummaryStatistics statistics =
            employees.stream()
                .collect(Collectors.summarizingInt(e -> e.salary));
        System.out.println("\n===== SALARY SUMMARY =====");
        System.out.println("Total Employees: " + statistics.getCount());
        System.out.println("Total Salary: " + statistics.getSum());
        System.out.println("Minimum Salary: " + statistics.getMin());
        System.out.println("Maximum Salary: " + statistics.getMax());
        System.out.println("Average Salary: " + statistics.getAverage());
        Map<String, Double> averageSalary =
            employees.stream()
                .collect(Collectors.groupingBy(
                    e -> e.department,
                    Collectors.averagingInt(e -> e.salary)
                ));
        System.out.println("\n===== AVERAGE SALARY BY DEPARTMENT =====");
        averageSalary.forEach((department, average) ->
            System.out.println(department + ": " + average)
        );
    }
}

