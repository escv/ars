package com.prodyna.pac.ars.masterdata.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Aircraft implements Serializable {

	private static final long serialVersionUID = 5797153755094884704L;

	@Id
	private long id;
	
	private String name;
	
	private String tailsign;
	
	
	@ManyToOne
	private AircraftType type;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public AircraftType getType() {
		return type;
	}

	public void setType(AircraftType type) {
		this.type = type;
	}

	public String getTailsign() {
		return tailsign;
	}

	public void setTailsign(String tailsign) {
		this.tailsign = tailsign;
	}

	
	
}
