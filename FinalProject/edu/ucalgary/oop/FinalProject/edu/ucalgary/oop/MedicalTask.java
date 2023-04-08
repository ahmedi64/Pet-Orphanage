package FinalProject.edu.ucalgary.oop;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
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

    public Set<Integer> getAnimalsFed() throws SQLException {
        Set<Integer> animalsFed = new HashSet<>();
    
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/EWR", "root", "password");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT AnimalID FROM TREATMENTS WHERE TaskID = 1")) {
    
            while (resultSet.next()) {
                int animalID = resultSet.getInt("AnimalID");
                animalsFed.add(animalID);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return animalsFed;
    }

    public ArrayList<String[]> getInfo() {
        //Create an ArrayList containing String Arrays of all medical task startHours, durations, taskNames, and animalIDs.
        ArrayList<String[]> infoList = new ArrayList<String[]>();
        for (int i = 0; i < treatmentRows; i++) {
            //get values from nested arrays
            int startHour = Integer.parseInt(treatments[i][2]);
            int duration = Integer.parseInt(tasks[Integer.parseInt(treatments[i][1]) - 1][2]);
            String taskID = tasks[Integer.parseInt(treatments[i][1]) - 1][0];
            String animalID = treatments[i][1];
    
            boolean added = false;
            for (int j = 0; j < infoList.size(); j++) {
                //checks if there is an entry with the same start hour as the current one
                String[] currentInfo = infoList.get(j);
                int currentStartHour = Integer.parseInt(currentInfo[0]);
                int currentDuration = Integer.parseInt(currentInfo[2]);
                int currentMaxWindow = Integer.parseInt(tasks[Integer.parseInt(currentInfo[1]) - 1][3]);
                int maxWindow = Integer.parseInt(tasks[Integer.parseInt(treatments[i][1]) - 1][3]);
        
                //if the start hours are different, there is no conflict.
                if (startHour != currentStartHour) {
                    continue;
                }
                //if the combined durations can happen within an hour, they will be written
                if (currentDuration + duration <= 60) {
                    String[] newEntry = new String[4];
                    newEntry[0] = String.valueOf(currentStartHour);
                    newEntry[1] = currentInfo[1];
                    newEntry[2] = String.valueOf(currentDuration);
                    newEntry[3] = currentInfo[3];
                    infoList.add(newEntry);
                    added = true;
                    break;
                } else {
                    // check max window and add accordingly
                    if (maxWindow <= currentMaxWindow) {
                        String[] newEntry = new String[4];
                        newEntry[0] = String.valueOf(startHour);
                        newEntry[1] = taskID;
                        newEntry[2] = String.valueOf(duration);
                        newEntry[3] = animalID;
                        infoList.add(newEntry);
                        added = true;
                        break;
                    } else {
                        startHour += 1;
                        maxWindow -= 1;
                        infoList.remove(j); // remove the entry with larger max window
                        j--; // decrease index because we removed an element from the list
                    }
                }
            }
            //if there was no previous entry with the same start hour, add a new entry
            if (!added) {
                String[] newEntry = new String[4];
                newEntry[0] = String.valueOf(startHour);
                newEntry[1] = taskID;
                newEntry[2] = String.valueOf(duration);
                newEntry[3] = animalID;
                infoList.add(newEntry);
            }
        }
        return infoList;
    }
                
}
