// package FinalProject.edu.ucalgary.oop;

public class Animal{
    public String name;
    public int numanimal;
    public int tobefed;
    public int[] feedtimes;
    public int feedtime;
    public int onefeed;
    public int prepfeed;

    public int[] feedtimehour;
    public int cleantime;
    private FeedingTask feed;
    private CleaningTask clean;


    public Animal(String name){
        this.name=name;
        this.feed = new FeedingTask(name);
        this.clean = new CleaningTask(name);
        this.numanimal =feed.getNumberAnimal();
        this.tobefed = feed.getNumberAnimal();
        this.feedtimes = feed.getFeedTime();
        this.prepfeed=feedtimes[0];
        this.onefeed=feedtimes[1];
        int hour =feedtimes[2];
        this.feedtimehour = new int[] {hour, hour+1, hour+2};
        this.cleantime = clean.getCleanTime();

    }
    public void decTobefed(int num) {
        this.tobefed = this.tobefed - num;
    }

    public int getTobefed() {
        return tobefed;
    }

    public int getCleantime() {
        return cleantime;
    }

    public int getNumAnimal() {
        return numanimal;
    }

    public int[] animalCBF(int min){
        min=min-prepfeed;


        int numanimalCBF=min/onefeed;
        return new int[]{numanimalCBF, min % onefeed};
    }

    public int[] getFeedTimeHour() {
        return feedtimehour;
    }
    public String getName() {
        return name;
    }
}
