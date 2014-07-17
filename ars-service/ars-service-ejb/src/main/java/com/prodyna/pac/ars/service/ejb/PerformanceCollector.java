package com.prodyna.pac.ars.service.ejb;

import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

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
	private Map<String, List<Integer>> performanceData = new HashMap<>();
	
	@Override
	public void addPerformanceEntry(String operation, int duration) {
		if (duration>0 && operation!=null && !operation.isEmpty()) {
			List<Integer> data = performanceData.get(operation);
			if (data == null) {
				data = new LinkedList<>();
				performanceData.put(operation, data);
			}
			data.add(duration);
		}
	}

	@Override
	public int getRecentPerformanceEntry(String operation) {
		int result = -1;
		List<Integer> entries = this.performanceData.get(operation);
		if (entries != null) {
			result = entries.get(entries.size()-1);
		}
		return result;
	}

	@Override
	public String printPerformanceEntries() {
		StringBuilder result = new StringBuilder();
		for (Entry<String, List<Integer>> e : performanceData.entrySet()) {
			result.append("--------------------------------------------------\r\n");
			result.append(e.getKey()+"\r\n");
			result.append("\t\tCount: "+e.getValue().size()+"\r\n");
			result.append("\t\tAverage: "+average(e.getValue())+"\r\n");
			result.append("\t\tLast Calls: ");
			for (int i=e.getValue().size()-1; i>= Math.max(e.getValue().size()-10, 0) ; i--) {
				result.append(e.getValue().get(i)+" ");
			}
			result.append("\r\n");
		}
		return result.toString();
	}
	
	private float average(Iterable<Integer> list) {
		int sum = 0;
		int count = 0;
		for (Integer number : list) {
			sum += number;
			count ++;
		}
		return (float)sum/(float)count;
	}
	
	@Override
	public void clearPerformanceData() {
		this.performanceData.clear();
		
	}
	
	@PostConstruct
    public void registerInJMX() {        

        logger.info( "Registering in JMX Console" );
        try {
            objectName = new ObjectName( "com.prodyna.pac.ars:service=PerformanceCollectorMBean" );
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

    @Override
	public Map<String, List<Integer>> getPerformanceData() {
		return performanceData;
	}

    @Override
    public Map<Float, String> listByWorstAverage() {
    	
    	TreeMap<Float, String> map = new TreeMap<>();
    	for (Entry<String, List<Integer>> e : performanceData.entrySet()) {
    		map.put(average(e.getValue()), e.getKey());
    	}

    	return map.descendingMap();
    }
    
    
}
