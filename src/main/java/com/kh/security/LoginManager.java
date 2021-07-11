package com.kh.security;

import com.kh.facades.AdminFacade;
import com.kh.facades.ClientFacade;
import com.kh.facades.CompanyFacade;
import com.kh.facades.CustomerFacade;

public class LoginManager {
    private static LoginManager instance = null;

    public LoginManager() {
    }

    public static LoginManager getInstance() {
        if (instance == null) {
            synchronized (LoginManager.class) {
                if (instance == null) {
                    instance = new LoginManager();
                }
            }
        }
        return instance;
    }

    public ClientFacade login(String email, String password, ClientType clientType) {
        switch (clientType) {
            case Administrator:
                AdminFacade adminFacade = new AdminFacade();
                if (adminFacade.login(email, password)) {
                    return adminFacade;
                }
                break;
            case Company:
                CompanyFacade companyFacade = new CompanyFacade();
                try {
                    if (companyFacade.login(email, password)) {
                        return companyFacade;
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            case Customer:
                CustomerFacade customerFacade = new CustomerFacade();
                try {
                    if (customerFacade.login(email, password)) {
                        customerFacade.setCustomerId(1);
                        return customerFacade;
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            default:
                break;
        }
        return null;
    }
}
