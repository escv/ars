package com.prodyna.pac.ars.frontend.sample;

import java.util.List;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

import com.prodyna.pac.ars.frontend.service.AircraftManagementClient;
import com.prodyna.pac.ars.masterdata.model.Aircraft;

public class Sample {

	public static void main(String[] args) {
		RegisterBuiltin.register(ResteasyProviderFactory.getInstance());

		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target("http://localhost:8080/ars-service-rest/rest");

		AircraftManagementClient myService = target.proxy(AircraftManagementClient.class);
		
		List<Aircraft> readAllAircrafts = myService.readAllAircrafts();
		System.out.println(readAllAircrafts);
	}

}
