package mafioso.so.so.android.BE;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Map;

public class PictureBE implements Parcelable {
    private int id;
    private String name;
    private String timeStamp;
    private long latitude;
    private long longitude;
    private Bitmap image;

    public PictureBE()
    {}

    public PictureBE(Map<String, Object> map)
    {
        id = Math.toIntExact((long)map.get("id"));
        name = (String)map.get("name");
        timeStamp = (String)map.get("timeStamp");
        latitude = (long)map.get("latitude");
        longitude = (long)map.get("longitude");
    }

    public long getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getTimeStamp() { return timeStamp; }

    public void setTimeStamp(String timeStamp) { this.timeStamp = timeStamp; }

    public long getLatitude() { return latitude; }

    public void setLatitude(long lattitude) { this.latitude = lattitude; }

    public long getLongitude() { return longitude; }

    public void setLongitude(long longitude) { this.longitude = longitude; }

    public void setObjectImage(Bitmap image) { this.image = image;}

    public Bitmap getObjectImage() { return image; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.timeStamp);
        dest.writeLong(this.latitude);
        dest.writeLong(this.longitude);
        //dest.writeParcelable(this.image, flags);
    }

    protected PictureBE(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.timeStamp = in.readString();
        this.latitude = in.readLong();
        this.longitude = in.readLong();
        //this.image = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<PictureBE> CREATOR = new Creator<PictureBE>() {
        @Override
        public PictureBE createFromParcel(Parcel source) {
            return new PictureBE(source);
        }

        @Override
        public PictureBE[] newArray(int size) {
            return new PictureBE[size];
        }
    };
}
