/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ili.base.dao.organization;

import java.util.List;
import net.ili.base.dao.SuperJpaDao;
import net.ili.base.entities.organization.Organization;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Weir
 */
public interface OrganizationDao extends SuperJpaDao<Organization, Long> {

//    @Override
//    @Query(value = "select o from Organization o JOIN FETCH o.departments")
//    public List<Organization> findAll();
}
