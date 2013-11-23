/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ili.grass.webbeans.authority;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import net.ili.grass.entities.authority.User;
import net.ili.grass.webbeans.SuperWebBean;

/**
 *
 * @author Weir
 */
@Named
@SessionScoped
public class MySession extends SuperWebBean {

    private static final long serialVersionUID = -8962262238830833486L;
    private User loginUser;
    private boolean loggedIn = false;
    private boolean rememberMe = false;

    public MySession() {
    }

    public void clear() {
        loginUser = null;
        rememberMe = false;
        loggedIn = false;
    }

    public void clearLoginUser() {
        loginUser = null;
    }

    public User getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(User loginUser) {
        this.loginUser = loginUser;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
