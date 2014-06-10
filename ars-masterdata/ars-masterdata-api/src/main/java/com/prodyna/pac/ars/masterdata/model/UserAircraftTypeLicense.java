package com.prodyna.pac.ars.masterdata.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

public class UserAircraftTypeLicense implements Serializable {

	private static final long serialVersionUID = 5465818023222750247L;

	@Id
	private long id;
	
	@NotNull
	private Date validFrom;
	
	@NotNull
	private Date validUntil;
	
	@NotNull
	@ManyToOne
	private User user;
	
	@ManyToOne
	private AircraftType aircraft;

	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public Date getValidUntil() {
		return validUntil;
	}

	public void setValidUntil(Date validUntil) {
		this.validUntil = validUntil;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public AircraftType getAircraft() {
		return aircraft;
	}

	public void setAircraft(AircraftType aircraft) {
		this.aircraft = aircraft;
	}

}