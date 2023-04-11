import java.util.HashMap;

//package FinalProject.edu.ucalgary.oop;

public interface FormatSchedule{
    // Use this interface in order to format the final schedule so it is properly formatted
    abstract void formatSchedule(HashMap<Integer,Hour> Schedule);
}