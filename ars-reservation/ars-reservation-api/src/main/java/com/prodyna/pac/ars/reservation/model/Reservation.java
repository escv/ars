package com.prodyna.pac.ars.reservation.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.prodyna.pac.ars.masterdata.model.Aircraft;
import com.prodyna.pac.ars.masterdata.model.User;

public class Reservation implements Serializable {


	private static final long serialVersionUID = 7141077664027679705L;
	
	@Id
	private long id;
	
	@Enumerated(EnumType.STRING)
	private ReservationState state;
	
	private Date created;
	
	private Date begin;
	
	private Date end;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Aircraft aircraft;
	
	
	
}
