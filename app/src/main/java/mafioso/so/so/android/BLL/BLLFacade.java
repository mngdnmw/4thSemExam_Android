package mafioso.so.so.android.BLL;

import android.util.Log;

import mafioso.so.so.android.BLL.Interfaces.ILocationService;
import mafioso.so.so.android.BLL.Factories.LocationServiceFactory;

public class BLLFacade {

    /** --- Tag for debug logging. --- */
    private static String TAG = "SOSOMAFIOSO::Facades::BLLFacade";

    /** --- Reference to LocationService object. --- */
    private ILocationService locationService;

    /** --- Singleton instance of the BLLFacade. --- */
    private static BLLFacade instance;

    private BLLFacade()
    {}

    /**
     * Get the singleton instance of the BLL facade. Creates new if null.
     * @return
     * BLL Facade object.
     */
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

    /**
     * Get LocationService instance. Retrieve new instance from factory if null.
     * @return
     * LocationService object instance.
     */
    public ILocationService LocationService()
    {
        Log.d(TAG, "LocationService: Attempting get instance. Instance: " + (locationService != null));
        if (locationService == null)
        {
            locationService = new LocationServiceFactory().getLocationService();
            Log.d(TAG, "LocationService: New LocationService instance.");
        }
        Log.d(TAG, "LocationService: Returning instance.");
        return locationService;
    }
}
