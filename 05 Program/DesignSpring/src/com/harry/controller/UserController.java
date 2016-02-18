package com.harry.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.harry.common.Const;
import com.harry.exception.ParameterException;
import com.harry.model.User;
import com.harry.service.UserService;
import com.harry.utils.StringUtil;

@Controller
@RequestMapping("/")
public class UserController {
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "users.action", method = RequestMethod.GET)
    public ModelAndView users(Map<String, Object> sessions, Map<String, String> paramters) throws IOException,
            ServletException {
        ModelAndView modelAndView = new ModelAndView();

        List<User> users = userService.getAllUsers();
        modelAndView.addObject(Const.PARAM_USER_LIST, users);
        modelAndView.setViewName("list");
        return modelAndView;
    }

    @RequestMapping(value = "delete.action", method = RequestMethod.GET)
    public ModelAndView delete(Map<String, Object> sessions, Map<String, String> paramters) throws IOException {
        ModelAndView modelAndView = new ModelAndView();

        String id = paramters.get("id");
        userService.delete(Integer.parseInt(id));
        modelAndView.setView(new RedirectView("users.action"));
        return modelAndView;
    }

    @RequestMapping(value = "edit.action", method = RequestMethod.GET)
    public ModelAndView edit(Map<String, Object> sessions, Map<String, String> paramters) throws IOException,
            ServletException {
        ModelAndView modelAndView = new ModelAndView();

        String userId = paramters.get("id");
        if (!StringUtil.isEmpty(userId)) {
            User user = userService.getUserById(Integer.parseInt(userId));
            if (user != null) {
                modelAndView.addObject(Const.PARAM_USER, user);
            }
        }
        modelAndView.setViewName("edit");
        return modelAndView;
    }

    @RequestMapping(value = "save.action", method = RequestMethod.POST)
    public ModelAndView save(Map<String, Object> sessions, Map<String, String> paramters) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        String userName = paramters.get("userName");
        String password = paramters.get("password");
        user.setUserName(userName);
        user.setUserPassword(password);

        String userId = paramters.get("userId");
        if (!StringUtil.isEmpty(userId)) {
            user.setUserId(Integer.parseInt(userId));
            userService.update(user);
        } else {
            userService.create(user);
        }
        modelAndView.setView(new RedirectView("users.action"));
        return modelAndView;
    }

    @RequestMapping(value = "login.action", method = RequestMethod.GET)
    public ModelAndView login(Map<String, Object> sessions, Map<String, String> paramters) throws IOException,
            ServletException {
        ModelAndView modelAndView = new ModelAndView();

        if (sessions.get(Const.PARAM_USER) != null) {
            modelAndView.setView(new RedirectView(Const.USERS_SELVLET));
        } else {
            String goPage = paramters.get("go");
            if (!StringUtil.isEmpty(goPage)) {
                modelAndView.addObject("go", goPage);
            }
            modelAndView.setViewName("login");
        }
        return modelAndView;
    }

    @RequestMapping(value = "saveLogin.action", method = RequestMethod.POST)
    public ModelAndView saveLogin(@RequestParam(value = "userName", defaultValue = "") String userName,
            @RequestParam(value = "password", defaultValue = "") String password,
            @RequestParam(value = "go", defaultValue = "") String go, HttpSession session) throws IOException,
            ServletException {
        ModelAndView modelAndView = new ModelAndView();

        try {
            User user = userService.login(userName, password);
            if (user == null) {
                modelAndView.addObject(Const.PARAM_LOGIN_ERROR, "userName or password is wrong.");
                modelAndView.setViewName("login");
            } else {
                session.setAttribute(Const.PARAM_USER, user);
                if (!StringUtil.isEmpty(go)) {
                    modelAndView.setView(new RedirectView(go));
                } else {
                    modelAndView.setView(new RedirectView("users.action"));
                }
            }
        } catch (ParameterException e) {
            modelAndView.addObject(Const.PARAM_REQUIRED, e.getErrorMsg());
            modelAndView.setViewName("login");
        }
        return modelAndView;
    }

}