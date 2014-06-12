package com.prodyna.pac.ars.service.ejb;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@PerformanceMonitored
@Interceptor
public class PerformanceInterceptor {
	
	@Inject
	private PerformanceCollector performanceCollector;
	
	@AroundInvoke
	public Object collectPerformance(InvocationContext ctx) throws Exception {
		long now = System.currentTimeMillis();
		Object result = ctx.proceed();
		
		// save to cast to int because we only look for the difference of two timestamps
		this.performanceCollector.addPerformanceEntry(ctx.getMethod().toString(), (int)(System.currentTimeMillis()-now));
		return result;
	}

}
