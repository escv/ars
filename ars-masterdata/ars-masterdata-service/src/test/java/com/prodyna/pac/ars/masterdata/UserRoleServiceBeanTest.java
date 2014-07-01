/**
 * 
 */
package com.prodyna.pac.ars.masterdata;

import java.security.PrivilegedAction;
import java.util.List;

import javax.inject.Inject;
import javax.security.auth.Subject;

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
import com.prodyna.pac.ars.service.ejb.SecuredTest;

@RunWith(Arquillian.class)
public class UserRoleServiceBeanTest extends SecuredTest {

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class)
				.addPackages(true, "com.prodyna.pac.ars.masterdata")
				.addPackages(true, "com.prodyna.pac.ars.service")
				.addAsResource("META-INF/persistence.xml")
				.addAsResource("META-INF/ejb-jar.xml")
				.addAsResource("META-INF/jboss-ejb3.xml")
				.addAsResource("roles.properties")
				.addAsResource("users.properties")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@Inject
	private UserRoleService userRoleService;

	@Test(expected = Exception.class)
	public void testCreateNull() {
		Subject.doAs(loginContext.getSubject(), new PrivilegedAction<Void>() {
			@Override
			public Void run() {
				userRoleService.addUserRole(null);
				return null;
			}
		});
	}

	@Test(expected = Exception.class)
	public void testUpdateNull() {
		Subject.doAs(loginContext.getSubject(), new PrivilegedAction<Void>() {
			@Override
			public Void run() {
				userRoleService.updateUserRole(null);
				return null;
			}
		});
	}

	@Test(expected = Exception.class)
	public void testInvalidObject() {
		Subject.doAs(loginContext.getSubject(), new PrivilegedAction<Void>() {
			@Override
			public Void run() {
				UserRole u = new UserRole();
				u.setName("A");
				userRoleService.addUserRole(u);
				return null;
			}
		});
	}

	@Test(expected = Exception.class)
	public void testDidntFindByName() throws Exception {
		Subject.doAs(loginContext.getSubject(), new PrivilegedAction<Void>() {
			@Override
			public Void run() {
				Assert.assertNull(userRoleService
						.readUserRoleByName("YET A TEST"));
				return null;
			}
		});
	}

	@Test
	@InSequence(1)
	public void testCreateUserRole() throws Exception {
		Subject.doAs(loginContext.getSubject(), new PrivilegedAction<Void>() {
			@Override
			public Void run() {

				UserRole UserRole = new UserRole();
				UserRole.setName("ACT1");
				UserRole created = userRoleService
						.addUserRole(UserRole);
				Assert.assertTrue(created.getId() > 0);
				return null;
			}
		});
	}

	@Test
	@InSequence(2)
	public void testReadSingle() throws Exception {
		Subject.doAs(loginContext.getSubject(), new PrivilegedAction<Void>() {
			@Override
			public Void run() {
				UserRole userRole = new UserRole();
				userRole.setName("ACT2");
				UserRole created = userRoleService
						.addUserRole(userRole);

				UserRole readUserRole = userRoleService
						.readUserRole(created.getId());

				Assert.assertEquals(userRole, readUserRole);
				return null;
			}
		});
	}

	@Test
	@InSequence(3)
	public void testReadByName() throws Exception {
		Subject.doAs(loginContext.getSubject(), new PrivilegedAction<Void>() {
			@Override
			public Void run() {
				UserRole UserRole = userRoleService
						.readUserRoleByName("ACT1");
				Assert.assertEquals("ACT1", UserRole.getName());
				return null;
			}
		});
	}

	@Test
	@InSequence(4)
	public void testReadAllUserRole() {
		Subject.doAs(loginContext.getSubject(), new PrivilegedAction<Void>() {
			@Override
			public Void run() {
				List<UserRole> allUserRoles = userRoleService
						.readAllUserRoles();
				Assert.assertEquals(2+2, allUserRoles.size()); // 2 roles are allways created by initializer
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
				return null;
			}
		});
	}

	@Test(expected = Exception.class)
	@InSequence(5)
	public void testUniqueName() {
		Subject.doAs(loginContext.getSubject(), new PrivilegedAction<Void>() {
			@Override
			public Void run() {
				UserRole userRole = new UserRole();
				userRole.setName("ACT2");
				userRoleService.addUserRole(userRole);
				return null;
			}
		});
	}

	@Test
	@InSequence(6)
	public void testRemoveAircraft() throws Exception {
		Subject.doAs(loginContext.getSubject(), new PrivilegedAction<Void>() {
			@Override
			public Void run() {
				UserRole act = userRoleService
						.readUserRoleByName("ACT1");
				userRoleService.removeUserRole(act.getId());
				Assert.assertNull(userRoleService.readUserRole(act
						.getId()));
				return null;
			}
		});
	}

}
