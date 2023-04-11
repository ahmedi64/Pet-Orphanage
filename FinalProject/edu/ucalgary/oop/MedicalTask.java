//package FinalProject.edu.ucalgary.oop;
//Members: Ahmed Iqbal, Musa Jawad, Abrar Rehan, Rishik Roy
//Code version: 11.0.17

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MedicalTask {
    // this class is to do with the medical treatments
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
        //this method determines which animals are orphans, depending on whether the taskID for the treatment is 1 or not, and returns a hashmap with the information per animal species
        //Create a set so there will be no duplicates of fed animals
        Set<Integer> animalsFed = new HashSet<>();
        // access the animal IDs from the SQL table
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/EWR", "oop", "password");
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

    public ArrayList<String[]> getInfo() throws MedicalTaskException {
        //this method returns an arraylist with the number of string arrays corresponding to the number of treatment rows
        //it changes up the hours of the treatments based on their corresponding max windows, and ensures no hour has a duration above 60 mins
        //the output string arrays contain the start hour, the task name, the duration per treatment, and the animal ID that the treatment corresponds to
        ArrayList<String[]> infoList = new ArrayList<String[]>();
        //create a map to store start hours and total duration for treatments with same start hour
        Map<Integer, Integer> startHourToDuration = new HashMap<Integer, Integer>();
        //create a map to store treatment index and duration
        Map<Integer, Integer> treatmentIndexToDuration = new HashMap<Integer, Integer>();
    
        //initialize the duration for each treatment
        for (int i = 0; i < TREATMENTROWS; i++) {
            int duration = Integer.parseInt(TASKS[Integer.parseInt(TREATMENTS[i][1]) - 1][2]);
            treatmentIndexToDuration.put(i, duration);
        }
    
        //sort the treatments based on max window in ascending order
        Arrays.sort(TREATMENTS, new Comparator<String[]>() {
            @Override
            public int compare(final String[] entry1, final String[] entry2) {
                final int maxWindow1 = Integer.parseInt(TASKS[Integer.parseInt(entry1[1]) - 1][3]);
                final int maxWindow2 = Integer.parseInt(TASKS[Integer.parseInt(entry2[1]) - 1][3]);
                return Integer.compare(maxWindow1, maxWindow2);
            }
        });
    
        for (int i = 0; i < TREATMENTROWS; i++) {
            //get values from nested arrays
            int startHour = Integer.parseInt(TREATMENTS[i][2]);
            int duration = treatmentIndexToDuration.get(i);
            String taskName = TASKS[Integer.parseInt(TREATMENTS[i][1]) - 1][1];
            String animalID = ANIMALS[Integer.parseInt(TREATMENTS[i][0]) - 1][1];
    
            //check if there is already a treatment with this start hour
            if (startHourToDuration.containsKey(startHour)) {
                int totalDuration = startHourToDuration.get(startHour) + duration;
                //check if the total duration is above 60 minutes
                if (totalDuration > 60) {
                    //subtract 60 from total duration and start a new count for the remaining time
                    int remainingDuration = totalDuration - 60;
                    totalDuration = 60;
    
                    //find the treatment with the highest maxWindow that can be moved to the next hour
                    int j = i;
                    int maxWindow = Integer.parseInt(TASKS[Integer.parseInt(TREATMENTS[j][1]) - 1][3]);
                    int windowIndex = j;
                    while (j < TREATMENTROWS && Integer.parseInt(TREATMENTS[j][2]) == startHour && Integer.parseInt(TASKS[Integer.parseInt(TREATMENTS[j][1]) - 1][3]) == maxWindow) {
                        j++;
                        if (j < TREATMENTROWS && Integer.parseInt(TREATMENTS[j][2]) == startHour && Integer.parseInt(TASKS[Integer.parseInt(TREATMENTS[j][1]) - 1][3]) > maxWindow) {
                            maxWindow = Integer.parseInt(TASKS[Integer.parseInt(TREATMENTS[j][1]) - 1][3]);
                            windowIndex = j;
                        }
                    }
                    //set the start hour of the treatment with highest maxWindow to one hour later
                    TREATMENTS[windowIndex][2] = Integer.toString(startHour + 1);
                    startHour = startHour + 1;
                    // if the start hour ever increases to 24 because the tasks couldnt fit in the 23rd hour then throw an error
                    if (startHour == 24){
                        throw new MedicalTaskException("Unable to fit all medical tasks within a 24 hour window!");
                    }
                    //decrease maxWindow by 1
                    int newMaxWindow = Integer.parseInt(TASKS[Integer.parseInt(TREATMENTS[windowIndex][1]) - 1][3]) - 1;
                    // check if the newMaxWindow is < 0, which means that the medical tasks cant fit into the scheudle, and throw an error
                     if (newMaxWindow < 0){
                         throw new MedicalTaskException("The medical tasks cannot fit into the schedule due to an overload of tasks, and the max window of a task being too short");
                     }
                    TASKS[Integer.parseInt(TREATMENTS[windowIndex][1]) - 1][3] = Integer.toString(newMaxWindow);
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
        // return final arraylist
        return infoList;
    }
    
}