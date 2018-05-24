package mafioso.so.so.android.BLL.LocationService;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import mafioso.so.so.android.BLL.Interfaces.ILocationService;

public class LocationService implements ILocationService {

    /** --- Reference to the application context. --- */
    Context mContext;

    /** --- Reference to the location manager. --- */
    LocationManager mlocationManager;

    /** --- Tag for debug logging. --- */
    String TAG = "SOSOMAFIOSO::GPS";


    /**
     * Create a new LocationService.
     * @param context
     * Application context.
     */
    public LocationService(Context context) {
        mContext = context;
        mlocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    /**
     * Method for acquiring last know location from the location manager.
     * @return
     * An object of the type 'Location'. Last known registered position by the phone's GPS.
     */
    public Location lastKnownLocation() {

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Has permission, getting location");
            return mlocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        Log.d(TAG, "No permission last known");
        return null;
    }

    /**
     * Extrapolates nearest city from location data.
     * @param latitude
     * Coordinate latitude.
     * @param longitude
     * Coordinate longitude.
     * @return
     * Name of nearest city if found, otherwise returns null.
     */
    public String getAddress(double latitude, double longitude) {
        Address address;
        Geocoder geoCoder = new Geocoder(mContext);
        List<Address> list = null;
        try {
            list = geoCoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (list != null & list.size() > 0) {
            address = list.get(0);
            if (address.getLocality() == null)
            {
                return "No city";
            }
            return address.getLocality();
        }
        else { return null; }
    }

}
