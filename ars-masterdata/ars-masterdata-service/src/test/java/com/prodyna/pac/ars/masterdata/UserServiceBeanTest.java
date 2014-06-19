/**
 * 
 */
package com.prodyna.pac.ars.masterdata;

import java.util.List;

import javax.inject.Inject;

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

@RunWith(Arquillian.class)
public class UserServiceBeanTest {

	@Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
        	.addPackages(true, "com.prodyna.pac.ars.masterdata")
        	.addPackages(true, "com.prodyna.pac.ars.service")
        	.addAsResource("META-INF/persistence.xml")
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }
	
	@Inject
    private UserService userService;
		
	@Test(expected=Exception.class)
	public void testCreateNull() {
		this.userService.addUser(null);
	}
	
	@Test(expected=Exception.class)
	public void testUpdateNull() {
		this.userService.updateUser(null);
	}
	
	@Test(expected=Exception.class)
	public void testInvalidObject() {
		User u = new User();
		u.setName("A");
		this.userService.addUser(u);
	}

	@Test(expected=Exception.class)
	public void testInvalidEMail() {
		User u = new User();
		u.setName("Andre");
		u.setPasswordDigest("1234567890qwertzuiop0987654321ZZ");
		u.setEmail("andre@albert");
		this.userService.addUser(u);
	}
	
	@Test(expected=Exception.class)
	public void testDidntFindByName() throws Exception {
		Assert.assertNull(this.userService.readUserByName("YET A TEST"));
	}
	
	@Test
	@InSequence(1)
	public void testCreateUser() throws Exception {
		User user = new User();
		user.setName("Jo");
		user.setPasswordDigest("1234567890qwertzuiop0987654321ZZ");
		user.setEmail("valid@email.com");
		User created = this.userService.addUser(user);
		Assert.assertTrue(created.getId()>0);
	}
	
	@Test
	@InSequence(2)
	public void testReadSingle() throws Exception {
		User user = new User();
		user.setName("Max");
		user.setEmail("valid@email.com");
		user.setPasswordDigest("1234567890qwertzuiop0987654321ZZ");
		User created = this.userService.addUser(user);
		
		User readUser = this.userService.readUser(created.getId());
		
		Assert.assertEquals(user, readUser);
	}
	
	@Test
	@InSequence(3)
	public void testReadByName() throws Exception {
		User user = this.userService.readUserByName("Jo");
		Assert.assertEquals("valid@email.com", user.getEmail());
	}
	
	@Test
	@InSequence(4)
    public void testReadAllUser() {
        List<User> allUsers = this.userService.readAllUsers();
        Assert.assertEquals(2, allUsers.size());
        boolean foundJo = false, foundMax=false;
        for (User u : allUsers) {
        	if ("Jo".equals(u.getName())) {
        		foundJo = true;
        	}
        	if ("Max".equals(u.getName())) {
        		foundMax = true;
        	}
        }
        Assert.assertTrue(foundJo && foundMax);
    }
	
	@Test(expected=Exception.class)
	@InSequence(5)
    public void testUniqueName() {
		User user = new User();
		user.setName("Max");
		user.setEmail("valid@email.com");
		user.setPasswordDigest("1234567890qwertzuiop0987654321ZZ");
		this.userService.addUser(user);
	}
	
	@Test
	@InSequence(6)
	public void testRemoveAircraft() throws Exception {
		User jo = this.userService.readUserByName("Jo");
		this.userService.removeUser(jo.getId());
		Assert.assertNull(this.userService.readUser(jo.getId()));
	}
	
}
