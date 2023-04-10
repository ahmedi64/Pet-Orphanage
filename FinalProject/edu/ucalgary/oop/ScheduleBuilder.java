// package FinalProject.edu.ucalgary.oop;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ScheduleBuilder {

    private Connection myConnect;
    private String[][] tasks;
    private String[][] animals;
    private String[][] treatments;


    //The constructor will create a connection to the database adnd retrieve the 3 tables
    public ScheduleBuilder(){
        createConnection();
        retrieveAnimals();
        retrieveTasks();
        retrieveTreatment();
    }

    //Create the connection to database
    public void createConnection(){
                
        try{
            myConnect = DriverManager.getConnection("jdbc:mysql://localhost/ewr", "root", "Jawad195");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Using the string input, look at the table that matches the string and count the rows from it
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

    //retrieve the animals table and put it into a double array 
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

    //retrieve the tasks table and put it into a double array 
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

    //retrieve the treatment table and put it into a double array 
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

    //return the tasks table
    public String[][] getTasks() {
        return tasks;
    }

    //return the animals table
    public String[][] getAnimals() {
        return animals;
    }

    //return the treatment table
    public String[][] getTreatments() {
        return treatments;
    }


    //Look at the data for feeding tasks and the current schedule and figure out which hours can be filled
    //with feeding tasks
    public ArrayList<String[]> addFeedingTasks(HashMap<Integer,Hour> Scheduele,Animal animal){

        //Create the return array and make a variable that will show how many animals need to be fed. The
        //return array has a list of arrays where each array is[Hour,Task descrpition,duration of task
        //animal name, do we need a volenteer]
        ArrayList<String[]> tasks = new ArrayList<>();
        int num = animal.getTobefed();

        //Each animal is fed at certain time intervals depending on what time of the day they are most active
        //This for loop goes thorugh every hour that the input animal can be fed at
        for(int i = 0;i < 3;i++){

            //if the number of remaining animals that need to be fed is 0 or less then just countinue
            if(num<=0){
                continue;
            }

            //Get the hours that the animal can be fed at and get the exact hour depending on the iteration of
            //the for loop. Next get the hour object currently assosiated with that hour so we have the ability to 
            //add tasks to it. From the hour get the time reamaining which is the time which is not being used 
            //in the hour.Next use the animalCBF function to get a int[]. The 0 index contains the number of animals
            //that can be fed duing the remaining time, and the 1 index is the remaining time if we couldnt fix everything
            //perfect into that one hour
            int hourToFeed = animal.getFeedTimeHour()[i];
            Hour hour = Scheduele.get(hourToFeed);
            int timeRemaining = hour.getTimeRemaining();
            int animalCanBeFed[] = animal.animalCBF(timeRemaining);

            //If the animasl that can be fed in the hour is greater than the number of animals that need to be fed
            //then just change the number of animals that can be fed to the number of animals that need to be fed.
            if(num-animalCanBeFed[0]<0){
                animalCanBeFed[0] = num;
            }

            //Check to see if any animals can be fed during this hour.
            //If they can then add values into the return arrayList and minus values for tracking resons;
            if( animalCanBeFed[0] >=1){

                String str = "Feed "+animalCanBeFed[0]+" "+animal.getName();

                String[] tmp = {Integer.toString(hourToFeed) ,str, Integer.toString(timeRemaining-animalCanBeFed[1]),animal.getName(),"false"};
                tasks.add(tmp);
                hour.setTimeRemaining(animalCanBeFed[0]);

                num = num - animalCanBeFed[0];
                animal.decTobefed(animalCanBeFed[0]);
            }


        }


        //It does this if we are unable to fit all the animals and we need backup volenteers
        if(animal.getTobefed()>=1){

            //Each animal is fed at certain time intervals depending on what time of the day they are most active
            //This for loop goes thorugh every hour that the input animal can be fed at
            for(int i = 0;i < 3;i++){

                //if the number of remaining animals that need to be fed is 0 or less then just countinue
                if(num<=0){
                    continue;
                }

                
                //Get the hours that the animal can be fed at and get the exact hour depending on the iteration of
                //the for loop. Next get the hour object currently assosiated with that hour so we have the ability to 
                //add tasks to it. From the hour get the time reamaining and add 60 which will simulate if we add a volenteer
                //to that hour. Next use the animalCBF function to get a int[]. The 0 index contains the number of animals
                //that can be fed duing the remaining time, and the 1 index is the remaining time if we couldnt fix everything
                //perfect into that one hour
                int hourToFeed = animal.getFeedTimeHour()[i];
                Hour hour = Scheduele.get(hourToFeed);
                int timeRemaining = hour.getTimeRemaining();
                int animalCanBeFed[] = animal.animalCBF(timeRemaining+60);
    
                if(num-animalCanBeFed[0]<0){
                    animalCanBeFed[0] = num;
                }
                //Check to see if any animals can be fed during this hour.
                //If they can then add values into the return arrayList and minus values for tracking resons;
                if( animalCanBeFed[0] >=1){

                    String str = "Feed "+animalCanBeFed[0]+" "+animal.getName();

                    String[] tmp = {Integer.toString(hourToFeed) ,str, Integer.toString(timeRemaining-animalCanBeFed[1]),animal.getName(),"true"};
                    tasks.add(tmp);
                    hour.setTimeRemaining(animalCanBeFed[1]);
                    num = num -animalCanBeFed[0];
                }
            }
        }

        //If even adding backup volenteers we still cant feed all teh animals then throw a new message.
        if(animal.getTobefed()>=1){throw new Error("You cant do feeding tasks for " + animal.getName());}
        return tasks;
    }


    //Look at the data for cleaning tasks and the current schedule and figure out which hours can be filled
    //with cleaning tasks
    public ArrayList<String[]> addCleaningTasks(HashMap<Integer,Hour> Schedule,CleaningTask cleaning,Animal animal){
        
        //Create the return array and make a variable that will show how many cages must be cleaned. The
        //return array has a list of arrays where each array is[Hour,Task descrpition,duration of task
        //animal name, do we need a volenteer]
        ArrayList<String[]> strReturn = new ArrayList<>();
        int cagesToClean = animal.getNumAnimal();

        //Loop through all 24 hours 
        for(int i = 0;i<23;i++){

            //If the remaining cages that must be cleaned is 0 or less then continue
            if(cagesToClean<=0){
                continue;
            }
            

            //Get the hour object currently assosiated with that hour so we have the ability to 
            //add tasks to it. From the hour get the time reamaining which is the time which is not being used 
            //in the hour.
            Hour hour = Schedule.get(i);
            int timeRemaining =  hour.getTimeRemaining();

            //Next use the cleanCBF function to get a int[]. The 0 index contains the number of cages
            //that can be cleaned duing the remaining time, and the 1 index is the remaining time if we couldnt clean everything
            //perfect into that one hour. 

            //Check to see if any cages can be cleaned during the time
            if(cleaning.cleanCBF(timeRemaining)[0]>0){


                //store the numbe rof cages that can be cleaned in a variable
                int animalsCleaning = cleaning.cleanCBF(timeRemaining)[0];

                //If the cages that can be cleaned in the hour is greater than the number of cages that need to be cleaned
                //then just change the number of cages that can be cleaned to the number of cages that need to be cleaned.
                if(cagesToClean-animalsCleaning<0){
                   animalsCleaning = cagesToClean;
                }

                //Add all the values to the return arraylist
                String str = "Clean "+animalsCleaning+" "+animal.getName()+" cage/cages";
                cagesToClean = cagesToClean-animalsCleaning;
                String[] s = {Integer.toString(i),str,Integer.toString(timeRemaining-cleaning.cleanCBF(timeRemaining)[1]),animal.getName(),"false"};
                strReturn.add(s);
            }
        }



        //Check to see if there were any cages that normally coudnt be added to each hour and at the minimum need
        //a backup volenteer
        if(cagesToClean>=1){
            //iterate thorugh every hour
            for(int i = 0;i<23;i++){

                //if all teh cages have been cleaned then continue 
                if(cagesToClean<=0){
                    continue;
                }
                
                //get the hour object based off the hour and then get the time remaining and add it
                //with 60 to simulate a backup volenteer 
                Hour hour = Schedule.get(i);
                int timeRemaining =  hour.getTimeRemaining()+60;
    
                //Check to see if any cages can be cleaned in the hour
                if(cleaning.cleanCBF(timeRemaining)[0]>0){

                    int animalsCleaning = cleaning.cleanCBF(timeRemaining)[0];
    
                    //IF the number of cages that can be cleaned is bigger than the number of cages that 
                    //need to be cleaned then make them equal
                    if(cagesToClean-animalsCleaning<0){
                       animalsCleaning = cagesToClean;
    
                    }
    
                    //add values to arraylist
                    String str = "Clean "+animalsCleaning+" "+animal.getName()+" cage/cages";
                    cagesToClean = cagesToClean-animalsCleaning;
                    String[] s = {Integer.toString(i),str,Integer.toString(timeRemaining-cleaning.cleanCBF(timeRemaining)[1]),animal.getName(),"true"};
                    strReturn.add(s);
                }
            }

        }
        if(cagesToClean>=1){throw new Error("You cant do feeding tasks for "+ animal.getName());}

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


    //get the medical tasks from Medical Task Class
    MedicalTask medicalTask = new MedicalTask(schedule.getAnimals(),schedule.getTasks(),schedule.getTreatments(),rowsTreatment);
    ArrayList<String[]> medicalTasks = medicalTask.getInfo();


    //Add all the Medical Tasks to the Hashmap Hour
    for(int i=0;i<medicalTasks.size();i++){
        String hour= medicalTasks.get(i)[0];
        String taskName= medicalTasks.get(i)[1];
        String duration= medicalTasks.get(i)[2];
        String animalNickname= medicalTasks.get(i)[3];        
        
        Schedule.get(Integer.parseInt(hour)).addTasks(taskName, Integer.parseInt(duration) , animalNickname);
    }
    

    //Create instances of the Animal Species and then add them into an array
    Animal coyotes = new Animal("coyote"); 
    Animal beavers = new Animal("beaver"); 
    Animal racoons = new Animal("racoon"); 
    Animal foxes = new Animal("fox"); 
    Animal porcupines = new Animal("porcupine"); 
    Animal[] animalSpecies = {coyotes,beavers,racoons,foxes,porcupines};


    //Check to see if there are any animals that were already fed in Medical tasks, if
    //there was then minus them from the total amounts of animals that need to be fed.
    for(int i =0;i<animalSpecies.length;i++){
        try {
            Integer orphans = medicalTask.getAnimalsFed().get(animalSpecies[i].getName());
            if(orphans != null){
                animalSpecies[i].decTobefed(1);
            }
        } catch (SQLException e) {e.printStackTrace();}

    }
    
 
    //Add feeding tasks
    for(int i = 0;i<animalSpecies.length;i++){

        
        ArrayList<String[]> feedForAnimal = new ArrayList<>();
        try{
            feedForAnimal =  schedule.addFeedingTasks(Schedule, animalSpecies[i]);
        }
        catch(Error e){
            e.printStackTrace();
        }


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
        ArrayList<String[]> taskForSpecies = new ArrayList<>();

        try{
            taskForSpecies = schedule.addCleaningTasks(Schedule,cleaning, animalSpecies[i]);
        }
        catch(Error e){
            e.printStackTrace();
        }


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



    //Display the Schedule in terminal
    for(int i = 0;i<24;i++){
        Hour hour = Schedule.get(i);
        List<String[]> tasksforHour =  hour.getTasks();

        for(int j=0;j<tasksforHour.size();j++){
            String[] tasks = tasksforHour.get(j);

            if(hour.getVolenteer()){
                System.out.println("Hour: "+i+" Task:"+tasks[0]+" Animal:"+tasks[1]+ "VOLENTEER NEEDED");
  
            }
            else{
                System.out.println("Hour: "+i+" Task:"+tasks[0]+" Animal:"+tasks[1]);


            }


        }


    }


}
}
   
            






