// package FinalProject.edu.ucalgary.oop;

import static org.junit.Assert.*;
import org.junit.Assert;

public class Test {

    @org.junit.Test
    public void testGetTobefed() {
        Animal animal = new Animal("coyote");
        assertEquals(animal.getTobefed(), animal.getNumAnimal());
    }

    @org.junit.Test
    public void testAnimalCBF() {
        Animal animal = new Animal("porcupine");
        int[] result = animal.animalCBF(25);
        int[] expected = new int[]{4, 5};
        assertArrayEquals(expected, result);
    }

    @org.junit.Test
    public void testDecTobefed() {
        Animal animal = new Animal("raccoon");
        animal.decTobefed(1);
        assertEquals(animal.getTobefed(), animal.getNumAnimal() - 1);
    }


    @org.junit.Test
    public void testFeedingTask() {
        FeedingTask task1 = new FeedingTask("coyote");
        Assert.assertEquals(2, task1.getNumberAnimal());
        Assert.assertArrayEquals(new int[]{10, 5}, task1.getFeedTime());

        FeedingTask task2 = new FeedingTask("fox");
        Assert.assertEquals(4, task2.getNumberAnimal());
        Assert.assertArrayEquals(new int[]{5, 5}, task2.getFeedTime());

        FeedingTask task3 = new FeedingTask("beaver");
        Assert.assertEquals(1, task3.getNumberAnimal());
        Assert.assertArrayEquals(new int[]{0, 5}, task3.getFeedTime());

        FeedingTask task4 = new FeedingTask("porcupine");
        Assert.assertEquals(3, task4.getNumberAnimal());
        Assert.assertArrayEquals(new int[]{0, 5}, task4.getFeedTime());

        FeedingTask task5 = new FeedingTask("invalid_species");
        Assert.assertEquals(0, task5.getNumberAnimal());
        Assert.assertArrayEquals(new int[]{0, 0}, task5.getFeedTime()); // Invalid species should return feed time of 0
    }
    }

