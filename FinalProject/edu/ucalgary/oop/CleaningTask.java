
public class CleaningTask {
    private int cleanTime;

    public CleaningTask(String name) {
        if (name.equalsIgnoreCase("c") || name.equalsIgnoreCase("f") || name.equalsIgnoreCase("r")) {
            cleanTime = 5;
        } else if (name.equalsIgnoreCase("p")) {
            cleanTime = 10;
        } else {
            System.out.println("Invalid animal name for cleaning task.");
        }
    }

    public int getCleanTime() {
        return cleanTime;
    }
}