package com.kh.playground;
import com.kh.beans.Category;
import com.kh.beans.Company;
import com.kh.beans.Coupon;
import com.kh.beans.Customer;
import com.kh.dao.CompaniesDAO;
import com.kh.dao.CouponsDAO;
import com.kh.dao.CustomersDAO;
import com.kh.db.DatabaseManager;
import com.kh.dbdao.CompaniesDBDAO;
import com.kh.dbdao.CouponsDBDAO;
import com.kh.dbdao.CustomersDBDAO;
import com.kh.facades.AdminFacade;
import com.kh.facades.ClientFacade;
import com.kh.facades.CompanyFacade;
import com.kh.facades.CustomerFacade;
import com.kh.job.CouponExpirationDailyJob;
import com.kh.security.ClientType;
import com.kh.security.LoginManager;
import com.kh.utils.ArtUtils;
import com.kh.utils.TablePrinter;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) {

        CouponsDAO couponsDAO = new CouponsDBDAO();
        CompaniesDAO companiesDAO = new CompaniesDBDAO();
        CustomersDAO customersDAO = new CustomersDBDAO();

        System.out.println("START");


        try {
            DatabaseManager.dropAndCreate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        /////////////////////////START TEST DELETE EXPIRED COUPONS/////////////////////////////

        Thread thread = new CouponExpirationDailyJob();
        thread.start();

        //////////////////////////////////////////////////////////////////////////////

        System.out.println("DELETE EXPIRED COUPONS");

        System.out.println(ArtUtils.INSERT);
        Company companyTest = new Company("Biga", "biga@gmail.com", "biga777");
        try {
            companiesDAO.addCompany(companyTest);
            TablePrinter.print(companiesDAO.getOneCompany(1));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Customer customerTest = new Customer("efrat", "saar", "efi@gmail.com", "12345");
        try {
            customersDAO.addCustomers(customerTest);
            TablePrinter.print(customersDAO.getOneCustomers(1));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Coupon couponTest = new Coupon(1, 1, "Asian", "Sushi roll",
                Date.valueOf(LocalDate.now().minusDays(3)), Date.valueOf(LocalDate.now().minusDays(1)), 30, 34.9, "biga.com");
        try {
            couponsDAO.addCoupons(couponTest);
            TablePrinter.print(couponsDAO.getOneCoupons(1));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            couponsDAO.addCouponsPurchase(1, 1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

///////////////////////////////////COMPANIES/////////////////////////////////
        System.out.println("***** COMPANIES *****" );
        System.out.println(ArtUtils.INSERT);

        Company company1 = new Company("kaza", "kaza@.com", "1234");
        Company company2 = new Company("doron", "doron@.com", "2222");
        Company company3 = new Company("etzmale", "ezt@.com", "3333");
        Company company4 = new Company("tali", "tali@.com", "4444");

        try {
            companiesDAO.addCompany(company1);
            companiesDAO.addCompany(company2);
            companiesDAO.addCompany(company3);
            companiesDAO.addCompany(company4);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            TablePrinter.print(companiesDAO.getAllCompanies());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("----------if_company_exist------tali@.com /4444----");
        try {
            if(companiesDAO.isCompanyExist("tali@.com","4444") > 0){
                System.out.println("The company exists");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("----------if_company_exist------efi@.com /4422----");
        try {
            if(companiesDAO.isCompanyExist("efi@.com","4422") > 0){
                System.out.println("The company exists");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }

        System.out.println(ArtUtils.UPDATE);


        try {
            Company companyUpdate = companiesDAO.getOneCompany(3);
            companyUpdate.setEmail("dordor@gmail.com");
            companiesDAO.updateCompany(companyUpdate);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println(companiesDAO.getOneCompany(3));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println(ArtUtils.DELETE);
        try {
            companiesDAO.deleteCompany(5);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            companiesDAO.getAllCompanies().forEach(System.out::println);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        ///////////////////////////////////CUSTOMERS/////////////////////////////////
        System.out.println("***** CUSTOMERS *****" );
        System.out.println(ArtUtils.INSERT);

        Customer customer1 = new Customer("keren", "hilel", "keren@.com", "1111");
        Customer customer2 = new Customer("michael", "hilel", "mmm@.com", "2222");
        Customer customer3 = new Customer("taliya", "ser", "ttt@.com", "3333");
        Customer customer4 = new Customer("natan", "mot", "natan@.com", "4444");

        try {
            customersDAO.addCustomers(customer1);
            customersDAO.addCustomers(customer2);
            customersDAO.addCustomers(customer3);
            customersDAO.addCustomers(customer4);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            TablePrinter.print(customersDAO.getAllCustomers());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("----------if_customer_exist------keren@.com /1111----");
        try {
            if (customersDAO.isCustomerExist("keren@.com", "1111") > 0) {
                System.out.println("The customer exists");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("----------if_customer_exist------roy@.com /1212----");

        try {
            if (customersDAO.isCustomerExist("roy@.com", "1212") > 0) {
                System.out.println("The customer exists");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println(ArtUtils.UPDATE);
        Customer customerUp = null;
        try {
            customerUp = customersDAO.getOneCustomers((2));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        customerUp.setFirstName("nofar");
        customerUp.setEmail("nofar@.com");
        try {
            customersDAO.updateCustomers(customerUp);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            TablePrinter.print(customersDAO.getOneCustomers(2));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(ArtUtils.DELETE);
        try {
            customersDAO.deleteCustomer(5);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            TablePrinter.print(customersDAO.getAllCustomers());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        ///////////////////////////////////COUPONS/////////////////////////////////
        System.out.println("***** COUPONS *****" );
        System.out.println(ArtUtils.INSERT);

        Coupon c1 = new Coupon(
                2,
                2,
                "kaza",
                "furniture",
                Date.valueOf(LocalDate.now()),
                Date.valueOf(LocalDate.now().plusDays(12)),
                200,
                99.99,
                "bla.com");

        Coupon c2 = new Coupon(
                3,
                2,
                "gaza",
                "furniture",
                Date.valueOf(LocalDate.now()),
                Date.valueOf(LocalDate.now().plusDays(12)),
                400,
                200,
                "blabla.com");

        Coupon c3 = new Coupon(
                4,
                5,
                "spa",
                "masag",
                Date.valueOf(LocalDate.now()),
                Date.valueOf(LocalDate.now().plusDays(40)),
                400,
                99.9,
                "msg.com");
        Coupon coupon4 = new Coupon(
                3,
                4,
                "GAME",
                "TETRIS",
                Date.valueOf(LocalDate.now()),
                Date.valueOf(LocalDate.now().plusDays(15)),
                70,
                39.9,
                "tet.com");


        try {
            couponsDAO.addCoupons(c1);
            couponsDAO.addCoupons(c2);
            couponsDAO.addCoupons(c3);
            couponsDAO.addCoupons(coupon4);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            TablePrinter.print(couponsDAO.getAllCoupons());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(ArtUtils.UPDATE);
        Coupon couponUpdate = null;
        try {
            couponUpdate = couponsDAO.getOneCoupons(2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        couponUpdate.setPrice(50.50);
        couponUpdate.setAmount(100);
        try {
            couponsDAO.updateCoupons(couponUpdate);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            TablePrinter.print(couponsDAO.getAllCoupons());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println(ArtUtils.DELETE);
        try {
            couponsDAO.deleteCoupons(5);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            TablePrinter.print(couponsDAO.getAllCoupons());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        ///////////////////////////////////CUSTOMERS-COUPONS/////////////////////////////////
        System.out.println("***** CUSTOMERS-COUPONS *****" );

        System.out.println(ArtUtils.INSERT);
        try {
            couponsDAO.addCouponsPurchase(2,2);
            couponsDAO.addCouponsPurchase(2,3);
            couponsDAO.addCouponsPurchase(2,4);
            couponsDAO.addCouponsPurchase(3,4);
            couponsDAO.addCouponsPurchase(3,2);
            couponsDAO.addCouponsPurchase(4,3);
            couponsDAO.addCouponsPurchase(4,4);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println(ArtUtils.DELETE);
        try {
            couponsDAO.deleteCouponsPurchase(4,4);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        ///////////////////////////////////ADMIN FACADE/////////////////////////////////
        System.out.println( "----------------------------------------");
        System.out.println( "--------ADMIN FACADE--------------------");
        System.out.println( "----------------------------------------");

        AdminFacade adminFacade = new AdminFacade();

        System.out.println("LOGIN TEST /admin@admin.com/an ");

        if( adminFacade.login("admin@admin.com","an")){
            System.out.println("ADMIN LOGIN");
        } else {
            System.out.println("UNSUCCSSEFUL LOGIN");
        }

        System.out.println("LOGIN TEST /admin@admin.com/admin ");

        if( adminFacade.login("admin@admin.com","admin")){
            System.out.println("ADMIN LOGIN");
        } else {
            System.out.println("UNSUCCSSEFUL LOGIN");
        }

        System.out.println("****COMPANIES****");
        System.out.println(ArtUtils.INSERT);
        Company company5 = new Company("nike", "nike@.com", "4444");
        Company company6 = new Company("adidas", "adidas@.com", "3456");

        try {
            adminFacade.addCompany(company5);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            adminFacade.addCompany(company6);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            TablePrinter.print(adminFacade.getAllCompanies());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println(ArtUtils.UPDATE);

        Company comToUpdate = null;
        try {
            comToUpdate = adminFacade.getOneCompany(6);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        comToUpdate.setEmail("nikenew@gmail.com");
        comToUpdate.setPassword("e344");

        try {
            adminFacade.updateCompany(comToUpdate);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            TablePrinter.print(adminFacade.getAllCompanies());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println(ArtUtils.DELETE);
        try {
            adminFacade.deleteCompany(4);
            TablePrinter.print(couponsDAO.getAllCoupons());
            TablePrinter.print(adminFacade.getAllCompanies());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("****CUSTOMERS****");
        System.out.println(ArtUtils.INSERT);

        Customer customer5 = new Customer("keren", "hilel", "keren@.com", "kk123");
        Customer customer6 = new Customer("rut", "gros", "ttt@.com", "7777");

        try {
            adminFacade.addNewCustomer(customer5);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            adminFacade.addNewCustomer(customer6);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            TablePrinter.print(adminFacade.getAllCustomers());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(ArtUtils.UPDATE);

        Customer customerToUpdate = null;
        try {
            customerToUpdate = adminFacade.getOneCustomer(4);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        customerToUpdate.setLastName("levi");
        customerToUpdate.setEmail("taliya@gmail.com");

        try {
            adminFacade.updateCustomer(customerToUpdate);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            TablePrinter.print(adminFacade.getAllCustomers());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println(ArtUtils.DELETE);

        try {
            adminFacade.deleteCustomer(3);
            TablePrinter.print(adminFacade.getAllCustomers());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        ///////////////////////COMPANY FACADE//////////////////////

        System.out.println("-----------------------------------------");
        System.out.println("--------------COMPANY FACADE-------------");
        System.out.println("-----------------------------------------");

        CompanyFacade companyFacade = new CompanyFacade();
        List<Coupon> companyCoupons = new ArrayList<>();

        System.out.println("LOGIN TEST /nike@.com/4444 ");

        try {
            if (companyFacade.login("nike@.com", "4444")) {
                System.out.println("login success");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("LOGIN TEST /nikenew@gmail.com/e344 ");

        try {
            if (companyFacade.login("nikenew@gmail.com", "e344")) {
                System.out.println("LOGIN SUCCESS");
                System.out.println("COMPANY IN CONTROL : " + adminFacade.getOneCompany(companyFacade.getCompanyId()).getName());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("***** COUPONS *****" );
        System.out.println(ArtUtils.INSERT);

        Coupon c4 = new Coupon(
                6,
                6,
                "lamp",
                "black lamp",
                Date.valueOf(LocalDate.now()),
                Date.valueOf(LocalDate.now().plusDays(80)),
                100,
                50,
                "lampblack.com");

        Coupon c5 = new Coupon(
                6,
                3,
                "lamp",
                "red lamp",
                Date.valueOf(LocalDate.now()),
                Date.valueOf(LocalDate.now().plusDays(80)),
                100,
                500,
                "redlamp.com");
        Coupon c6 = new Coupon(
                6,
                1,
                "Bottle",
                "sport Bottle",
                Date.valueOf(LocalDate.now()),
                Date.valueOf(LocalDate.now().plusDays(80)),
                100,
                150,
                "Bottle.com");
        Coupon c7 = new Coupon(
                6,
                4,
                "shirt",
                "sport undershirt",
                Date.valueOf(LocalDate.now()),
                Date.valueOf(LocalDate.now().plusDays(80)),
                100,
                250,
                "undershirt.com");

        try {
            companyFacade.addCoupon(c4);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            companyFacade.addCoupon(c5);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            companyFacade.addCoupon(c6);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            companyFacade.addCoupon(c7);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            TablePrinter.print(couponsDAO.getAllCoupons());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        System.out.println(ArtUtils.UPDATE);
        Coupon couponToUpdate2 = null;
        try {
            couponToUpdate2 = couponsDAO.getOneCoupons(6);
            couponToUpdate2.setAmount(50);
            couponToUpdate2.setTitle("shoe");
            couponToUpdate2.setDescription("Black shoe");
            couponToUpdate2.setImage("Blackshoe.com");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            companyFacade.updateCoupon(couponToUpdate2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            TablePrinter.print(companyFacade.getCompanyCoupons());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            couponsDAO.addCouponsPurchase(2, 6);
        }  catch (Exception e) {
        System.out.println(e.getMessage());
    }
        try {
            couponsDAO.addCouponsPurchase(6, 6);
        }  catch (Exception e) {
        System.out.println(e.getMessage());
        }

        System.out.println(ArtUtils.DELETE);

        try {
            companyFacade.deleteCoupon(6);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            TablePrinter.print(companyFacade.getCompanyCoupons());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("------------GET ALL COUPONS BY CATEGORY--------------");
        try {
            TablePrinter.print(companyFacade.getCompanyCouponsByCategory(Category.FOOD));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("------------GET ALL COUPONS BY MAX PRICE--------------");
        try {
            TablePrinter.print(companyFacade.getCompanyCouponsByMaxPrise(500.0));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("------------GET ALL COUPONS BY DETAILS--------------");
        try {
            TablePrinter.print(companyFacade.getCompanyDetails());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        ///////////////////////CUSTOMER FACADE//////////////////////

        System.out.println("-----------------------------------------");
        System.out.println("-------------CUSTOMER FACADE-------------");
        System.out.println("-----------------------------------------");

        CustomerFacade customerFacade = new CustomerFacade();

        System.out.println("LOGIN TEST /abcd@.com/222292 ");

        try {
            if (customerFacade.login("abcd@.com", "222292")) {
                System.out.println("LOGIN SUCCESSFUL");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("LOGIN TEST /keren@.com/kk123 ");

        try {
            if (customerFacade.login("keren@.com", "kk123")) {
                System.out.println("LOGIN SUCCESSFUL");
                System.out.println("USER IN CONTROL : " + adminFacade.getOneCustomer(customerFacade.getCustomerId()).getFirstName()
                        + " " + adminFacade.getOneCustomer(customerFacade.getCustomerId()).getLastName());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("*********CUSTOMER PURCHASE*********");
        System.out.println(ArtUtils.INSERT);

        try {
            customerFacade.purchaseCoupon(couponsDAO.getOneCoupons(8));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            TablePrinter.print(customerFacade.getCustomerCoupon());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println(ArtUtils.UPDATE);
        try {
            Coupon coupon8 = couponsDAO.getOneCoupons(7);
            coupon8.setAmount(0);
            couponsDAO.updateCoupons(coupon8);
            TablePrinter.print(couponsDAO.getOneCoupons(7));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {

            System.out.println(ArtUtils.INSERT);
            customerFacade.purchaseCoupon(couponsDAO.getOneCoupons(7));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println(ArtUtils.UPDATE);
        try {
            Coupon coupon8 = couponsDAO.getOneCoupons(7);
            coupon8.setAmount(100);
            coupon8.setEndDate(Date.valueOf(LocalDate.now().minusDays(1)));
            couponsDAO.updateCoupons(coupon8);
            TablePrinter.print(couponsDAO.getOneCoupons(7));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println(ArtUtils.INSERT);
            customerFacade.purchaseCoupon(couponsDAO.getOneCoupons(7));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        System.out.println(ArtUtils.UPDATE);
        try {
            Coupon coupon8 = couponsDAO.getOneCoupons(7);
            coupon8.setEndDate(Date.valueOf(LocalDate.now().plusDays(10)));
            couponsDAO.updateCoupons(coupon8);
            TablePrinter.print(couponsDAO.getOneCoupons(7));
            System.out.println(ArtUtils.INSERT);
            customerFacade.purchaseCoupon(couponsDAO.getOneCoupons(7));
            System.out.println("SUCCESSFUL PURCHASE");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("******GET ALL CUSTOMER COUPONS******");
        try {
            TablePrinter.print(customerFacade.getCustomerCoupon());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("******GET ALL CUSTOMER COUPONS BY CATEGORY (4-PC)******");
        try {
            TablePrinter.print(customerFacade.getCustomerCoupon(Category.PC));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("******GET ALL CUSTOMER COUPONS BY MAX PRICE (200.0)******");
        try {
            TablePrinter.print(customerFacade.getCustomerCoupon(200.0));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("******GET CUSTOMER DETAILS******");
        try {
            TablePrinter.print(customerFacade.getCustomerDetails());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        ///////////////////////CLIENT LOGIN//////////////////////

        System.out.println("-----------------------------------------");
        System.out.println("-------------CLIENT LOGIN----------------");
        System.out.println("-----------------------------------------");

        LoginManager loginManager = LoginManager.getInstance();
        ClientFacade clientFacade = loginManager.login("admin@admin.com", "admin", ClientType.Administrator);

        if (clientFacade instanceof AdminFacade) {
            try {
                System.out.println("admin is in control");
                TablePrinter.print(((AdminFacade) clientFacade).getAllCompanies());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }


        System.out.println("******END TASK CLEAN EXPIRED COUPONS******");

        ((CouponExpirationDailyJob) thread).bye();

        try {
            TablePrinter.print(couponsDAO.getAllCoupons());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }



        //////////////////////////////////////////////////////////////////////////////

        /*System.out.println("CLOSE CONNECTION POOL");

        try {
            ConnectionPool.getInstance().closeAllConnections();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

         */



        //////////////////////////////////////////////////////////////////////////////

        System.out.println("end");
    }
}
