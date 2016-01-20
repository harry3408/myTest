package com.harry.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.harry.common.Const;
import com.harry.model.User;

@WebFilter(filterName = "sessionFilter", urlPatterns = { "/*" }, initParams = { @WebInitParam(name = "noNeedLogin", value = ",login.action,saveLogin.action") })
public class SessionFilter implements Filter {

    public static List<String> noNeedLogin = new ArrayList<>();

    public SessionFilter() {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest ServletRequest, ServletResponse ServletResponse, FilterChain chain) throws IOException, ServletException {
        boolean isAllow = false;

        HttpServletRequest request = (HttpServletRequest) ServletRequest;
        HttpServletResponse response = (HttpServletResponse) ServletResponse;

        String uri = request.getRequestURI();
        String requestURI = uri.substring(request.getContextPath().length() + 1);

        for (String noNeedLoginPage : noNeedLogin) {
            if (!requestURI.endsWith(".action") || requestURI.equals(noNeedLoginPage)) {
                isAllow = true;
                break;
            }
        }
        if (isAllow) {
            chain.doFilter(ServletRequest, ServletResponse);
        } else {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(Const.PARAM_USER);
            if (user != null) {
                chain.doFilter(ServletRequest, ServletResponse);
            } else {
                String goPage = getGoPage(request, requestURI);
                response.sendRedirect(request.getContextPath() + Const.LOGIN_SELVLET + goPage);
            }
        }
    }

    private String getGoPage(HttpServletRequest request, String requestURI) {
        String goPage = "";
        if ("get".equalsIgnoreCase(request.getMethod())) {
            goPage = "?go=" + requestURI;
            // ?go = edit.action?id=1&name=2
            Enumeration<String> enumeration = request.getParameterNames();
            boolean isFirst = true;
            String connStr = "";
            while (enumeration.hasMoreElements()) {
                String parameter = enumeration.nextElement();
                if (isFirst) {
                    connStr = "?";
                    isFirst = false;
                } else {
                    connStr = "&";
                }
                goPage = goPage + connStr + parameter + "=" + request.getParameter(parameter);
            }
        }
        return goPage;
    }

    public void init(FilterConfig fConfig) throws ServletException {
        String noNeedLoginStr = fConfig.getInitParameter("noNeedLogin");
        noNeedLogin = Arrays.asList(noNeedLoginStr.split(","));
    }

}
