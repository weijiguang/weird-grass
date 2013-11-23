/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ili.grass.webbeans.authority;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import net.ili.grass.entities.authority.Role;
import net.ili.grass.entities.authority.User;
import net.ili.grass.services.authority.RoleService;
import net.ili.grass.services.authority.UserService;
import net.ili.grass.services.commons.Const;
import net.ili.grass.webbeans.SuperWebBean;
import net.ili.grass.webbeans.commons.Msg;
import org.omnifaces.util.Messages;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Weir
 */
@Named
@ViewScoped
public class UserBean extends SuperWebBean {

    private static final long serialVersionUID = -8198060850382159015L;
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(UserBean.class);
    private static final String VIEW_ROOT = "/views/authority/user/";
    private static final String listURL = VIEW_ROOT + "list";
    private static final String registerURL = VIEW_ROOT + "register";
    private static final String modifypasswordURL = VIEW_ROOT + "modifypassword";
    private static final String loginURL = VIEW_ROOT + "login";
    @Inject
    private MySession mySession;
    @Inject
    private UserService userService;
    @Inject
    private RoleService roleService;
    private User user;
    private User selectedUser;
    private List<User> userList;
    private List<Role> roles;
    private List<Role> selectedRoles;

    public UserBean() {
    }

    @PostConstruct
    private void init() {
        user = new User();
    }

    public String prepareRegister() {
        user = new User();
        return registerURL;
    }

    public String prepareLogin() {

        user = new User();
        return loginURL;
    }

    public String prepareList() {
        list();
        return listURL;
    }

    public String prepareGrantRole() {
        return VIEW_ROOT + "grant_role";
    }

    public String register() {
        try {
            userService.saveAndFlush(user);
        } catch (SQLException ex) {
            log.error(ex.getMessage());
            Msg.error("注册失败！已存在该用户！");
            return null;
        }
        Msg.info("注册成功！");
        return this.prepareRegister();
    }

    public void saveRoleSetting() {
        if (this.selectedRoles != null && !this.selectedRoles.isEmpty()) {
            this.user = this.userService.getOne(this.selectedUser.getId());
            user.getRoles().clear();
            user.getRoles().addAll(selectedRoles);
            try {
                this.userService.saveAndFlush(selectedUser);
                Messages.addGlobalInfo("操作成功", new Object[]{});
            } catch (SQLException ex) {
                log.error(ex.getMessage());
                Messages.addGlobalError("操作失败", new Object[]{});
            }
        }
    }

    public void list() {
        userList = new ArrayList();
        userList = userService.getAll();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public List<User> getUserList() {
        if (userList == null) {
            list();
        }
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<Role> getRoles() {
        if (selectedUser != null) {
            this.roles = this.roleService.getAll();
            this.user = this.userService.getOne(selectedUser.getId());
            if (selectedRoles != null) {
                selectedRoles.clear();
                selectedRoles.addAll(user.getRoles());
            } else {
                selectedRoles = new ArrayList<>();
                selectedRoles.addAll(user.getRoles());
            }
        }
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Role> getSelectedRoles() {
        return selectedRoles;
    }

    public void setSelectedRoles(List<Role> selectedRoles) {
        this.selectedRoles = selectedRoles;
    }
}
