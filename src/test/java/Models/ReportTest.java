package Models;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ReportTest {

    Student student;
    TrainingProgram program;
    List<Course> courses;
    LocalDateTime reportGenerationTime;
    LocalDateTime courseStartTime;
    Map<Student, TrainingProgram> studentCourses;
    Report report;


    @Before
    public void init(){
        courseStartTime = LocalDateTime.of(2021,12,1,10,0);

        courses = new ArrayList<>();
        studentCourses = new HashMap<>();
        student = new Student("Jan", "Kowalski");
        courses.add(new Course("JavaBasic", 9));
        program = new TrainingProgram("Java", courses, courseStartTime);

        studentCourses.put(student, program);
    }

    @Test
    public void generateShortReportTest(){
        reportGenerationTime = LocalDateTime.of(2021,12,2,15,0);
        report = new Report(studentCourses, reportGenerationTime);
        String expected = String.format("%s (%s) - %s%s%s",
                student.getFullName(), program.getTrainingName(),
                "Program completed. ", program.calculateTimeToCompletionOrAfterCompletion(reportGenerationTime) ,
                " have passed since the end.\n\n");

        assertEquals(expected, report.generateReport("0"));
    }

    @Test
    public void generateFullReportTest(){
        reportGenerationTime = LocalDateTime.of(2021,12,3,12,0);
        report = new Report(studentCourses, reportGenerationTime);
        String expected = String.format("%s\n%s%s%s%s",
                student.getFullName(),
                program.toString(),
                "Program completed. ",
                program.calculateTimeToCompletionOrAfterCompletion(reportGenerationTime) ,
                " have passed since the end.\n\n");

        assertEquals(expected, report.generateReport("a"));
    }

    @Test
    public void programHasNotStarted(){
        reportGenerationTime = LocalDateTime.of(2021,10,2,15,0);
        report = new Report(studentCourses, reportGenerationTime);
        String expected = String.format("%s (%s) - %s", student.getFullName(), program.getTrainingName(), "Program has not started yet!\n\n");

        assertEquals(expected, report.generateReport("0"));
    }

    @Test
    public void programJustCompletedTest(){
        reportGenerationTime = LocalDateTime.of(2021,12,2,11,0);
        report = new Report(studentCourses, reportGenerationTime);
        String expected = String.format("%s (%s) - %s", student.getFullName(), program.getTrainingName(), "Program just completed!\n\n");

        assertEquals(expected, report.generateReport("0"));
    }

    @Test
    public void programJustStartedTest(){
        String expected = String.format("%s (%s) - %s", student.getFullName(), program.getTrainingName(), "Just started!\n\n");
        report = new Report(studentCourses, courseStartTime);

        assertEquals(expected, report.generateReport("0"));
    }
}
