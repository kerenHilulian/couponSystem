package com.kh.dbdao;

import com.kh.beans.Company;
import com.kh.beans.Customer;
import com.kh.dao.CustomersDAO;
import com.kh.utils.DBUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomersDBDAO implements CustomersDAO {

    private static final String QUERY_INSERT = "INSERT INTO `coupon-system`.`customers` (`first_name`, `last_name`, `email`, `password`) VALUES (?, ?, ?, ?);\n";
    private static final String QUERY_UPDATE = "UPDATE `coupon-system`.`customers` SET `first_name` = ?, `last_name` = ?, `email` = ?, `password` = ? WHERE (`id` = ?);";
    private static final String QUERY_DELETE = "DELETE FROM `coupon-system`.`customers`  WHERE (`id` = ?);";
    private static final String QUERY_GET_ONE = "SELECT * FROM `coupon-system`.`customers`  WHERE (`id` = ?);";
    private static final String QUERY_GET_ALL = "SELECT * FROM `coupon-system`.`customers`";
    private static final String QUERY_IS_CUSTOMER_EXIST = "SELECT * FROM `coupon-system`.`customers`  WHERE (`email` = ?) and (`password` = ?);\n ";
    private static final String QUERY_IS_EMAIL_EXIST = "SELECT COUNT(*) FROM `coupon-system`.`customers` WHERE (`email` = ?);";


    @Override
    public int isCustomerExist(String email, String password) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, email);
        map.put(2, password);
        ResultSet resultSet = DBUtils.runQueryWithResults(QUERY_IS_CUSTOMER_EXIST, map);
        int id = 0;
        resultSet.next();
        try {
            id = resultSet.getInt(1);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return id;

    }

    @Override
    public int isCustomersExist(String email, String password) throws SQLException {
        return 0;
    }

    @Override
    public void addCustomers(Customer customer) throws SQLException {
        Map<Integer,Object> map = new HashMap<>();
        map.put(1,customer.getFirstName());
        map.put(2,customer.getLastName());
        map.put(3,customer.getEmail());
        map.put(4,customer.getPassword());
        DBUtils.runQuery(QUERY_INSERT,map);

    }

    @Override
    public void updateCustomers(Customer customer) throws SQLException {
        Map<Integer,Object>map = new HashMap<>();
        map.put(1,customer.getFirstName());
        map.put(2,customer.getLastName());
        map.put(3,customer.getEmail());
        map.put(4,customer.getPassword());
        map.put(5,customer.getId());
        DBUtils.runQuery(QUERY_UPDATE,map);

    }

    @Override
    public void deleteCustomer(int customerId) throws SQLException {
        Map<Integer,Object>map = new HashMap<>();
        map.put(1,customerId);
        DBUtils.runQuery(QUERY_DELETE,map);

    }

    @Override
    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> results = new ArrayList<>();
        ResultSet resultSet = DBUtils.runQueryWithResults(QUERY_GET_ALL);
        while (resultSet.next()){
            int id = resultSet.getInt(1);
            String firsName = resultSet.getString(2);
            String lastName = resultSet.getString(3);
            String email = resultSet.getString(4);
            String password = resultSet.getString(5);
            results.add(new Customer(id,firsName,lastName,email,password));
        }
        return results;
    }

    @Override
    public Customer getOneCustomers(int customerId) throws SQLException {
        Map<Integer,Object>map = new HashMap<>();
        map.put(1,customerId);
        ResultSet resultSet = DBUtils.runQueryWithResults(QUERY_GET_ONE,map);
        resultSet.next();
        String firstName = resultSet.getString(2);
        String lastName = resultSet.getString(3);
        String email = resultSet.getString(4);
        String password = resultSet.getString(5);
        return new Customer(customerId,firstName,lastName,email,password);
    }

    @Override
    public boolean isEmailExist(String email) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, email);
        ResultSet resultSet = DBUtils.runQueryWithResults(QUERY_IS_EMAIL_EXIST, map);
        resultSet.next();
        return !(resultSet.getInt(1) == 1);
    }


}
