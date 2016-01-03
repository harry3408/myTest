package com.harry.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.harry.common.Const;
import com.harry.model.ActionConfig;
import com.harry.model.ModelAndView;

@WebServlet("*.action")
public class DispatherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Map<String, ActionConfig> actionConfigs = new HashMap<String, ActionConfig>();

    public DispatherServlet() {
        super();
        actionConfigs.put("users.action", new ActionConfig("com.harry.Controller.UserController", "users"));
        actionConfigs.put("delete.action", new ActionConfig("com.harry.Controller.UserController", "delete"));
        actionConfigs.put("edit.action", new ActionConfig("com.harry.Controller.UserController", "edit"));
        actionConfigs.put("save.action", new ActionConfig("com.harry.Controller.UserController", "save"));

        actionConfigs.put("login.action", new ActionConfig("com.harry.Controller.UserController", "login"));
        actionConfigs.put("saveLogin.action", new ActionConfig("com.harry.Controller.UserController", "saveLogin"));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String requestURI = uri.substring(request.getContextPath().length() + 1);
        ActionConfig actionCofing = actionConfigs.get(requestURI);
        if (actionCofing != null) {
            String className = actionCofing.getClassName();
            try {
                Class<?>[] parameterType = new Class[2];
                parameterType[0] = Map.class;
                parameterType[1] = Map.class;

                Class<?> cls = Class.forName(className);
                Object controller = cls.newInstance();

                String methodName = actionCofing.getMethodName();
                Method method = cls.getMethod(methodName, parameterType);

                // Get session list from the request.
                Map<String, Object> sessionFromRequest = new HashMap<String, Object>();
                HttpSession session = request.getSession();
                Enumeration<String> sessionNames = session.getAttributeNames();
                while (sessionNames.hasMoreElements()) {
                    String key = sessionNames.nextElement();
                    sessionFromRequest.put(key, session.getAttribute(key));
                }

                // Get request parameter from the request
                Map<String, String> parametersFromRequest = new HashMap<String, String>();
                Enumeration<String> requesParameterNames = request.getParameterNames();
                while (requesParameterNames.hasMoreElements()) {
                    String key = requesParameterNames.nextElement();
                    parametersFromRequest.put(key, request.getParameter(key));
                }
                parametersFromRequest.put(Const.PARAM_CONTEXT_PATH, request.getContextPath());

                Object[] parameterValues = new Object[2];
                parameterValues[0] = sessionFromRequest;
                parameterValues[1] = parametersFromRequest;

                ModelAndView modelAndView = (ModelAndView) method.invoke(controller, parameterValues);

                // set the session list to the request
                HttpSession sessionsToRequest = request.getSession();
                for (Entry<String, Object> entry : modelAndView.getRequestSessions().entrySet()) {
                    sessionsToRequest.setAttribute(entry.getKey(), entry.getValue());
                }

                // set the parameter list to the request
                for (Entry<String, Object> entry : modelAndView.getRequestParameters().entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }

                if (modelAndView.isRedirect()) {
                    response.sendRedirect(modelAndView.getView());
                } else {
                    request.getRequestDispatcher(modelAndView.getView()).forward(request, response);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
