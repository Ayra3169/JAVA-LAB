
package JDBCLab;

import java.sql.*;

public class JDBCDemo {

    public static void main(String[] args) {

        try {
            // Load MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to college database
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/college",
                "root",
                "1447Ayra;"
            );

            System.out.println("Database Connected Successfully!");

            Statement st = con.createStatement();

            // Create Student table if it does not exist
            String createTable = "CREATE TABLE IF NOT EXISTS Student (" +
                    "rollno INT PRIMARY KEY, " +
                    "full_name VARCHAR(50), " +
                    "marks INT NOT NULL, " +
                    "grade VARCHAR(1), " +
                    "city VARCHAR(50))";

            st.executeUpdate(createTable);

            System.out.println("Student Table Created!");

            // Insert student records
            String insert = "INSERT INTO Student " +
        "(rollno, full_name, marks, grade, city) VALUES " +
        "(107, 'Aman', 85, 'A', 'Delhi'), " +
        "(108, 'Riya', 92, 'A', 'Chandigarh'), " +
        "(109, 'Rahul', 76, 'B', 'Patiala') " +
        "ON DUPLICATE KEY UPDATE " +
        "full_name = VALUES(full_name), " +
        "marks = VALUES(marks), " +
        "grade = VALUES(grade), " +
        "city = VALUES(city)";

            int rows = st.executeUpdate(insert);

            System.out.println(rows + " Records Inserted Successfully!");

            // Display student records
            ResultSet rs = st.executeQuery("SELECT * FROM Student");

            System.out.println("\nStudent Records:");
            System.out.println("RollNo\tName\tMarks\tGrade\tCity");

            while (rs.next()) {

                System.out.println(
                    rs.getInt("rollno") + "\t" +
                    rs.getString("full_name") + "\t" +
                    rs.getInt("marks") + "\t" +
                    rs.getString("grade") + "\t" +
                    rs.getString("city")
                );
            }

            // Close resources
            rs.close();
            st.close();
            con.close();

            System.out.println("\nDatabase Operations Completed!");

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}


