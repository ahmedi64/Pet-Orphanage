//package FinalProject.edu.ucalgary.oop;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MedicalTask {
    private final String[][] ANIMALS;
    private final String[][] TASKS;
    private final String[][] TREATMENTS;
    private final int TREATMENTROWS;


    public MedicalTask(String[][] animals, String[][] tasks, String[][] treatments, int treatmentRows) {
        //get 3 nested arrays and variable from ScheduleBuilder
        this.ANIMALS = animals;
        this.TASKS = tasks;
        this.TREATMENTS = treatments;
        this.TREATMENTROWS = treatmentRows;
    }

    public HashMap<String, Integer> getAnimalsFed() throws SQLException {
        //Create a set so there will be no duplicates of fed animals
        Set<Integer> animalsFed = new HashSet<>();
        // access the animal IDs from the SQL table
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/EWR", "root", "");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT AnimalID FROM TREATMENTS WHERE TaskID = 1")) {
        //get the animal IDs and add them to the set
            while (resultSet.next()) {
                int animalID = resultSet.getInt("AnimalID");
                animalsFed.add(animalID);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        //Match the species to the number of Orphans(have an taskID of 1)
        HashMap<String, Integer> speciesToOrphans = new HashMap<String, Integer>();
        //For each loop to populate the hashmap
        for (int id: animalsFed){
            String speciesName = ANIMALS[id - 1][2];
            speciesToOrphans.put(speciesName, id);
        }
        return speciesToOrphans;
    }

    public ArrayList<String[]> getInfo() {
        ArrayList<String[]> infoList = new ArrayList<String[]>();
        //create a map to store start hours and total duration for treatments with same start hour
        Map<Integer, Integer> startHourToDuration = new HashMap<Integer, Integer>();
    
        for (int i = 0; i < TREATMENTROWS; i++) {
            //get values from nested arrays
            int startHour = Integer.parseInt(TREATMENTS[i][2]);
            int duration = Integer.parseInt(TASKS[Integer.parseInt(TREATMENTS[i][1]) - 1][2]);
            String taskName = TASKS[(Integer.parseInt(TREATMENTS[i][1]) - 1)][1];
            String animalID = ANIMALS[(Integer.parseInt(TREATMENTS[i][0]) - 1)][1];
    
            //check if there is already a treatment with this start hour
            if (startHourToDuration.containsKey(startHour)) {
                int totalDuration = startHourToDuration.get(startHour) + duration;
                //check if the total duration is above 60 minutes
                if (totalDuration > 60) {
                    //subtract 60 from total duration and start a new count for the remaining time
                    int remainingDuration = totalDuration - 60;
                    totalDuration = 60;
    
                    //find the treatment with the highest maxWindow using a for loop
                    int maxWindow = -1;
                    int windowIndex = -1;
                    for (int j = 0; j < TREATMENTROWS; j++) {
                        if (Integer.parseInt(TREATMENTS[j][2]) == startHour && Integer.parseInt(TASKS[Integer.parseInt(TREATMENTS[j][1]) - 1][3]) > maxWindow) {
                            maxWindow = Integer.parseInt(TASKS[Integer.parseInt(TREATMENTS[j][1]) - 1][3]);
                            windowIndex = j;
                        }
                    }
                    //set the start hour of the treatment with highest maxWindow to one hour later
                    TREATMENTS[windowIndex][2] = Integer.toString(startHour + 1);
                    startHour = startHour + 1;
    
                    //add the remaining duration to the start hour in the Hashmap
                    startHourToDuration.put(startHour, remainingDuration);
                } else {
                    //update the total duration for this start hour
                    startHourToDuration.put(startHour, totalDuration);
                }
            } else {
                //add the start hour and duration to the startHourtoDuration
                startHourToDuration.put(startHour, duration);
            }
    
            //add the values to infoArray
            String[] infoArray = { Integer.toString(startHour), taskName, Integer.toString(duration), animalID };
            infoList.add(infoArray);
        }
    
        return infoList;
    }
    
}