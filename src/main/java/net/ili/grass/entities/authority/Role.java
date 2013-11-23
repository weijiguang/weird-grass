/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ili.grass.entities.authority;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import net.ili.grass.entities.IdEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 *
 * @author Weir
 */
@Entity
@Table(name = "ili_base_role")
public class Role extends IdEntity {

    private static final long serialVersionUID = 2237539342120721537L;
    private String name;
    private String displayName;
    private String description;
    @ManyToMany(mappedBy = "roles")
    private List<User> members;
    @ManyToMany
    private List<Resource> resources;
    private String permissionDefinition;
    @ManyToOne
    private Role parent;
    @OneToMany
    private List<Role> children;

    public Role() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public String getPermissionDefinition() {
        return permissionDefinition;
    }

    public void setPermissionDefinition(String permissionDefinition) {
        this.permissionDefinition = permissionDefinition;
    }

    public Role getParent() {
        return parent;
    }

    public void setParent(Role parent) {
        this.parent = parent;
    }

    public List<Role> getChildren() {
        return children;
    }

    public void setChildren(List<Role> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
