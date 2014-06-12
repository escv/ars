package com.prodyna.pac.ars.service.ejb;

public interface PerformanceCollectorMBean {

	void addPerformanceEntry(String operation, int duration);
	int getRecentPerformanceEntry(String operation);
	
	String printPerformanceEntries();
	
	//int getBacklog();
	//void setBacklog(int backlog);
	void clearPerformanceData();
    
}
