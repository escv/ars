package com.prodyna.pac.ars.masterdata;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserRole implements Serializable {

	private static final long serialVersionUID = 8056762319269561109L;

	@Id
	private long id;
	
	private String name;

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
	
}
