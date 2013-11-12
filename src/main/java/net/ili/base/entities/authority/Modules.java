/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ili.base.entities.authority;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import net.ili.base.entities.IdEntity;

/**
 *
 * @author Weir
 */
@Entity
@Table(name = "ili_base_modules")
public class Modules extends IdEntity {

    private static final long serialVersionUID = 7549223987176126701L;
    //模块名称
    @Column(nullable = false, unique = true)
    private String name;
    private String displayName;
    private String description;
    @OneToMany(mappedBy = "modules")
    private List<Resource> resources;

    public Modules() {
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

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }
}
