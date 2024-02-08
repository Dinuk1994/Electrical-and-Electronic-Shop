package dao.custom.impl;

import dao.custom.LoginDao;
import dao.util.HibernateUtil;
import entity.Admin;
import entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class LoginDaoImpl implements LoginDao {

    @Override
    public boolean save(User entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(User entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<User> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean searchUser(User entity)  {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("FROM User WHERE email = :email AND primaryPassword = :newPassword");

        query.setParameter("email",entity.getEmail());
        query.setParameter("newPassword",entity.getPrimaryPassword());
        User user = (User) query.uniqueResult();
        transaction.commit();
        session.close();
        return user!=null;
    }

    public boolean searchAdmin(Admin entity) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("FROM Admin WHERE email = :email AND password = :password");

        query.setParameter("email",entity.getEmail());
        query.setParameter("password",entity.getPassword());
        Admin admin = (Admin) query.uniqueResult();
        transaction.commit();
        session.close();
        return admin!=null;
    }
}
