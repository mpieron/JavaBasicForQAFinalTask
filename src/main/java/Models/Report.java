package Models;

import java.time.LocalDateTime;
import java.util.Map;

public class Report {

    private final Map<Student, TrainingProgram> studentCourses;
    private final LocalDateTime reportGenerationTime;

    public Report(Map<Student, TrainingProgram> studentCourses, LocalDateTime reportGenerationTime){
        this.studentCourses = studentCourses;
        this.reportGenerationTime = reportGenerationTime;
    }

    public String generateReport(String input){
        StringBuilder report = new StringBuilder();

        if(input.equals("0") || input.equals("")){
            studentCourses.forEach((key, value) -> report.append(generateShortReport(key, value)).append("\n\n"));
        }
        else {
            studentCourses.forEach((key, value) -> report.append(generateFullReport(key, value)).append("\n\n"));
        }
        return report.toString();
    }

    private String generateShortReport(Student student, TrainingProgram training) {
        return String.format("%s (%s) - %s",
                student.getFullName(),
                training.getTrainingName(),
                trainingStatusSupport(training));
    }

    private String generateFullReport(Student student, TrainingProgram training){
        return String.format("%s\n%s%s",
                student.getFullName(),
                training.toString(),
                trainingStatusSupport(training));
    }

    private String trainingStatusSupport(TrainingProgram training){
        String calculatedTime = training.calculateTimeToCompletionOrAfterCompletion(reportGenerationTime);
        StringBuilder status = new StringBuilder();

        if(reportGenerationTime.isBefore(training.getStartDate())){
            return "Program has not started yet!";
        }
        else if(reportGenerationTime.isEqual(training.getStartDate())){
            return "Just started!";
        }
        else if(calculatedTime.equals("")){
            status.append("Program just completed!");
        }
        else {
            status.append(training.isFinished(reportGenerationTime) ? "Program completed. " : "Training is not finished. ");
            status.append(calculatedTime);
            status.append(training.isFinished(reportGenerationTime) ? " have passed since the end." : " are left until the end.");
        }
        return status.toString();
    }
}
