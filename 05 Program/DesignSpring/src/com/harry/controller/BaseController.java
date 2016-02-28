package com.harry.controller;

import org.springframework.web.servlet.view.RedirectView;

import com.harry.utils.PathUtil;
import com.harry.utils.SessionUtil;

public class BaseController {

	public RedirectView getRedirectView(String path) {
		return new RedirectView(PathUtil.getFullPath(path));
	}

	public void addSession(String key, Object value) {
		SessionUtil.addSession(key, value);
	}

	public Object getSession(String key) {
		return SessionUtil.getSession(key);
	}

	public void invalidate() {
		SessionUtil.invalidate();
	}
}
