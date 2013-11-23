/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ili.grass.services.authority;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import net.ili.grass.dao.authority.RoleDao;
import net.ili.grass.entities.authority.Role;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Weir
 */
@Named
@Transactional
public class RoleService {

    @Inject
    RoleDao dao;

    public List<Role> getAll() {
        return dao.findAll();
    }

    public Role getOne(Long id) {
        return dao.findOne(id);
    }

    //********************** 数据更新方法 ***********************
    @CacheEvict(value = "shiroAuthorizationCache", allEntries = true)//清空所有授权信息缓存
    public void saveAndFlush(Role role) {
        dao.saveAndFlush(role);
    }

    @CacheEvict(value = "shiroAuthorizationCache", allEntries = true)//清空所有授权信息缓存
    public void remove(Role role) {
        dao.delete(role);
    }
}
