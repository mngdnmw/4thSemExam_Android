package mafioso.so.so.android;

import android.view.View;

import mafioso.so.so.android.BE.PictureBE;

public interface ItemClickListener {

    void onClick(View view, int position, boolean isLongClick);
}
