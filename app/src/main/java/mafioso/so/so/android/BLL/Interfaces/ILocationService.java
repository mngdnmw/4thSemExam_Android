package mafioso.so.so.android.BLL.Interfaces;

import android.location.Location;

public interface ILocationService {

    Location lastKnownLocation();

    String getAddress(double latitude, double longitude);
}
