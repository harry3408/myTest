package com.harry.common;

import java.util.HashMap;
import java.util.Map;

public class ModelAndView {

    private String view;
    private boolean isRedirect = true;
    private Map<String, Object> requestSessions = new HashMap<String, Object>();
    private Map<String, Object> requestParameters = new HashMap<String, Object>();

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public boolean isRedirect() {
        return isRedirect;
    }

    public void setRedirect(boolean isRedirect) {
        this.isRedirect = isRedirect;
    }

    public void addRequestSession(String key, Object value) {
        this.requestSessions.put(key, value);
    }

    public Map<String, Object> getRequestSessions() {
        return requestSessions;
    }

    public void removeAllRequestSessions() {
        this.requestSessions.clear();
    }

    public void addRequestParameter(String key, Object value) {
        this.requestParameters.put(key, value);
    }

    public Map<String, Object> getRequestParameters() {
        return requestParameters;
    }

    public Map<String, Object> removeAllRequestParameters() {
        return requestParameters;
    }
}
