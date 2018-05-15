package mafioso.so.so.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import mafioso.so.so.android.BE.PictureBE;
import mafioso.so.so.android.DAL.DAO;

public class DetailActivity extends AppCompatActivity {

    /** --- Tag for debug logging. --- */
    String TAG = "SOSOMAFIOSO::MAIN";

    PictureBE picture;

    DAO m_DAO;

    ImageView imageView_detailPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        m_DAO = new DAO(this);

        imageView_detailPicture = findViewById(R.id.imageView_detailPicture);

        if (this.getIntent().getExtras() != null) {
            this.picture = this.getIntent().getExtras().getParcelable("picture");
            String path = this.getIntent().getStringExtra("path");
            Log.d(TAG, "onCreate: " + path);

            this.picture.setObjectImage(m_DAO.getImageFromFile(path));
            setupViews();
        }

    }

    private void setupViews()
    {
        imageView_detailPicture.setImageBitmap(picture.getObjectImage());
    }
}
