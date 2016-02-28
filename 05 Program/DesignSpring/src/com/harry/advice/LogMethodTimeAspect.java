package com.harry.advice;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

import com.harry.common.AppContext;
import com.harry.common.Const;

public class LogMethodTimeAspect {
    private final Logger logger = Logger.getLogger(LogMethodTimeAspect.class);

    public void doBefore(JoinPoint jp) {
        logger.info("...doBefore...");
    }

    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("Invoke method");
        long startTime = System.currentTimeMillis();

        Object returnVal = pjp.proceed();

        long endTime = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        sb.append(AppContext.getAppContext().getObject(Const.APP_CONTEXT_USER_NAME));
        sb.append(":");
        sb.append(pjp.getTarget().getClass().getSimpleName());
        sb.append(":");
        sb.append(pjp.getSignature().getName());
        sb.append(":");
        sb.append(startTime - endTime);
        logger.info(sb.toString());

        return returnVal;
    }

    public void doAfter(JoinPoint jp) {
        logger.info("...doAfter...");
    }
}
