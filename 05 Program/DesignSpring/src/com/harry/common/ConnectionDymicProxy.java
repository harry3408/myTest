package com.harry.common;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;

import com.harry.exception.DBException;
import com.harry.utils.DBUtil;

public class ConnectionDymicProxy implements InvocationHandler {
    private final Object target;

    public ConnectionDymicProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object returnObj = null;
        ConnectionHoder hoder = (ConnectionHoder) AppContext.getAppContext().getObject(Const.APP_CONTEXT_CONNECTHODER);
        boolean needClose = false;
        if (hoder == null) {
            hoder = new ConnectionHoder();
            hoder.setConn(DBUtil.getConnection());

            if ("create".equalsIgnoreCase(method.getName())) {
                hoder.getConn().setAutoCommit(false);
                hoder.setStartTranction(true);
            }

            AppContext.getAppContext().setObjects(Const.APP_CONTEXT_CONNECTHODER, hoder);
            needClose = true;
        } else {
            if ("create".equalsIgnoreCase(method.getName()) && !hoder.isStartTranction()) {
                hoder.getConn().setAutoCommit(false);
                hoder.setStartTranction(true);
            }
        }

        try {
            returnObj = method.invoke(target, args);
            if (hoder.isStartTranction()) {
                hoder.getConn().commit();
            }
            return returnObj;
        } catch (Exception e) {
            if (hoder.isStartTranction()) {
                hoder.getConn().rollback();
            }
            throw new DBException("Internal error", e);
        } finally {
            if (needClose) {
                DBUtil.close(null, null, hoder.getConn());
                hoder.setConn(null);
                hoder = null;
                AppContext.clean();
            }
        }
    }
}
