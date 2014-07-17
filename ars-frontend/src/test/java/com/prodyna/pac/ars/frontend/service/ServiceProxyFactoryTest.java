package com.prodyna.pac.ars.frontend.service;

import java.util.List;

import junit.framework.Assert;

import com.prodyna.pac.ars.masterdata.AircraftService;
import com.prodyna.pac.ars.masterdata.AircraftTypeService;
import com.prodyna.pac.ars.masterdata.model.Aircraft;
import com.prodyna.pac.ars.masterdata.model.AircraftType;

public class ServiceProxyFactoryTest {
	
	//@Test
	public void testAircraftServiceProxy() throws Exception {
		AircraftService aircraftService = ServiceProxyFactory.createServiceProxy(AircraftService.class);
		Assert.assertNotNull(aircraftService);
		AircraftTypeService aircraftTypeService = ServiceProxyFactory.createServiceProxy(AircraftTypeService.class);
		Assert.assertNotNull(aircraftTypeService);
		
		AircraftType at = new AircraftType();
		at.setName("A380");
		
		AircraftType savedAircraftType = aircraftTypeService.addAircraftType(at);
		Assert.assertTrue(savedAircraftType.getId()>0);
		
		Aircraft a = new Aircraft();
		a.setName("AC1");
		a.setTailsign("AC2-r45-45");
		a.setType(savedAircraftType);
		
		Aircraft savedAircraft = aircraftService.addAircraft(a);
		Assert.assertTrue(savedAircraft.getId()>0);
		
		List<Aircraft> readAllAircrafts = aircraftService.readAllAircrafts();
		Assert.assertTrue(readAllAircrafts.size()>0);
	}
}
