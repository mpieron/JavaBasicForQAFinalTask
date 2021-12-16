import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class TrainingProgram  implements Training {

    private String trainingName;
    private LocalDate startDate;
    private Date endDate;
    private List<Course> courseList;

    public TrainingProgram(List<Course> courseList)
    {
        this.courseList = courseList;
        startDate = LocalDate.now();
    }

    @Override
    public String calculateToComplete() {
        return null;
    }

    @Override
    public String calculateCompleted() {
        return null;
    }

    @Override
    public String toString(){
        return "CURRICULUM:\t\t" +  trainingName +
                "START_DATE: \t\t" +  startDate +
                "COURSE\t\tDURATION (hrs)\n"+
                "--------------------------------------------";
    }
}
