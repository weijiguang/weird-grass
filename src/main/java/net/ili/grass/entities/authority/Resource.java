/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ili.grass.entities.authority;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import net.ili.grass.entities.IdEntity;

/**
 *
 * @author Weir
 */
@Entity
@Table(name = "ili_base_resource")
public class Resource extends IdEntity {

    private static final long serialVersionUID = 687096449372477192L;
    //资源(功能)名称
    @Column(nullable = false, unique = true)
    private String name;
    private String displayName;
    //资源(网页)地址
    @Column(nullable = false, unique = true)
    private String url;
    private String filterName;
    //访问资源必需的权限(证书)
    private String permission;
    //资源描述
    private String description;
    @ManyToMany(mappedBy = "resources")
    private List<Role> roles;
    @ManyToOne
    private Modules modules;

    /**
     *
     */
    public Resource() {
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Modules getModules() {
        return modules;
    }

    public void setModules(Modules modules) {
        this.modules = modules;
    }

    /**
     *
     * @return
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    /**
     *
     * @return
     */
    public String getPermission() {
        return permission;
    }

    /**
     *
     * @param permission
     */
    public void setPermission(String permission) {
        this.permission = permission;
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
