/**
 * 
 */
package com.prodyna.pac.ars.masterdata;

import java.util.List;

import javax.ejb.EJBException;
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

import com.prodyna.pac.ars.masterdata.model.Aircraft;
import com.prodyna.pac.ars.masterdata.model.AircraftType;

@RunWith(Arquillian.class)
public class AircraftServiceBeanTest extends SecuredTest {

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class).addPackages(true, "com.prodyna.pac.ars.masterdata")
				.addPackages(true, "com.prodyna.pac.ars.service").addAsResource("META-INF/persistence.xml")
				.addAsResource("META-INF/ejb-jar.xml").addAsResource("META-INF/jboss-ejb3.xml")
				.addAsResource("roles.properties").addAsResource("users.properties")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@Inject
	private AircraftService aircraftService;

	@Inject
	private AircraftTypeService aircraftTypeService;

	@Test(expected = Exception.class)
	public void testCreateNull() {
		aircraftService.addAircraft(null);
	}

	@Test(expected = Exception.class)
	public void testUpdateNull() {
		aircraftService.updateAircraft(null);
	}

	@Test(expected = Exception.class)
	public void testInvalidObject() {
		Aircraft a = new Aircraft();
		a.setName("A");
		a.setTailsign("B");
		this.aircraftService.addAircraft(a);
	}

	@Test(expected=EJBException.class)
	public void testDidntFindById() throws Exception {
		this.aircraftService.readAircraft(82L);
	}

	@Test(expected=EJBException.class)
	public void testDidntFindByName() throws Exception {
		this.aircraftService.readAircraftByName("YET A TEST");
	}

	@Test
	@InSequence(1)
	public void testCreateAircraft() throws Exception {
		Aircraft a = new Aircraft();
		a.setName("AC1");
		a.setTailsign("BTSK-AS-df34");
		AircraftType type = new AircraftType();
		type.setName("A380");
		a.setType(aircraftTypeService.addAircraftType(type));

		Aircraft created = aircraftService.addAircraft(a);
		Assert.assertTrue(created.getId() > 0);
	}

	@Test
	@InSequence(2)
	public void testReadSingle() throws Exception {
		Aircraft a = new Aircraft();
		a.setName("AC2");
		a.setTailsign("ZTSK-AS-YV34");
		AircraftType type = new AircraftType();
		type.setName("A340");
		a.setType(aircraftTypeService.addAircraftType(type));
		Aircraft created = aircraftService.addAircraft(a);

		Aircraft readAircraft = aircraftService.readAircraft(created.getId());

		Assert.assertEquals(a, readAircraft);
	}

	@Test
	@InSequence(3)
	public void testReadByName() throws Exception {
		Aircraft aircraft = this.aircraftService.readAircraftByName("AC1");
		Assert.assertEquals("BTSK-AS-df34", aircraft.getTailsign());
	}

	@Test
	@InSequence(4)
	public void testReadAllAircrafts() {
		List<Aircraft> allAircrafts = aircraftService.readAllAircrafts();
		Assert.assertEquals(2, allAircrafts.size());
		boolean foundAC1 = false, foundAC2 = false;
		for (Aircraft a : allAircrafts) {
			if ("AC1".equals(a.getName())) {
				foundAC1 = true;
			}
			if ("AC2".equals(a.getName())) {
				foundAC2 = true;
			}
		}
		Assert.assertTrue(foundAC1 && foundAC2);
	}

	@Test
	@InSequence(5)
	public void testRemoveAircraft() throws Exception {
		Aircraft aircraft = this.aircraftService.readAircraftByName("AC1");
		this.aircraftService.removeAircraft(aircraft.getId());
		try {
			this.aircraftService.readAircraft(aircraft.getId());
			Assert.fail("No Exception thrown for reading not existent entity");
		} catch (EJBException e) {
			System.out.println("Alles Gut");
		}
	}

}
