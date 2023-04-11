package edu.ucalgary.oop;

//Members: Ahmed Iqbal, Musa Jawad, Abrar Rehan, Rishik Roy
//Code version: 11.0.17
public class CleaningTask {
    private int cleanTime;

    public CleaningTask(String name) throws IllegalArgumentException{
        //Hardcodes the time required to clean one animal's cage.
        if (name.equalsIgnoreCase("coyote") || name.equalsIgnoreCase("fox") || name.equalsIgnoreCase("beaver")|| name.equalsIgnoreCase("racoon")) {
            cleanTime = 5;
        } else if (name.equalsIgnoreCase("porcupine")) {
            cleanTime = 10;
        } else {
            throw new IllegalArgumentException("Invalid animal name for cleaning task: " + name);
        }
    }

    public int getCleanTime() {
        return cleanTime;
    }

    public int[] cleanCBF(int min){
        //Provides the number of cages that can be cleaned in a particular time and also time remaining after cleaning those cages.
        int numanimalCBF=min/cleanTime;
        return new int[]{numanimalCBF, min % cleanTime};
    }

}
