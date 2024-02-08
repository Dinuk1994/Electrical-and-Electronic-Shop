package bo.custom;

import bo.SuperBo;
import dto.AdminDto;
import dto.UserDto;
import entity.User;

import java.sql.SQLException;

public interface LoginBo extends SuperBo {
    public boolean isFound(UserDto userDto) throws SQLException, ClassNotFoundException;

    boolean isFound(AdminDto adminDto);

    public String generateOTP(int length);
}
