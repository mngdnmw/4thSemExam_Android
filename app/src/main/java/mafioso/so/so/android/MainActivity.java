package mafioso.so.so.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.time.LocalTime;

import mafioso.so.so.android.BE.PictureBE;
import mafioso.so.so.android.DAL.DAO;

public class MainActivity extends AppCompatActivity {

    DAO _DAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _DAO = new DAO();


        PictureBE picture = new PictureBE();
        picture.setId(20);
        picture.setName("The realest");
        picture.setLatitude(50);
        picture.setLongitude(50);
        picture.setTimeStamp(LocalTime.now().toString());

        _DAO.insert(picture);

    }
}
