package com.kh.dao;

import com.kh.beans.Company;
import com.kh.beans.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomersDAO {
    int isCustomersExist(String email,String password) throws SQLException;
    void addCustomers( Customer customer) throws SQLException;
    void updateCustomers(Customer customer) throws SQLException;
    void deleteCustomer(int customerId) throws SQLException;
    List<Customer> getAllCustomers() throws SQLException;
    Customer getOneCustomers(int customerId) throws SQLException;
    boolean isEmailExist(String email) throws SQLException;
    int isCustomerExist(String email, String password) throws SQLException;
}
