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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import mafioso.so.so.android.BE.PictureBE;

public class DAO {

    /** --- Reference to the application context. --- */
    Context context;

    /** --- Firestore DB reference. --- */
    public FirebaseFirestore m_db;

    /** --- Firebase Storage reference --- */
    private FirebaseStorage m_storage;
    StorageReference m_storageRef;
    public static final String FIRE_COLLECTION_PICTURES = "pictures";

    /** --- Tag for debug purposes. --- */
    String TAG = "SOSOMAFIOSO::DAO";

    public DAO(Context context) {
        this.context = context;

        this.m_db = FirebaseFirestore.getInstance();

        this.m_storage = FirebaseStorage.getInstance();
        this.m_storageRef = m_storage.getReferenceFromUrl("gs://sosomafioso-5c8ef.appspot.com/images");
    }

    /**
     * Produces a download task for an image stored on firebase.
     * @param id
     * Filename for the image to download from firebase.
     * @return
     * A task object to download a byte array.
     */
    private Task<byte[]> getImage(String id) {
        final long MAX_SIZE = 1024 * 1024;
        Log.d(TAG, "getImage: Attempting download with path " + m_storageRef.child(id + ".JPG").getPath());
        return m_storageRef.child(id + ".JPG").getBytes(MAX_SIZE);
    }

    /**
     * Applies an image download to a PictureBE, sets it when downloaded and sends a broadcoast
     * to update the valid views once completed.
     * @param picture
     * The PictureBE to apply an image to.
     */
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

    /**
     * Sends an update broadcast to notify that a picture has finished downloading.
     */
    private void sendMessage() {
        Log.d("sender", "Broadcasting message");
        Intent intent = new Intent("ImgDlComplete");
        LocalBroadcastManager.getInstance(this.context).sendBroadcast(intent);
    }

    /**
     * Saves a bitmap image as a temporary file in local storage.
     * @param image
     * The image to be stored.
     * @return
     * Returns the path to the newly created file if successfull, otherwise returns null.
     */
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

    /**
     * Retrieves bitmap image from a given path.
     * @param path
     * Path to a locally stored picture.
     * @return
     * Returns found picture as a bitmap, or null if no valid file found.
     */
    public Bitmap getImageFromFile(String path)
    {
        File file = new File(path);
        if (file.exists()) {

            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(path, bmOptions);

            return bitmap;
        }
        else { return null; }
    }




}