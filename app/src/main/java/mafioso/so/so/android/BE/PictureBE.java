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
    private double latitude;
    private double longitude;
    private Bitmap image;

    public PictureBE()
    {}

    /**
     * Constructor for mapping the object with JSON data received from firebase.
     * @param map
     * A map containing object data mapped to string keys.
     */
    public PictureBE(Map<String, Object> map)
    {
        id = Math.toIntExact((long)map.get("id"));
        name = (String)map.get("name");
        timeStamp = (String)map.get("timeStamp");
        latitude = (double)map.get("latitude");
        longitude = (double)map.get("longitude");
    }

    public long getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getTimeStamp() { return timeStamp; }

    public void setTimeStamp(String timeStamp) { this.timeStamp = timeStamp; }

    public double getLatitude() { return latitude; }

    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }

    public void setLongitude(double longitude) { this.longitude = longitude; }

    public void setObjectImage(Bitmap image) { this.image = image;}

    public Bitmap getObjectImage() { return image; }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Writes object data to parcel for passing through intent.
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.timeStamp);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
    }

    /**
     * Creates PictureBE from an inputted parcel.
     * @param in
     */
    protected PictureBE(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.timeStamp = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
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
