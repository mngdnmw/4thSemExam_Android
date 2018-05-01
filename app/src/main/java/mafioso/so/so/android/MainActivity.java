package mafioso.so.so.android;

import android.content.Intent;
import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import mafioso.so.so.android.BE.PictureBE;
import mafioso.so.so.android.DAL.DAO;

public class MainActivity extends AppCompatActivity {

    /** --- Reference to the adapter for the list view. --- */
    PictureListAdapter pictureAdapter;

    /** --- Reference to the data access object. --- */
    DAO m_DAO;

    /** --- Tag for debug logging. --- */
    String TAG = "SOSOMAFIOSO";

    /** --- Reference to view elements. --- */
    ListView listOfPictures;

    /** --- List containing PictureBE's gottn from the backend. --- */
    ArrayList<PictureBE> m_pictures;

    ListenerRegistration registration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        m_DAO = new DAO();
        m_pictures = new ArrayList<>();

        pictureAdapter = new PictureListAdapter(this, m_pictures);
        listOfPictures = findViewById(R.id.listView_pictureList);
        listOfPictures.setAdapter(pictureAdapter);
        listOfPictures.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                //Item clicked;
                Log.d("SOSOMAFIOSO", "Picture: " + id);
                PictureBE picture = pictureAdapter.getItem(position);
                Toast.makeText(getBaseContext(), "You clicked an image in the list! " + id , Toast.LENGTH_SHORT ).show();
                showDetailView(null, true);

            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        registration = m_DAO.m_db.collection(m_DAO.FIRE_COLLECTION_PICTURES)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                                                        @Override
                                                                        public void onEvent(QuerySnapshot snapshot, FirebaseFirestoreException e) {

                                                                            m_pictures.clear();
                                                                            for (DocumentSnapshot document : snapshot.getDocuments()) {
                                                                                Log.d(TAG, document.getId() + " => " + document.getData());
                                                                                PictureBE newPic = new PictureBE(document.getData());
                                                                                m_pictures.add(newPic);
                                                                                Log.d(TAG, "Got doc: " + newPic.getName());

                                                                            }
                                                                            pictureAdapter.notifyDataSetChanged();
                                                                        }
                                                                    }
        );

    }

    private void showDetailView(PictureBE picture, boolean newPicture) {

        /*
        Intent intent = new Intent(this, PictureDetail.class);
        Bundle bundle = new Bundle();
        if (!newPicture)
        {
            bundle.putSerializable("picture", picture);
        }
        bundle.putBoolean("newPicture", newPicture);
        intent.putExtras(bundle);
        startActivity(intent);
        */

    }
}
