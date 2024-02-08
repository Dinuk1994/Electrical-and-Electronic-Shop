package bo.custom.impl;

import bo.custom.UpdatePasswordBo;
import dao.DaoFactory;
import dao.custom.UpdatePasswordDao;
import dao.custom.UserUpdateDao;
import dao.util.DaoType;
import dto.UserDto;
import entity.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UpdatePasswordBoImpl implements UpdatePasswordBo {

    UpdatePasswordDao updatePasswordDao= DaoFactory.getInstance().getDao(DaoType.PASSWORD_UPDATE);
    @Override
    public boolean isFound(UserDto dto) throws SQLException, ClassNotFoundException {

        return updatePasswordDao.searchUser(new User(
                dto.getUserId(),
                dto.getName(),
                dto.getContactNumber(),
                dto.getEmail(),
                dto.getAddress(),
                dto.getPrimaryPassword()
        ));


    }

    @Override
    public boolean isUpdate(UserDto userDto)  {
        return updatePasswordDao.updateUser(new User(
                userDto.getUserId(),
                userDto.getAddress(),
                userDto.getContactNumber(),
                userDto.getEmail(),
                userDto.getName(),
                userDto.getPrimaryPassword()
        ));

    }

    public List<UserDto> allUsers() {
        List<User> entityList=updatePasswordDao.getAll();
        List<UserDto>  dtoList=new ArrayList<>();
        for (User user:entityList) {
            dtoList.add(new UserDto(
                    user.getUserId(),
                    user.getAddress(),
                    user.getContactNumber(),
                    user.getEmail(),
                    user.getName(),
                    user.getPrimaryPassword()
            ));
        }

        return dtoList;
    }
}
