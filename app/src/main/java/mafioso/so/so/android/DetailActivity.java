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

import mafioso.so.so.android.BE.PictureBE;
import mafioso.so.so.android.DAL.DAO;
import mafioso.so.so.android.LocationService.LocationService;

public class DetailActivity extends AppCompatActivity {

    /** --- Tag for debug logging. --- */
    String TAG = "SOSOMAFIOSO::MAIN";

    PictureBE m_picture;

    DAO m_DAO;
    LocationService m_GPS;

    ImageView imageView_detailPicture;

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

    }

    private void setupViews()
    {
        imageView_detailPicture.setImageBitmap(m_picture.getObjectImage());
        textView_name.setText(m_picture.getName());

        Location loc = new Location("");
        loc.setLatitude(m_picture.getLatitude());
        loc.setLongitude(m_picture.getLongitude());

        if (m_GPS.lastKnownLocation() != null) {
            textView_disResult.setText("" + loc.distanceTo(m_GPS.lastKnownLocation()));
        }
        textView_date.setText(m_picture.getTimeStamp());

    }

    private void clickMap()
    {
        Toast.makeText(getBaseContext(),"You clicked the button!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MapsActivity.class);

        intent.putExtra("picture", m_picture);
        startActivity(intent);
    }
}
