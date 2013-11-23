/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ili.grass.entities.authority;

import java.sql.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import net.ili.grass.entities.IdEntity;
import net.ili.grass.entities.enums.OrgTreeNodeType;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 账号（用户）信息
 *
 * @author Weir
 */
@Entity
@Table(name = "ili_base_user")
public class User extends IdEntity {

    private static final long serialVersionUID = -113442553155L;
//    private String numbering;
    @Column(nullable = false, unique = true)
    private String name;
    @Size(min = 6, max = 128)
    private String password;
    private String salt;
    private Date firstLoginTime;
    private Date lastLoginTime;
    private int loginCount;
    private Date createTime;
    @ManyToOne
    private User creator;
    private Boolean enable = true;
    private Boolean enableDelete = true;
    private String description;
    @Enumerated(value = EnumType.STRING)
    private final OrgTreeNodeType type = OrgTreeNodeType.USER;
//    @ManyToMany
//    private List<Role> roles;
//    @ManyToMany
//    private List<Permission> permissions;
    @ManyToMany
    private List<Role> roles;

    public User() {
    }

    public User(String name, String password, String salt, String description) {
        this.name = name;
        this.password = password;
        this.salt = salt;
        this.description = description;
    }

    //
    //    public User(String name, String password, String salt, String description, List<Role> roles, List<Permission> permissions) {
    //        this.name = name;
    //        this.password = password;
    //        this.salt = salt;
    //        this.description = description;
    ////        this.roles = roles;
    //    }
    //    }
//    public String getNumbering() {
//        return numbering;
//    }
//
//    public void setNumbering(String numbering) {
//        this.numbering = numbering;
//    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Boolean getEnableDelete() {
        return enableDelete;
    }

    public void setEnableDelete(Boolean enableDelete) {
        this.enableDelete = enableDelete;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OrgTreeNodeType getType() {
        return type;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

//
//    public List<Permission> getPermissions() {
//        return permissions;
//    }
//
//    public void setPermissions(List<Permission> permissions) {
//        this.permissions = permissions;
//    }
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
