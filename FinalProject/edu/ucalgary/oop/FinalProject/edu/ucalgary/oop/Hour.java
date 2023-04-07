package FinalProject.edu.ucalgary.oop;

public class Hour {
    private String taskname;
    private int quantity=0;
    private int duration;

    public void Hour(String taskname, int quantity, int duration){
        this.taskname=taskname;
        this.quantity=quantity;
        this.duration=duration;
    }

    public int getDuration() {
        return duration;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getTaskname() {
        return taskname;
    }
}
