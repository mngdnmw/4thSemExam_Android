package mafioso.so.so.android.DAL;

import android.util.Log;

import mafioso.so.so.android.MyApplication;

public class DALFacade {

    /** --- Tag for debug logging. --- */
    private static String TAG = "SOSOMAFIOSO::Facades::DALFacade";

    private static DAO DAO;

    private static DALFacade instance;

    private DALFacade()
    {
        DAO = new DAO(MyApplication.getAppContext());
        Log.d(TAG, "DALFacade: DAO Instance created.");
    }

    public static DALFacade getInstance()
    {
        Log.d(TAG, "getInstance: Attempting get instance. Instance: " + (instance != null));
        if (instance == null)
        {
            instance = new DALFacade();
            Log.d(TAG, "getInstance: Facade instance created.");
        }
        Log.d(TAG, "getInstance: Returning facade instance.");
        return instance;
    }

    public static DAO DAO()
    {
        Log.d(TAG, "DAO: Attempting get instance. Instance: " + (DAO != null));
        if (DAO == null)
        {
            DAO = new DAO(MyApplication.getAppContext());
            Log.d(TAG, "DAO: New DAO instance.");
        }
        Log.d(TAG, "DAO: Returning instance.");
        return DAO;
    }
}
