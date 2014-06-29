package com.prodyna.pac.ars.frontend.service;

import java.io.IOException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;

import org.jboss.resteasy.util.Base64;

public class ClientAuthenticationSetter implements ClientRequestFilter {

    private String auth;

    public ClientAuthenticationSetter(String username, String password) {
    	
        auth = "Basic " + Base64.encodeBytes(String.format("%s:%s", username, password).getBytes());
    }

    @Override
    public void filter(ClientRequestContext clientRequestContext) throws IOException {
        clientRequestContext.getHeaders().add("Authorization", auth);
    }

}
