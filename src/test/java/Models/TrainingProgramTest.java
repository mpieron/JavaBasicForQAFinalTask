package Models;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TrainingProgramTest {

    List<Course> courses;
    TrainingProgram program;


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
        Assert.assertTrue(program.isFinished(LocalDateTime.of(2022, 6,6,6,0)));
    }

    @Test
    public void calculateEndDateTest(){
        Assert.assertEquals(LocalDateTime.of(2022,1,3,11,0), program.calculateEndDate());
    }

}
