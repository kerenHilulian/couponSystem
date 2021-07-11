package com.kh.dbdao;

import com.kh.beans.Company;
import com.kh.dao.CompaniesDAO;
import com.kh.utils.DBUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompaniesDBDAO implements CompaniesDAO {
    private static final String QUERY_INSERT = "INSERT INTO `coupon-system`.`companies` (`name`, `email`, `password`) VALUES (?, ?, ?);";
    private static final String QUERY_UPDATE = "UPDATE `coupon-system`.`companies`  SET `name` =?, `email` = ?, `password` = ? WHERE (`id` = ?);\n";
    private static final String QUERY_DELETE = "DELETE FROM `coupon-system`.`companies`  WHERE (`id` = ?);";
    private static final String QUERY_GET_ONE = "SELECT * FROM `coupon-system`.`companies`  WHERE (`id` = ?);";
    private static final String QUERY_GET_ALL = "SELECT * FROM `coupon-system`.`companies`";
    private static final String QUERY_IS_COMPANY_EXIST = "SELECT `id` FROM `coupon-system`.`companies`  WHERE (`email` = ?) and (`password` = ?);\n";
    private static final String QUERY_NAME_OR_EMAIL_EXIST = "SELECT COUNT(*) FROM `coupon-system`.`companies`  WHERE (`name` = ?) and (`email` = ?);\n ";
    @Override
    public int isCompanyExist(String email, String password) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, email);
        map.put(2, password);
        ResultSet resultSet = DBUtils.runQueryWithResults(QUERY_IS_COMPANY_EXIST, map);
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
    public void addCompany(Company company) throws SQLException {
        Map<Integer,Object> map = new HashMap<>();
        map.put(1,company.getName());
        map.put(2,company.getEmail());
        map.put(3,company.getPassword());
        DBUtils.runQuery(QUERY_INSERT,map);

    }

    @Override
    public void updateCompany(Company company) throws SQLException {
        Map<Integer,Object>map = new HashMap<>();
        map.put(1,company.getName());
        map.put(2,company.getEmail());
        map.put(3,company.getPassword());
        map.put(4,company.getId());
        DBUtils.runQuery(QUERY_UPDATE,map);

    }

    @Override
    public void deleteCompany(int companyId) throws SQLException {
        Map<Integer,Object>map = new HashMap<>();
        map.put(1,companyId);
        DBUtils.runQuery(QUERY_DELETE,map);

    }

    @Override
    public List<Company> getAllCompanies() throws SQLException {
        List<Company> results = new ArrayList<>();
        ResultSet resultSet = DBUtils.runQueryWithResults(QUERY_GET_ALL);
        while (resultSet.next()){
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String email = resultSet.getString(3);
            String password = resultSet.getString(4);
            results.add(new Company(id,name,email,password));
        }
        return results;
    }


    @Override
    public Company getOneCompany(int companyId) throws SQLException {
        Map<Integer,Object>map = new HashMap<>();
        map.put(1,companyId);
        ResultSet resultSet = DBUtils.runQueryWithResults(QUERY_GET_ONE,map);
        resultSet.next();
        String name = resultSet.getString(2);
        String email = resultSet.getString(3);
        String password = resultSet.getString(4);
        return new Company(companyId,name,email,password);
    }

    @Override
    public boolean isNameOrEmailExist(String name, String email) throws SQLException {

        Map<Integer, Object> map = new HashMap<>();
        map.put(1, name);
        map.put(2, email);
        ResultSet resultSet = DBUtils.runQueryWithResults(QUERY_NAME_OR_EMAIL_EXIST, map);
        resultSet.next();
        return (resultSet.getInt(1) == 0);

    }
}
