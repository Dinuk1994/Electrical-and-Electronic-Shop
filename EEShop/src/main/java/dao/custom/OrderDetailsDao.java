package dao.custom;

import dao.SuperDao;
import dto.OrderDetailsDto;
import entity.OrderDetails;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailsDao extends SuperDao {
    List<OrderDetails> getAll() throws SQLException, ClassNotFoundException;
}
