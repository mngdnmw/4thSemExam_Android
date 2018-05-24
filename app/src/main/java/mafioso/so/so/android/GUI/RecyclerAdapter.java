package mafioso.so.so.android.GUI;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import mafioso.so.so.android.BE.PictureBE;
import mafioso.so.so.android.BLL.BLLFacade;
import mafioso.so.so.android.DAL.DALFacade;
import mafioso.so.so.android.GUI.Controllers.DetailActivity;
import mafioso.so.so.android.R;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    DALFacade mDALFacade;

    BLLFacade mBLLFacade;

    /** --- Tag for debug logging. --- */
    String TAG = "SOSOMAFIOSO::RECYCLERADAPTER";

    private Context mContext;
    private List<PictureBE> mPictures;

    public RecyclerAdapter(Context context, List<PictureBE> pictures)
    {
        mContext = context;
        mPictures = pictures;
        mDALFacade = DALFacade.getInstance();
        mBLLFacade = BLLFacade.getInstance();

    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(R.layout.item_listview, parent, false);

        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        PictureBE picture = mPictures.get(position);

        if(position %2 == 1)
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#b7d0ce"));
        }
        else
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#a1b8b7"));
        }

        holder.mImageView_picture.setImageBitmap(picture.getObjectImage());
        holder.mTextView_location.setText(mBLLFacade.LocationService().getAddress(picture.getLatitude(), picture.getLongitude()));
        holder.mTextView_date.setText(picture.getTimeStamp().toString());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(isLongClick)
                {
                    Toast.makeText(mContext, "Long click: " + mPictures.get(position), Toast.LENGTH_SHORT).show();
                } else {
                    PictureBE picture = mPictures.get(position);
                    showDetailView(picture);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPictures.size();
    }

    /**
     * Navigate to the details view for the selected object.
     * @param picture
     * The selected PictureBE.
     */
    private void showDetailView(PictureBE picture) {
        if (picture.getObjectImage() != null) {
            Intent intent = new Intent(mContext, DetailActivity.class);
            if (picture.getObjectImage() != null) {
                String imgPath = mDALFacade.DAO().saveImgToFile(picture.getObjectImage());
                intent.putExtra("path", imgPath);
            }
            intent.putExtra("picture", picture);
            mContext.startActivity(intent);
        }
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener
    {
        CircleImageView mImageView_picture;
        TextView mTextView_location, mTextView_date;

        private ItemClickListener mItemClickListener;

        private RecyclerViewHolder(View itemView)
        {
            super(itemView);

            itemView.setOnClickListener(this);

            itemView.setOnLongClickListener(this);

            mImageView_picture = itemView.findViewById(R.id.imageView_picture);
            mTextView_location = itemView.findViewById(R.id.textView_location);
            mTextView_date = itemView.findViewById(R.id.textView_date);

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
