/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ili.grass.webbeans.authority;

import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import net.ili.grass.entities.authority.User;
import net.ili.grass.services.authority.UserService;
import net.ili.grass.webbeans.commons.Msg;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Weir
 */
@Named
@ViewScoped
public class LoginBean implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(LoginBean.class);
    private static final long serialVersionUID = 4808747101789064988L;
    private static final String HOME_URL = "/home.xhtml";
    private final String VIEW_ROOT = "/views/authority/user/";
    @Inject
    private MySession mySession;
    @Inject
    private UserService userService;
    private User user;

    @PostConstruct
    private void init() {
        user = new User();
    }

    public String prepareLogin() {
        user = new User();
        return VIEW_ROOT + "login";
    }

    public String authenticate() throws IOException {
        RequestContext context = RequestContext.getCurrentInstance();
        User loginUser = userService.login(user);
        if (loginUser != null && !loginUser.itIsNew()) {

            context.addCallbackParam("loggedIn", true);
            //*********************** 登录shiro系统 *********************************
            Subject currentUser = SecurityUtils.getSubject();
            // Example using most common scenario of username/password pair:
            UsernamePasswordToken token = new UsernamePasswordToken(loginUser.getName(), loginUser.getPassword());
            // "Remember Me" built-in:
            //记录该令牌  
            token.setRememberMe(mySession.isRememberMe());
            try {
                currentUser.login(token);
                //验证是否成功登录的方法  
                if (currentUser.isAuthenticated()) {
                    //上一个访问请求
                    SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(Faces.getRequest());
                    //重定向到上一个访问请求的页面
                    Faces.redirect(savedRequest != null ? savedRequest.getRequestUrl() : HOME_URL);
                }
            } catch (AuthenticationException e) {
                log.warn(e.getMessage());
                Messages.addGlobalError("登录失败", new Object[]{});
                return null;
            }

            mySession.setLoggedIn(true);
            mySession.setLoginUser(loginUser);

            Msg.info("登录成功！");
        } else {
            mySession.clear();
            Messages.addGlobalError("您输入的帐号或密码不正确，请重新输入。", new Object[]{});
            return null;
        }

        return HOME_URL;
    }

    public String logout() {
        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.logout();
            //销毁session
            Faces.invalidateSession();
            //重定向到首页
            Faces.redirect(HOME_URL);

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        mySession.clear();
        return VIEW_ROOT + "login.xhtml";
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
