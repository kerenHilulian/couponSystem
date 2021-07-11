package com.kh.facades;

import com.kh.beans.Company;
import com.kh.beans.Coupon;
import com.kh.beans.Customer;
import com.kh.exceptions.CouponSystemException;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

public class AdminFacade extends ClientFacade{

    public AdminFacade() {
        super();
    }

    @Override
    public boolean login(String email, String password) {
        return email.equals("admin@admin.com") && password.equals("admin");
    }

    public void addCompany(Company company) throws CouponSystemException, SQLException {

        if(!this.companiesDAO.isNameOrEmailExist(company.getName(),company.getEmail())){
            throw new CouponSystemException("Sorry name or email already exist...");
        }

        this.companiesDAO.addCompany(company);
    }

    public void updateCompany(Company company) throws SQLException, CouponSystemException {

        Company companyDB =companiesDAO.getOneCompany((company.getId()));
        if(companyDB == null){
            throw new CouponSystemException("cant update not exist id..");
        }

        if (!companyDB.getName().equals(company.getName())){
            throw new CouponSystemException("cant update name with existing company name..");
        }

        this.companiesDAO.updateCompany(company);

    }

    public void deleteCompany(int companyID) throws SQLException {

        List<Coupon> coupons = this.couponsDAO.getAllCouponsByCompanyId(companyID);
        // select coupon id with same company
        for (Coupon c:coupons) {
            this.couponsDAO.deleteCouponsCustomersByCouponId(c.getId());
            this.couponsDAO.deleteCoupons(c.getId());
        }
        this.companiesDAO.deleteCompany(companyID);

    }

    public List<Company> getAllCompanies() throws SQLException {
        return this.companiesDAO.getAllCompanies();
    }

    public Company getOneCompany(int companyId) throws SQLException {
        return this.companiesDAO.getOneCompany(companyId);
    }

    public void addNewCustomer(Customer customer) throws CouponSystemException, SQLException {
        if(!this.customersDAO.isEmailExist(customer.getEmail())){
            throw new CouponSystemException("Sorry email already exist...");
        }

        this.customersDAO.addCustomers(customer);

    }

    public void updateCustomer(Customer customer) throws SQLException, CouponSystemException {

        Customer customerDB =customersDAO.getOneCustomers((customer.getId()));
        if(customerDB == null){
            throw new CouponSystemException("cant update not exist id..");
        }
        this.customersDAO.updateCustomers(customer);
    }

    public void deleteCustomer(int customerID) throws SQLException {

        this.couponsDAO.deleteCouponsCustomersByCustomerId(customerID);
        this.customersDAO.deleteCustomer(customerID);

    }

    public List<Customer> getAllCustomers() throws SQLException {
        return this.customersDAO.getAllCustomers();
    }

    public Customer getOneCustomer(int customerId) throws SQLException {
        return this.customersDAO.getOneCustomers(customerId);
    }

}
