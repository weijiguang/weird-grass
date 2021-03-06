/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ili.grass.services.organization;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import net.ili.grass.dao.organization.OrganizationDao;
import net.ili.grass.entities.organization.Organization;

/**
 *
 * @author Weir
 */
@Named
public class OrganizationService {

    @Inject
    private OrganizationDao dao;

    public List<Organization> getAll() {
        return dao.findAll();
    }

    public Organization getOne(final Long id) {
        return dao.findOne(id);
    }

    public void saveAndFlush(Organization org) {
        dao.saveAndFlush(org);
    }

    public void remove(Organization organ) {
        dao.delete(organ);
    }
}
