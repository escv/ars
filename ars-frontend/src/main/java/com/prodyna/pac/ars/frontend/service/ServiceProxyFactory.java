package com.prodyna.pac.ars.frontend.service;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

public class ServiceProxyFactory {

    static {
            RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
    }
    public static String USERNAME;
    public static String PASSWORD;

    public static <T> T createServiceProxy(Class<T> clazz) {

        return createServiceProxy(clazz, USERNAME, PASSWORD);
    }
    
    public static <T> T createServiceProxy(Class<T> clazz, String username, String password) {
        ResteasyClient client = new ResteasyClientBuilder().build();

        if (username!=null && !username.isEmpty() && password!=null && !password.isEmpty()) {
            client.register(new ClientAuthenticationSetter(username, password));
        }

        ResteasyWebTarget target = client.target("http://localhost:8080/ars-service-rest/rest");
        T proxyService = target.proxy(clazz);

        return proxyService;
    }
}
