/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ili.grass.service.authority;

import net.ili.grass.services.authority.UserService;
import javax.inject.Inject;
import net.ili.grass.SuperSpringTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Weir
 */
public class UserServiceTest extends SuperSpringTestCase{
    
    @Inject
    UserService us;
    
    public UserServiceTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testSomeMethod() {
    }
}