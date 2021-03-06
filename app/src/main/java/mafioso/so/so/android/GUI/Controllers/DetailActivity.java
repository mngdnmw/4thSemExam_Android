package mafioso.so.so.android.GUI.Controllers;

import android.content.Intent;
import android.graphics.Typeface;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import mafioso.so.so.android.BE.PictureBE;
import mafioso.so.so.android.BLL.BLLFacade;
import mafioso.so.so.android.DAL.DALFacade;
import mafioso.so.so.android.R;
import mafioso.so.so.android.BLL.WeatherService.IAsyncResponse;
import mafioso.so.so.android.BLL.WeatherService.WeatherService;

public class DetailActivity extends AppCompatActivity {

    /** --- Tag for debug logging. --- */
    String TAG = "SOSOMAFIOSO::DETAIL";

    /** --- Reference to the selected pictureBE. --- */
    PictureBE mPicture;

    /** --- Reference to the data access facade. --- */
    DALFacade mDALFacade;

    /** --- Reference to the business logic facade. --- */
    BLLFacade mBLLFacade;

     /** --- References to view objects. --- */
    ImageView imageView_detailPicture;

    ImageButton imageButton_map;

    TextView imageView_weather;
    TextView textView_date;
    TextView textView_disResult;
    TextView textView_cityResult;
    TextView textView_weaResult;
    TextView textView_daysResult;

    Typeface weatherFont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mDALFacade = DALFacade.getInstance();
        mBLLFacade = BLLFacade.getInstance();

        weatherFont = Typeface.createFromAsset(getAssets(), "fonts/weatherIcons.ttf");

        imageView_detailPicture = findViewById(R.id.imageView_detailPicture);
        imageView_weather = findViewById(R.id.imageView_weather);

        imageButton_map = findViewById(R.id.imageButton_map);

        imageButton_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickMap();
            }
        });

        textView_date = findViewById(R.id.textView_date);
        textView_disResult = findViewById(R.id.textView_disResult);
        textView_cityResult = findViewById(R.id.textView_cityResult);
        textView_weaResult = findViewById(R.id.textView_WeaResult);
        textView_daysResult = findViewById(R.id.textView_daysResult);

        imageView_weather.setTypeface(weatherFont);

        if (this.getIntent().getExtras() != null) {
            mPicture = this.getIntent().getExtras().getParcelable("picture");
            String path = this.getIntent().getStringExtra("path");
            Log.d(TAG, "onCreate: " + path);

            mPicture.setObjectImage(mDALFacade.DAO().getImageFromFile(path));
            setupViews();
        }

        WeatherService asyncTask = new WeatherService(new IAsyncResponse() {
            public void processFinish(String weather_city, String weather_description, String weather_temperature, String weather_humidity, String weather_pressure, String weather_updatedOn, String weather_iconText) {


                textView_weaResult.setText(weather_temperature);
                Log.d(TAG, "processFinish: " + weather_description);
                imageView_weather.setText(Html.fromHtml(weather_iconText));
            }
        }, this);

        asyncTask.execute(""+ mPicture.getLatitude(), ""+ mPicture.getLongitude());

        Log.d(TAG, "Detail UID: " + mPicture.getUid());

    }

    /**
     * Sets up the various views, loading data from the selected picture.
     */
    private void setupViews()
    {
        imageView_detailPicture.setImageBitmap(mPicture.getObjectImage());

        Location loc = new Location("");
        loc.setLatitude(mPicture.getLatitude());
        loc.setLongitude(mPicture.getLongitude());

        if (mBLLFacade.LocationService().lastKnownLocation() != null) {
            Location gpsLoc = mBLLFacade.LocationService().lastKnownLocation();
            textView_disResult.setText(distanceFormat(loc.distanceTo(gpsLoc)));
        }

        String address = mBLLFacade.LocationService().getAddress(loc.getLatitude(), loc.getLongitude());
        Log.d(TAG, "setupViews: Got address: " + address);

        if (address != null)
        {
            textView_cityResult.setText(address);
        }

        textView_date.setText(mPicture.getTimeStamp().toString());
        
        textView_daysResult.setText("Days: "+ daysBetween(mPicture.getTimeStamp()));

    }

    /**
     * Redirects to a map activity, to show the location of the currently selected picture.
     */
    private void clickMap()
    {
        Intent intent = new Intent(this, MapsActivity.class);

        intent.putExtra("picture", mPicture);
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
            return formatter.format(distance) + " m";
        }
        else
        {
            Float kmDistance = distance / 1000;
            return formatter.format(kmDistance) + " km";
        }
    }

    /**
     * Calculate amount of days between a given date and the current date.
     * @param date
     * LocalDate object of the date to be compared.
     * @return
     * Long representing number of days between given date and the current date.
     */
    private long daysBetween(LocalDate date)
    {
        if (date != null) {
            LocalDate today = LocalDate.now();

            long daysBetween = ChronoUnit.DAYS.between(date, today);

            return daysBetween;
        }
        return 0;
    }
}
