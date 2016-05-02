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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.model.LatLng;

import omarlee.weathermap.MapsActivity;
import omarlee.weathermap.WeatherData;
import omarlee.weathermap.WeatherService;
import omarlee.weathermap.WeatherServiceByName;
import omarlee.weathermap.WeatherServiceListener;
public class MainActivity extends AppCompatActivity implements WeatherServiceListener,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,LocationListener{
    protected TextView   t;
    protected TextView   t2;
    protected TextView   t3;
    protected EditText   et;
    protected ImageView  im;
    protected GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;
    protected LocationRequest mLocationRequest;
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    /**
     * The fastest rate for active location updates. Exact. Updates will never be more frequent
     * than this value.
     */
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        createLocationRequest();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bt=(Button) findViewById(R.id.button);
        im=(ImageView)findViewById(R.id.imageView);
        t=(TextView)findViewById(R.id.textView);
        t2=(TextView)findViewById(R.id.textView2);
        t3=(TextView)findViewById(R.id.textView3);
        et=(EditText)findViewById(R.id.editText);
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
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();

        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than
        // requested if other applications are requesting location at a faster interval.
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //stop location update
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
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
                != PackageManager.PERMISSION_GRANTED) {}//request  permission
    mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if(mLastLocation!=null) {
            LatLng mlocation=new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude());
            WeatherService al = new WeatherService(MainActivity.this);
            al.getWeather(mlocation);
        }
        else{

            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,mLocationRequest,this);
            et.setText("Dallas");
            WeatherServiceByName al = new WeatherServiceByName(MainActivity.this);
            al.getWeatherByName(et.getText().toString());
        }

    }

    @Override
    public void onLocationChanged(Location location) {
       mLastLocation=location;
        WeatherService al = new WeatherService(MainActivity.this);
        al.getWeather(new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude()));
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void serviceSuccess(WeatherData weatherData) {
        String mDrawableName = "icon_"+weatherData.getId();
        int resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
        im.setImageDrawable(getResources().getDrawable(resID,null));
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
