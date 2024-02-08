package bo.custom;

import bo.SuperBo;
import dto.ItemDto;
import entity.Item;

import java.sql.SQLException;
import java.util.List;

public interface ItemBo extends SuperBo {
    public boolean save(ItemDto itemDto) throws SQLException, ClassNotFoundException;

    public boolean update(ItemDto itemDto) throws SQLException, ClassNotFoundException;

    public boolean delete(String itemCode) throws SQLException, ClassNotFoundException;

    public List<ItemDto> getAll() throws SQLException, ClassNotFoundException;

    public String generateId();
}
