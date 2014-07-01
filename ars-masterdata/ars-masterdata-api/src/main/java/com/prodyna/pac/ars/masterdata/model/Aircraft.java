package com.prodyna.pac.ars.masterdata.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@NamedQueries({
	@NamedQuery(name="Aircraft.findAll", query="SELECT a FROM Aircraft a"),
	@NamedQuery(name="Aircraft.findByName", query="SELECT a FROM Aircraft a WHERE a.name=:name")
})
public class Aircraft implements Serializable {

	private static final long serialVersionUID = 5797153755094884704L;

	@Id
	@GeneratedValue
	private long id;
	
	@NotNull
	@Size(min=3)
	private String name;
	
	@NotNull
	@Size(min=3)
	@Column(unique=true)
	private String tailsign;

	@ManyToOne(optional=false)
	@NotNull
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((tailsign == null) ? 0 : tailsign.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Aircraft other = (Aircraft) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (tailsign == null) {
			if (other.tailsign != null)
				return false;
		} else if (!tailsign.equals(other.tailsign))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Aircraft "+name+", Tailsign "+tailsign;
	}
	
	
}
