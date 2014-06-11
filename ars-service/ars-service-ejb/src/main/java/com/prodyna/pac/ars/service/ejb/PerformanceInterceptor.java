package com.prodyna.pac.ars.service.ejb;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@PerformanceMonitored
@Interceptor
public class PerformanceInterceptor {
	@AroundInvoke
	public Object collectPerformance(InvocationContext ctx) throws Exception {
		System.out.println("WHOAA");
		return ctx.proceed();
	}

}
