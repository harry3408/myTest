package com.harry.model;

import java.util.List;

public class ResultConfig {

    private String resultName;
    private String view;
    private boolean redirect;
    private List<ViewParamter> viewParamters;

    public ResultConfig(String resultName, String view, boolean redirect, List<ViewParamter> viewParamters) {
        this.resultName = resultName;
        this.view = view;
        this.redirect = redirect;
        this.viewParamters = viewParamters;
    }

    public String getResultName() {
        return resultName;
    }

    public String getView() {
        return view;
    }

    public boolean getRedirect() {
        return redirect;
    }

    public List<ViewParamter> getViewParamters() {
        return viewParamters;
    }
}
