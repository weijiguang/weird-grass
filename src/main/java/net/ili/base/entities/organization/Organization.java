/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ili.base.entities.organization;

import java.util.List;
import javax.persistence.Column;
import net.ili.base.entities.enums.OrgTreeNodeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import net.ili.base.entities.IdEntity;
import net.ili.base.entities.TreeNodeEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 *
 * @author Weir
 */
@Entity
@Table(name = "ili_base_Organization")
public class Organization extends IdEntity implements TreeNodeEntity<Organization> {

    private static final long serialVersionUID = -6316643628209924776L;
    protected String numbering;
    protected String name;
    protected String description;
    @ManyToOne
    protected Organization parent;
    @Column(length = 2000)
    private String hierarchy;
    protected Integer sortOrder;
    @Enumerated(value = EnumType.STRING)
    private final OrgTreeNodeType type = OrgTreeNodeType.ORGANIZATION;
    @OneToMany(mappedBy = "organ")
    private List<Department> departments;

    public Organization() {
    }

    public String getNumbering() {
        return numbering;
    }

    public void setNumbering(String numbering) {
        this.numbering = numbering;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Organization getParent() {
        return parent;
    }

    @Override
    public void setParent(Organization parent) {
        this.parent = parent;
    }

    @Override
    public String getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(String hierarchy) {
        this.hierarchy = hierarchy;
    }

    @Override
    public String getShortClassName() {
        String className = getClass().getName();
        return className.substring(className.lastIndexOf(".") + 1, className.length());
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public OrgTreeNodeType getType() {
        return type;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
