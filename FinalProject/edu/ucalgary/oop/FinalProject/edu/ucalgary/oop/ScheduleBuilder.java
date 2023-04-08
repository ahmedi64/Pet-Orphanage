package FinalProject.edu.ucalgary.oop;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;


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







public static void main(String[] args) {

    //This is where we will actually make the schedule using all classes and methods
    ScheduleBuilder schedule = new ScheduleBuilder();
    int rowsTreatment = schedule.countRows("treatments");

    MedicalTask medicalTask = new MedicalTask(schedule.getAnimals(),schedule.getTasks(),schedule.getTreatments(),rowsTreatment);
    
    //get the information from medical task
    ArrayList<String[]> medicalTasks = medicalTask.getInfo();

    //find the hour that has the most tasks and save this in a variable
    //set the second half of the array list ot the most hours
    //Ahmed gives [starthour,TaskID,duration,animalID]



    HashMap<Integer,Hour> Scheduele = new HashMap<>();

    for (int i = 1; i <= 24; i++) {
        Hour hour = new Hour(); // create an instance of the Hour object
        Scheduele.put(i, hour); // add the element to the map
    }


    for (int i = 0; i < medicalTasks.size(); i++) {
        String[] tasks = medicalTasks.get(i);
        Hour hourforTask =  Scheduele.get( Integer.parseInt(tasks[0]));
        hourforTask.addTasks(tasks[1],Integer.parseInt(tasks[2]), tasks[3]);
    }
    //Above for loop will add all the medical tasks




    //Change animals to create seperate instances of each animal, and then i will use getters to get values from it
    //


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
   
            
}





