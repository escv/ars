package com.prodyna.pac.ars.frontend.sample;

import java.util.List;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

import com.prodyna.pac.ars.frontend.service.ClientAuthenticationSetter;
import com.prodyna.pac.ars.masterdata.AircraftService;
import com.prodyna.pac.ars.masterdata.model.Aircraft;

public class Sample {

	public static void main(String[] args) {
		RegisterBuiltin.register(ResteasyProviderFactory.getInstance());

		ResteasyClient client = new ResteasyClientBuilder().build();
		client.register(new ClientAuthenticationSetter("john", "secret"));
		ResteasyWebTarget target = client.target("http://localhost:8080/ars-service-rest/rest");
		AircraftService myService = target.proxy(AircraftService.class);
		
		List<Aircraft> readAllAircrafts = myService.readAllAircrafts();
		System.out.println(readAllAircrafts);
	}

}
