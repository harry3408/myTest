package com.harry.controller;

import org.springframework.web.servlet.view.RedirectView;

import com.harry.utils.PathUtil;

public class BaseController {

    public RedirectView getRedirectView(String path) {
        return new RedirectView(PathUtil.getFullPath(path));
    }
}
