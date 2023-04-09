// package FinalProject.edu.ucalgary.oop;

public class CleaningTask {
    private int cleanTime;

    public CleaningTask(String name) {
        if (name.equalsIgnoreCase("coyote") || name.equalsIgnoreCase("fox") || name.equalsIgnoreCase("beaver")|| name.equalsIgnoreCase("racoon")) {
            cleanTime = 5;
        } else if (name.equalsIgnoreCase("porcupine")) {
            cleanTime = 10;
        } else {
            System.out.println("Invalid animal name for cleaning task.");
        }
    }

    public int getCleanTime() {
        return cleanTime;
    }

    public int[] cleanCBF(int min){

        int numanimalCBF=min/cleanTime;
        return new int[]{numanimalCBF, min % cleanTime};
    }
}
