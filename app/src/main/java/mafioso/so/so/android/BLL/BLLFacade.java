package mafioso.so.so.android.BLL;

import android.util.Log;

import mafioso.so.so.android.BLL.LocationService.ILocationService;
import mafioso.so.so.android.BLL.LocationService.LocationService;
import mafioso.so.so.android.MyApplication;

public class BLLFacade {

    /** --- Tag for debug logging. --- */
    private static String TAG = "SOSOMAFIOSO::Facades::BLLFacade";

    private static ILocationService locationService;

    private static BLLFacade instance;

    private BLLFacade()
    {
        locationService = new LocationService(MyApplication.getAppContext());
    }

    public static BLLFacade getInstance()
    {
        Log.d(TAG, "getInstance: Instance state:" + (instance != null));
        if (instance == null)
        {
            Log.d(TAG, "getInstance: Creating BLLFacade instance.");
            instance = new BLLFacade();
        }
        Log.d(TAG, "getInstance: Returning BLLFacade instance.");
        return instance;
    }

    public static ILocationService LocationService()
    {
        Log.d(TAG, "LocationService: Attempting get instance. Instance: " + (locationService != null));
        if (locationService == null)
        {
            locationService = new LocationService(MyApplication.getAppContext());
            Log.d(TAG, "LocationService: New LocationService instance.");
        }
        Log.d(TAG, "LocationService: Returning instance.");
        return locationService;
    }
}
