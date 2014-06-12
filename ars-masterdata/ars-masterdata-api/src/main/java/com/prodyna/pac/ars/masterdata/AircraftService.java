package com.prodyna.pac.ars.masterdata;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.prodyna.pac.ars.masterdata.model.Aircraft;

public interface AircraftService {

	Aircraft addAircraft(@NotNull @Valid Aircraft aircraft);
	
	void updateAircraft(@NotNull @Valid Aircraft aircraft);
	
	@NotNull List<Aircraft> readAllAircrafts();
	
	Aircraft readAircraft(@Min(1) long id);
	
	void removeAircraft(@Min(1) long id);
}
