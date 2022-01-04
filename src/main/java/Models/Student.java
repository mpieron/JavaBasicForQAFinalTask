package Models;

public class Student {

    private final String name;
    private final String surname;

    public Student(String name, String surname){
        this.name = name;
        this.surname = surname;
    }

    public String getFullName(){
        return  String.format("%s %s", name, surname);
    }
}
