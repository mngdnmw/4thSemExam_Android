package mafioso.so.so.android.BLL.Factories;

import mafioso.so.so.android.DAL.DAO;
import mafioso.so.so.android.BLL.Interfaces.IDAO;
import mafioso.so.so.android.MyApplication;

public class DAOFactory {

    public IDAO getDAO()
    {
        return new DAO(MyApplication.getAppContext());
    }
}
