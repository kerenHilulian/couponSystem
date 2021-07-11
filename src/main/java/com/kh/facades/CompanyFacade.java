package com.kh.facades;

import com.kh.beans.Category;
import com.kh.beans.Company;
import com.kh.beans.Coupon;
import com.kh.exceptions.CouponSystemException;

import java.sql.SQLException;
import java.util.List;

public class CompanyFacade extends ClientFacade {

    private int companyId;

    public CompanyFacade() {
        super();
    }

    @Override
    public boolean login(String email, String password) throws SQLException {
        setCompanyId(this.companiesDAO.isCompanyExist(email, password));
        return companyId > 0;
    }

    public void addCoupon(Coupon coupon) throws CouponSystemException, SQLException {

        if (!this.couponsDAO.isTitleExist(companyId, coupon.getTitle())) {
            throw new CouponSystemException("Sorry title already exist...");
        }

        this.couponsDAO.addCoupons(coupon);
    }

    public void updateCoupon(Coupon coupon) throws SQLException, CouponSystemException {
        Coupon couponDB = this.couponsDAO.getOneCoupons(coupon.getId());

        if (couponDB == null) {
            throw new CouponSystemException("Sorry coupon already exist...");
        }

        if (companyId != couponDB.getCompanyId()) {
            throw new CouponSystemException("Sorry can not update this companyId...");
        }

        this.couponsDAO.updateCoupons(coupon);
    }


    public List<Coupon> getCompanyCoupons() throws SQLException {
        return this.couponsDAO.getAllCouponsByCompanyId(companyId);
    }

    public void deleteCoupon(int couponId) throws CouponSystemException, SQLException {
        Coupon couponDB = this.couponsDAO.getOneCoupons(couponId);

        if (couponDB == null) {
            throw new CouponSystemException("Sorry couponId not exist...");
        }

        if (companyId != couponDB.getCompanyId()) {
            throw new CouponSystemException("Sorry Invalid couponId...");
        }

        this.couponsDAO.deleteCouponsCustomersByCouponId(couponId);
        this.couponsDAO.deleteCoupons(couponId);
    }

    public List<Coupon> getCompanyCouponsByCategory(Category category) throws  SQLException {
        return this.couponsDAO.getAllCouponsByCategory(companyId, category);
    }

    public List<Coupon> getCompanyCouponsByMaxPrise(Double maxPrise) throws  SQLException {
        return this.couponsDAO.getAllCouponsByMaxPrice(companyId, maxPrise);
    }

    public Company getCompanyDetails() throws  SQLException {
        return this.companiesDAO.getOneCompany(getCompanyId());
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

}


