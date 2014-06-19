package com.prodyna.pac.ars.masterdata;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.prodyna.pac.ars.masterdata.model.AircraftType;

public interface AircraftTypeService {

	AircraftType addAircraftType(@NotNull @Valid AircraftType aircraftType);
	
	void updateAircraftType(@NotNull @Valid  AircraftType aircraft);
	
	List<AircraftType> readAllAircraftTypes();
	
	AircraftType readAircraftType(@Min(1) long id);
	
	void removeAircraftType(@Min(1) long id);
	
	AircraftType readAircraftTypeByName(@Size(min=3) String name);
}
