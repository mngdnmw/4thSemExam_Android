package mafioso.so.so.android.BLL.Factories;

import mafioso.so.so.android.BLL.Interfaces.ILocationService;
import mafioso.so.so.android.BLL.LocationService.LocationService;
import mafioso.so.so.android.MyApplication;

public class LocationServiceFactory {

    public ILocationService getLocationService()
    {
        return new LocationService(MyApplication.getAppContext());
    }
}
