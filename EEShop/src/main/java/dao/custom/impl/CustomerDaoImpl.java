package dao.custom.impl;

import dao.custom.CustomerDao;
import dao.util.CrudUtil;
import dao.util.HibernateUtil;
import db.DBConnection;
import dto.CustomerDto;
import entity.Customer;
import entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {
    @Override
    public boolean save(Customer entity) throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(User entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Customer entity) throws SQLException, ClassNotFoundException {

        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Customer customer = session.find(Customer.class, entity.getCustomerId());
        customer.setCustomerName(entity.getCustomerName());
        customer.setCustomerAddress(entity.getCustomerAddress());
        customer.setCustomerEmail(entity.getCustomerEmail());
        customer.setCustomerContactNumber(entity.getCustomerContactNumber());

        session.save(customer);
        transaction.commit();
        session.close();
        return true;
    }

    @Override

    public boolean delete(String entity) throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();

        Customer customer = session.find(Customer.class, entity);
        session.delete(customer);
        transaction.commit();
        session.close();
        return true;

    }

    @Override
    public List<Customer> getAll() throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM Customer");
        List<Customer> list = query.list();
        session.close();
        return  list;
    }


    @Override
    public CustomerDto lastItem() throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("SELECT u FROM Customer u ORDER BY u.customerId DESC");
        query.setMaxResults(1);
        Customer customer = (Customer) query.uniqueResult();
        if (customer!=null){
            return new CustomerDto(
                    customer.getCustomerId(),
                    customer.getCustomerName(),
                    customer.getCustomerAddress(),
                    customer.getCustomerContactNumber(),
                    customer.getCustomerEmail()
            );
        }
        session.close();
        return null;
    }

}
