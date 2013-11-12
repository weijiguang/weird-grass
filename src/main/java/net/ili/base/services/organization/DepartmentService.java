/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ili.base.services.organization;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import net.ili.base.dao.organization.DepartmentDao;
import net.ili.base.entities.organization.Department;
import net.ili.base.entities.organization.Organization;

/**
 *
 * @author Weir
 */
@Named
public class DepartmentService {

    @Inject
    private DepartmentDao dao;
    @Inject
    private OrganizationService orgDao;

    public List<Department> getAll() {
        return dao.findAll();
    }

    public List<Organization> getAllOrganization() {
        return orgDao.getAll();
    }

    public Department getOne(final Long id) {
        return dao.findOne(id);
    }

    public void saveAndFlush(final Department dept) {
        dao.saveAndFlush(dept);
    }

    public void remove(final Department dept) {
        dao.delete(dept);
    }
}
