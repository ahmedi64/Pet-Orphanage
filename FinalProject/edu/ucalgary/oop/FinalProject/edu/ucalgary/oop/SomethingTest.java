package FinalProject.edu.ucalgary.oop.FinalProject.edu.ucalgary.oop;

import static org.junit.Assert.*;

import FinalProject.edu.ucalgary.oop.Animal;
import org.junit.Test;

public class SomethingTest {

    @Test
    public void testGetTobefed() {
        Animal animal = new Animal("coyote");
        assertEquals(animal.getTobefed(), animal.getNumAnimal());
    }

    @Test
    public void testAnimalCBF() {
        Animal animal = new Animal("porcupine");
        int[] result = animal.animalCBF(25);
        int[] expected = new int[]{4, 5};
        assertArrayEquals(expected, result);
    }

    @Test
    public void testDecTobefed() {
        Animal animal = new Animal("raccoon");
        animal.decTobefed();
        assertEquals(animal.getTobefed(), animal.getNumAnimal() - 1);
    }
}

