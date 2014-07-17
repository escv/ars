package com.prodyna.pac.ars.masterdata.cli;

import java.util.List;

import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotAuthorizedException;

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
public class SecurityClientTest extends AbstractRESTClientTest {

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

	@InSequence(1)
	@Test
	@RunAsClient
	public void testAdminCanCreate() throws Exception {
		AircraftTypeService service = createService(AircraftTypeService.class, "john", "secret");
		AircraftType at = new AircraftType();
		at.setName("A320");
		AircraftType addedACT = service.addAircraftType(at);
		Assert.assertTrue(addedACT.getId()>0);
	}


	@InSequence(2)
	@Test
	@RunAsClient
	public void testGuestCanRead() throws Exception {
		// no password = not Auth Header
		AircraftTypeService service = createService(AircraftTypeService.class);
		List<AircraftType> aircraftTypes = service.readAllAircraftTypes();
		Assert.assertFalse(aircraftTypes.isEmpty());
		
		for (AircraftType at : aircraftTypes) {
			AircraftType aircraftTypeByName = service.readAircraftTypeByName(at.getName());
			Assert.assertTrue(aircraftTypeByName.getId()>0);
		}
	}
	
	@InSequence(3)
	@Test(expected=NotAuthorizedException.class)
	@RunAsClient
	public void testGuestMustNotCreate() throws Exception {
		// no password = not Auth Header
		AircraftTypeService service = createService(AircraftTypeService.class);
		AircraftType at = new AircraftType();
		at.setName("A320");
		service.addAircraftType(at);
	}
	
	@InSequence(4)
	@Test(expected=NotAuthorizedException.class)
	@RunAsClient
	public void testGuestMustNotRemove() throws Exception {
		// no password = not Auth Header
		AircraftTypeService service = createService(AircraftTypeService.class);
		AircraftType act = service.readAircraftTypeByName("A320");
		service.removeAircraftType(act.getId());
	}
	
	@InSequence(5)
	@Test(expected=NotAllowedException.class)
	@RunAsClient
	public void testPilotMustNotCreate() throws Exception {
		// no password = not Auth Header
		AircraftTypeService service = createService(AircraftTypeService.class, "pilot", "secret");
		AircraftType at = new AircraftType();
		at.setName("A320");
		service.addAircraftType(at);
	}
	
	@InSequence(6)
	@Test(expected=NotAllowedException.class)
	@RunAsClient
	public void testPilotMustNotRemove() throws Exception {
		// no password = not Auth Header
		AircraftTypeService service = createService(AircraftTypeService.class, "pilot", "secret");
		AircraftType act = service.readAircraftTypeByName("A320");
		service.removeAircraftType(act.getId());
	}
}
