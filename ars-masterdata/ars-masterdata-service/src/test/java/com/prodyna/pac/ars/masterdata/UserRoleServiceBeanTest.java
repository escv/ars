/**
 * 
 */
package com.prodyna.pac.ars.masterdata;

import java.util.List;

import javax.ejb.EJBException;
import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.ars.masterdata.model.UserRole;

@RunWith(Arquillian.class)
public class UserRoleServiceBeanTest extends SecuredTest {

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class).addPackages(true, "com.prodyna.pac.ars.masterdata")
				.addPackages(true, "com.prodyna.pac.ars.service").addAsResource("META-INF/persistence.xml")
				.addAsResource("META-INF/ejb-jar.xml").addAsResource("META-INF/jboss-ejb3.xml")
				.addAsResource("roles.properties").addAsResource("users.properties")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@Inject
	private UserRoleService userRoleService;

	@Test(expected=EJBException.class)
	public void testDidntFindById() throws Exception {
		this.userRoleService.readUserRole(82L);
	}
	
	@Test(expected = Exception.class)
	public void testCreateNull() {
		userRoleService.addUserRole(null);
	}

	@Test(expected = Exception.class)
	public void testUpdateNull() {
		userRoleService.updateUserRole(null);
	}

	@Test(expected = Exception.class)
	public void testInvalidObject() {
		UserRole u = new UserRole();
		u.setName("A");
		userRoleService.addUserRole(u);
	}

	@Test(expected=EJBException.class)
	public void testDidntFindByName() throws Exception {
		userRoleService.readUserRoleByName("YET A TEST");
	}

	@Test
	@InSequence(1)
	public void testCreateUserRole() throws Exception {
		UserRole UserRole = new UserRole();
		UserRole.setName("ACT1");
		UserRole created = userRoleService.addUserRole(UserRole);
		Assert.assertTrue(created.getId() > 0);
	}

	@Test
	@InSequence(2)
	public void testReadSingle() throws Exception {
		UserRole userRole = new UserRole();
		userRole.setName("ACT2");
		UserRole created = userRoleService.addUserRole(userRole);

		UserRole readUserRole = userRoleService.readUserRole(created.getId());

		Assert.assertEquals(userRole, readUserRole);
	}

	@Test
	@InSequence(3)
	public void testReadByName() throws Exception {
		UserRole UserRole = userRoleService.readUserRoleByName("ACT1");
		Assert.assertEquals("ACT1", UserRole.getName());
	}

	@Test
	@InSequence(4)
	public void testReadAllUserRole() {
		List<UserRole> allUserRoles = userRoleService.readAllUserRoles();
		Assert.assertEquals(2 + 2, allUserRoles.size()); // 2 roles are allways
															// created by
															// initializer
		boolean foundACT1 = false, foundACT2 = false;
		for (UserRole u : allUserRoles) {
			if ("ACT1".equals(u.getName())) {
				foundACT1 = true;
			}
			if ("ACT2".equals(u.getName())) {
				foundACT2 = true;
			}
		}
		Assert.assertTrue(foundACT1 && foundACT2);
	}

	@Test(expected = Exception.class)
	@InSequence(5)
	public void testUniqueName() {
		UserRole userRole = new UserRole();
		userRole.setName("ACT2");
		userRoleService.addUserRole(userRole);
	}

	@Test
	@InSequence(6)
	public void testRemoveUserRole() throws Exception {
		UserRole role = userRoleService.readUserRoleByName("ACT1");
		userRoleService.removeUserRole(role.getId());
		try {
			this.userRoleService.readUserRole(role.getId());
			Assert.fail("No Exception thrown for reading not existent entity");
		} catch (EJBException e) {
			
		}
	}

}
