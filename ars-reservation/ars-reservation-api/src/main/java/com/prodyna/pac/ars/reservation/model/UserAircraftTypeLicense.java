package com.prodyna.pac.ars.reservation.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.prodyna.pac.ars.masterdata.model.AircraftType;
import com.prodyna.pac.ars.masterdata.model.User;

@Entity
@NamedQueries({
	@NamedQuery(name="License.findAll", 
			query="SELECT l FROM UserAircraftTypeLicense l"),
	@NamedQuery(name="License.findByUserId", 
			query="SELECT l FROM UserAircraftTypeLicense l WHERE l.user.id=:userId"),
	@NamedQuery(name="License.findByAircraftType", 
			query="SELECT l FROM UserAircraftTypeLicense l WHERE l.aircraftType.id=:actId"),
	@NamedQuery(name="License.findValidByUserAircraftType", 
			query="SELECT l FROM UserAircraftTypeLicense l WHERE l.aircraftType.id=:actId AND l.user.id=:userId AND l.validFrom<:validFrom AND l.validUntil>:validUntil")
})
public class UserAircraftTypeLicense implements Serializable {

	private static final long serialVersionUID = 5465818023222750247L;

	@Id
	@GeneratedValue
	private long id;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date validFrom;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date validUntil;
	
	@NotNull
	@ManyToOne
	private User user;
	
	@NotNull
	@ManyToOne
	private AircraftType aircraftType;

	
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

	public AircraftType getAircraftType() {
		return aircraftType;
	}

	public void setAircraftType(AircraftType aircraft) {
		this.aircraftType = aircraft;
	}

}