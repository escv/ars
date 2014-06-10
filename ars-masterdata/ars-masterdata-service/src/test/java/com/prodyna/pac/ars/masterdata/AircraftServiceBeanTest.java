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
        	.addAsResource("META-INF/persistence.xml")
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }
	
	@Inject
    AircraftService aircraftService;
	
	@Test
    public void testReadAllAircrafts() {
        List<Aircraft> allAircrafts = this.aircraftService.readAllAircrafts();
        Assert.assertNotNull(allAircrafts);
    }
	
}
