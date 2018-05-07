package mafioso.so.so.android.BE;

import android.graphics.Bitmap;

import java.util.Map;

public class PictureBE {
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
}
