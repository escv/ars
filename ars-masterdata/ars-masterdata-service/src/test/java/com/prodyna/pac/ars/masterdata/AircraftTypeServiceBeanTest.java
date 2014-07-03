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

import com.prodyna.pac.ars.masterdata.model.AircraftType;

@RunWith(Arquillian.class)
public class AircraftTypeServiceBeanTest extends SecuredTest {

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class).addPackages(true, "com.prodyna.pac.ars.masterdata")
				.addPackages(true, "com.prodyna.pac.ars.service").addAsResource("META-INF/persistence.xml")
				.addAsResource("META-INF/ejb-jar.xml").addAsResource("META-INF/jboss-ejb3.xml")
				.addAsResource("roles.properties").addAsResource("users.properties")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@Inject
	private AircraftTypeService aircraftTypeService;

	@Test(expected=EJBException.class)
	public void testDidntFindById() throws Exception {
		this.aircraftTypeService.readAircraftType(82L);
	}
	
	@Test(expected = Exception.class)
	public void testCreateNull() {
		aircraftTypeService.addAircraftType(null);
	}

	@Test(expected = Exception.class)
	public void testUpdateNull() {
		aircraftTypeService.updateAircraftType(null);
	}

	@Test(expected = Exception.class)
	public void testInvalidObject() {
		AircraftType u = new AircraftType();
		u.setName("A");
		aircraftTypeService.addAircraftType(u);
	}

	@Test(expected=EJBException.class)
	public void testDidntFindByName() throws Exception {
		aircraftTypeService.readAircraftTypeByName("YET A TEST");
	}

	@Test
	@InSequence(1)
	public void testCreateAircraftType() throws Exception {
		AircraftType AircraftType = new AircraftType();
		AircraftType.setName("ACT1");
		AircraftType created = aircraftTypeService.addAircraftType(AircraftType);
		Assert.assertTrue(created.getId() > 0);
	}

	@Test
	@InSequence(2)
	public void testReadSingle() throws Exception {
		AircraftType aircraftType = new AircraftType();
		aircraftType.setName("ACT2");
		AircraftType created = aircraftTypeService.addAircraftType(aircraftType);

		AircraftType readAircraftType = aircraftTypeService.readAircraftType(created.getId());

		Assert.assertEquals(aircraftType, readAircraftType);
	}

	@Test
	@InSequence(3)
	public void testReadByName() throws Exception {
		AircraftType AircraftType = aircraftTypeService.readAircraftTypeByName("ACT1");
		Assert.assertEquals("ACT1", AircraftType.getName());
	}

	@Test
	@InSequence(4)
	public void testReadAllAircraftType() {
		List<AircraftType> allAircraftTypes = aircraftTypeService.readAllAircraftTypes();
		Assert.assertEquals(2, allAircraftTypes.size());
		boolean foundACT1 = false, foundACT2 = false;
		for (AircraftType u : allAircraftTypes) {
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
		AircraftType aircraftType = new AircraftType();
		aircraftType.setName("ACT2");
		aircraftTypeService.addAircraftType(aircraftType);
	}

	@Test
	@InSequence(6)
	public void testRemoveAircraft() throws Exception {
		AircraftType act = aircraftTypeService.readAircraftTypeByName("ACT1");
		aircraftTypeService.removeAircraftType(act.getId());
		try {
			this.aircraftTypeService.readAircraftType(act.getId());
			Assert.fail("No Exception thrown for reading not existent entity");
		} catch (EJBException e) {
			
		}
	}

}
