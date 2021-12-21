package Models;

public class Student {

    private final String name;
    private final String surname;
    private static int id = 1;
    private final int studentId;

    public Student(String name, String surname){
        this.name = name;
        this.surname = surname;
        this.studentId = id;
        id ++;
    }

    public String getName() { return name; }

    public String getSurname() { return surname; }

    public String getFullName(){
        return  name + " " + surname;
    }

    public int getStudentId() {
        return studentId;
    }
}
