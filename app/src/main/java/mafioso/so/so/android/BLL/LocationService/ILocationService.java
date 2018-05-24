package mafioso.so.so.android.BLL.LocationService;

import android.location.Location;

public interface ILocationService {

    Location lastKnownLocation();

    String getAddress(double latitude, double longitude);
}
