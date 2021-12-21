package Models;

public class Course {

    private String courseName;
    private int durationHours;

    @Override
    public String toString(){
        return  courseName + "\t\t" + durationHours;
    }

    public String getCourseName(){
        return courseName;
    }

    public int getDurationHours(){
        return durationHours;
    }
}
