package FinalProject.edu.ucalgary.oop;

import java.util.ArrayList;
import java.util.List;

public class Hour {
    private List<String[]> tasks = new ArrayList<>();
    private int timeRemaining;
    private boolean volenteer;



    public Hour(){
        this.timeRemaining = 60;
        volenteer = false;
    }

   

    public void addTasks(String task,int duration,String animal){

        if(timeRemaining-duration <= 0){
            
            return;
        }

        String[] newArray = new String[2];
        newArray[0] = Integer.toString(duration);
        newArray[1] = animal;

        tasks.add(newArray);
        
    }



    public void addVolenteer(){
        timeRemaining = timeRemaining + 60;
        volenteer = true;
    }


    public int getTimeRemaining() {
        return timeRemaining;
    }

    public boolean getVolenteer(){
        return volenteer;
    }
    

    public List<String[]> getTasks() {
        return tasks;
    }

    public void setTimeRemaining(int timeRemaining) {
        this.timeRemaining = timeRemaining;
    }
}
