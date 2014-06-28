package com.prodyna.pac.ars.reservation.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.prodyna.pac.ars.service.ejb.ModificationType;

@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=ValidReservationValidator.class)
@Documented
public @interface ValidReservation {

	String message() default "{Invalid Reservation}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	ModificationType value();
}
