package com.harry.utils;

import java.lang.reflect.Method;

import com.harry.common.AppContext;
import com.harry.common.Const;

public class SessionUtil {

	private static Object getSessionFromThread() {
		return AppContext.getAppContext().getObject(Const.APP_CONTEXT_SESSION);
	}

	public static void addSession(String key, Object value) {
		Object session = getSessionFromThread();
		if (session == null) {
			return;
		}

		Class<?>[] parameterTypes = new Class[2];
		parameterTypes[0] = String.class;
		parameterTypes[1] = Object.class;

		try {
			Method method = session.getClass().getMethod("setAttribute", parameterTypes);

			Object[] args = new Object[2];
			args[0] = key;
			args[1] = value;

			method.invoke(session, args);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Object getSession(String key) {
		Object session = getSessionFromThread();
		if (session == null) {
			return null;
		}

		Object returnObj = null;
		Class<?>[] parameterTypes = new Class[1];
		parameterTypes[0] = String.class;

		Object[] args = new Object[1];
		args[0] = key;

		try {
			Method method = session.getClass().getMethod("getAttribute", parameterTypes);
			returnObj = method.invoke(session, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnObj;
	}

	public static void invalidate() {
		Object session = getSessionFromThread();
		if (session == null) {
			return;
		}
		try {
			Method method = session.getClass().getMethod("invalidate");
			method.invoke(session);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
