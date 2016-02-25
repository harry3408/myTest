package com.harry.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.harry.common.AppContext;
import com.harry.common.Const;

@WebFilter(filterName = "AppContextFilter", urlPatterns = { "/page/*" })
public class AppContextFilter implements Filter {

    public AppContextFilter() {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        AppContext appContext = AppContext.getAppContext();
        appContext.setObjects(Const.APP_CONTEXT_REQUEST, request);
        appContext.setObjects(Const.APP_CONTEXT_RESPONSE, response);
        appContext.setAppContextPath(request.getContextPath());
        try {
            chain.doFilter(request, response);
        } finally {
            AppContext.removeAppContext();
        }
    }

    public void init(FilterConfig fConfig) throws ServletException {
    }
}
