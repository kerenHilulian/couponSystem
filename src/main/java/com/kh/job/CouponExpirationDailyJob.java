package com.kh.job;

import com.kh.beans.Coupon;
import com.kh.dao.CouponsDAO;
import com.kh.dbdao.CouponsDBDAO;

import java.util.List;

public class CouponExpirationDailyJob extends Thread{

    private CouponsDAO couponsDAO = new CouponsDBDAO();
    private static volatile boolean quit;

    @Override
    public void run() {

        while (!quit) {
            try {
                List<Coupon> expiredCoupons = this.couponsDAO.getAllExpiredCoupons();
                for (Coupon c : expiredCoupons) {
                    this.couponsDAO.deleteCouponsCustomersByCouponId(c.getId());
                    this.couponsDAO.deleteCoupons(c.getId());
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            try {
                Thread.sleep(1000 );
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void bye() {
        quit = true;
    }

}
