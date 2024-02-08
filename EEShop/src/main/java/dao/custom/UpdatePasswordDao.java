package dao.custom;

import dao.CrudDao;
import dao.SuperDao;
import entity.User;

import java.util.List;

public interface UpdatePasswordDao extends SuperDao {

    boolean searchUser(User entity);

    boolean updateUser(User entity);

    List<User> getAll();


}