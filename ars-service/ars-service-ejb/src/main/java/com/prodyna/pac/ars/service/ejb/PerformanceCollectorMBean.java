package com.prodyna.pac.ars.service.ejb;

import java.util.List;
import java.util.Map;

public interface PerformanceCollectorMBean {

	void addPerformanceEntry(String operation, int duration);
	int getRecentPerformanceEntry(String operation);
	
	String printPerformanceEntries();
	
	void clearPerformanceData();
	
	Map<String, List<Integer>> getPerformanceData();
    
	Map<Float, String> listByWorstAverage();
}
