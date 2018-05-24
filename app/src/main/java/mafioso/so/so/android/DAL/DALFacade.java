package mafioso.so.so.android.DAL;

import android.util.Log;

import mafioso.so.so.android.BLL.Factories.DAOFactory;
import mafioso.so.so.android.BLL.Interfaces.IDAO;

public class DALFacade {

    /** --- Tag for debug logging. --- */
    private static String TAG = "SOSOMAFIOSO::Facades::DALFacade";

    private static IDAO DAO;

    private static DALFacade instance;

    private DALFacade()
    {}

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
