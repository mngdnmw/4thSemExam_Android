package mafioso.so.so.android.BLL.Interfaces;

import android.graphics.Bitmap;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import mafioso.so.so.android.BE.PictureBE;

public interface IDAO {

    FirebaseFirestore getDb();
    void applyImage(final PictureBE picture);
    String saveImgToFile(Bitmap image);
    Bitmap getImageFromFile(String path);
}
