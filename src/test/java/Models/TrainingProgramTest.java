package Models;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

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

    private Object[] datesToTestCalculateEndDate(){
        return new Object[] {
                new Object[] {LocalDateTime.of(2021, 12,31,10,0), LocalDateTime.of(2022,1,3,11,0)},
                new Object[] {LocalDateTime.of(2021, 12,1,10,0), LocalDateTime.of(2021,12,2,11,0)},
                new Object[] {LocalDateTime.of(2021, 12,24,15,0), LocalDateTime.of(2021,12,27,16,0)}
        };
    }

    private Object[] datesToTestCalculateTimeToCompletionOrAfterCompletion(){
        return new Object[] {
                new Object[] {LocalDateTime.of(2021, 12,31,10,0), LocalDateTime.of(2021, 12,31,15,0), "0 d 4 hours"},
                new Object[] {LocalDateTime.of(2021, 12,3,10,0), LocalDateTime.of(2021,12,3,12,0), "0 d 7 hours"},
                new Object[] {LocalDateTime.of(2021, 12,6,15,0), LocalDateTime.of(2021,12,10,11,0), "2 d 3 hours"}
        };
    }
}
