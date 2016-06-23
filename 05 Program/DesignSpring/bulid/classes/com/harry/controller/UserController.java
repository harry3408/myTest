package com.harry.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.harry.common.Const;
import com.harry.exception.ParameterException;
import com.harry.model.User;
import com.harry.service.UserService;
import com.harry.utils.StringUtil;

@Controller
@RequestMapping("/page/")
public class UserController extends BaseController {

	@Autowired
	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "users", method = RequestMethod.GET)
	public ModelAndView users() throws IOException, ServletException {
		ModelAndView modelAndView = new ModelAndView();

		List<User> users = userService.getAllUsers();
		modelAndView.addObject(Const.PARAM_USER_LIST, users);
		modelAndView.setViewName("list");
		return modelAndView;
	}

	@RequestMapping(value = "delete/{userId}", method = RequestMethod.GET)
	public ModelAndView delete(@PathVariable(value = "userId") int userId) throws IOException {
		ModelAndView modelAndView = new ModelAndView();

		userService.delete(userId);
		modelAndView.setView(this.getRedirectView("users"));
		return modelAndView;
	}

	@RequestMapping(value = "edit/{userId}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable(value = "userId") int userId) throws IOException, ServletException {
		ModelAndView modelAndView = new ModelAndView();

		if (userId != -1) {
			User user = userService.getUserById(userId);
			if (user != null) {
				modelAndView.addObject(Const.PARAM_USER, user);
			}
		}
		modelAndView.setViewName("edit");
		return modelAndView;
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public ModelAndView save(@RequestParam(value = "userId", defaultValue = "") int userId,
			@RequestParam(value = "userName", defaultValue = "") String userName,
			@RequestParam(value = "userPassword", defaultValue = "") String userPassword) throws IOException {
		ModelAndView modelAndView = new ModelAndView();

		User user = new User();
		user.setUserName(userName);
		user.setUserPassword(userPassword);
		user.setUserId(userId);

		if (user.getUserId() != -1) {
			userService.update(user);
		} else {
			userService.create(user);
		}
		modelAndView.setView(this.getRedirectView("users"));
		return modelAndView;
	}

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public ModelAndView login(Map<String, String> paramters) throws IOException, ServletException {
		ModelAndView modelAndView = new ModelAndView();

		if (getSession(Const.PARAM_USER) != null) {
			modelAndView.setView(this.getRedirectView(Const.USERS_SELVLET));
		} else {
			String goPage = paramters.get("go");
			if (!StringUtil.isEmpty(goPage)) {
				modelAndView.addObject("go", goPage);
			}
			modelAndView.setViewName("login");
		}
		return modelAndView;
	}

	@RequestMapping(value = "saveLogin", method = RequestMethod.POST)
	public ModelAndView saveLogin(@RequestParam(value = "userName", defaultValue = "") String userName,
			@RequestParam(value = "password", defaultValue = "") String password,
			@RequestParam(value = "go", defaultValue = "") String go) throws IOException, ServletException {
		ModelAndView modelAndView = new ModelAndView();

		try {
			User user = userService.login(userName, password);
			if (user == null) {
				modelAndView.addObject(Const.PARAM_LOGIN_ERROR, "userName or password is wrong.");
				modelAndView.setViewName("login");
			} else {
				addSession(Const.PARAM_USER, user);
				if (!StringUtil.isEmpty(go)) {
					go = URLDecoder.decode(go, "utf-8");
					modelAndView.setView(this.getRedirectView(go));
				} else {
					modelAndView.setView(this.getRedirectView("users"));
				}
			}
		} catch (ParameterException e) {
			modelAndView.addObject(Const.PARAM_REQUIRED, e.getErrorMsg());
			modelAndView.setViewName("login");
		}
		return modelAndView;
	}
}