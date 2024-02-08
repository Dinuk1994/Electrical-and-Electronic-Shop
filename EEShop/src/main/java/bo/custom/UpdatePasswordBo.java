package bo.custom;

import bo.SuperBo;
import dto.UserDto;
import entity.User;

import java.sql.SQLException;
import java.util.List;

public interface  UpdatePasswordBo extends SuperBo {
    public boolean isFound(UserDto dto) throws SQLException, ClassNotFoundException;

    public boolean isUpdate(UserDto dto);

    public List<UserDto> allUsers();
}
