package com.kh.dao;

import com.kh.beans.Category;
import com.kh.beans.Coupon;
import com.kh.beans.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CouponsDAO {

    void addCoupons(Coupon coupon) throws SQLException;
    void updateCoupons(Coupon coupon) throws SQLException;
    void deleteCoupons(int couponId) throws SQLException;
    List<Coupon> getAllCoupons() throws SQLException;
    Coupon getOneCoupons(int couponId) throws SQLException;
    void addCouponsPurchase(int customerId , int couponId) throws SQLException;
    void deleteCouponsPurchase(int customerId , int couponId) throws SQLException;
    List<Coupon> getAllCouponsByCompanyId(int companyID) throws SQLException;
    void deleteCouponsCustomersByCouponId(int id) throws SQLException;
    void deleteCouponsCustomersByCustomerId(int customerID) throws SQLException;
    boolean isTitleExist(int companyId, String title) throws SQLException;
    List<Coupon> getAllCouponsByCategory(int companyId, Category category) throws SQLException;
    List<Coupon> getAllCouponsByMaxPrice(int companyId, Double maxPrise) throws SQLException;
    boolean isCustomerPurchaseCouponAlready(int customerId, int couponId) throws SQLException;
    List<Coupon> getAllCouponsByCustomerId(int customerId) throws SQLException;
    List<Coupon> getAllCouponsCustomerByCategory(int customerId, Category category) throws SQLException;
    List<Coupon> getAllCouponsCustomerByMaxPrice(int customerId, double maxPrice) throws SQLException;
    List<Coupon> getAllExpiredCoupons() throws SQLException;
}
