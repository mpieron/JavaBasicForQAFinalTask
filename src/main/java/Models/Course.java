package Models;

public class Course {

    private final String courseName;
    private final int durationHours;

    public Course(String courseName, int durationHours){
        this.courseName = courseName;
        this.durationHours = durationHours;
    }

    @Override
    public String toString(){
        return String.format("%s\t%dh",courseName, durationHours);
    }

    public int getDurationHours(){
        return durationHours;
    }
}
