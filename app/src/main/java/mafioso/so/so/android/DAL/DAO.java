package mafioso.so.so.android.DAL;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import mafioso.so.so.android.BE.PictureBE;

public class DAO {

    Context context;

    public FirebaseFirestore m_db;

    private FirebaseStorage m_storage;
    StorageReference m_storageRef;
    String TAG = "SOSOMAFIOSO::DAO";

    public static final String FIRE_COLLECTION_PICTURES = "pictures";

    public DAO(Context context) {
        this.context = context;

        this.m_db = FirebaseFirestore.getInstance();

        this.m_storage = FirebaseStorage.getInstance();
        this.m_storageRef = m_storage.getReferenceFromUrl("gs://sosomafioso-5c8ef.appspot.com/images");
    }


    public void insert(PictureBE picture) {
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

    private Task<byte[]> getImage(String id) {
        final long MAX_SIZE = 1024 * 1024;
        //StorageReference ref = m_storage.getReferenceFromUrl("gs://sosomafioso-5c8ef.appspot.com/images/" + id + ".JPG");
        Log.d(TAG, "getImage: Attempting download with path " + m_storageRef.child(id + ".JPG").getPath());
        return m_storageRef.child(id + ".JPG").getBytes(MAX_SIZE);
    }

    public void applyImage(final PictureBE picture)
    {
        // REPLACE WITH ACTUAL PICTURE ID
        String TESTID = "test";
        getImage(TESTID).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap img = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                Log.d(TAG, "onSuccess: Download success. Image: " + img.getWidth() + "x" + img.getHeight());
                picture.setObjectImage(img);
                sendMessage();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e(TAG, "onFailure: Download failed.", exception);
            }
        });
    }

    private void sendMessage() {
        Log.d("sender", "Broadcasting message");
        Intent intent = new Intent("ImgDlComplete");
        LocalBroadcastManager.getInstance(this.context).sendBroadcast(intent);
    }

    public String saveImgToFile(Bitmap image)
    {
        String path = "";
        FileOutputStream out = null;
        File outputDir = context.getCacheDir(); // context being the Activity pointer
        File outputFile;
        try {
            outputFile = File.createTempFile("tempImg", ".PNG", outputDir);
            path = outputFile.getAbsolutePath();
            Log.d(TAG, "saveImgToFile: Path: " + path);
            out = new FileOutputStream(outputFile);
            image.compress(Bitmap.CompressFormat.PNG, 100, out);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                return path;

            }catch (IOException e)
            {
                e.printStackTrace();
                return null;
            }
        }
    }

    public Bitmap getImageFromFile(String path)
    {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(path,bmOptions);

        return bitmap;
    }




}