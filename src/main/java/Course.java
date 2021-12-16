public class Course {

    private boolean finished = false;
    private String courseName;
    private int durationHours;

    public void finishedCourse(){
        this.finished = true;
    }

    @Override
    public String toString(){
        return  courseName + "\t\t" + durationHours;
    }

    public boolean isFinished() {
        return finished;
    }

    public String getCourseName(){
        return courseName;
    }

    public int getDurationHours(){
        return durationHours;
    }
}
