/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ili.base.services.authority;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import net.ili.base.dao.authority.ResourceDao;
import net.ili.base.entities.authority.Resource;

/**
 *
 * @author Weir
 */
@Named
public class ResourceService {

    @Inject
    private ResourceDao dao;

    public List<Resource> getAll() {
        return dao.findAll();
    }

    public Resource getOne(Long id) {
        return dao.findOne(id);
    }

    public void saveAndFlush(final Resource resource) {
        dao.saveAndFlush(resource);
    }

    public void remove(final Long id) {
        dao.delete(id);
    }

    public void remove(final Resource resource) {
        dao.delete(resource);
    }
}
