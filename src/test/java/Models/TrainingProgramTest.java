package Models;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(JUnitParamsRunner.class)
public class TrainingProgramTest {

    private List<Course> courses;
    private TrainingProgram program;

    @Before
    public void init(){
        courses = new ArrayList<>();
        courses.add(new Course("Java Basic", 3));
        courses.add(new Course("Java Core", 1));
        courses.add(new Course("Testing", 5));

        program = new TrainingProgram("Java", courses, LocalDateTime.of(2021, 12,31,10,0) );
    }

    @Test
    public void coursesListIsEmptyTest(){
        program = new TrainingProgram("No courses", new ArrayList<>(), LocalDateTime.of(2021, 12,31,10,0) );

        assertEquals(program.getStartDate(), program.determineEndDate());
        assertTrue(program.toString().contains("Program duration: 0h"));
    }

    @Test
    public void programIsFinishedTest(){
        assertTrue(program.isFinished(LocalDateTime.of(2022, 6,6,6,0)));
    }

    @Test
    public void programIsNotFinishedTest(){
        assertFalse(program.isFinished(LocalDateTime.of(2021, 12,31,15,0)));
    }

    @Test
    @Parameters(method = "datesToTestCalculateEndDate")
    public void calculateEndDateTest(LocalDateTime startDate, LocalDateTime expectedEndDate){
        program = new TrainingProgram("Java", courses, startDate);

        assertEquals(expectedEndDate, program.determineEndDate());
    }

    @Test
    @Parameters(method = "datesToTestCalculateTimeToCompletionOrAfterCompletion")
    public void calculateTimeToCompletionOrAfterCompletionTest(LocalDateTime startDate, LocalDateTime now, String expected){
        program = new TrainingProgram("Java", courses, startDate);

        assertEquals(expected, program.calculateTimeToCompletionOrAfterCompletion(now));
    }

    @Test(expected = DateTimeException.class)
    public void CourseStartDateIsSaturdayTest() throws DateTimeException{
        program = new TrainingProgram("Java", courses, LocalDateTime.of(2021, 12, 25, 10, 0));

        fail("Course can't start at weekend!");
    }

    private Object[] datesToTestCalculateEndDate(){
        return new Object[] {
                new Object[] {LocalDateTime.of(2021, 12,31,10,0), LocalDateTime.of(2022,1,3,11,0)},
                new Object[] {LocalDateTime.of(2021, 12,1,10,0), LocalDateTime.of(2021,12,2,11,0)},
                new Object[] {LocalDateTime.of(2021, 12,24,15,0), LocalDateTime.of(2021,12,27,16,0)}
        };
    }

    private Object[] datesToTestCalculateTimeToCompletionOrAfterCompletion(){
        return new Object[] {
                new Object[] {LocalDateTime.of(2021, 12,31,10,0), LocalDateTime.of(2021, 12,31,15,0), "4 hours"},
                new Object[] {LocalDateTime.of(2021, 12,3,10,0), LocalDateTime.of(2021,12,3,12,0), "7 hours"},
                new Object[] {LocalDateTime.of(2021, 12,6,15,0), LocalDateTime.of(2021,12,10,11,0), "2 days 3 hours"},
                new Object[] {LocalDateTime.of(2021,12,1,10,0), LocalDateTime.of(2021,12,3,15,0), "1 days 4 hours"},
                new Object[] {LocalDateTime.of(2021,11,22,10,0), LocalDateTime.of(2021,12,6,12,0), "9 days 1 hours"},
                new Object[] {LocalDateTime.of(2021,12,30,10,0), LocalDateTime.of(2021,12,31,8,0), "1 hours"},
                new Object[] {LocalDateTime.of(2021,12,30,10,0), LocalDateTime.of(2021,12,31,20,0), "7 hours"},
                new Object[] {LocalDateTime.of(2021,12,30,10,0), LocalDateTime.of(2021,12,30,20,0), "1 hours"},
                new Object[] {LocalDateTime.of(2021,12,29,16,0), LocalDateTime.of(2021,12,30,8,0), "7 hours"}
        };
    }
}
