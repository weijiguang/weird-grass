/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ili.base.services.authority;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import net.ili.base.dao.authority.ModulesDao;
import net.ili.base.entities.authority.Modules;

/**
 *
 * @author Weir
 */
@Named
public class ModulesService {
    
    @Inject
    ModulesDao dao;
    
    public List<Modules> getAll() {
        return dao.findAll();
    }
    
    public Modules getOne(Long id) {
        return dao.findOne(id);
    }
    
    public void saveAndFlush(Modules modules) {
        dao.saveAndFlush(modules);
    }
    
    public void remove(Modules modules) {
        dao.delete(modules);
    }
}
