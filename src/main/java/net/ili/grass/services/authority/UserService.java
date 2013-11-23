/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ili.grass.services.authority;

import java.sql.SQLException;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import net.ili.grass.dao.authority.UserDao;
import net.ili.grass.entities.authority.Role;
import net.ili.grass.entities.authority.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Weir
 */
@Named
@Transactional
public class UserService {

    @Inject
    private UserDao dao;

    public List<User> getAll() {
        return dao.findAll();
    }

    public User getOne(Long id) {
        return dao.findOne(id);
    }

    public List<Role> getRoles(Long id) {
        return dao.findOne(id).getRoles();
    }

    public User login(final User user) {
        return dao.findByNameAndPassword(user.getName(), user.getPassword());
    }

    public boolean existUser(final User user) {
        User u = dao.findByName(user.getName());
        return u == null ? false : u.getName().equalsIgnoreCase(user.getName());
    }
    //********************** 数据更新方法 ***********************

    @CacheEvict(value = "shiroAuthorizationCache", allEntries = true)//清空所有授权信息缓存
    public void saveAndFlush(User user) throws SQLException {
        if (user != null && user.itIsNew() && existUser(user)) {
            throw new SQLException("该用户以存在。[" + user.toString() + "]");
        }
        dao.saveAndFlush(user);
    }

    @CacheEvict(value = "shiroAuthorizationCache", allEntries = true)//清空所有授权信息缓存
    public void remove(Long id) {
        dao.delete(id);
    }
}
