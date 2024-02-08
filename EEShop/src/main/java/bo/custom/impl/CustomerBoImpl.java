package bo.custom.impl;

import bo.custom.CustomerBo;
import dao.DaoFactory;
import dao.custom.CustomerDao;
import dao.util.DaoType;
import dto.CustomerDto;
import entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBoImpl implements CustomerBo {
    CustomerDao customerDao = DaoFactory.getInstance().getDao(DaoType.CUSTOMER);


    @Override
    public boolean saveCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException {
        return customerDao.save(new Customer(
                customerDto.getCustomerId(),
                customerDto.getCustomerName(),
                customerDto.getCustomerAddress(),
                customerDto.getCustomerContactNumber(),
                customerDto.getCustomerEmail()

        ));
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDao.delete(id);
    }

    @Override
    public List<CustomerDto> allCustomers() throws SQLException, ClassNotFoundException {
        List<Customer> entityList=customerDao.getAll();
        List<CustomerDto> customerDtos=new ArrayList<>();

        for (Customer customer:entityList) {
            customerDtos.add(new CustomerDto(
                    customer.getCustomerId(),
                    customer.getCustomerName(),
                    customer.getCustomerAddress(),
                    customer.getCustomerContactNumber(),
                    customer.getCustomerEmail()

            ));
        }
        return customerDtos;
    }

    @Override
    public boolean updateCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException {
        return customerDao.update(new Customer(
                customerDto.getCustomerId(),
                customerDto.getCustomerName(),
                customerDto.getCustomerAddress(),
                customerDto.getCustomerContactNumber(),
                customerDto.getCustomerEmail()

        ));
    }

    public String generateID(){
        try {
            CustomerDto customerDto = customerDao.lastItem();
            if (customerDto != null) {
                String orderId = customerDto.getCustomerId();
                int num = Integer.parseInt(orderId.split("CU")[1]);
                num++;
                return String.format("CU%03d", num);
            } else {
                return "CU001";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


}
