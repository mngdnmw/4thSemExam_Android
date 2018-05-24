package mafioso.so.so.android.GUI.Controllers;

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
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import mafioso.so.so.android.BE.PictureBE;
import mafioso.so.so.android.DAL.DALFacade;
import mafioso.so.so.android.DAL.DAO;
import mafioso.so.so.android.GUI.RecyclerAdapter;
import mafioso.so.so.android.R;

public class MainActivity extends AppCompatActivity {

    /** --- Tag for debug logging. --- */
    String TAG = "SOSOMAFIOSO::MAIN";

    /** --- Reference to the adapter for the recycler view. --- */
    RecyclerAdapter mAdapter;

    /** --- Reference to the data access facade. --- */
    DALFacade mDALFacade;

    String mSort = "Ascending";

    /** --- Reference to view elements. --- */
    RecyclerView listOfPictures;
    Spinner dropDownMenu;

    /** --- List containing PictureBE's received from the backend. --- */
    ArrayList<PictureBE> mPictures;

    /** --- Reference to Firestore listener. --- */
    ListenerRegistration mRegistration;

    /** --- Receiver for image update broadcasts. --- */
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
        mDALFacade = DALFacade.getInstance();
        mPictures = new ArrayList<>();

        // Setup RecyclerView.
        listOfPictures = findViewById(R.id.listView_pictureList);
        listOfPictures.setHasFixedSize(true);

        listOfPictures.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new RecyclerAdapter(this, mPictures);

        listOfPictures.setAdapter(mAdapter);

        dropDownMenu = findViewById(R.id.dropDownMenu);
        spinnerSetup();

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

    private void spinnerSetup()
    {
        String[] items = new String[]{"Ascending", "Descending"};
        final List<String> itemsArray = new ArrayList<>(Arrays.asList(items));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        itemsArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDownMenu.setAdapter(adapter);

        dropDownMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                mSort = itemsArray.get(position);
                sortList();
        }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.d(TAG, "onNothingSelected: Oh my god you selected nothing. Rude.");
            }

        });
    }

    private void sortList()
    {
        switch (mSort) {
            default:
                break;
            case ("Ascending"):
                Log.d(TAG, "Sort: " + mSort);
                Collections.sort(mPictures);
                mAdapter.notifyDataSetChanged();
                break;
            case ("Descending"):
                Log.d(TAG, "Sort: " + mSort);
                Collections.reverse(mPictures);
                mAdapter.notifyDataSetChanged();
                break;
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        mRegistration = mDALFacade.DAO().getDb().collection(this.getResources().getString(R.string.FIRE_COLLECTION_PICTURES))
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                         @Override
                                         public void onEvent(QuerySnapshot snapshot, FirebaseFirestoreException e) {

                                             mPictures.clear();
                                             for (DocumentSnapshot document : snapshot.getDocuments()) {
                                                 Log.d(TAG, document.getId() + " => " + document.getData());
                                                 PictureBE newPic = new PictureBE(document.getData());
                                                 mDALFacade.DAO().applyImage(newPic);
                                                 mPictures.add(newPic);
                                                 sortList();

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
