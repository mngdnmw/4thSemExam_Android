package mafioso.so.so.android.GUI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import mafioso.so.so.android.BE.PictureBE;
import mafioso.so.so.android.DAL.DAO;
import mafioso.so.so.android.GUI.Controllers.DetailActivity;
import mafioso.so.so.android.R;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    DAO m_DAO;

    /** --- Tag for debug logging. --- */
    String TAG = "SOSOMAFIOSO::RECYCLERADAPTER";

    private Context mContext;
    private List<PictureBE> pictures;

    public RecyclerAdapter(Context context, List<PictureBE> pictures)
    {
        this.mContext = context;
        this.pictures = pictures;
        m_DAO = new DAO(context);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(R.layout.item_even_listview, parent, false);

        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        PictureBE picture = pictures.get(position);

        holder.mImageView_picture.setImageBitmap(picture.getObjectImage());
        holder.mTextView_objectName.setText(picture.getName());
        holder.mTextView_date.setText(picture.getTimeStamp());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(isLongClick)
                {
                    Toast.makeText(mContext, "Long click: " + pictures.get(position), Toast.LENGTH_SHORT).show();
                } else {
                    PictureBE picture = pictures.get(position);
                    showDetailView(picture);

                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return pictures.size();
    }

    private void showDetailView(PictureBE picture) {
        if (picture.getObjectImage() != null) {
            Intent intent = new Intent(mContext, DetailActivity.class);
            if (picture.getObjectImage() != null) {
                String imgPath = m_DAO.saveImgToFile(picture.getObjectImage());
                intent.putExtra("path", imgPath);
            }


            intent.putExtra("picture", picture);
            mContext.startActivity(intent);
        }


    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener
    {
        ImageView mImageView_picture;
        TextView mTextView_objectName, mTextView_location, mTextView_date;

        private ItemClickListener mItemClickListener;

        private RecyclerViewHolder(View itemView)
        {
            super(itemView);

            itemView.setOnClickListener(this);

            itemView.setOnLongClickListener(this);

            mImageView_picture = (ImageView)itemView.findViewById(R.id.imageView_picture);
            mTextView_objectName = (TextView) itemView.findViewById(R.id.textView_objectName);
            mTextView_location = (TextView) itemView.findViewById(R.id.textView_location);
            mTextView_date = (TextView) itemView.findViewById(R.id.textView_date);

        }

        private void setItemClickListener(ItemClickListener itemClickListener)
        {
            this.mItemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {

            mItemClickListener.onClick(v, getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View v) {

            mItemClickListener.onClick(v, getAdapterPosition(), true);
            return true;
        }


    }
}
