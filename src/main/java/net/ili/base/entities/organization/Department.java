/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ili.base.entities.organization;

import javax.persistence.Column;
import net.ili.base.entities.enums.OrgTreeNodeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import net.ili.base.entities.IdEntity;
import net.ili.base.entities.TreeNodeEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 *
 * @author Weir
 */
@Entity
@Table(name = "ili_base_department")
public class Department extends IdEntity implements TreeNodeEntity<Department> {

    private static final long serialVersionUID = 2610911538367742687L;
    private String numbering;
    private String name;
    private String description;
    private Integer SortOrder;
    //optional=false说明这个属性必须有，不能为空
    @ManyToOne
    private Organization organ;
    @ManyToOne
    private Department parent;
    @Column(length = 2000)
    private String hierarchy;
    @Enumerated(value = EnumType.STRING)
    private final OrgTreeNodeType type = OrgTreeNodeType.DEPARTMENT;

    public Department() {
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

    public Integer getSortOrder() {
        return SortOrder;
    }

    public void setSortOrder(Integer SortOrder) {
        this.SortOrder = SortOrder;
    }

    public Organization getOrgan() {
        return organ;
    }

    public void setOrgan(Organization organ) {
        this.organ = organ;
    }

    @Override
    public Department getParent() {
        return parent;
    }

    @Override
    public void setParent(Department parent) {
        this.parent = parent;
    }

    @Override
    public String getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(String hierarchy) {
        this.hierarchy = hierarchy;
    }

    public OrgTreeNodeType getType() {
        return type;
    }

    @Override
    public String getShortClassName() {
        String className = getClass().getName();
        return className.substring(className.lastIndexOf(".") + 1, className.length());
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
