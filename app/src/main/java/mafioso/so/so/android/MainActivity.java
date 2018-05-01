package mafioso.so.so.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import mafioso.so.so.android.BE.PictureBE;
import mafioso.so.so.android.DAL.DAO;

public class MainActivity extends AppCompatActivity {

    DAO m_DAO;
    String TAG = "SOSOMAFIOSO";

    ArrayList<PictureBE> m_pictures;
    ListenerRegistration registration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_DAO = new DAO();
        m_pictures = new ArrayList<>();

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        registration = m_DAO.m_db.collection(m_DAO.FIRE_COLLECTION_PICTURES).addSnapshotListener(new EventListener<QuerySnapshot>() {
                                                                        @Override
                                                                        public void onEvent(QuerySnapshot snapshot, FirebaseFirestoreException e) {

                                                                            m_pictures.clear();
                                                                            for (DocumentSnapshot document : snapshot.getDocuments()) {
                                                                                Log.d(TAG, document.getId() + " => " + document.getData());
                                                                                PictureBE newPic = new PictureBE(document.getData());
                                                                                m_pictures.add(newPic);
                                                                                Log.d(TAG, "Got doc: " + newPic.getName());

                                                                            }
                                                                        }
                                                                    }
        );

    }
}
