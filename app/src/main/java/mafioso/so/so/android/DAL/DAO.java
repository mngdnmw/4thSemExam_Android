package mafioso.so.so.android.DAL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

import mafioso.so.so.android.BE.PictureBE;

public class DAO {

    public FirebaseFirestore m_db;
    private StorageReference m_storage;
    String TAG = "SOSOMAFIOSO";

    public static final String FIRE_COLLECTION_PICTURES = "pictures";

    public DAO() {
        this.m_db = FirebaseFirestore.getInstance();
        this.m_storage = FirebaseStorage.getInstance().getReference();
    }


    public void insert(PictureBE picture)
    {
        m_db.collection(FIRE_COLLECTION_PICTURES)
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

    public Bitmap getImage(String id)
    {
        StorageReference riversRef = m_storage.child("images/"+id+".jpg");


        File localFile = null;
        try {
            localFile = File.createTempFile(id, "jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        riversRef.getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        // Successfully downloaded data to local file
                        // ...
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle failed download
                // ...
            }
        });
        Bitmap image = BitmapFactory.decodeFile(localFile.getAbsolutePath());
        return image;
    }

}