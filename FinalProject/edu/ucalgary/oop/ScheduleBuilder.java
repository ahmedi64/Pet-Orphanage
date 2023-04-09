// package FinalProject.edu.ucalgary.oop;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ScheduleBuilder {

    public Connection myConnect;
    public String[][] tasks;
    public String[][] animals;
    public String[][] treatments;


    public ScheduleBuilder(){
        createConnection();
        retrieveAnimals();
        retrieveTasks();
        retrieveTreatment();
    }

    public void createConnection(){
                
        try{
            myConnect = DriverManager.getConnection("jdbc:mysql://localhost/ewr", "root", "Jawad195");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int countRows(String tablename){

        try {
            Statement stmt = myConnect.createStatement();

            String query = "SELECT COUNT(*) FROM " + tablename;

            ResultSet result = stmt.executeQuery(query);

            result.next();
            int rowCount = result.getInt(1);
            return rowCount;
        } 
        catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        
        
    }


    public void retrieveAnimals(){
        try {
            Statement myStmt = myConnect.createStatement();
            ResultSet results = myStmt.executeQuery("SELECT * FROM animals");

            String[][] table = new String[countRows("animals")][3];
            int i = 0;
            while (results.next()){
                table[i][0] = results.getString("animalID");
                table[i][1] = results.getString("animalnickname");
                table[i][2] = results.getString("animalspecies");

                i = i +1;
              
            }
            myStmt.close();
            results.close();
            this.animals = table;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
    } 

    public void retrieveTasks(){
        try {
            Statement myStmt = myConnect.createStatement();
            ResultSet results = myStmt.executeQuery("SELECT * FROM tasks");

            String[][] table = new String[countRows("tasks")][4];
            int i = 0;
            while (results.next()){
                table[i][0] = results.getString("taskID");
                table[i][1] = results.getString("description");
                table[i][2] = results.getString("Duration");
                table[i][3] = results.getString("maxwindow");
                i = i +1;
              
            }
            myStmt.close();
            results.close();
            this.tasks = table;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
    } 

    public void retrieveTreatment(){
        try {
            Statement myStmt = myConnect.createStatement();
            ResultSet results = myStmt.executeQuery("SELECT * FROM treatments");

            String[][] table = new String[countRows("treatments")][3];
            int i = 0;
            while (results.next()){
                table[i][0] = results.getString("animalID");
                table[i][1] = results.getString("taskid");
                table[i][2] = results.getString("starthour");

                i = i +1;
              
            }
            myStmt.close();
            results.close();
            this.treatments = table;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
    } 


    public String[][] getTasks() {
        return tasks;
    }

    public String[][] getAnimals() {
        return animals;
    }

    public String[][] getTreatments() {
        return treatments;
    }


    public ArrayList<String[]> addFeedingTasks(HashMap<Integer,Hour> Scheduele,Animal animal){

        ArrayList<String[]> tasks = new ArrayList<>();
        int num = animal.getTobefed();

        for(int i = 0;i < 3;i++){

            if(num<=0){
                continue;
            }

            int hourToFeed = animal.getFeedTimeHour()[i];

            Hour hour = Scheduele.get(hourToFeed);

            int timeRemaining = hour.getTimeRemaining();

            int animalCanBeFed[] = animal.animalCBF(timeRemaining);


            if(num-animalCanBeFed[0]<0){
                animalCanBeFed[0] = num;
            }


            if( animalCanBeFed[0] >=1){

                String str = "Feed "+animalCanBeFed[0]+" "+animal.getName();

                String[] tmp = {Integer.toString(hourToFeed) ,str, Integer.toString(timeRemaining-animalCanBeFed[1]),animal.getName(),"false"};
                tasks.add(tmp);
                hour.setTimeRemaining(animalCanBeFed[0]);

                num = num - animalCanBeFed[0];
                animal.decTobefed(animalCanBeFed[0]);
            }


        }


        //We have to use backup volenteers
        if(animal.getTobefed()>=1){

            for(int i = 0;i < 3;i++){

                if(num<=0){
                    continue;
                }

                int hourToFeed = animal.getFeedTimeHour()[i];
    
                Hour hour = Scheduele.get(hourToFeed);
    
                int timeRemaining = hour.getTimeRemaining();
    
                int animalCanBeFed[] = animal.animalCBF(timeRemaining+60);
    
                if( animalCanBeFed[0] >=1){

                    String str = "Feed "+animalCanBeFed[0]+" "+animal.getName();

                    String[] tmp = {Integer.toString(hourToFeed) ,str, Integer.toString(timeRemaining-animalCanBeFed[1]),animal.getName(),"true"};
                    tasks.add(tmp);
                    hour.setTimeRemaining(animalCanBeFed[1]);

                    num = num -animalCanBeFed[0];

                }
            }
        }

        if(animal.getTobefed()>=1){throw new Error("You cant do feeding tasks");}
        return tasks;
    }


    public ArrayList<String[]> addCleaningTasks(HashMap<Integer,Hour> Schedule,CleaningTask cleaning,Animal animal){
        
        ArrayList<String[]> strReturn = new ArrayList<>();

        int cagesToClean = animal.getNumAnimal();

        for(int i = 0;i<23;i++){

            if(cagesToClean<=0){
                continue;
            }
            

            Hour hour = Schedule.get(i);
            int timeRemaining =  hour.getTimeRemaining();

            if(cleaning.cleanCBF(timeRemaining)[0]>0){

                int animalsCleaning = cleaning.cleanCBF(timeRemaining)[0];


                if(cagesToClean-animalsCleaning<0){
                   animalsCleaning = cagesToClean;

                }


                String str = "Clean "+animalsCleaning+" "+animal.getName()+" cage/cages";
                cagesToClean = cagesToClean-animalsCleaning;

                String[] s = {Integer.toString(i),str,Integer.toString(timeRemaining-cleaning.cleanCBF(timeRemaining)[1]),animal.getName(),"false"};
                strReturn.add(s);
            }
        }

        if(cagesToClean>=1){
            for(int i = 0;i<23;i++){

                if(cagesToClean<=0){
                    continue;
                }
                
    
                Hour hour = Schedule.get(i);
                int timeRemaining =  hour.getTimeRemaining()+60;
    
                if(cleaning.cleanCBF(timeRemaining)[0]>0){

                    int animalsCleaning = cleaning.cleanCBF(timeRemaining)[0];
    
    
                    if(cagesToClean-animalsCleaning<0){
                       animalsCleaning = cagesToClean;
    
                    }
    
    
                    String str = "Clean "+animalsCleaning+" "+animal.getName()+" cage/cages";
                    cagesToClean = cagesToClean-animalsCleaning;
    
                    String[] s = {Integer.toString(i),str,Integer.toString(timeRemaining-cleaning.cleanCBF(timeRemaining)[1]),animal.getName(),"true"};
                    strReturn.add(s);
                }
            }

        }
        return strReturn;

    }





public static void main(String[] args) {

    ScheduleBuilder schedule = new ScheduleBuilder();
    int rowsTreatment = schedule.countRows("treatments");
    HashMap<Integer,Hour> Schedule = new HashMap<>();

    for (int i = 0; i <= 23; i++) {
        Hour hour = new Hour(); // create an instance of the Hour object
        Schedule.put(i, hour); // add the element to the map
    }

    MedicalTask medicalTask = new MedicalTask(schedule.getAnimals(),schedule.getTasks(),schedule.getTreatments(),rowsTreatment);
    
    //get the information from medical task
    ArrayList<String[]> medicalTasks = medicalTask.getInfo();


    //find the hour that has the most tasks and save this in a variable
    //set the second half of the array list ot the most hours
    //Ahmed gives [starthour,TaskID,duration,animalID]

    //Add all the elemets to the Hashmap
    for(int i=0;i<medicalTasks.size();i++){

        String hour= medicalTasks.get(i)[0];
        String taskName= medicalTasks.get(i)[1];
        String duration= medicalTasks.get(i)[2];
        String animalNickname= medicalTasks.get(i)[3];        
        
        Schedule.get(Integer.parseInt(hour)).addTasks(taskName, Integer.parseInt(duration) , animalNickname);

    }
    

    Animal coyotes = new Animal("coyote"); 
    Animal beavers = new Animal("beaver"); 
    Animal racoons = new Animal("racoon"); 
    Animal foxes = new Animal("fox"); 
    Animal porcupines = new Animal("porcupine"); 

    Animal[] animalSpecies = {coyotes,beavers,racoons,foxes,porcupines};

    for(int i =0;i<animalSpecies.length;i++){
        try {
            Integer orphans = medicalTask.getAnimalsFed().get(animalSpecies[i].getName());
            if(orphans != null){
                animalSpecies[i].decTobefed(1);
            }
        } catch (SQLException e) {e.printStackTrace();}

    }
    
 
    //Add feedig tasks
    for(int i = 0;i<animalSpecies.length;i++){
        ArrayList<String[]> feedForAnimal =  schedule.addFeedingTasks(Schedule, animalSpecies[i]);

        for(int j = 0;j<feedForAnimal.size();j++){

            int hour= Integer.parseInt(feedForAnimal.get(j)[0]);
            String taskName= feedForAnimal.get(j)[1];
            int duration= Integer.parseInt(feedForAnimal.get(j)[2]); 
            String animalNickname= feedForAnimal.get(j)[3]; 
            boolean volenteer = Boolean.valueOf(feedForAnimal.get(j)[4]);
            Hour mapHour =  Schedule.get(hour);
                
            if(volenteer){mapHour.addVolenteer();}


            mapHour.addTasks(taskName, duration , animalNickname);
           


        }
    }


    String[] animalSpecienames = {"coyote","beaver","racoon","fox","porcupine"};

    //add cleaning tasks
    for(int i =0;i<animalSpecies.length;i++){

        CleaningTask cleaning = new CleaningTask(animalSpecienames[i]);

        ArrayList<String[]> taskForSpecies = schedule.addCleaningTasks(Schedule,cleaning, animalSpecies[i]);


        for(int j = 0;j<taskForSpecies.size();j++){
            int hour= Integer.parseInt(taskForSpecies.get(j)[0]);

            String taskName= taskForSpecies.get(j)[1];
            int duration= Integer.parseInt(taskForSpecies.get(j)[2]); 
            String animalNickname= taskForSpecies.get(j)[3]; 
            boolean volenteer = Boolean.valueOf(taskForSpecies.get(j)[4]);
            Hour mapHour =  Schedule.get(hour);
                

            if(volenteer){mapHour.addVolenteer();}



            mapHour.addTasks(taskName, duration , animalNickname);
           

        }




    }





        for(int i = 0;i<23;i++){
            Hour hour = Schedule.get(i);
            List<String[]> tasksforHour =  hour.getTasks();

            for(int j=0;j<tasksforHour.size();j++){
                String[] tasks = tasksforHour.get(j);

                System.out.println("Hour: "+i+" Task:"+tasks[0]+" Animal:"+tasks[1]);

            }


        }



    }


    

    //Now we have to do the same for the feeding and cleqning tasks



    //Feeding tasks can recieve the remaining time on the hours of their respective animals and then 
    //feed the animals respectively. If it cant be done then we can calculate the total time it will take 
    //to feed all the animls and then check the remaining time of all the hours, if the feeding can be done in 
    //one hour with a backup volenteer then great. If not go through all 3 of the hours. If it cant even do that then
    //We tell the user that it is impossible to create a schedule.
    //



    //After this we can check for any reamining time left and add the cleaning tasks in those time slots, if
    //unable add volenteers for the hours, if even then it dosent work then tell the user it is impossible

    // print the Hashmap with formatSchedule as a guide and we are done, make sure to check the volenteer boolean
    //to let them know if a backup is needed


    }
   
            






