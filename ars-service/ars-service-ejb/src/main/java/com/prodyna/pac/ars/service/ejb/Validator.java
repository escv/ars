package com.prodyna.pac.ars.service.ejb;



/**
 * @author aalbert
 *
 * @param <T> The entity type to validate (defined by implementing subclasses)
 */
public interface Validator <T> {

	/**
	 * 
	 * @param entry the entity to validate
	 * @return an error string if entry is invalid, null if everything is fine
	 */
	String validate(T entry);
}
