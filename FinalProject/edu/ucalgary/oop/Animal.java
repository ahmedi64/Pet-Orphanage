package FinalProject.edu.ucalgary.oop;
public class Animal{
    public String name;
    public int numanimal;
    public int tobefed;
    public int feedtime;
    public int cleantime;
    private FeedingTask feed;
    private CleaningTask clean;
    public animal(String name){
        this.name=name;
        this.feed = new FeedingTask(name);
        this.clean = new CleaningTask(name);
        this.numanimal =feed.getnumberanimals();
        this.tobefed = feed.getnumberanimals();
        this.feedtime = feed.getfeedtime();
        this.cleantime = feed.getcleantime();

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