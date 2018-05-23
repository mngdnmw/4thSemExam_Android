package mafioso.so.so.android;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import mafioso.so.so.android.BE.PictureBE;
import mafioso.so.so.android.DAL.DAO;
import mafioso.so.so.android.LocationService.ILocationService;
import mafioso.so.so.android.LocationService.LocationService;
import mafioso.so.so.android.WeatherService.WeatherService;

public class DetailActivity extends AppCompatActivity {

    /** --- Tag for debug logging. --- */
    String TAG = "SOSOMAFIOSO::DETAIL";

    /** --- Reference to the selected pictureBE. --- */
    PictureBE m_picture;

    /** --- Reference to the data access object. --- */
    DAO m_DAO;

     /** --- References to view objects. --- */
    ImageView imageView_detailPicture, imageView_weather;

    /** --- Reference to the location service. --- */
    ILocationService m_GPS;



    ImageButton imageButton_map;

    TextView textView_name;
    TextView textView_date;
    TextView textView_disResult;
    TextView textView_cityResult;
    TextView textView_weaResult;
    TextView textView_daysResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        m_DAO = new DAO(this);
        m_GPS = new LocationService(this);

        imageView_detailPicture = findViewById(R.id.imageView_detailPicture);
        imageView_weather = findViewById(R.id.imageView_weather);

        imageButton_map = findViewById(R.id.imageButton_map);

        imageButton_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickMap();
            }
        });

        textView_name = findViewById(R.id.textView_name);
        textView_date = findViewById(R.id.textView_date);
        textView_disResult = findViewById(R.id.textView_disResult);
        textView_cityResult = findViewById(R.id.textView_cityResult);
        textView_weaResult = findViewById(R.id.textView_WeaResult);
        textView_daysResult = findViewById(R.id.textView_daysResult);

        if (this.getIntent().getExtras() != null) {
            m_picture = this.getIntent().getExtras().getParcelable("picture");
            String path = this.getIntent().getStringExtra("path");
            Log.d(TAG, "onCreate: " + path);

            m_picture.setObjectImage(m_DAO.getImageFromFile(path));
            setupViews();
        }

        WeatherService.placeIdTask asyncTask = new WeatherService.placeIdTask(new WeatherService.AsyncResponse() {
            public void processFinish(String weather_city, String weather_description, String weather_temperature, String weather_humidity, String weather_pressure, String weather_updatedOn) {


                textView_weaResult.setText(weather_temperature);
            }
        });

        asyncTask.execute(""+m_picture.getLatitude(), ""+m_picture.getLongitude()); //  asyncTask.execute("Latitude", "Longitude")



    }

    /**
     * Sets up the various views, loading data from the selected picture.
     */
    private void setupViews()
    {
        imageView_detailPicture.setImageBitmap(m_picture.getObjectImage());
        textView_name.setText(m_picture.getName());

        Location loc = new Location("");
        loc.setLatitude(m_picture.getLatitude());
        loc.setLongitude(m_picture.getLongitude());

        if (m_GPS.lastKnownLocation() != null) {
            Location gpsLoc = m_GPS.lastKnownLocation();
            textView_disResult.setText(distanceFormat(loc.distanceTo(gpsLoc)));
        }

        String address = m_GPS.getAddress(loc.getLatitude(), loc.getLongitude());
        Log.d(TAG, "setupViews: Got address: " + address);

        if (address != null)
        {
            textView_cityResult.setText(address);
        }

        textView_date.setText(m_picture.getTimeStamp());

    }

    /**
     * Redirects to a map activity, to show the location of the currently selected picture.
     */
    private void clickMap()
    {
        Toast.makeText(getBaseContext(),"You clicked the button!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MapsActivity.class);

        intent.putExtra("picture", m_picture);
        startActivity(intent);
    }

    /**
     * Formats a given number, returning a string better representing distance.
     * @param distance
     * Distance in meters.
     * @return
     * A formatted string with unit.
     */
    private String distanceFormat(Float distance)
    {
        NumberFormat formatter = new DecimalFormat("#.#");
        if (distance < 1000)
        {
            return formatter.format(distance) + "m";
        }
        else
        {
            Float kmDistance = distance / 1000;
            return formatter.format(kmDistance) + "km";
        }
    }
}
