package com.kh.facades;

import com.kh.beans.Category;
import com.kh.beans.Coupon;
import com.kh.beans.Customer;
import com.kh.exceptions.CouponSystemException;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class CustomerFacade extends ClientFacade{

    private int customerId;

    public CustomerFacade() {
        super();
    }

    @Override
    public boolean login(String email, String password) throws SQLException {
        setCustomerId(this.customersDAO.isCustomerExist(email, password));
        return customerId > 0;
    }

    public void purchaseCoupon(Coupon coupon) throws SQLException, CouponSystemException {

        Coupon fromDB = this.couponsDAO.getOneCoupons(coupon.getId());
        if (fromDB == null) {
            throw new CouponSystemException("Sorry coupon does not exist...");
        }

        if (customerId == 0) {
            throw new CouponSystemException("Sorry customer does not exist...");        }

        if (!this.couponsDAO.isCustomerPurchaseCouponAlready(customerId, coupon.getId())) {
            throw new CouponSystemException("Sorry the customer has already purchased the coupon");        }

        if (fromDB.getAmount() == 0) {
            throw new CouponSystemException("Sorry coupon then out of stock");        }

        if (fromDB.getEndDate().before(Date.valueOf(LocalDate.now()))) {
            throw new CouponSystemException("Sorry the coupon has expired");        }

        fromDB.setAmount(fromDB.getAmount() - 1);
        this.couponsDAO.updateCoupons(fromDB);
        this.couponsDAO.addCouponsPurchase(customerId,fromDB.getId());

    }

    public List<Coupon> getCustomerCoupon() throws  SQLException {
        return this.couponsDAO.getAllCouponsByCustomerId(customerId);
    }

    public List<Coupon> getCustomerCoupon(Category category) throws  SQLException {
        return this.couponsDAO.getAllCouponsCustomerByCategory(customerId, category);
    }

    public List<Coupon> getCustomerCoupon(double maxPrice) throws  SQLException {
        return this.couponsDAO.getAllCouponsCustomerByMaxPrice(customerId, maxPrice);
    }

    public Customer getCustomerDetails() throws  SQLException {
        return this.customersDAO.getOneCustomers(getCustomerId());
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
