package com.harry.model;

import java.util.ArrayList;
import java.util.List;

public class ResultConfig {

    private String name;
    private String view;
    private boolean redirect;
    private List<ViewParamter> viewParamters = new ArrayList<ViewParamter>();

    public ResultConfig(String name, String view, boolean redirect) {
        this.name = name;
        this.view = view;
        this.redirect = redirect;
    }

    public String getName() {
        return name;
    }

    public String getView() {
        return view;
    }

    public List<ViewParamter> getViewParamters() {
        return viewParamters;
    }

    public void setViewParamters(List<ViewParamter> viewParamters) {
        this.viewParamters = viewParamters;
    }
}
