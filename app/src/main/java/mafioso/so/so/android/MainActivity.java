package mafioso.so.so.android;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

    /**
     * --- Reference to the adapter for the recycler view. ---
     */
    RecyclerAdapter mAdapter;

    /**
     * --- Reference to the data access object. ---
     */
    DAO m_DAO;

    /**
     * --- Tag for debug logging. ---
     */
    String TAG = "SOSOMAFIOSO::MAIN";

    /**
     * --- Reference to view elements. ---
     */
    RecyclerView listOfPictures;

    /**
     * --- List containing PictureBE's received from the backend. ---
     */
    ArrayList<PictureBE> m_pictures;

    /**
     * --- Reference to Firestore listener. ---
     */
    ListenerRegistration registration;

    /**
     * --- Receiver for image update broadcasts. ---
     */
    BroadcastReceiver mMessageReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Request permissions
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        // Instantiate member variables.
        m_DAO = new DAO(this);
        m_pictures = new ArrayList<>();

        // Setup recylerview.
        listOfPictures = findViewById(R.id.listView_pictureList);
        listOfPictures.setHasFixedSize(true);

        listOfPictures.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new RecyclerAdapter(this, m_pictures);

        listOfPictures.setAdapter(mAdapter);

        mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG, "IMG Update");
                mAdapter.notifyDataSetChanged();
            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("ImgDlComplete"));
    }


    @Override
    protected void onStart() {
        super.onStart();
        registration = m_DAO.m_db.collection(m_DAO.FIRE_COLLECTION_PICTURES)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                         @Override
                                         public void onEvent(QuerySnapshot snapshot, FirebaseFirestoreException e) {

                                             m_pictures.clear();
                                             for (DocumentSnapshot document : snapshot.getDocuments()) {
                                                 Log.d(TAG, document.getId() + " => " + document.getData());
                                                 PictureBE newPic = new PictureBE(document.getData());
                                                 m_DAO.applyImage(newPic);
                                                 m_pictures.add(newPic);

                                             }
                                             mAdapter.notifyDataSetChanged();
                                         }
                                     }
                );

    }

    @Override
    protected void onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }
}
