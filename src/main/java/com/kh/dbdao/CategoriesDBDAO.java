package com.kh.dbdao;

import com.kh.beans.Category;
import com.kh.beans.Company;
import com.kh.dao.CategoriesDAO;
import com.kh.utils.DBUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoriesDBDAO implements CategoriesDAO {

    private static final String QUERY_INSERT = "INSERT INTO `coupon-system`.`categories` (`name`) VALUES (?);";

    @Override
    public void addCategory(Category category) throws SQLException {
        Map<Integer,Object> map = new HashMap<>();
        map.put(1,category.name());
        DBUtils.runQuery(QUERY_INSERT,map);
    }

    @Override
    public void updateCategory(Category category) throws SQLException {

    }

    @Override
    public void deleteCategory(int categoryId) throws SQLException {

    }

    @Override
    public List<Category> getAllCategories() throws SQLException {
        return null;
    }

    @Override
    public Category getOneCategory(int categoryId) throws SQLException {
        return null;
    }

}
