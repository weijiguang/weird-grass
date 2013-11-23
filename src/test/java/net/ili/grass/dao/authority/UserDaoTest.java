/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ili.grass.dao.authority;

import net.ili.grass.dao.authority.UserDao;
import javax.inject.Inject;
import net.ili.grass.SuperSpringTestCase;
import net.ili.grass.entities.authority.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Weir
 */
public class UserDaoTest extends SuperSpringTestCase {

    @Inject
    UserDao dao;
    User u;

    public UserDaoTest() {
    }

    @Before
    public void setUp() {
        u = new User();
        u.setName("user1");
        u.setPassword("123456");
    }

    @After
    public void tearDown() {
    }

//    @Test
    public void testLogin() {
        System.out.println("====================================");
//        for (User u : dao.findAll()) {
//            System.out.println(u.getName() + "   " + u.getPassword());
//        }
//        System.out.println(u.toString());
//        System.out.println(dao.findByName(u.getName()).toString());
        System.out.println("====================================");
        int cnt = dao.login(u.getName(), u.getPassword());
//        Assert.isTrue(cnt == 1);
    }

    @Test
    public void testFindResources() {
//        dao.searchResources(new Long(1));
        dao.searchRoles(new Long(1));

//        dao.searchRoles(u);
    }
}