/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ili.grass;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Weir
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/spring-main.xml"})
@Transactional
@ActiveProfiles(profiles = "development")
public abstract class SuperSpringTestCase {
}
