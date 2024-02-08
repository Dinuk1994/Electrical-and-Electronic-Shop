package bo.custom;

import bo.SuperBo;
import dao.DaoFactory;
import dao.custom.OrdersDao;
import dao.util.DaoType;
import dto.OrdersDto;

import java.sql.SQLException;

public interface OrderBo extends SuperBo {

    boolean saveOrder(OrdersDto ordersDto) throws SQLException;
    public String generateId();
}
