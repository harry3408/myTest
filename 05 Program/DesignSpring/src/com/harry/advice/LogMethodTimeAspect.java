package com.harry.advice;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;

import com.harry.common.AppContext;
import com.harry.common.Const;
import com.harry.service.impl.LogMethodTime;

public class LogMethodTimeAspect {
	private final Logger logger = Logger.getLogger(LogMethodTimeAspect.class);

	public void doBefore() {
		logger.info("...dobefore...");
	}

	public Object doAround(MethodInvocation invocation) throws Throwable {
		System.out.println("Invoke method");
		long startTime = System.currentTimeMillis();

		Object returnVal = invocation.proceed();

		long endTime = System.currentTimeMillis();
		StringBuilder sb = new StringBuilder();
		sb.append(AppContext.getAppContext().getObject(Const.APP_CONTEXT_USER_NAME));
		sb.append(":");
		sb.append(invocation.getMethod().getDeclaringClass().getSimpleName());
		sb.append(":");
		sb.append(invocation.getMethod().getName());
		sb.append(":");
		sb.append(startTime - endTime);
		logger.info(sb.toString());

		return returnVal;
	}

	public void doAfter() {
		logger.info("...doAfter...");
	}

	public void afterThrowing() {
		logger.info("...doThrowing...");
	}
}
