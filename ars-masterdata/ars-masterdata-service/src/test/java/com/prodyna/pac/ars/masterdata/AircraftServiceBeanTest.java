/**
 * 
 */
package com.prodyna.pac.ars.masterdata;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.ars.masterdata.model.Aircraft;

@RunWith(Arquillian.class)
public class AircraftServiceBeanTest {

	@Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
        	.addPackages(true, "com.prodyna.pac.ars.masterdata")
        	.addPackages(true, "com.prodyna.pac.ars.service")
        	.addAsResource("META-INF/persistence.xml")
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }
	
	@Inject
    private AircraftService aircraftService;
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateNull() {
		this.aircraftService.addAircraft(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testUpdateNull() {
		this.aircraftService.updateAircraft(null);
	}
	
	@Test(expected=Exception.class)
	public void testInvalidObject() {
		Aircraft a = new Aircraft();
		a.setName("A");
		a.setTailsign("B");
		this.aircraftService.addAircraft(a);
	}

	@Test
	public void testCreateAircraft() throws Exception {
		Aircraft a = new Aircraft();

		a.setName("AC1");
		a.setTailsign("BTSK-AS-df34");
		Aircraft created = this.aircraftService.addAircraft(a);
		Assert.assertTrue(created.getId()>0);
	}
	
	@Test
    public void testReadAllAircrafts() {
        List<Aircraft> allAircrafts = this.aircraftService.readAllAircrafts();
        Assert.assertNotNull(allAircrafts);
    }
	
}
