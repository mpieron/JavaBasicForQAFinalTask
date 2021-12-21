package Models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TrainingProgram  implements Training {

    private final String trainingName;
    private final LocalDate startDate;
    private final List<Course> courseList;
    private final int durationTime;

    public TrainingProgram(String trainingName, List<Course> courseList, LocalDate startDate)
    {
        this.trainingName = trainingName;
        this.courseList = courseList;
        this.startDate = startDate;
        this.durationTime = calculateDurationTime();
    }

    @Override
    public int calculateTime(LocalDateTime now) {
        return isFinished(now) ? calculateWhenFinished() : calculateWhenWillFinished();
    }

    private int calculateWhenFinished(){
        return 0;
    }

    private int calculateWhenWillFinished(){
        return 0;
    }

    public boolean isFinished(LocalDateTime now){
        return now.isAfter(calculateEndDate());
    }

    private int calculateDurationTime(){
        int durationTime = 0;

        for(Course course : courseList)
            durationTime += course.getDurationHours();

        return durationTime;
    }

    private LocalDateTime calculateEndDate(){
        int days = durationTime/workingHours;
        int hours = durationTime%workingHours;

        return LocalDateTime.of(startDate.getYear(), startDate.getMonthValue(),
                startDate.plusDays(days).getDayOfYear(), 10+hours, 0);
    }

    public String getTrainingName(){
        return trainingName;
    }

    @Override
    public String toString(){
        return "Working time: " +  startHour + " - " + endHour + "\n" +
                "Program name: " + trainingName + "\n" +
                "Program duration: "  +  durationTime + "\n" +
                "Start date: " + startDate + "\n" +
                "End date: " + calculateEndDate() + "\n";
    }
}
