import java.util.HashMap;
//Members: Ahmed Iqbal, Musa Jawad, Abrar Rehan, Rishik Roy
//Code version: 11.0.17

//package FinalProject.edu.ucalgary.oop;

public interface FormatSchedule{
    // Use this interface in order to format the final schedule so it is properly formatted
    abstract void formatSchedule(HashMap<Integer,Hour> Schedule);
}