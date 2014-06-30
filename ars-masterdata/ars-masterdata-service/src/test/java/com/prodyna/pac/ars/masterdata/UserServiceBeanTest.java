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

import com.prodyna.pac.ars.masterdata.model.User;
import com.prodyna.pac.ars.service.ejb.SecuredTest;

@RunWith(Arquillian.class)
public class UserServiceBeanTest extends SecuredTest {

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
	private UserService userService;

	@Test(expected = Exception.class)
	public void testCreateNull() {
		Subject.doAs(loginContext.getSubject(), new PrivilegedAction<Void>() {
			@Override
			public Void run() {
				userService.addUser(null);
				return null;
			}
		});
	}

	@Test(expected = Exception.class)
	public void testUpdateNull() {
		Subject.doAs(loginContext.getSubject(), new PrivilegedAction<Void>() {
			@Override
			public Void run() {
				userService.updateUser(null);
				return null;
			}
		});
	}

	@Test(expected = Exception.class)
	public void testInvalidObject() {
		Subject.doAs(loginContext.getSubject(), new PrivilegedAction<Void>() {
			@Override
			public Void run() {

				User u = new User();
				u.setName("A");
				userService.addUser(u);
				return null;
			}
		});
	}

	@Test(expected = Exception.class)
	public void testInvalidEMail() {
		Subject.doAs(loginContext.getSubject(), new PrivilegedAction<Void>() {
			@Override
			public Void run() {

				User u = new User();
				u.setName("Andre");
				u.setPasswordDigest("1234567890qwertzuiop0987654321ZZ");
				u.setEmail("andre@albert");
				userService.addUser(u);
				return null;
			}
		});
	}

	@Test(expected = Exception.class)
	public void testDidntFindByName() throws Exception {
		Subject.doAs(loginContext.getSubject(), new PrivilegedAction<Void>() {
			@Override
			public Void run() {

				Assert.assertNull(userService.readUserByName("YET A TEST"));
				return null;
			}
		});
	}

	@Test
	@InSequence(1)
	public void testCreateUser() throws Exception {
		Subject.doAs(loginContext.getSubject(), new PrivilegedAction<Void>() {
			@Override
			public Void run() {

				User user = new User();
				user.setName("Jo");
				user.setPasswordDigest("1234567890qwertzuiop0987654321ZZ");
				user.setEmail("valid@email.com");
				User created = userService.addUser(user);
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

				User user = new User();
				user.setName("Max");
				user.setEmail("valid@email.com");
				user.setPasswordDigest("1234567890qwertzuiop0987654321ZZ");
				User created = userService.addUser(user);

				User readUser = userService.readUser(created.getId());

				Assert.assertEquals(user, readUser);
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
		User user = userService.readUserByName("Jo");
		Assert.assertEquals("valid@email.com", user.getEmail());
		return null;
			}
		});
	}

	@Test
	@InSequence(4)
	public void testReadAllUser() {
		Subject.doAs(loginContext.getSubject(), new PrivilegedAction<Void>() {
			@Override
			public Void run() {
		List<User> allUsers = userService.readAllUsers();
		Assert.assertEquals(2 + 1, allUsers.size()); // one user gets created on
														// initializer /thats
														// why +1/
		boolean foundJo = false, foundMax = false;
		for (User u : allUsers) {
			if ("Jo".equals(u.getName())) {
				foundJo = true;
			}
			if ("Max".equals(u.getName())) {
				foundMax = true;
			}
		}
		Assert.assertTrue(foundJo && foundMax);
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
		User user = new User();
		user.setName("Max");
		user.setEmail("valid@email.com");
		user.setPasswordDigest("1234567890qwertzuiop0987654321ZZ");
		userService.addUser(user);
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
		User jo = userService.readUserByName("Jo");
		userService.removeUser(jo.getId());
		Assert.assertNull(userService.readUser(jo.getId()));
		return null;
			}
		});
	}

}
