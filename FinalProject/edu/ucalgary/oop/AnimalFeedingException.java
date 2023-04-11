// package FinalProject.edu.ucalgary.oop;
//Members: Ahmed Iqbal, Musa Jawad, Abrar Rehan, Rishik Roy
//Code version: 11.0.17

public class AnimalFeedingException extends Exception {

    private int oldStartHour;
    private int taskID;
    private int animalID;

    public AnimalFeedingException(String message,int oldStartHour,int taskID,int animalID) {
        super(message);

        this.oldStartHour = oldStartHour;
        this.taskID = taskID;
        this.animalID = animalID;
    }

    public int getAnimalID() {
        return animalID;
    }

    public int getTaskID() {
        return taskID;
    }

    public int getOldStartHour() {
        return oldStartHour;
    }

}