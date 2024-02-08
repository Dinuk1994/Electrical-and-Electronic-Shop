package dao.custom.impl;


import dao.DaoFactory;
import dao.custom.OrderDetailsDao;
import dao.custom.OrdersDao;

import dao.util.CrudUtil;
import dao.util.DaoType;
import dao.util.HibernateUtil;
import db.DBConnection;
import dto.OrderDetailsDto;
import dto.OrdersDto;
import entity.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrdersDaoImpl implements OrdersDao {
    OrderDetailsDao orderDetailsDao= DaoFactory.getInstance().getDao(DaoType.ORDER_DETAILS);
    @Override
    public boolean orderSaved(OrdersDto ordersDto) throws SQLException {

        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Orders order = new Orders(
                ordersDto.getOrderId(),
                ordersDto.getItemCategory(),
                ordersDto.getCustomerId(),
                ordersDto.getItemName(),
                ordersDto.getItemQty(),
                ordersDto.getItemPrice(),
                ordersDto.getOrderDate()

        );
        order.setCustomer(session.find(Customer.class,ordersDto.getCustomerId()));
        session.save(order);

        List<OrderDetailsDto> list=ordersDto.getList();
        List<OrderDetails> details =new ArrayList<>();
        for (OrderDetailsDto dto:list) {
            OrderDetails orderDetails = new OrderDetails(
                    new OrderDetailsKey(dto.getOrderId(), dto.getItemCode()),
                    session.find(Item.class, dto.getItemCode()),
                    order,
                    dto.getCustomerName(),
                    dto.getItemCategory(),
                    dto.getItemName(),
                    dto.getItemQty(),
                    dto.getItemPrice(),
                    dto.getStatus()

            );
            session.save(orderDetails);
            details.add(orderDetails);
        }
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public OrdersDto lastOrder() throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("SELECT u FROM Orders u ORDER BY u.orderId DESC ");
        query.setMaxResults(1);
        Orders orders = (Orders) query.uniqueResult();
        if (orders!=null){
            return new OrdersDto(
                    orders.getOrderId(),
                    orders.getItemCategory(),
                    orders.getCustomerId(),
                    orders.getItemName(),
                    orders.getItemQty(),
                    orders.getItemPrice(),
                    orders.getOrderDate(),
                    null
            );
        }
      return null;
    }
}
