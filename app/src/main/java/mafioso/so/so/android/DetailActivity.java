package mafioso.so.so.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import mafioso.so.so.android.BE.PictureBE;
import mafioso.so.so.android.DAL.DAO;

public class DetailActivity extends AppCompatActivity {

    /** --- Tag for debug logging. --- */
    String TAG = "SOSOMAFIOSO::MAIN";

    PictureBE m_picture;

    DAO m_DAO;

    ImageView imageView_detailPicture;

    TextView textView_name;
    TextView textView_location;
    TextView textView_disResult;
    TextView textView_cityResult;
    TextView textView_weaResult;
    TextView textView_daysResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        m_DAO = new DAO(this);

        imageView_detailPicture = findViewById(R.id.imageView_detailPicture);

        textView_name = findViewById(R.id.textView_name);
        textView_location = findViewById(R.id.textView_location);
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
        
    }
}
