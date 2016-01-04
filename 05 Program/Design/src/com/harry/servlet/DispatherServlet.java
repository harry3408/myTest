package com.harry.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.harry.common.Const;
import com.harry.model.ActionConfig;
import com.harry.model.ModelAndView;
import com.harry.model.ResultConfig;
import com.harry.model.ViewParamter;
import com.harry.utils.StringUtil;

@WebServlet("*.action")
public class DispatherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final String suffix = ".action";
    private Map<String, ActionConfig> actionConfigs = new HashMap<String, ActionConfig>();

    public DispatherServlet() {
        super();
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder;

            documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Document document = documentBuilder.parse(DispatherServlet.class
                    .getResourceAsStream("/com/harry/Action.xml"));
            NodeList actionNodeList = document.getElementsByTagName("action");
            for (int index = 0; index < actionNodeList.getLength(); index++) {
                Element actionElement = (Element) actionNodeList.item(index);
                String name = actionElement.getAttribute("name");
                String className = actionElement.getAttribute("class");
                String methodName = actionElement.getAttribute("method");
                String httpMethodStr = actionElement.getAttribute("httpMethod");
                if(StringUtil.isEmpty(httpMethodStr)) {
                    httpMethodStr = "GET";
                }
                String[] httpMethodArr = httpMethodStr.split(",");
                ActionConfig actionConfig = new ActionConfig(className, methodName, httpMethodArr);
                NodeList resultNodeList = actionElement.getElementsByTagName("result");
                int resultNodeListCount = resultNodeList.getLength();
                if (resultNodeListCount > 0) {
                    List<ResultConfig> resultConfigs = new ArrayList<ResultConfig>();
                    for (int i = 0; i < resultNodeListCount; i++) {
                        Element resultElement = (Element) resultNodeList.item(i);

                        String resultName = resultElement.getAttribute("name");
                        String resultView = resultElement.getAttribute("view");
                        String redirect = resultElement.getAttribute("redirect");
                        if(StringUtil.isEmpty(redirect)) {
                            redirect = "false";
                        }

                        String resultParameterStr = resultElement.getAttribute("viewParameter");
                        List<ViewParamter> viewParamters = new ArrayList<ViewParamter>();
                        if (!StringUtil.isEmpty(resultParameterStr)) {
                            String[] resultParameterArr = resultParameterStr.split(",");
                            for (int j = 0; j < resultParameterArr.length; j++) {
                                String[] parameterArr = resultParameterArr[i].split(":");
                                String parameterName = parameterArr[0].trim();
                                String parameterFrom = parameterArr[1].trim();
                                viewParamters.add(new ViewParamter(parameterName, parameterFrom));
                            }
                        }
                        ResultConfig resultConfig = new ResultConfig(resultName, resultView, Boolean.parseBoolean(redirect));
                        resultConfig.setViewParamters(viewParamters);
                        resultConfigs.add(resultConfig);
                    }
                    actionConfig.setResultConfigs(resultConfigs);
                }
                for(int j = 0; j < httpMethodArr.length; j ++) {
                    actionConfigs.put(name + suffix + "#" + httpMethodArr[j], actionConfig);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String httpMethod = request.getMethod();
        String requestURI = uri.substring(request.getContextPath().length() + 1);
        ActionConfig actionCofing = actionConfigs.get(requestURI + "#" + httpMethod.toUpperCase());
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

                List<ResultConfig> resultConfigs = actionCofing.getResultConfigs();
                if (resultConfigs.isEmpty()) {
                    if (modelAndView.isRedirect()) {
                        response.sendRedirect(modelAndView.getView());
                    } else {
                        request.getRequestDispatcher(modelAndView.getView()).forward(request, response);
                    }
                } else {
                    
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}