package com.harry.controller;

import org.springframework.web.servlet.view.RedirectView;

import com.harry.utils.URLUtil;

public class BaseController {

    public RedirectView getRedirectView(String path) {
        return new RedirectView(URLUtil.getFullPath(path));
    }
}
