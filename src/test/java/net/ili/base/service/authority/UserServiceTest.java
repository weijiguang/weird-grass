/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ili.base.service.authority;

import net.ili.base.services.authority.UserService;
import javax.inject.Inject;
import net.ili.base.SuperSpringTestCase;
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