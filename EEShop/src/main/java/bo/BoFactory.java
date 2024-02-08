package bo;

import bo.custom.impl.*;
import dao.custom.impl.UpdatePasswordDaoImpl;
import dao.util.BoType;

import static dao.util.DaoType.CUSTOMER;
import static dao.util.DaoType.ORDER_DETAILS;

public class BoFactory {
    private static BoFactory boFactory;

    private BoFactory(){}

    public static BoFactory getInstance(){
        return boFactory!=null?boFactory:(boFactory=new BoFactory());
    }

    public <T extends SuperBo>T getBo(BoType type){
        switch (type){
            case USER:return (T) new UserRegBoImpl();
            case LOGIN:return (T) new LoginBoImpl();
            case UPDATE:return (T) new UserUpdateBoImpl();
            case CUSTOMER:return (T) new CustomerBoImpl();
            case ITEM:return (T)new ItemBoImpl();
            case ORDER_DETAILS : return (T) new OrderDetailsBoImpl();
            case ORDERS:return (T) new OrderBoImpl();
            case UPDATE_PASSWORD:return (T) new UpdatePasswordBoImpl();
        }
        return null;
    }
}
