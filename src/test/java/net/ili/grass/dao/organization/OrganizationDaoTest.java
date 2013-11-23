/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ili.grass.dao.organization;

import net.ili.grass.dao.organization.OrganizationDao;
import javax.inject.Inject;
import net.ili.grass.SuperSpringTestCase;
import net.ili.grass.entities.organization.Organization;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Weir
 */

public class OrganizationDaoTest extends SuperSpringTestCase{

    @Inject
    OrganizationDao orgDao;
    Organization org;

    public OrganizationDaoTest() {
    }

    @Before
    public void setup() {
        org = new Organization();
    }

    @Test
    public void testSave() {
        org.setNumbering("MWR001");
        org.setName("ili软件工作室");
        org.setDescription("从事软件研发、销售、维护及咨询等软件领域服务。");
        org.setSortOrder(1);

        orgDao.saveAndFlush(org);

        Assert.assertEquals(org, orgDao.findOne(org.getId()));
    }
}