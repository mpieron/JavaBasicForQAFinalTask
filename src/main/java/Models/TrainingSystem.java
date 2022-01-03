package Models;

import java.time.LocalDateTime;
import java.util.*;

public class TrainingSystem {

    public static void main(String[] args) {
        List<Course> javaCourses = new ArrayList<>();
        List<Course> testingCourses = new ArrayList<>();
        Map<Student, TrainingProgram> studentCourses = new HashMap<>();

        javaCourses.add(new Course("Java Basic", 3));
        javaCourses.add(new Course("Java Core", 1));
        javaCourses.add(new Course("Testing", 5));

        testingCourses.add(new Course("Basics of testing", 5));
        testingCourses.add(new Course("Manual testing vs Automation testing", 10));
        testingCourses.add(new Course("Selenium", 8));

        TrainingProgram javaTraining = new TrainingProgram("Java", javaCourses, LocalDateTime.of(2021,12,30,10,0));
        TrainingProgram testingProgram = new TrainingProgram("Testing", testingCourses, LocalDateTime.of(2021,12,30,10,0));

        studentCourses.put(new Student("Ivanov", "Ivan"), javaTraining);
        studentCourses.put(new Student("Sidorov", "Ivan"), testingProgram);

        Report report = new Report(studentCourses);

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        System.out.println(report.generateReport(input));
    }
}
