package FinalProject.edu.ucalgary.oop;

public class MedicalTask {
    private boolean animalFed = false;
    private int taskId;
    private int animalId;
    private int startHour;
    private int taskDuration;
    private int maxWindow;


    public MedicalTask(int animalId, int taskId, int startHour, int taskDuration, int maxWindow, boolean animalFed) {
        this.animalId = animalId;
        this.taskId = taskId;
        this.startHour = startHour;
        this.taskDuration = taskDuration;
        this.maxWindow = maxWindow;
        if (taskId == 1) {
            animalFed = true;
        }
    }

    public boolean getAnimalFed(){  
        return animalFed;
    }
    public void setAnimalFed(boolean animalFed){
        this.animalFed = animalFed;
    }
    public int getTaskId(){
        return taskId;
    }
    public void setTaskId(int taskId){
        this.taskId = taskId;
    }
    public int getAnimalId(){
        return animalId;
    }
    public void setAnimalId(int animalId){
        this.animalId = animalId;
    }
    public int getStartHour(){
        return startHour;
    }
    public void setStartHour(int startHour){
        this.startHour = startHour;
    }
    public int getTaskDuration(){
        return taskDuration;
    }
    public void setTaskDuration(int taskDuration){
        this.taskDuration = taskDuration;
    }
    public int getMaxWindow(){
        return maxWindow;
    }
    public void setMaxWindow(int maxWindow){
        this.maxWindow = maxWindow;

    }

}
