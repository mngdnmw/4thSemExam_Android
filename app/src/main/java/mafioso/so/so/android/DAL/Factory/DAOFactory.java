package mafioso.so.so.android.DAL.Factory;

import mafioso.so.so.android.DAL.DAO;
import mafioso.so.so.android.DAL.Interface.IDAO;
import mafioso.so.so.android.MyApplication;

public class DAOFactory {

    public IDAO getDAO()
    {
        return new DAO(MyApplication.getAppContext());
    }
}
