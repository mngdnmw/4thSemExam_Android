package mafioso.so.so.android.BE;

public class PictureBE {
    private int id;
    private String name;
    private String timeStamp;
    private long lattitude;
    private long longitude;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getTimeStamp() { return timeStamp; }

    public void setTimeStamp(String timeStamp) { this.timeStamp = timeStamp; }

    public long getLatitude() { return lattitude; }

    public void setLatitude(long lattitude) { this.lattitude = lattitude; }

    public long getLongitude() { return longitude; }

    public void setLongitude(long longitude) { this.longitude = longitude; }
}
