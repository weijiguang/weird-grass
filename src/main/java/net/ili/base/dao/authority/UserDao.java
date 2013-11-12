/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ili.base.dao.authority;

import java.util.List;
import net.ili.base.dao.SuperJpaDao;
import net.ili.base.entities.authority.Resource;
import net.ili.base.entities.authority.Role;
import net.ili.base.entities.authority.User;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Weir
 */
public interface UserDao extends SuperJpaDao<User, Long> {

    /**
     * 查找指定的用户名的用户信息
     *
     * @param name 用户名
     * @return 返回查找的用户
     */
    public User findByName(String name);

    /**
     * 查找指定用户名和用户密码的用户信息
     *
     * @param name 用户名
     * @param password 密码
     * @return 返回查找到的用户
     */
    public User findByNameAndPassword(String name, String password);

    /**
     * 查找指定用户名和密码的用户信息
     *
     * @param name 用户名
     * @param password 密码
     * @return 返回查找到的用户数量
     */
    @Query("select count(u) from User u where u.name = ?1 and u.password = ?2")
    public int login(String name, String password);

    @Query("select s from User u JOIN u.roles r JOIN r.resources s WHERE u.id = ?1")
    public List<Resource> searchResources(Long userId);

    @Query("select u.roles from User u WHERE u.id = ?1")
    public List<Role> searchRoles(Long userId);
}
