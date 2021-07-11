package com.kh.facades;

import com.kh.beans.Coupon;
import com.kh.beans.Customer;
import com.kh.dao.CompaniesDAO;
import com.kh.dao.CouponsDAO;
import com.kh.dao.CustomersDAO;
import com.kh.dbdao.CompaniesDBDAO;
import com.kh.dbdao.CouponsDBDAO;
import com.kh.dbdao.CustomersDBDAO;

import java.sql.SQLException;
import java.util.List;

public abstract class  ClientFacade {
    protected CompaniesDAO companiesDAO;
    protected CustomersDAO customersDAO;
    protected CouponsDAO couponsDAO;


    public ClientFacade() {
        companiesDAO = new CompaniesDBDAO();
        customersDAO = new CustomersDBDAO();
        couponsDAO = new CouponsDBDAO();

    }

    public abstract boolean login (String email, String password) throws SQLException;
}
