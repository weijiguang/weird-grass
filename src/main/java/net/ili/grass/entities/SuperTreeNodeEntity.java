/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ili.grass.entities;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author Weir
 */
@MappedSuperclass
public abstract class SuperTreeNodeEntity<T> extends IdEntity implements TreeNodeEntity<T> {

    private static final long serialVersionUID = -7121797814439413884L;
    @ManyToOne
    protected T parent;
    @Column(length = 2000)
    private String hierarchy;
    private String shortClassName;

    @Override
    public T getParent() {
        return parent;
    }

    @Override
    public void setParent(T parent) {
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
        shortClassName = className.substring(className.lastIndexOf(".") + 1, className.length());
        return shortClassName;
    }
}
