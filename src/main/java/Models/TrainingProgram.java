package Models;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

public class TrainingProgram{

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

    public String calculateTimeToCompletionOrAfterCompletion(LocalDateTime now) {
        int hours;
        int days;

        if(isFinished(now)){
            hours = calculateFinishedOrToFinishHours(determineEndDate(), now);
            days = calculateFinishedOrToFinishDays(determineEndDate(), now);
        }
        else {
            hours = calculateFinishedOrToFinishHours(now, determineEndDate());
            days = calculateFinishedOrToFinishDays(now, determineEndDate());
        }
        return String.format("%d d %d hours", days, hours);
    }

    private int calculateFinishedOrToFinishDays(LocalDateTime before, LocalDateTime after){
        int days = 0;
        while(before.plusDays(1).isBefore(after)){
            if(before.plusDays(1).getDayOfWeek() != DayOfWeek.SATURDAY && before.plusDays(1).getDayOfWeek() != DayOfWeek.SUNDAY){
                days++;
            }
            before = before.plusDays(1);
        }
        return days;
    }

    private int calculateFinishedOrToFinishHours(LocalDateTime before, LocalDateTime after){
        return after.getHour() < before.getHour() ? endHour - before.getHour() + after.getHour() - startHour : after.getHour() - before.getHour();
    }

    public boolean isFinished(LocalDateTime now){
        return now.isAfter(determineEndDate());
    }

    public LocalDateTime determineEndDate(){
        LocalDateTime date = determineEndDay();
        int hour = determineEndHour();

        return LocalDateTime.of(date.getYear(), date.getMonth(),
                date.getDayOfMonth(), hour, 0);
    }

    private LocalDateTime determineEndDay(){
        LocalDateTime endDate = startDate;
        int days = durationTime/workingHours;

        if(durationTime%workingHours + startDate.getHour() > endHour)
            days++;

        while (days > 0) {
            if(endDate.plusDays(1).getDayOfWeek() != DayOfWeek.SUNDAY && endDate.plusDays(1).getDayOfWeek() != DayOfWeek.SATURDAY) {
                    days--;
            }
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
        return courseList.stream()
                .reduce(0, (partialTime, course) -> partialTime + course.getDurationHours(), Integer::sum);
    }

    public String getTrainingName(){
        return trainingName;
    }

    @Override
    public String toString(){
        return String.format("Working time: %d - %d\nProgram name: %s\nProgram duration: %dh \nStart date: %s \nEnd date: %s\n",
                startHour, endHour, trainingName ,durationTime, startDate, determineEndDate());
    }
}
