package com.prodyna.pac.ars.masterdata.cli;

import java.util.List;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.ars.masterdata.AircraftTypeService;
import com.prodyna.pac.ars.masterdata.model.AircraftType;
import com.prodyna.pac.ars.service.rest.ArsApp;

@RunWith(Arquillian.class)
public class AircraftTypeServiceClientTest extends AbstractRESTClientTest {

	@Deployment
	public static WebArchive createDeployment() {
		WebArchive deployable = ShrinkWrap.create(WebArchive.class, "test.war").addPackages(true, "com.prodyna.pac.ars.masterdata")
				.addPackages(true, "com.prodyna.pac.ars.service").addAsResource("META-INF/persistence.xml")
				.addAsManifestResource("META-INF/ejb-jar.xml").addAsManifestResource("META-INF/jboss-ejb3.xml")
				.setWebXML("WEB-INF/web.xml").addClass(ArsApp.class)
				.addAsResource("roles.properties").addAsResource("users.properties")
				.addAsResource(EmptyAsset.INSTANCE, "META-INF/beans.xml");
		return deployable;
	}

	@Test
	@InSequence(1)
	@RunAsClient
	public void testCreateAircraftType() throws Exception {
		AircraftTypeService service = createService(AircraftTypeService.class, "john", "secret");
		AircraftType at = new AircraftType();
		at.setName("A320");
		AircraftType addedACT = service.addAircraftType(at);
		Assert.assertTrue(addedACT.getId()>0);
	}

	@Test
	@InSequence(2)
	@RunAsClient
	public void testReadAircrafts() throws Exception {
		AircraftTypeService service = createService(AircraftTypeService.class, "john", "secret");

		List<AircraftType> aircraftTypes = service.readAllAircraftTypes();
		boolean foundAt = false;
		for(AircraftType at : aircraftTypes) {
			if ("A320".equals(at.getName())) {
				foundAt = true;
				break;
			}
		}
		Assert.assertTrue(foundAt);
	}
	
	@Test
	@InSequence(3)
	@RunAsClient
	public void testRemoveAircraftType() throws Exception {
		AircraftTypeService service = createService(AircraftTypeService.class, "john", "secret");
		
		service.removeAircraftType(service.readAircraftTypeByName("A320").getId());
		
		List<AircraftType> aircraftTypes = service.readAllAircraftTypes();
		boolean foundAt = false;
		for(AircraftType at : aircraftTypes) {
			if ("A320".equals(at.getName())) {
				foundAt = true;
				break;
			}
		}
		Assert.assertFalse(foundAt);
	}
}
