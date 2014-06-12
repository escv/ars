package com.prodyna.pac.ars.masterdata;

import java.util.List;

import com.prodyna.pac.ars.masterdata.model.AircraftType;

public interface AircraftTypeService {

	AircraftType addAircraftType(AircraftType aircraftType);
	
	void updateAircraftType(AircraftType aircraft);
	
	List<AircraftType> readAllAircraftTypes();
	
	AircraftType readAircraftType(long id);
	
	void removeAircraftType(long id);
}
