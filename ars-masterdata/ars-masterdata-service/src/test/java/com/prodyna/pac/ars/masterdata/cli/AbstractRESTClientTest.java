package com.prodyna.pac.ars.masterdata.cli;

import java.io.IOException;
import java.net.URL;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;

import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.util.Base64;


public class AbstractRESTClientTest {

	@ArquillianResource
	protected URL url;
	
	protected <T> T createService(Class<T> clazz, final String username, final String password) throws Exception {
		ResteasyClient client = new ResteasyClientBuilder().build();

        if (username!=null && !username.isEmpty() && password!=null && !password.isEmpty()) {
            client.register(new ClientRequestFilter() {
				
				@Override
				public void filter(ClientRequestContext requestContext) throws IOException {
					String auth = "Basic " + Base64.encodeBytes(String.format("%s:%s", username, password).getBytes());
					requestContext.getHeaders().add("Authorization", auth);
				}
			});
        }

        ResteasyWebTarget target = client.target(this.url.toURI().resolve("rest"));
        T proxyService = target.proxy(clazz);

        return proxyService;
	}
	protected <T> T createService(Class<T> clazz) throws Exception {
		return this.createService(clazz, null, null);
	}
}
