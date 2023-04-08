package FinalProject.edu.ucalgary.oop;

import java.util.ArrayList;
import java.util.List;

public class Hour {
    List<String[]> tasks = new ArrayList<>();
    private int timeRemaining;



    public Hour(){
        this.timeRemaining = 60;
    }


    public void addTasks(String task,int duration,String animal){

        if(timeRemaining-duration < 0){
            return;
        }

        String[] newArray = new String[2];
        newArray[0] = Integer.toString(duration);
        newArray[1] = animal;

        tasks.add(newArray);
        
        
    }

    public void addVolenteer(){
        timeRemaining = timeRemaining + 60;
    }


    public int getTimeRemaining() {
        return timeRemaining;
    }

    

    public List<String[]> getTasks() {
        return tasks;
    }
}
