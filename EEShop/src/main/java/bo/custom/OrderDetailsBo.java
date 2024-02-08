package bo.custom;

import bo.SuperBo;
import dto.OrderDetailsDto;
import entity.OrderDetails;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailsBo extends SuperBo {
    List<OrderDetailsDto> getAll() throws SQLException, ClassNotFoundException;
}
