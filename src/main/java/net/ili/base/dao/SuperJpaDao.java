/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ili.base.dao;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author Weir
 */
public interface SuperJpaDao<T, ID extends Serializable> extends JpaRepository<T, ID>,
        JpaSpecificationExecutor<ID> {
}
