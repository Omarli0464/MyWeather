package omarlee.myweather;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import omarlee.weathermap.MapsActivity;
import omarlee.weathermap.WeatherData;
import omarlee.weathermap.WeatherServiceByName;
import omarlee.weathermap.WeatherServiceListener;
public class MainActivity extends AppCompatActivity implements WeatherServiceListener,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener{
   LatLng sydy = new LatLng(36,43);
   // ImageView im= (ImageView)findViewById(R.id.imageView);
    protected TextView   t;
    protected TextView   t2;
    protected TextView   t3;
    protected EditText   et;
    protected GoogleApiClient mGoogleApiClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bt=(Button) findViewById(R.id.button);
        t=(TextView)findViewById(R.id.textView);
        t2=(TextView)findViewById(R.id.textView2);
        t3=(TextView)findViewById(R.id.textView3);
        et=(EditText)findViewById(R.id.editText);
       // WeatherServiceByName al = new WeatherServiceByName(this);
         //al.getWeatherByName("qingdao");
        Button bt2=(Button) findViewById(R.id.button2);

        bt2.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View v) {

                                      WeatherServiceByName al = new WeatherServiceByName(MainActivity.this);
                                      al.getWeatherByName(et.getText().toString());
                                  }
                              }

        );
       bt.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View v) {
                                      Intent intent = new Intent(v.getContext(),MapsActivity.class);
                                      startActivity(intent);
                                  }
                              }

        );

    }
    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }
    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }
    @Override
    public void onConnected(Bundle connectionHint) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {}
       Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(mLastLocation!=null) {
            et.setText(mLastLocation.toString());
            WeatherServiceByName al = new WeatherServiceByName(MainActivity.this);
            al.getWeatherByName(et.getText().toString());
        }
        else{
            et.setText("Dallas");
            WeatherServiceByName al = new WeatherServiceByName(MainActivity.this);
            al.getWeatherByName(et.getText().toString());
        }

    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void serviceSuccess(WeatherData weatherData) {
        t.setText(""+weatherData.getTemp());
        t2.setText(weatherData.getName());
        t3.setText(weatherData.getWeather());
      Log.d("chengongle",weatherData.getName());
        Log.d("success",weatherData.getWeather());
    }

    @Override
    public void serviceFailure(Exception e) {
        Log.d("shibaile",e.toString());
    }
}
