package bo.custom.impl;

import bo.custom.OrderDetailsBo;
import dao.DaoFactory;
import dao.custom.OrderDetailsDao;
import dao.util.DaoType;
import dto.OrderDetailsDto;
import entity.OrderDetails;
import entity.OrderDetailsKey;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailsBoImpl implements OrderDetailsBo {

    OrderDetailsDao orderDetailsDao= DaoFactory.getInstance().getDao(DaoType.ORDER_DETAILS);

    @Override
    public List<OrderDetailsDto> getAll() throws SQLException, ClassNotFoundException {
        List<OrderDetails> entityList = orderDetailsDao.getAll();
        List<OrderDetailsDto> dtoList=new ArrayList<>();
        for (OrderDetails orderDetails:entityList) {
            OrderDetailsKey id=orderDetails.getId();
            dtoList.add(new OrderDetailsDto(
                    id.getOrderId(),
                    id.getItemCode(),

                    orderDetails.getCustomerName(),
                    orderDetails.getItemCategory(),
                    orderDetails.getItemName(),
                    orderDetails.getItemQty(),
                    orderDetails.getItemPrice(),
                    orderDetails.getStatus()
            ));
        }
        return dtoList;

    }
}
