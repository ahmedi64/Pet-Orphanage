// package FinalProject.edu.ucalgary.oop;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

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
        Animal animal = new Animal("racoon");
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
                {"1", "Loner", "coyote"},
                {"7", "Slinky", "fox"}
        };
        String[][] tasks = {
                {"1", "Kit feeding", "30"},
                {"2", "Rebandage leg wound", "20"}
        };
        String[][] treatments = {
                {"1", "6", "1", "0"},
                {"2", "6", "1", "2"},
                {"3", "6", "1", "4"}
        };
        int treatmentRows = 3;

        MedicalTask task = new MedicalTask(animals, tasks, treatments, treatmentRows);

        ArrayList<String[]> infoList = task.getInfo();
        assertEquals(3, infoList.size());
        assertArrayEquals(new String[]{"0", "Kit feeding", "30", "fox"}, infoList.get(0));
        assertArrayEquals(new String[]{"2", "Kit feeding", "30", "fox"}, infoList.get(1));
        assertArrayEquals(new String[]{"4", "Kit feeding", "30", "fox"}, infoList.get(2));
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

}


