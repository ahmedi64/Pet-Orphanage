// package FinalProject.edu.ucalgary.oop;

import org.junit.Before;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Test {

    @org.junit.Test
    public void testGetTobefed() {
        Animal animal = new Animal("coyote");
        assertEquals(animal.getTobefed(), animal.getNumAnimal());
    }

    @org.junit.Test
    public void testAnimalConstructor() {
        Animal animal = new Animal("coyote");
        assertEquals("coyote", animal.getName());
        assertEquals(3, animal.getFeedTimeHour().length);
        assertEquals(5, animal.getCleantime());
    }

    @org.junit.Test
    public void testDecTobefed() {
        Animal animal = new Animal("raccoon");
        animal.decTobefed(1);
        assertEquals(animal.getTobefed(), animal.getNumAnimal() - 1);
    }
    @org.junit.Test
    public void testFeedingTaskConstructor() {
        FeedingTask feedingTask = new FeedingTask("coyote");
        assertNotNull(feedingTask);
    }
    @org.junit.Test
    public void testGetInfo() {
        String[][] animals = {
                {"1", "Monkey", "Primate"},
                {"2", "Parrot", "Bird"}
        };
        String[][] tasks = {
                {"1", "Feeding", "30"},
                {"2", "Medical Checkup", "15"}
        };
        String[][] treatments = {
                {"1", "1", "10"},
                {"2", "1", "10"},
                {"1", "2", "11"}
        };
        int treatmentRows = 3;

        MedicalTask task = new MedicalTask(animals, tasks, treatments, treatmentRows);

        ArrayList<String[]> infoList = task.getInfo();
        assertEquals(3, infoList.size());
        assertArrayEquals(new String[]{"10", "Feeding", "30", "Monkey"}, infoList.get(0));
        assertArrayEquals(new String[]{"10", "Feeding", "30", "Parrot"}, infoList.get(1));
        assertArrayEquals(new String[]{"11", "Medical Checkup", "15", "Monkey"}, infoList.get(2));
    }
    @org.junit.Test
    public void testAddVolenteer() {
        Hour hour = new Hour();
        assertFalse(hour.getVolenteer());

        // Add a volunteer and check that timeRemaining is updated
        hour.addVolenteer();
        assertTrue(hour.getVolenteer());
        assertEquals(120, hour.getTimeRemaining());

    }

    @org.junit.Test
    public void testAddFeedingTasks() {
        ScheduleBuilder scheduleBuilder = new ScheduleBuilder();

        HashMap<Integer, Hour> schedule = new HashMap<>();
        for (int i = 0; i <= 23; i++) {
            Hour hour = new Hour();
            schedule.put(i, hour);
        }

        Animal animal = new Animal("coyote");

        ArrayList<String[]> feedingTasks;
        try {
            feedingTasks = scheduleBuilder.addFeedingTasks(schedule, animal);
            assertNotNull(feedingTasks);
        } catch (AnimalFeedingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    private Hour hour;

    @Before
    public void setUp() {
        hour = new Hour();
    }

    @org.junit.Test
    public void testInitialValues() {
        // Test initial time remaining
        assertEquals("Initial time remaining should be 60 minutes", 60, hour.getTimeRemaining());

        // Test initial volunteer status
        assertFalse("Initial volunteer status should be false", hour.getVolenteer());
    }

    @org.junit.Test
    public void testAddTasks() {
        // Add a task
        hour.addTasks("Feed", 30, "Coyote");

        // Test time remaining after adding a task
        assertEquals("Time remaining should be reduced after adding a task", 30, hour.getTimeRemaining());

        // Test task list size after adding a task
        List<String[]> tasks = hour.getTasks();
        assertEquals("Task list should have one task after adding a task", 1, tasks.size());

        // Test task content
        String[] task = tasks.get(0);
        assertEquals("Task name should be 'Feed'", "Feed", task[0]);
        assertEquals("Task animal should be 'Coyote'", "Coyote", task[1]);
    }

    @org.junit.Test
    public void testAddVolunteer() {
        // Add a volunteer
        hour.addVolenteer();

        // Test time remaining after adding a volunteer
        assertEquals("Time remaining should be increased after adding a volunteer", 120, hour.getTimeRemaining());

        // Test volunteer status after adding a volunteer
        assertTrue("Volunteer status should be true after adding a volunteer", hour.getVolenteer());
    }

    @org.junit.Test
    public void testSetTimeRemaining() {
        // Set time remaining
        hour.setTimeRemaining(40);

        // Test time remaining after setting it
        assertEquals("Time remaining should be set to the specified value", 40, hour.getTimeRemaining());
    }
}



