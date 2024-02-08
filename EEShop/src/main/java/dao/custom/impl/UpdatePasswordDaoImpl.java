package dao.custom.impl;

import dao.custom.UpdatePasswordDao;
import dao.util.HibernateUtil;
import entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class UpdatePasswordDaoImpl implements UpdatePasswordDao {

    @Override
    public boolean searchUser(User entity) {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM User WHERE email=:email");
        query.setParameter("email",entity.getEmail());
        User user = (User) query.uniqueResult();
        return user!=null;
    }


    @Override
    public boolean updateUser(User entity) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("UPDATE User SET primaryPassword = :newPassword WHERE email = :userEmail");
        query.setParameter("newPassword",entity.getPrimaryPassword());
        query.setParameter("userEmail",entity.getEmail());
        int result = query.executeUpdate();
        transaction.commit();
        session.close();
        return result > 0;
    }


    @Override
    public List<User> getAll() {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM User");
        List<User> list = query.list();
        session.close();
        return list;
    }
}
