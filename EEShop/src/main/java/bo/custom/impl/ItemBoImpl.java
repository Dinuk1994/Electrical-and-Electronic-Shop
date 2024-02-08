package bo.custom.impl;

import bo.BoFactory;
import bo.custom.ItemBo;
import dao.DaoFactory;
import dao.custom.ItemDao;
import dao.util.BoType;
import dao.util.DaoType;
import dto.ItemDto;
import entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemBoImpl implements ItemBo {
   ItemDao itemDao= DaoFactory.getInstance().getDao(DaoType.ITEM);
    @Override
    public boolean save(ItemDto itemDto) throws SQLException, ClassNotFoundException {
        String selectedItemCategory= itemDto.getItemCategory();
        itemDto.setItemCategory(selectedItemCategory);
        return itemDao.save(new Item(
                itemDto.getItemCode(),
                itemDto.getItemCategory(),
                itemDto.getItemName(),
                itemDto.getItemPrice()
        ));
    }

    @Override
    public boolean update(ItemDto itemDto) throws SQLException, ClassNotFoundException {
        return itemDao.update(new Item(
                itemDto.getItemCode(),
                itemDto.getItemCategory(),
                itemDto.getItemName(),
                itemDto.getItemPrice()
        ));
    }

    @Override

    public boolean delete(String itemCode) throws SQLException, ClassNotFoundException {
        return itemDao.delete(itemCode);
    }

    @Override
    public List<ItemDto> getAll() throws SQLException, ClassNotFoundException {
        List<Item> entityList=itemDao.getAll();
        List<ItemDto> dtoList =new ArrayList<>();
        for (Item item:entityList) {
            dtoList.add(new ItemDto(
                    item.getItemCode(),
                    item.getItemCategory(),
                    item.getItemName(),
                    item.getItemPrice()
            ));
        }
        return dtoList;
    }

    public String generateId(){
        try {
            ItemDto itemDto = itemDao.lastItem();
            if (itemDto != null) {
                String itemCode = itemDto.getItemCode();
                int num = Integer.parseInt(itemCode.split("I")[1]);
                num++;
                return String.format("I%03d", num);
            } else {
                return "I001";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
