package dao.custom.impl;

import dao.custom.OrderDetailsDao;
import dao.util.CrudUtil;
import dao.util.HibernateUtil;
import db.DBConnection;
import dto.OrderDetailsDto;
import entity.OrderDetails;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class
OrderDetailsDaoImpl implements OrderDetailsDao {

    @Override
    public List<OrderDetails> getAll() throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM OrderDetails ");
        List<OrderDetails> list=query.list();
        return list;

    }
}

