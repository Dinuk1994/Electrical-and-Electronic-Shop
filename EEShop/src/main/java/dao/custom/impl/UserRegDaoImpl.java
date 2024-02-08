package dao.custom.impl;

import dao.custom.UserRegDao;
import dao.util.HibernateUtil;
import dto.UserDto;
import entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class
UserRegDaoImpl implements UserRegDao {
    @Override
    public boolean save(User entity) throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(User entity) throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        User user = session.find(User.class, entity.getUserId());
        user.setAddress(entity.getAddress());
        user.setName(entity.getName());
        user.setEmail(entity.getEmail());
        user.setPrimaryPassword(entity.getPrimaryPassword());
        user.setContactNumber(entity.getContactNumber());

        session.save(user);
        transaction.commit();
        return true;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        User user = session.find(User.class, id);
        session.delete(user);
        transaction.commit();
        return true;
    }

    @Override
    public List<User> getAll() throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM User");
        List<User> list = query.list();

        return list;
    }

    @Override
    public UserDto lastUser() throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("SELECT u FROM User u ORDER BY u.userId DESC");
        query.setMaxResults(1);
        User user = (User) query.uniqueResult();
        if (user!=null){
            return new UserDto(
                    user.getUserId(),
                    user.getAddress(),
                    user.getContactNumber(),
                    user.getEmail(),
                    user.getName(),
                    user.getPrimaryPassword()
            );
        }

       return null;
    }
}
