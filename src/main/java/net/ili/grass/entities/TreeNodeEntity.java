/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ili.grass.entities;

/**
 *
 * @author Weir
 * @param <T>
 */
public interface TreeNodeEntity<T> {

    Long getId();

    void setId(Long id);

    T getParent();

    void setParent(T parent);

    String getHierarchy();

    String getShortClassName();

    @Override
    int hashCode();

    @Override
    boolean equals(Object obj);

    @Override
    String toString();
}
