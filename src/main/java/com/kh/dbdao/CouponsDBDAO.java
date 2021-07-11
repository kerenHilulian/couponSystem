package com.kh.dbdao;

import com.kh.beans.Category;
import com.kh.beans.Coupon;
import com.kh.dao.CouponsDAO;
import com.kh.utils.DBUtils;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CouponsDBDAO implements CouponsDAO {

    private static final String QUERY_INSERT = "INSERT INTO `coupon-system`.`coupons` (`company_id`, `category_id`, `title`, `description`, `start_date`, `end_date`, `amount`, `price`, `image`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String QUERY_ADD_COUPON_PURCHASE = "INSERT INTO `coupon-system`.`customers_coupons` (`customer_id`, `coupon_id`) VALUES (?, ?);";;
    private static final String QUERY_UPDATE = "UPDATE `coupon-system`.`coupons` SET `company_id` = ?, `category_id` = ?, `title` = ?, `description` = ?, `start_date` = ?, `end_date` = ?, `amount` = ?, `price` = ?, `image` = ? WHERE (`id` = ?);";
    private static final String QUERY_DELETE = "DELETE FROM `coupon-system`.`coupons`  WHERE (`id` = ?);";
    private static final String QUERY_DELETE_COUPON_PURCHASE = "DELETE FROM `coupon-system`.`customers_coupons` WHERE (`customer_id` = ?) AND (`coupon_id` = ?);";
    private static final String QUERY_DELETE_COUPON_PURCHASE_BY_COUPON_ID = "DELETE FROM `coupon-system`.`customers_coupons` WHERE (`coupon_id` = ?);";
    private static final String QUERY_DELETE_COUPON_PURCHASE_BY_CUSTOMER_ID = "DELETE FROM `coupon-system`.`customers_coupons` WHERE (`customer_id` = ?);";
    private static final String QUERY_GET_ONE = "SELECT * FROM `coupon-system`.`coupons`  WHERE (`id` = ?);";
    private static final String QUERY_GET_ALL = "SELECT * FROM `coupon-system`.`coupons`";
    private static final String QUERY_GET_ALL_BY_COMPANY_ID = "SELECT * FROM `coupon-system`.`coupons` WHERE (`company_id` = ?);";
    private static final String QUERY_GET_ALL_COUPONS_BY_CATEGORY = "SELECT * FROM `coupon-system`.`coupons` WHERE (`company_id` = ?) AND (`category_id` = ?);";
    private static final String QUERY_GET_ALL_COUPONS_BY_MAX_PRICE = "SELECT * FROM `coupon-system`.`coupons` WHERE (`company_id` = ?) AND (`price` <= ?);";
    private static final String QUERY_GET_ALL_CUSTOMER_COUPONS = "SELECT * FROM `coupon-system`.`coupons` AS `coupons` INNER JOIN `coupon-system`.`customers_coupons` AS purchase ON coupons.id = purchase.coupon_id WHERE (purchase.customer_id = ?);";
    private static final String QUERY_GET_ALL_CUSTOMER_COUPONS_BY_CATEGORY = "SELECT * FROM `coupon-system`.`coupons` AS `coupons` INNER JOIN `coupon-system`.`customers_coupons` AS purchase ON coupons.id = purchase.coupon_id WHERE (purchase.customer_id = ?) AND (coupons.category_id = ?);";
    private static final String QUERY_GET_ALL_CUSTOMER_COUPONS_BY_MAX_PRICE = "SELECT * FROM `coupon-system`.`coupons` AS `coupons` INNER JOIN `coupon-system`.`customers_coupons` AS purchase ON coupons.id = purchase.coupon_id WHERE (purchase.customer_id = ?) AND (coupons.price <= ?) ORDER BY coupons.price;";
    private static final String QUERY_GET_ALL_EXPIRED_COUPONS = "SELECT * FROM `coupon-system`.`coupons` WHERE (`end_date` < ?);";
    private static final String QUERY_IS_CUSTOMER_PURCHASED_COUPON = "SELECT COUNT(*) FROM `coupon-system`.`customers_coupons` WHERE (`customer_id` = ?) AND (`coupon_id` = ?);";
    private static final String QUERY_TITLE_EXIST = "SELECT COUNT(*) FROM `coupon-system`.`coupons`  WHERE (`company_id` = ?) AND (`title` = ?);";


    @Override
    public void addCoupons(Coupon coupon) throws SQLException {
        Map<Integer,Object> map = new HashMap<>();
        map.put(1,coupon.getCompanyId());
        map.put(2,coupon.getCategoryId());
        map.put(3,coupon.getTitle());
        map.put(4,coupon.getDescription());
        map.put(5,coupon.getStartDate());
        map.put(6,coupon.getEndDate());
        map.put(7,coupon.getAmount());
        map.put(8,coupon.getPrice());
        map.put(9,coupon.getImage());

        DBUtils.runQuery(QUERY_INSERT,map);

    }

    @Override
    public void updateCoupons(Coupon coupon) throws SQLException {
        Map<Integer,Object>map = new HashMap<>();
        map.put(1,coupon.getCompanyId());
        map.put(2,coupon.getCategoryId());
        map.put(3,coupon.getTitle());
        map.put(4,coupon.getDescription());
        map.put(5,coupon.getStartDate());
        map.put(6,coupon.getEndDate());
        map.put(7,coupon.getAmount());
        map.put(8,coupon.getPrice());
        map.put(9,coupon.getImage());
        map.put(10,coupon.getId());
        DBUtils.runQuery(QUERY_UPDATE,map);
    }

    @Override
    public void deleteCoupons(int couponId) throws SQLException {
        Map<Integer,Object>map = new HashMap<>();
        map.put(1,couponId);
        DBUtils.runQuery(QUERY_DELETE,map);
    }

    @Override
    public List<Coupon> getAllCoupons() throws SQLException {
        List<Coupon> results = new ArrayList<>();
        ResultSet resultSet = DBUtils.runQueryWithResults(QUERY_GET_ALL);
        while (resultSet.next()){
            int id = resultSet.getInt(1);
            int companyId = resultSet.getInt(2);
            int categoryId = resultSet.getInt(3);
            String title = resultSet.getString(4);
            String description = resultSet.getString(5);
            Date startDate = resultSet.getDate(6);
            Date endDate = resultSet.getDate(7);
            int amount = resultSet.getInt(8);
            double price = resultSet.getDouble(9);
            String image = resultSet.getString(10);
            results.add(new Coupon(id,companyId,categoryId,title,description,startDate,endDate,amount,price,image));
        }
        return results;
    }

    @Override
    public Coupon getOneCoupons(int couponId) throws SQLException {
        Map<Integer,Object>map = new HashMap<>();
        map.put(1,couponId);
        ResultSet resultSet = DBUtils.runQueryWithResults(QUERY_GET_ONE,map);
        resultSet.next();
        int companyId = resultSet.getInt(2);
        int categoryId = resultSet.getInt(3);
        String title = resultSet.getString(4);
        String description = resultSet.getString(5);
        Date startDate = resultSet.getDate(6);
        Date endDate = resultSet.getDate(7);
        int amount = resultSet.getInt(8);
        double price = resultSet.getDouble(9);
        String image = resultSet.getString(10);
        return new Coupon(couponId,companyId,categoryId,title,description,startDate,endDate,amount,price,image);
    }

    @Override
    public void addCouponsPurchase(int customerId, int couponId) throws SQLException {
        Map<Integer,Object> map = new HashMap<>();
        map.put(1,customerId);
        map.put(2,couponId);
        DBUtils.runQuery(QUERY_ADD_COUPON_PURCHASE,map);

    }

    @Override
    public void deleteCouponsPurchase(int customerId, int couponId) throws SQLException {
        Map<Integer,Object>map = new HashMap<>();
        map.put(1,customerId);
        map.put(2,couponId);
        DBUtils.runQuery(QUERY_DELETE_COUPON_PURCHASE,map);

    }

    @Override
    public List<Coupon> getAllCouponsByCompanyId(int companyID) throws SQLException {
        List<Coupon> results = new ArrayList<>();
        Map<Integer,Object>map = new HashMap<>();
        map.put(1,companyID);
        ResultSet resultSet = DBUtils.runQueryWithResults(QUERY_GET_ALL_BY_COMPANY_ID,map);
        while (resultSet.next()){
            int id = resultSet.getInt(1);
            int companyId = resultSet.getInt(2);
            int categoryId = resultSet.getInt(3);
            String title = resultSet.getString(4);
            String description = resultSet.getString(5);
            Date startDate = resultSet.getDate(6);
            Date endDate = resultSet.getDate(7);
            int amount = resultSet.getInt(8);
            double price = resultSet.getDouble(9);
            String image = resultSet.getString(10);
            results.add(new Coupon(id,companyId,categoryId,title,description,startDate,endDate,amount,price,image));
        }
        return results;
    }

    @Override
    public void deleteCouponsCustomersByCouponId(int couponId) throws SQLException {
        Map<Integer,Object>map = new HashMap<>();
        map.put(1,couponId);
        DBUtils.runQuery(QUERY_DELETE_COUPON_PURCHASE_BY_COUPON_ID,map);
    }

    @Override
    public void deleteCouponsCustomersByCustomerId(int customerID) throws SQLException {
        Map<Integer,Object>map = new HashMap<>();
        map.put(1,customerID);
        DBUtils.runQuery(QUERY_DELETE_COUPON_PURCHASE_BY_CUSTOMER_ID,map);
    }

    @Override
    public boolean isTitleExist(int companyId, String title) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, companyId);
        map.put(2, title);
        ResultSet resultSet = DBUtils.runQueryWithResults(QUERY_TITLE_EXIST, map);
        resultSet.next();
        return !(resultSet.getInt(1) == 1);

    }

    @Override
    public List<Coupon> getAllCouponsByCategory(int companyId, Category category) throws SQLException {
        List<Coupon> results = new ArrayList<>();
        Map<Integer,Object>map = new HashMap<>();
        map.put(1,companyId);
        map.put(2,category.value);
        ResultSet resultSet = DBUtils.runQueryWithResults(QUERY_GET_ALL_COUPONS_BY_CATEGORY,map);
        while (resultSet.next()){
            try {
                int id = resultSet.getInt(1);
                String title = resultSet.getString(4);
                String description = resultSet.getString(5);
                Date startDate = resultSet.getDate(6);
                Date endDate = resultSet.getDate(7);
                int amount = resultSet.getInt(8);
                Double price = resultSet.getDouble(9);
                String image = resultSet.getString(10);
                Coupon coupon = new Coupon(id, companyId, category.value, title, description, startDate, endDate, amount, price, image);
                results.add(coupon);
        } catch (Exception e) {
                System.out.println(e.getMessage());
        }
    }

        return results;
    }

    @Override
    public List<Coupon> getAllCouponsByMaxPrice(int companyId, Double maxPrise) throws SQLException {
        List<Coupon> coupons = new ArrayList<>();
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, companyId);
        map.put(2, maxPrise);

        ResultSet resultSet = DBUtils.runQueryWithResults(QUERY_GET_ALL_COUPONS_BY_MAX_PRICE, map);
        while (resultSet.next()) {
            try {
                int id = resultSet.getInt(1);
                Category category = Category.getCategory(resultSet.getInt(3));
                String title = resultSet.getString(4);
                String description = resultSet.getString(5);
                Date startDate = resultSet.getDate(6);
                Date endDate = resultSet.getDate(7);
                int amount = resultSet.getInt(8);
                Double price = resultSet.getDouble(9);
                String image = resultSet.getString(10);
                Coupon coupon = new Coupon(id, companyId, category.value, title, description, startDate, endDate, amount, price, image);
                coupons.add(coupon);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return coupons;
    }

    @Override
    public boolean isCustomerPurchaseCouponAlready(int customerId, int couponId) throws SQLException {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, customerId);
        map.put(2, couponId);
        ResultSet resultSet = DBUtils.runQueryWithResults(QUERY_IS_CUSTOMER_PURCHASED_COUPON, map);
        resultSet.next();
        return !(resultSet.getInt(1) == 1);
    }

    @Override
    public List<Coupon> getAllCouponsByCustomerId(int customerId) throws SQLException {
        List<Coupon> coupons = new ArrayList<>();
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, customerId);

        ResultSet resultSet = DBUtils.runQueryWithResults(QUERY_GET_ALL_CUSTOMER_COUPONS, map);
        while (resultSet.next()) {
            try {
                int id = resultSet.getInt(1);
                int companyId = resultSet.getInt(2);
                Category category = Category.getCategory(resultSet.getInt(3));
                String title = resultSet.getString(4);
                String description = resultSet.getString(5);
                Date startDate = resultSet.getDate(6);
                Date endDate = resultSet.getDate(7);
                int amount = resultSet.getInt(8);
                Double price = resultSet.getDouble(9);
                String image = resultSet.getString(10);
                Coupon coupon = new Coupon(id, companyId, category.value, title, description, startDate, endDate, amount, price, image);
                coupons.add(coupon);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return coupons;
    }

    @Override
    public List<Coupon> getAllCouponsCustomerByCategory(int customerId, Category category) throws SQLException {
        List<Coupon> coupons = new ArrayList<>();
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, customerId);
        map.put(2, category.value);

        ResultSet resultSet = DBUtils.runQueryWithResults(QUERY_GET_ALL_CUSTOMER_COUPONS_BY_CATEGORY, map);
        while (resultSet.next()) {
            try {
                int id = resultSet.getInt(1);
                int companyId = resultSet.getInt(2);
                String title = resultSet.getString(4);
                String description = resultSet.getString(5);
                Date startDate = resultSet.getDate(6);
                Date endDate = resultSet.getDate(7);
                int amount = resultSet.getInt(8);
                Double price = resultSet.getDouble(9);
                String image = resultSet.getString(10);
                Coupon coupon = new Coupon(id, companyId, category.value, title, description, startDate, endDate, amount, price, image);
                coupons.add(coupon);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return coupons;
    }



    @Override
    public List<Coupon> getAllCouponsCustomerByMaxPrice(int customerId, double maxPrice) throws SQLException {
        List<Coupon> coupons = new ArrayList<>();
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, customerId);
        map.put(2, maxPrice);

        ResultSet resultSet = DBUtils.runQueryWithResults(QUERY_GET_ALL_CUSTOMER_COUPONS_BY_MAX_PRICE, map);
        while (resultSet.next()) {
            try {
                int id = resultSet.getInt(1);
                int companyId = resultSet.getInt(2);
                Category category = Category.getCategory(resultSet.getInt(3));
                String title = resultSet.getString(4);
                String description = resultSet.getString(5);
                Date startDate = resultSet.getDate(6);
                Date endDate = resultSet.getDate(7);
                int amount = resultSet.getInt(8);
                Double price = resultSet.getDouble(9);
                String image = resultSet.getString(10);
                Coupon coupon = new Coupon(id, companyId, category.value, title, description, startDate, endDate, amount, price, image);
                coupons.add(coupon);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return coupons;
    }

    @Override
    public List<Coupon> getAllExpiredCoupons() throws SQLException {
        List<Coupon> coupons = new ArrayList<>();
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, Date.valueOf(LocalDate.now()));

        ResultSet resultSet = DBUtils.runQueryWithResults(QUERY_GET_ALL_EXPIRED_COUPONS, map);
        while (resultSet.next()) {
            try {
                int id = resultSet.getInt(1);
                int companyId = resultSet.getInt(2);
                Category category = Category.getCategory(resultSet.getInt(3));
                String title = resultSet.getString(4);
                String description = resultSet.getString(5);
                Date startDate = resultSet.getDate(6);
                Date endDate = resultSet.getDate(7);
                int amount = resultSet.getInt(8);
                Double price = resultSet.getDouble(9);
                String image = resultSet.getString(10);
                Coupon coupon = new Coupon(id, companyId, category.value, title, description, startDate, endDate, amount, price, image);
                coupons.add(coupon);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return coupons;
    }
}
