/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ili.base.webbeans.authority;

import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import net.ili.base.entities.authority.Role;
import net.ili.base.services.authority.ModulesService;
import net.ili.base.services.authority.RoleService;
import net.ili.base.services.commons.Const;
import net.ili.base.webbeans.SuperWebBean;
import org.omnifaces.util.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Weir
 */
@Named
@ViewScoped
public class RoleBean extends SuperWebBean {

    private static final long serialVersionUID = 8463962071428902650L;
    private static final Logger log = LoggerFactory.getLogger(RoleBean.class);
    private static final String VIEW_ROOT = "/views/authority/role/";
    @Inject
    private RoleService roleService;
    @Inject
    private ModulesService modulesService;
    private Role role;
    private List<Role> roleList;
    private Role selectedRole;

    public String prepareAdd() {
        role = new Role();
        return VIEW_ROOT + "add";
    }

    public String prepareList() {
        list();
        return VIEW_ROOT + "list";
    }

    public String prepareGrantPermission() {
        return VIEW_ROOT + "grant_permission";
    }

    public void list() {
        roleList = roleService.getAll();
    }

    public void save() {
        if (role != null) {
            try {
                this.roleService.saveAndFlush(role);
                Messages.addGlobalInfo("操作成功", new Object[]{});
            } catch (Exception e) {
                Messages.addGlobalInfo("操作失败", new Object[]{});
                log.error(e.getMessage());
            }
        }
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Role getSelectedRole() {
        return selectedRole;
    }

    public void setSelectedRole(Role selectedRole) {
        this.selectedRole = selectedRole;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }
}
