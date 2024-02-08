package bo.custom.impl;

import bo.custom.LoginBo;
import dao.DaoFactory;
import dao.custom.LoginDao;
import dao.util.DaoType;
import dto.AdminDto;
import dto.UserDto;
import entity.Admin;
import entity.User;

import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.SQLException;



public class LoginBoImpl implements LoginBo {

    LoginDao loginDao = DaoFactory.getInstance().getDao(DaoType.LOGIN);
    public boolean isFound(UserDto userDto) throws SQLException, ClassNotFoundException {
        return loginDao.searchUser(new User(
                userDto.getUserId(),
                userDto.getAddress(),
                userDto.getContactNumber(),
                userDto.getEmail(),
                userDto.getName(),
                userDto.getPrimaryPassword()
        ));

    }

    @Override
    public boolean isFound(AdminDto adminDto) {
        return loginDao.searchAdmin(new Admin(
                adminDto.getAdminId(),
                adminDto.getEmail(),
                adminDto.getPassword()
        ));
    }


    public String generateOTP(int length) {
       final String CHARACTERS = "01234567890";
       final SecureRandom RANDOM = new SecureRandom();

       StringBuilder otp = new StringBuilder(length);
       for (int i = 0; i < length; i++) {
           otp.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
       }
       return otp.toString();
    }

}
