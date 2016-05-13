package ateith.eortologio;

/**
 * Created by Γιώργος on 11/11/2014.
 */
public interface AsyncResponse {
    void processUpdate(int number_of_current_month);
    void processBefore();
    void processFinish(String result);

}
