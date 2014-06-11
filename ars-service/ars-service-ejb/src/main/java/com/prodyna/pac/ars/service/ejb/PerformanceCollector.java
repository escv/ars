package com.prodyna.pac.ars.service.ejb;

import java.lang.management.ManagementFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.slf4j.Logger;

@Singleton
@LocalBean
@Startup
public class PerformanceCollector implements PerformanceCollectorMBean {
	
	@Inject
    private Logger logger;
	private ObjectName objectName;
	
	@Override
	public void addPerformanceEntry(String operation, int duration) {

	}

	@Override
	public int getPerformanceEntry(String operation) {
		return 0;
	}

	@Override
	public String printPerformanceEntries() {
		return "Hello World";
	}

	@Override
	public int getBacklog() {
		return 0;
	}

	@Override
	public void setBacklog(int backlog) {

	}

	@PostConstruct
    public void registerInJMX() {        

        logger.info( "Registering in JMX Console" );
        try {
            objectName = new ObjectName( "ars:service=PerformanceCollectorMBean" );
            MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
            mBeanServer.registerMBean( this, objectName );
        } catch (InstanceAlreadyExistsException e) {
        	logger.warn("PerformanceCollector already exists. Ignoring this exception");
        } catch ( Exception e ) {
        	logger.error("Error while registering PerformanceCollector");
            throw new IllegalStateException( "Problem during registration into JMX:" + e );
        }
    }

    @PreDestroy
    public void unregisterFromJMX() {

        logger.info( "Unregistering in JMX Console" );

        try {
            ManagementFactory.getPlatformMBeanServer().unregisterMBean( this.objectName );
        } catch (InstanceNotFoundException e) {
        	logger.warn("Cannot unregister unavailable PerformanceCollector");
        } catch ( Exception e ) {
        	logger.error("Error while unregistering PerformanceCollector");
            throw new IllegalStateException( "Problem during unregistration of JMX:" + e );
        }
    }
}
