package JDBCLab;

import java.sql.*;

public class JDBCStatementDemo {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/college",
                "root",
                "1447Ayra;"
            );
            System.out.println("Connected Successfully!");
            Statement st = con.createStatement();
            st.executeUpdate(
                "CREATE TABLE IF NOT EXISTS LabStudent (" +
                "id INT PRIMARY KEY, " +
                "name VARCHAR(50), " +
                "marks INT)"
            );
            System.out.println("Table Created!");
            st.executeUpdate(
                "INSERT IGNORE INTO LabStudent VALUES " +
                "(1, 'Aman', 85), " +
                "(2, 'Riya', 90), " +
                "(3, 'Rahul', 78)"
            );
            System.out.println("Records Inserted!");
            ResultSet rs = st.executeQuery(
                "SELECT * FROM LabStudent"
            );
            System.out.println("\nID\tName\tMarks");
            while (rs.next()) {
                System.out.println(
                    rs.getInt("id") + "\t" +
                    rs.getString("name") + "\t" +
                    rs.getInt("marks")
                );
            }
            rs.close();
            st.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

