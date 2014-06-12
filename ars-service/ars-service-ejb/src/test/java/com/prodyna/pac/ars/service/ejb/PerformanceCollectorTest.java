package com.prodyna.pac.ars.service.ejb;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PerformanceCollectorTest {

	private PerformanceCollectorMBean collector;
	
	@Before
	public void setup() {
		this.collector = new PerformanceCollector();
	}

	@Test
	public void testAddingIllegalValues() throws Exception {
		this.collector.addPerformanceEntry("sdf", -3);
		Assert.assertTrue(this.collector.printPerformanceEntries().isEmpty());
		
		this.collector.addPerformanceEntry("", 4);
		Assert.assertTrue(this.collector.printPerformanceEntries().isEmpty());
	}
	
	@Test
	public void testRecentEntry() throws Exception {
		this.collector.addPerformanceEntry("ABC", 3);
		Assert.assertEquals(3, this.collector.getRecentPerformanceEntry("ABC"));

		this.collector.addPerformanceEntry("ABC", 5);
		Assert.assertEquals(5, this.collector.getRecentPerformanceEntry("ABC"));
	}
	
	@Test
	public void testDumpOut() throws Exception {
		this.collector.addPerformanceEntry("ABC", 3);
		this.collector.addPerformanceEntry("ABC", 5);
		this.collector.addPerformanceEntry("ABC", 7);
		String result = this.collector.printPerformanceEntries();
		
		Assert.assertTrue(result.contains("Count: 3"));
		Assert.assertTrue(result.contains("Average: 5"));
	}
}
