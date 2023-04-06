

public class Animal{
    public String name;
    public int numanimal;
    public int tobefed;
    public int feedtime;
    public int cleantime;
    private FeedingTask feed;
    private CleaningTask clean;
    public void Animal(String name){
        this.name=name;
        this.feed = new FeedingTask(name);
        this.clean = new CleaningTask(name);
        this.numanimal =feed.getNumberAnimal();
        this.tobefed = feed.getNumberAnimal();
        this.feedtime = feed.getFeedTime();
        this.cleantime = clean.getCleanTime();

    }
    public void decTobefed() {
        this.tobefed -= this.tobefed;
    }

    public int getTobefed() {
        return tobefed;
    }

    public int getCleantime() {
        return cleantime;
    }

    public int getFeedtime() {
        return feedtime*tobefed;
    }
}