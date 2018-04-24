package mafioso.so.so.android.DAL;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import mafioso.so.so.android.BE.PictureBE;

public class DAO {

    FirebaseFirestore db;
    String TAG = "MAFIOSO";

    private final String FIRE_COLLECTION_PICTURES = "pictures";

    public DAO() {
        this.db = FirebaseFirestore.getInstance();
    }


    public void insert(PictureBE picture)
    {
        db.collection(FIRE_COLLECTION_PICTURES)
                .add(picture)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

}