package mafioso.so.so.android.DAL;

import android.util.Log;

import mafioso.so.so.android.DAL.Factory.DAOFactory;
import mafioso.so.so.android.DAL.Interface.IDAO;

public class DALFacade {

    /** --- Tag for debug logging. --- */
    private static String TAG = "SOSOMAFIOSO::Facades::DALFacade";

    /** --- Reference to DAO object. --- */
    private static IDAO DAO;

    /** --- Singleton instance of the DALFacade. --- */
    private static DALFacade instance;

    private DALFacade()
    {}

    /**
     * Returns singleton instance of the DAL Facade. Creates new instance if null.
     * @return
     * DAL Facade object.
     */
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

    /**
     * Acquire DAO object. Retrieves new instance from factory if null.
     * @return
     * DAO object.
     */
    public static IDAO DAO()
    {
        Log.d(TAG, "DAO: Attempting get instance. Instance: " + (DAO != null));
        if (DAO == null)
        {
            DAO = new DAOFactory().getDAO();
            Log.d(TAG, "DAO: New DAO instance.");
        }
        Log.d(TAG, "DAO: Returning instance.");
        return DAO;
    }
}
