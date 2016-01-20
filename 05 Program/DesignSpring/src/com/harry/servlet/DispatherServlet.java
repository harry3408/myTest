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

import com.harry.common.ActionConfig;
import com.harry.common.BeanFactory;
import com.harry.common.Const;
import com.harry.common.ModelAndView;
import com.harry.common.ResultConfig;
import com.harry.common.ViewParamter;
import com.harry.utils.StringUtil;

@WebServlet(urlPatterns = { "*.action" }, loadOnStartup = 1)
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

			Document document = documentBuilder
					.parse(DispatherServlet.class.getResourceAsStream("/com/harry/Action.xml"));
			NodeList actionNodeList = document.getElementsByTagName("action");
			for (int index = 0; index < actionNodeList.getLength(); index++) {
				Element actionElement = (Element) actionNodeList.item(index);
				String name = actionElement.getAttribute("name");
				String className = actionElement.getAttribute("class");
				String methodName = actionElement.getAttribute("method");
				String httpMethodStr = actionElement.getAttribute("httpMethod").toUpperCase();
				if (StringUtil.isEmpty(httpMethodStr)) {
					httpMethodStr = "GET";
				}
				String[] httpMethodArr = httpMethodStr.split(",");
				ActionConfig actionConfig = new ActionConfig(className, methodName, httpMethodArr);

				NodeList resultNodeList = actionElement.getElementsByTagName("result");
				int resultNodeListCount = resultNodeList.getLength();
				if (resultNodeListCount > 0) {
					for (int i = 0; i < resultNodeListCount; i++) {
						Element resultElement = (Element) resultNodeList.item(i);

						String resultName = resultElement.getAttribute("name");
						String resultView = resultElement.getAttribute("view");
						String redirect = resultElement.getAttribute("redirect");
						if (StringUtil.isEmpty(redirect)) {
							redirect = "true";
						}

						String resultParameterStr = resultElement.getAttribute("viewParameter");
						List<ViewParamter> viewParamters = new ArrayList<ViewParamter>();
						if (!StringUtil.isEmpty(resultParameterStr)) {
							String[] resultParameterArr = resultParameterStr.split(",");
							for (int j = 0; j < resultParameterArr.length; j++) {
								String[] parameterArr = resultParameterArr[j].split(":");
								String parameterName = parameterArr[0].trim();
								String parameterFrom = parameterArr[1].trim();
								viewParamters.add(new ViewParamter(parameterName, parameterFrom));
							}
						}
						ResultConfig resultConfig = new ResultConfig(resultName, resultView,
								Boolean.parseBoolean(redirect), viewParamters);
						actionConfig.addResultConfig(resultName, resultConfig);
					}
				}
				for (int j = 0; j < httpMethodArr.length; j++) {
					actionConfigs.put(name + suffix + "#" + httpMethodArr[j], actionConfig);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Hello3");
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

				// Class<?> cls = Class.forName(className);
				// Object controller = cls.newInstance();
				Object controller = BeanFactory.getIntance().getBean(className);

				String methodName = actionCofing.getMethodName();
				Method method = controller.getClass().getMethod(methodName, parameterType);
				// Method method = cls.getMethod(methodName, parameterType);

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

				String viewName = modelAndView.getView();
				ResultConfig resultConfig = actionCofing.getResultConfig(viewName);
				if (resultConfig == null) {
					if (modelAndView.isRedirect()) {
						response.sendRedirect(request.getContextPath() + "/" + modelAndView.getView());
					} else {
						request.getRequestDispatcher(modelAndView.getView()).forward(request, response);
					}
				} else {
					if (resultConfig.getRedirect()) {
						String resultView = request.getContextPath() + "/" + resultConfig.getView();
						List<ViewParamter> viewParamters = resultConfig.getViewParamters();
						if (viewParamters == null || viewParamters.isEmpty()) {
							response.sendRedirect(resultView);
						} else {
							StringBuilder sb = new StringBuilder();
							boolean isFirstAppend = true;
							for (ViewParamter viewParamter : viewParamters) {
								String name = viewParamter.getName();
								String from = viewParamter.getFrom();
								String uriParameter = "";
								String value = "";
								if ("session".equalsIgnoreCase(from)) {
									value = (String) request.getSession().getAttribute(name);
								} else if ("parameter".equalsIgnoreCase(from)) {
									value = request.getParameter(name);
								} else {
									value = (String) request.getAttribute(name);
								}
								uriParameter = name + "=" + value;
								if (isFirstAppend) {
									uriParameter = "?" + uriParameter;
									isFirstAppend = false;
								} else {
									uriParameter = "&" + uriParameter;
								}
								sb.append(uriParameter);
							}
							resultView = resultView + sb.toString();
							response.sendRedirect(resultView);
						}
					} else {
						request.getRequestDispatcher(resultConfig.getView()).forward(request, response);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
