package FinalProject.edu.ucalgary.oop;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MedicalTask {
    private String[][] animals;
    private String[][] tasks;
    private String[][] treatments;
    private int treatmentRows;


    public MedicalTask(String[][] animals, String[][] tasks, String[][] treatments, int treatmentRows) {
        this.animals = animals;
        this.tasks = tasks;
        this.treatments = treatments;
        this.treatmentRows = treatmentRows;
    }

    public HashMap<String, Integer> getAnimalsFed() throws SQLException {
        Set<Integer> animalsFed = new HashSet<>();
    
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/EWR", "root", "");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT AnimalID FROM TREATMENTS WHERE TaskID = 1")) {
    
            while (resultSet.next()) {
                int animalID = resultSet.getInt("AnimalID");
                animalsFed.add(animalID);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        HashMap<String, Integer> speciesToOrphans = new HashMap<String, Integer>();
        for (int id: animalsFed){
            String speciesName = animals[id - 1][2];
            speciesToOrphans.put(speciesName, id);
        }
        return speciesToOrphans;
    }

    public ArrayList<String[]> getInfo() {
        ArrayList<String[]> infoList = new ArrayList<String[]>();
        //create a map to store start hours and total duration for treatments with same start hour
        Map<Integer, Integer> startHourToDuration = new HashMap<Integer, Integer>();
    
        for (int i = 0; i < treatmentRows; i++) {
            //get values from nested arrays
            int startHour = Integer.parseInt(treatments[i][2]);
            int duration = Integer.parseInt(tasks[Integer.parseInt(treatments[i][1]) - 1][2]);
            String taskName = tasks[(Integer.parseInt(treatments[i][1]) - 1)][1];
            String animalID = animals[(Integer.parseInt(treatments[i][0]) - 1)][1];
    
            //check if there is already a treatment with this start hour
            if (startHourToDuration.containsKey(startHour)) {
                int totalDuration = startHourToDuration.get(startHour) + duration;
                //check if the total duration is above 60 minutes
                if (totalDuration > 60) {
                    //subtract 60 from total duration and start a new count for the remaining time
                    int remainingDuration = totalDuration - 60;
                    totalDuration = 60;
    
                    //find the treatment with the highest maxWindow
                    int maxWindow = -1;
                    int windowIndex = -1;
                    for (int j = 0; j < treatmentRows; j++) {
                        if (Integer.parseInt(treatments[j][2]) == startHour && Integer.parseInt(tasks[Integer.parseInt(treatments[j][1]) - 1][3]) > maxWindow) {
                            maxWindow = Integer.parseInt(tasks[Integer.parseInt(treatments[j][1]) - 1][3]);
                            windowIndex = j;
                        }
                    }
                    //set the start hour of the treatment with highest maxWindow to one hour later
                    treatments[windowIndex][2] = Integer.toString(startHour + 1);
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