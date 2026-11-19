import java.util.*;
import java.io.*;


class GenericBox<T> {
    private T data;
    public void setData(T data) {
        this.data = data;
    }
    public T getData() {
        return data;
    }
}
class Student {
    int id;
    String name;
    int marks;
    Student(int id, String name, int marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
    }
    public String toString() {
        return id + "," + name + "," + marks;
    }
}
public class GenericsFileHandlingDemo {
    static ArrayList<Student> students = new ArrayList<>();
    static String fileName = "students.txt";
    static void writeFile() throws IOException {
        try (BufferedWriter writer =
             new BufferedWriter(new FileWriter(fileName))) {
            for (Student s : students) {
                writer.write(s.toString());
                writer.newLine();
            }
        }
        System.out.println("Student records saved to file!");
    }
    static void readFile() throws IOException {
        students.clear();
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("File does not exist!");
            return;
        }
        try (BufferedReader reader =
             new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0]);
                String name = data[1];
                int marks = Integer.parseInt(data[2]);
                students.add(new Student(id, name, marks));
            }
        }
        System.out.println("Student records loaded from file!");
    }
    static void displayStudents() {
        System.out.println("\nID\tName\tMarks");
        for (Student s : students) {
            System.out.println(
                s.id + "\t" + s.name + "\t" + s.marks
            );
        }
    }
    static void searchStudent(int searchId) {
        boolean found = false;
        for (Student s : students) {
            if (s.id == searchId) {
                System.out.println("Student Found:");
                System.out.println(s);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Student Not Found!");
        }
    }
    static void updateStudent(int updateId, String newName, int newMarks)
            throws IOException {
        boolean found = false;
        for (Student s : students) {
            if (s.id == updateId) {
                s.name = newName;
                s.marks = newMarks;
                found = true;
                break;
            }
        }
        if (found) {
            writeFile();
            System.out.println("Student Updated Successfully!");
        } else {
            System.out.println("Student Not Found!");
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            GenericBox<String> box = new GenericBox<>();
            box.setData("Java Generics");
            System.out.println("Generic Class Example: " +
                box.getData());
            // Create initial records only if file does not exist
File file = new File(fileName);

if (!file.exists()) {
    students.add(new Student(101, "Aman", 85));
    students.add(new Student(102, "Riya", 92));
    students.add(new Student(103, "Rahul", 78));

    writeFile();
} else {
    readFile();
}
            writeFile();
            int choice;
            do {
                System.out.println("\n===== STUDENT FILE MANAGEMENT =====");
                System.out.println("1. Write Student Records");
                System.out.println("2. Read Student Records");
                System.out.println("3. Search Student");
                System.out.println("4. Update Student");
                System.out.println("5. Display All Students");
                System.out.println("0. Exit");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        writeFile();
                        break;
                    case 2:
                        readFile();
                        displayStudents();
                        break;
                    case 3:
                        readFile();
                        System.out.print("Enter Student ID: ");
                        int searchId = sc.nextInt();
                        searchStudent(searchId);
                        break;
                    case 4:
                        readFile();
                        System.out.print("Enter Student ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter New Name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter New Marks: ");
                        int marks = sc.nextInt();
                        updateStudent(id, name, marks);
                        break;
                    case 5:
                        readFile();
                        displayStudents();
                        break;
                    case 0:
                        System.out.println("Program Closed!");
                        break;
                    default:
                        System.out.println("Invalid Choice!");
                }
            } while (choice != 0);
        } catch (IOException e) {
            System.out.println("File Error: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Please enter valid input!");
        } finally {
            sc.close();
        }
    }
}

