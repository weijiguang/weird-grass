/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ili.grass.dao.organization;

import java.util.List;
import net.ili.grass.dao.SuperJpaDao;
import net.ili.grass.entities.organization.Organization;
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
