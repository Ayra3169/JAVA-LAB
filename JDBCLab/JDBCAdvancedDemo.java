package JDBCLab;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class JDBCAdvancedDemo {
    static class Student {
        int id;
        String name;
        int marks;
        Student(int id, String name, int marks) {
            this.id = id;
            this.name = name;
            this.marks = marks;
        }
        void display() {
            System.out.println(id + "\t" + name + "\t" + marks);
        }
    }
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/college";
        String user = "root";
        String password = "1447Ayra;";
        try (
            Connection con = DriverManager.getConnection(url, user, password);
            Scanner sc = new Scanner(System.in)
        ) {
            System.out.println("Database Connected Successfully!");
            String createTable = "CREATE TABLE IF NOT EXISTS AdvancedStudent (" +
                    "id INT PRIMARY KEY, " +
                    "name VARCHAR(50), " +
                    "marks INT)";
            try (Statement st = con.createStatement()) {
                st.executeUpdate(createTable);
            }
            int choice;
            do {
                System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
                System.out.println("1. Insert Student");
                System.out.println("2. View All Students");
                System.out.println("3. Update Student");
                System.out.println("4. Delete Student");
                System.out.println("5. Display ResultSetMetaData");
                System.out.println("6. Display DatabaseMetaData");
                System.out.println("7. Transaction Demo");
                System.out.println("0. Exit");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();
                try {
                    switch (choice) {
                        case 1:
                            System.out.print("Enter ID: ");
                            int id = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Enter Name: ");
                            String name = sc.nextLine();
                            System.out.print("Enter Marks: ");
                            int marks = sc.nextInt();
                            String insert = "INSERT INTO AdvancedStudent VALUES (?, ?, ?)";
                            try (PreparedStatement ps = con.prepareStatement(insert)) {
                                ps.setInt(1, id);
                                ps.setString(2, name);
                                ps.setInt(3, marks);
                                int rows = ps.executeUpdate();
                                System.out.println(rows + " Student Inserted!");
                            }
                            break;
                        case 2:
                            ArrayList<Student> students = new ArrayList<>();
                            String select = "SELECT * FROM AdvancedStudent";
                            try (
                                Statement st = con.createStatement();
                                ResultSet rs = st.executeQuery(select)
                            ) {
                                while (rs.next()) {
                                    Student s = new Student(
                                        rs.getInt("id"),
                                        rs.getString("name"),
                                        rs.getInt("marks")
                                    );
                                    students.add(s);
                                }
                            }
                            System.out.println("\nID\tName\tMarks");
                            for (Student s : students) {
                                s.display();
                            }
                            System.out.println("Total Students: " + students.size());
                            break;
                        case 3:
                            System.out.print("Enter Student ID: ");
                            int updateId = sc.nextInt();
                            System.out.print("Enter New Marks: ");
                            int newMarks = sc.nextInt();
                            String update = "UPDATE AdvancedStudent SET marks = ? WHERE id = ?";
                            try (PreparedStatement ps = con.prepareStatement(update)) {
                                ps.setInt(1, newMarks);
                                ps.setInt(2, updateId);
                                int rows = ps.executeUpdate();
                                System.out.println(rows + " Student Record Updated!");
                            }
                            break;
                        case 4:
                            System.out.print("Enter Student ID to delete: ");
                            int deleteId = sc.nextInt();
                            String delete = "DELETE FROM AdvancedStudent WHERE id = ?";
                            try (PreparedStatement ps = con.prepareStatement(delete)) {
                                ps.setInt(1, deleteId);
                                int rows = ps.executeUpdate();
                                System.out.println(rows + " Student Record Deleted!");
                            }
                            break;
                        case 5:
                            String query = "SELECT * FROM AdvancedStudent";
                            try (
                                Statement st = con.createStatement();
                                ResultSet rs = st.executeQuery(query)
                            ) {
                                ResultSetMetaData rsmd = rs.getMetaData();
                                System.out.println("\nResultSet Metadata:");
                                System.out.println("Number of Columns: " +
                                    rsmd.getColumnCount());
                                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                                    System.out.println(
                                        "Column: " + rsmd.getColumnName(i) +
                                        " | Type: " + rsmd.getColumnTypeName(i)
                                    );
                                }
                            }
                            break;
                        case 6:
                            DatabaseMetaData dbmd = con.getMetaData();
                            System.out.println("\nDatabase Metadata:");
                            System.out.println("Database Name: " +
                                dbmd.getDatabaseProductName());
                            System.out.println("Database Version: " +
                                dbmd.getDatabaseProductVersion());
                            System.out.println("Driver Name: " +
                                dbmd.getDriverName());
                            System.out.println("Driver Version: " +
                                dbmd.getDriverVersion());
                            break;
                        case 7:
                            System.out.print("Enter Student ID: ");
                            int transactionId = sc.nextInt();
                            System.out.print("Enter New Marks: ");
                            int transactionMarks = sc.nextInt();
                            System.out.print("Commit changes? (Y/N): ");
                            String answer = sc.next();
                            con.setAutoCommit(false);
                            try {
                                String transactionQuery =
                                    "UPDATE AdvancedStudent SET marks = ? WHERE id = ?";
                                try (PreparedStatement ps =
                                    con.prepareStatement(transactionQuery)) {
                                    ps.setInt(1, transactionMarks);
                                    ps.setInt(2, transactionId);
                                    int rows = ps.executeUpdate();
                                    if (rows == 0) {
                                        System.out.println("Student ID not found!");
                                        con.rollback();
                                    } else if (answer.equalsIgnoreCase("Y")) {
                                        con.commit();
                                        System.out.println("Transaction Committed!");
                                    } else {
                                        con.rollback();
                                        System.out.println("Transaction Rolled Back!");
                                    }
                                }
                            } catch (SQLException e) {
                                con.rollback();
                                System.out.println("Transaction Failed: " +
                                    e.getMessage());
                            } finally {
                                con.setAutoCommit(true);
                            }
                            break;
                        case 0:
                            System.out.println("Exiting Program...");
                            break;
                        default:
                            System.out.println("Invalid Choice!");
                    }
                } catch (SQLException e) {
                    System.out.println("Database Error: " + e.getMessage());
                }
            } while (choice != 0);
        } catch (SQLException e) {
            System.out.println("Connection/Database Error: " + e.getMessage());
        }
    }
}

