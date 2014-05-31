package com.prodyna.pac.ars.masterdata.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User implements Serializable {

	private static final long serialVersionUID = -7911283610125463719L;

	@Id
	private long id;
	
	private String name;
	
	private String email;
	
	
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
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
