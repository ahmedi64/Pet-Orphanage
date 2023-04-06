
import java.sql.*;

public class FeedingTask {
    private int numberAnimal;
    private int feedTime;

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
                    feedTime = 10 + 5 * numberAnimal;
                    break;
                case "fox":
                    feedTime = 5 + 5*numberAnimal;
                    break;
                case "porcupine":
                    feedTime = 5 * numberAnimal;
                    break;
                case "raccoon":
                    feedTime = 5;
                    break;
                case "beaver":
                    feedTime = 5 * numberAnimal;
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

    public int getFeedTime() {
        return feedTime;
    }

    public int getNumberAnimal() {
        return numberAnimal;
    }
}
