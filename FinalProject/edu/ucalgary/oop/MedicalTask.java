package FinalProject.edu.ucalgary.oop;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class MedicalTask {
    private boolean animalFed = false;
    private int taskId;
    private int animalId;
    private int startHour;
    private int taskDuration;
    private int maxWindow;


    public MedicalTask(int animalId, int taskId, int startHour, int taskDuration, int maxWindow, boolean animalFed) {
        this.animalId = animalId;
        this.taskId = taskId;
        this.startHour = startHour;
        this.taskDuration = taskDuration;
        this.maxWindow = maxWindow;
        if (taskId == 1) {
            animalFed = true;
        }
    }

    public Set<Integer> getAnimalsFed() throws SQLException {
        Set<Integer> animalsFed = new HashSet<>();
    
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/EWR", "root", "password for root");
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
}
