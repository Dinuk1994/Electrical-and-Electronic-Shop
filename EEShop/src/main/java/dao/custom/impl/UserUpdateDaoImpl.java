package dao.custom.impl;

import dao.custom.UserUpdateDao;
import dao.util.HibernateUtil;
import entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class UserUpdateDaoImpl implements UserUpdateDao {
    @Override
    public boolean save(User entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(User entity) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        User user = session.find(User.class, entity.getUserId());
        user.setName(entity.getName());
        user.setAddress(entity.getAddress());
        user.setContactNumber(entity.getContactNumber());
        user.setPrimaryPassword(entity.getPrimaryPassword());
        user.setEmail(entity.getEmail());
        session.save(user);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<User> getAll() throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        //Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("FROM User");
        List<User> list = query.list();
        //transaction.commit();
        session.close();
        return list;
    }

    @Override
    public boolean searchUser(User entity) throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM User WHERE email = :email AND primaryPassword = :newPassword");

        query.setParameter("email", entity.getEmail());
        query.setParameter("newPassword", entity.getPrimaryPassword());
        User user = (User) query.uniqueResult();

        session.close();

        return user!=null;

    }
}
