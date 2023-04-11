// package FinalProject.edu.ucalgary.oop;
//Members: Ahmed Iqbal, Musa Jawad, Abrar Rehan, Rishik Roy
//Code version: 11.0.17

import java.util.ArrayList;

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

    // Test the constructor and getNumberAnimal() method for a valid animal name
    @org.junit.Test

    public void testValidAnimalName() {
        FeedingTask coyoteTask = new FeedingTask("coyote");
        assertEquals("The number of coyotes should be 2", 2, coyoteTask.getNumberAnimal());
    }


    // Test the getFeedTime() method for different animal species
    @org.junit.Test
    public void testGetFeedTime() {
        FeedingTask coyoteTask = new FeedingTask("coyote");
        assertArrayEquals(new int[]{10, 5, 19}, coyoteTask.getFeedTime());

        FeedingTask foxTask = new FeedingTask("fox");
        assertArrayEquals(new int[]{5, 5, 0}, foxTask.getFeedTime());

        FeedingTask porcupineTask = new FeedingTask("porcupine");
        assertArrayEquals(new int[]{0, 5, 19}, porcupineTask.getFeedTime());
    }
    @org.junit.Test
    public void testGetInfo() throws MedicalTaskException {
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
    public void testValidAnimalNames() {
        CleaningTask coyoteTask = new CleaningTask("coyote");
        assertEquals(5, coyoteTask.getCleanTime());

        CleaningTask porcupineTask = new CleaningTask("porcupine");
        assertEquals(10, porcupineTask.getCleanTime());
    }

    // Test the constructor for an invalid animal name, expecting an IllegalArgumentException
    @org.junit.Test(expected = IllegalArgumentException.class)
    public void testInvalidAnimalName() {
        CleaningTask invalidTask = new CleaningTask("invalidAnimal");
    }

    // Test the cleanCBF() method for various inputs
    @org.junit.Test
    public void testCleanCBF() {
        CleaningTask foxTask = new CleaningTask("fox");
        assertArrayEquals(new int[]{3, 1}, foxTask.cleanCBF(16));

        CleaningTask racoonTask = new CleaningTask("racoon");
        assertArrayEquals(new int[]{2, 0}, racoonTask.cleanCBF(10));

        CleaningTask porcupineTask = new CleaningTask("porcupine");
        assertArrayEquals(new int[]{1, 3}, porcupineTask.cleanCBF(13));
    }

}


