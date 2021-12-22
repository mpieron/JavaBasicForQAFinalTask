package Models;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

public class TrainingProgram{

    private static final int workingDays = 5;
    private static final int workingHours = 8;
    private static final int startHour = 10;
    private static final int endHour = 18;

    private final String trainingName;
    private final LocalDateTime startDate;
    private final List<Course> courseList;
    private final int durationTime;

    public TrainingProgram(String trainingName, List<Course> courseList, LocalDateTime startDate)
    {
        this.trainingName = trainingName;
        this.courseList = courseList;
        this.startDate = startDate;
        this.durationTime = calculateDurationTime();
    }

    public int calculateTime(LocalDateTime now) {
        return 0;
    }


    public boolean isFinished(LocalDateTime now){
        return now.isAfter(calculateEndDate());
    }

    public LocalDateTime calculateEndDate(){
        LocalDateTime date = determineEndDay();
        int hour = determineEndHour();

        return LocalDateTime.of(date.getYear(), date.getMonth(),
                date.getDayOfMonth(), hour, 0);
    }

    private LocalDateTime determineEndDay(){
        int days = durationTime/workingHours;
        LocalDateTime endDate = startDate;

        if(durationTime%workingHours + startDate.getHour() > endHour)
            days += 1;

        while (days > 0) {
            if(endDate.plusDays(1).getDayOfWeek() != DayOfWeek.SUNDAY && endDate.plusDays(1).getDayOfWeek() != DayOfWeek.SATURDAY) {
                    endDate = endDate.plusDays(1);
                    days -= 1;
            }
            else
                endDate = endDate.plusDays(1);
        }

        return endDate;
    }

    private int determineEndHour(){
        int hour = durationTime%workingHours + startDate.getHour();
        if(hour  > endHour)
            hour = startHour + hour%workingHours;

        return hour;
    }

    private int calculateDurationTime(){
        int durationTime = 0;

        for(Course course : courseList)
            durationTime += course.getDurationHours();

        return durationTime;
    }

    public String getTrainingName(){
        return trainingName;
    }

    @Override
    public String toString(){
        return String.format("Working time: %d - %d\n Program name: %s\n Program duration: %d \n Start date: %s \n End date: %s",
                startHour, endHour, trainingName ,durationTime, startDate, calculateEndDate());
    }
}
