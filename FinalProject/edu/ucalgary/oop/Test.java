package FinalProject.edu.ucalgary.oop;

import static org.junit.Assert.*;

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
        animal.decTobefed();
        assertEquals(animal.getTobefed(), animal.getNumAnimal() - 1);
    }
}
