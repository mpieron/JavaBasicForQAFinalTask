package Models;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TrainingProgram{

    private static final int workingHours = 8;
    private static final int startHour = 10;
    private static final int endHour = 18;

    private final LocalDateTime startDate;
    private final String trainingName;
    private final List<Course> courseList;
    private final int durationTime;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public TrainingProgram(String trainingName, List<Course> courseList, LocalDateTime startDate)
    {
        checkStartDate(startDate);
        this.trainingName = trainingName;
        this.courseList = courseList;
        this.startDate = startDate;
        this.durationTime = calculateDurationTime();
    }

    private void checkStartDate(LocalDateTime start) throws DateTimeException{
        if(start.getDayOfWeek() == DayOfWeek.SATURDAY || start.getDayOfWeek() == DayOfWeek.SUNDAY)
            throw new DateTimeException("Course can't start on the weekend!");
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
        return (days > 0 ? String.format("%d days ", days) : "") + (hours > 0 ? String.format("%d hours", hours) : "");
    }

    private int calculateFinishedOrToFinishDays(LocalDateTime before, LocalDateTime after){
        int days = 0;
        while(before.plusDays(1).isBefore(after)){
            if (before.plusDays(1).getDayOfWeek() != DayOfWeek.SATURDAY && before.plusDays(1).getDayOfWeek() != DayOfWeek.SUNDAY) {
                days++;
            }
            before = before.plusDays(1);
        }
        return days;
    }

    private int calculateFinishedOrToFinishHours(LocalDateTime before, LocalDateTime after){
        if(after.getHour() < startHour){
            return before.getHour()-startHour;
        }
        else if(after.getHour() > endHour){
            return endHour - before.getHour();
        }
        else if(before.getHour() < startHour || before.getHour() > endHour) {
            return after.getHour() - startHour;
        }
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

    public LocalDateTime getStartDate(){ return startDate; }

    @Override
    public String toString(){
        return String.format("Working time: %d - %d\nProgram name: %s\nProgram duration: %dh\n%sStart date: %s \nEnd date: %s\n",
                startHour,
                endHour,
                trainingName,
                durationTime,
                listCourses(),
                startDate.format(formatter),
                determineEndDate().format(formatter));
    }

    private String listCourses(){
        StringBuilder list = new StringBuilder();
        courseList.forEach(course -> list.append(course.toString()).append("\n"));

        return list.toString();
    }
}
