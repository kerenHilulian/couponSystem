package com.kh.dao;

import com.kh.beans.Company;

import java.sql.SQLException;
import java.util.List;

public interface CompaniesDAO {
    int isCompanyExist(String email,String password) throws SQLException;
    void addCompany(Company company) throws SQLException;
    void updateCompany(Company company) throws SQLException;
    void deleteCompany(int companyId) throws SQLException;
    List<Company> getAllCompanies() throws SQLException;
    Company getOneCompany(int companyId) throws SQLException;
    boolean isNameOrEmailExist (String name, String email) throws SQLException;
}
