package com.prodyna.pac.ars.masterdata;

import java.util.List;

import com.prodyna.pac.ars.masterdata.model.Aircraft;

public interface AircraftService {

	Aircraft addAircraft(Aircraft aircraft);
	
	List<Aircraft> readAllAircrafts();
}
