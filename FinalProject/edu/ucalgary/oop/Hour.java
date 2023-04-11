package edu.ucalgary.oop;
//Members: Ahmed Iqbal, Musa Jawad, Abrar Rehan, Rishik Roy
//Code version: 11.0.17

import java.util.ArrayList;
import java.util.List;

public class Hour {
    private ArrayList<String[]> tasks = new ArrayList<>();
    private int timeRemaining;
    private boolean volenteer;



    public Hour(){
        this.timeRemaining = 60;
        volenteer = false;
    }

   

    public void addTasks(String task,int duration,String animal){

        

        String[] newArray = new String[2];
        newArray[0] = task;
        newArray[1] = animal;

        tasks.add(newArray);
        this.timeRemaining = timeRemaining- duration;
        
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
