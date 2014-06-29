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

import com.prodyna.pac.ars.masterdata.model.AircraftType;

@RunWith(Arquillian.class)
public class AircraftTypeServiceBeanTest {

	@Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
        	.addPackages(true, "com.prodyna.pac.ars.masterdata")
        	.addPackages(true, "com.prodyna.pac.ars.service")
        	.addAsResource("META-INF/persistence.xml")
        	.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }
	
	@Inject
    private AircraftTypeService aircraftTypeService;
		
	@Test(expected=Exception.class)
	public void testCreateNull() {
		this.aircraftTypeService.addAircraftType(null);
	}
	
	@Test(expected=Exception.class)
	public void testUpdateNull() {
		this.aircraftTypeService.updateAircraftType(null);
	}
	
	@Test(expected=Exception.class)
	public void testInvalidObject() {
		AircraftType u = new AircraftType();
		u.setName("A");
		this.aircraftTypeService.addAircraftType(u);
	}
	
	@Test(expected=Exception.class)
	public void testDidntFindByName() throws Exception {
		Assert.assertNull(this.aircraftTypeService.readAircraftTypeByName("YET A TEST"));
	}
	
	@Test
	@InSequence(1)
	public void testCreateAircraftType() throws Exception {
		AircraftType AircraftType = new AircraftType();
		AircraftType.setName("ACT1");
		AircraftType created = this.aircraftTypeService.addAircraftType(AircraftType);
		Assert.assertTrue(created.getId()>0);
	}
	
	@Test
	@InSequence(2)
	public void testReadSingle() throws Exception {
		AircraftType aircraftType = new AircraftType();
		aircraftType.setName("ACT2");
		AircraftType created = this.aircraftTypeService.addAircraftType(aircraftType);
		
		AircraftType readAircraftType = this.aircraftTypeService.readAircraftType(created.getId());
		
		Assert.assertEquals(aircraftType, readAircraftType);
	}
	
	@Test
	@InSequence(3)
	public void testReadByName() throws Exception {
		AircraftType AircraftType = this.aircraftTypeService.readAircraftTypeByName("ACT1");
		Assert.assertEquals("ACT1", AircraftType.getName());
	}
	
	@Test
	@InSequence(4)
    public void testReadAllAircraftType() {
        List<AircraftType> allAircraftTypes = this.aircraftTypeService.readAllAircraftTypes();
        Assert.assertEquals(2, allAircraftTypes.size());
        boolean foundACT1 = false, foundACT2=false;
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
	
	@Test(expected=Exception.class)
	@InSequence(5)
    public void testUniqueName() {
		AircraftType aircraftType = new AircraftType();
		aircraftType.setName("ACT2");
		this.aircraftTypeService.addAircraftType(aircraftType);
	}
	
	@Test
	@InSequence(6)
	public void testRemoveAircraft() throws Exception {
		AircraftType act = this.aircraftTypeService.readAircraftTypeByName("ACT1");
		this.aircraftTypeService.removeAircraftType(act.getId());
		Assert.assertNull(this.aircraftTypeService.readAircraftType(act.getId()));
	}
	
}
