package mafioso.so.so.android.BE;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.Timestamp;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

public class PictureBE implements Parcelable, Comparable {
    //private String name;
    private String uid;
    private LocalDate timeStamp;
    private double latitude;
    private double longitude;
    private Bitmap image;

    /**
     * Constructor for mapping the object with JSON data received from firebase.
     * @param map
     * A map containing object data mapped to string keys.
     */
    public PictureBE(Map<String, Object> map)
    {

        String time = (String)map.get("time");
        if (time == null)
        {
            timeStamp = LocalDate.now();
        }
        else {
            timeStamp = LocalDate.parse(time);
        }


        latitude = (double)map.get("latitude");
        longitude = (double)map.get("longitude");
    }

    public String getUid() { return uid; }

    public void setUid(String uid) { this.uid = uid;}

    public LocalDate getTimeStamp() { return timeStamp; }

    public void setTimeStamp(LocalDate timeStamp) { this.timeStamp = timeStamp; }

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
        dest.writeString(this.uid);
        dest.writeString(this.timeStamp.toString());
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
    }

    /**
     * Creates PictureBE from an inputted parcel.
     * @param in
     */
    protected PictureBE(Parcel in) {
        this.uid = in.readString();
        this.timeStamp = LocalDate.parse(in.readString());
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
    }

    /**
     * Parcelable creator.
     */
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

    /**
     * Comparing this object to another for sorting in lists.
     * @param o
     * The object to compare with.
     * @return
     * Priority value.
     */
    @Override
    public int compareTo(@NonNull Object o) {
        PictureBE compare = (PictureBE) o;
        return this.getTimeStamp().toString().compareToIgnoreCase(compare.getTimeStamp().toString());
    }
}
