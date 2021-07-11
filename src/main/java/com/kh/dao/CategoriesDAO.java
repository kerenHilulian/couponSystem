package com.kh.dao;

import com.kh.beans.Category;
import com.kh.beans.Company;

import java.sql.SQLException;
import java.util.List;

public interface CategoriesDAO {
    void addCategory(Category category) throws SQLException;
    void updateCategory(Category category) throws SQLException;
    void deleteCategory(int categoryId) throws SQLException;
    List<Category> getAllCategories() throws SQLException;
    Category getOneCategory(int categoryId) throws SQLException;
}
