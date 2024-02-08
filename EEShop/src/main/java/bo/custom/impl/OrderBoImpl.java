package bo.custom.impl;

import bo.custom.OrderBo;
import dao.DaoFactory;
import dao.custom.OrdersDao;
import dao.util.DaoType;
import dto.OrdersDto;

import java.sql.SQLException;

public class OrderBoImpl implements OrderBo {
    OrdersDao ordersDao= DaoFactory.getInstance().getDao(DaoType.ORDER);


    @Override
    public boolean saveOrder(OrdersDto ordersDto) throws SQLException {
        return ordersDao.orderSaved(ordersDto);
    }

    @Override
    public String generateId() {
        try {
            OrdersDto ordersDto=ordersDao.lastOrder();
            if (ordersDto!=null){
                String orderId= ordersDto.getOrderId();
                int num=Integer.parseInt(orderId.split("OR")[1]);
                num++;
                return String.format("OR%03d",num);
            }else{
                return "OR001";
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



}
