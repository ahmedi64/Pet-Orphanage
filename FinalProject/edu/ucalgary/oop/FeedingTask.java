package FinalProject.edu.ucalgary.oop;

import java.sql.*;

public class FeedingTask {
    private int numberAnimal;
    private int[] feedTime= new int[2];

    public FeedingTask(String name) {
        String species = name.toLowerCase();
        try {
            // Connect to the database
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/ewr", "root", "Jawad195");

            // Query the database for the number of animals of the specified species
            PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM ANIMALS WHERE AnimalSpecies=?");
            stmt.setString(1, species);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                numberAnimal = rs.getInt(1);
            } else {
                System.out.println("No animals of species " + species + " found.");
            }

            // Set the feed time based on the species
            switch(species) {
                case "coyote":
                    feedTime[0] = 10;
                    feedTime[1] = 5;
                    break;
                case "fox":
                    feedTime[0] = 5;
                    feedTime[1] = 5;
                    break;
                case "porcupine":
                    feedTime[0] = 0;
                    feedTime[1] = 5;
                    break;
                case "racoon":
                    feedTime[0] = 0;
                    feedTime[1] = 5;
                    break;
                case "beaver":
                    feedTime[0] = 0;
                    feedTime[1] = 5;
                    break;
                default:
                    System.out.println("Invalid animal name.");
            }

            // Close the database connection
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int[] getFeedTime() {
        return feedTime;
    }

    public int getNumberAnimal() {
        return numberAnimal;
    }
}
