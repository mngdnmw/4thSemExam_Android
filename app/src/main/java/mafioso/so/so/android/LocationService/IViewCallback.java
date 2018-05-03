package mafioso.so.so.android.LocationService;

import android.location.Location;

public interface IViewCallback {

    void setTxtSpeed(double speed);

    void setCurrentLocation(Location location);
}
