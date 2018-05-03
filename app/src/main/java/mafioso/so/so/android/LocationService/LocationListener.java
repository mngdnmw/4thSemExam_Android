package mafioso.so.so.android.LocationService;

import android.location.Location;
import android.os.Bundle;

public class LocationListener {
    IViewCallback m_view;

    public LocationListener(IViewCallback view) { m_view = view; }

    public void onStatusChanged(String provider, int status,
                                Bundle extras) {
        // called when the provider status changes. Possible status:
        // OUT_OF_SERVICE, TEMPORARILY_UNAVAILABLE or AVAILABLE.
    }

    public void onProviderEnabled(String provider) {
        // called when the provider is enabled by the user
    }

    public void onProviderDisabled(String provider) {
        // called when the provider is disabled by the user, if it's
        // already disabled, it's called immediately after
        // requestLocationUpdates
    }

    public void onLocationChanged(Location location) {
        m_view.setTxtSpeed(location.getSpeed());

        m_view.setCurrentLocation(location);
    }
}
