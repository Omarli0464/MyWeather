package omarlee.myweather;

import android.app.IntentService;
import android.content.Intent;

public class WeatherUpdate extends IntentService {


    public WeatherUpdate() {
        super("WeatherUpdate");
    }




    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // Restore interrupt status.
            Thread.currentThread().interrupt();
        }
    }


}
