package mafioso.so.so.android;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import mafioso.so.so.android.BE.PictureBE;

/**
 * Created by Fjord82 on 23/04/2018.
 */

public class PictureListAdapter extends BaseAdapter {

    private final Context context;
    private List<PictureBE> pictures;

    public PictureListAdapter(Context context, List<PictureBE> pictures){
        this.context = context;
        this.pictures = pictures;
    }

    @Override
    public int getCount() {return pictures.size(); }

    @Override
    public PictureBE getItem(int i) {
        return pictures.get(i);
    }

    @Override
    public long getItemId(int i) {
        return pictures.get(i).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup)
    {
        View rowView = view;

        //recycling of views
        if(rowView  == null)
        {
            LayoutInflater inflater = LayoutInflater.from(context);
            rowView = inflater.inflate(R.layout.custom_row,null);

            //Configure viewholder
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.mImageView_picture = (ImageView) rowView.findViewById(R.id.imageView_picture);
            viewHolder.mTextView_objectName = (TextView) rowView.findViewById(R.id.textView_objectName);
            viewHolder.mTextView_location = (TextView) rowView.findViewById(R.id.textView_location);
            viewHolder.mTextView_date = (TextView) rowView.findViewById(R.id.textView_date);
            rowView.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) rowView.getTag();
        //fill data
        PictureBE picture = pictures.get(position);
        holder.mTextView_objectName.setText(picture.getName());
        Log.d("Friend Name: ",""+ picture.getName());
        //holder.mTextView_location.setText(picture.getLatitude());
        holder.mTextView_date.setText(picture.getTimeStamp());
        /*File imgFile = new  File(picture.getObjectImage());
        if(imgFile.exists())
        {
            Bitmap bmImg = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            if (bmImg != null) {
                holder.mImageView_picture.setImageBitmap(bmImg);
            }
        }*/
        return rowView;
    }

    static class ViewHolder
    {
        public ImageView mImageView_picture;
        public TextView mTextView_objectName;
        public TextView mTextView_location;
        public TextView mTextView_date;
    }
}
