package Models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

public class Report {

    // short report -  no parameter or 0
    // full report - otherwise

    private final Map<Student, TrainingProgram> studentCourses;
    private final LocalDateTime now = LocalDateTime.now();

    public Report(Map<Student, TrainingProgram> studentCourses){
        this.studentCourses = studentCourses;
    }

    public String generateReport(char input){
        String report = "";

        for (Map.Entry<Student, TrainingProgram> entry : studentCourses.entrySet()) {
            Student student = entry.getKey();
            TrainingProgram trainingProgram = entry.getValue();
            report = (input == '0' || input == ' ')  ? generateShortReport(student, trainingProgram) : generateFullReport(student, trainingProgram);
        }

        return report;
    }

    private String generateShortReport(Student student, TrainingProgram training) {
        boolean finished = training.isFinished(now);
        String report = student.getFullName() + "(" + training.getTrainingName() + ") - ";
        report +=  finished ? "Training completed." : "Training is not finished. ";
        report += training.calculateTime(now);
        report += finished ? "are left until the end." : "have passed since the end.";
        return report;
    }

    private String generateFullReport(Student student, TrainingProgram training){
        String report = student.getFullName() + "\n" + training.toString();
        report += training.calculateTime(now);
        report += training.isFinished(now) ? "are left until the end." : "have passed since the end.";
        return  report;
    }
}
