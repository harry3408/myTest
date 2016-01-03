package com.harry.Controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import com.harry.common.Const;
import com.harry.exception.ParameterException;
import com.harry.model.ModelAndView;
import com.harry.model.User;
import com.harry.service.UserService;
import com.harry.utils.StringUtil;

public class UserController {
    private static final String USER_LIST_JSP = "/WEB-INF/jsp/list.jsp";
    private static final String EDIT_PAGE = "/WEB-INF/jsp/edit.jsp";

    public ModelAndView users(Map<String, Object> sessions, Map<String, String> paramters) throws IOException, ServletException {
        ModelAndView modelAndView = new ModelAndView();

        UserService userService = new UserService();
        List<User> users = userService.getAllUsers();
        modelAndView.addRequestParameter(Const.PARAM_USER_LIST, users);
        modelAndView.setView(USER_LIST_JSP);
        modelAndView.setRedirect(false);
        return modelAndView;
    }

    public ModelAndView delete(Map<String, Object> sessions, Map<String, String> paramters) throws IOException {
        ModelAndView modelAndView = new ModelAndView();

        UserService userService = new UserService();
        String id = paramters.get("id");
        userService.delete(Integer.parseInt(id));
        modelAndView.setView(paramters.get(Const.PARAM_CONTEXT_PATH) + Const.USERS_SELVLET);
        return modelAndView;
    }

    public ModelAndView edit(Map<String, Object> sessions, Map<String, String> paramters) throws IOException, ServletException {
        ModelAndView modelAndView = new ModelAndView();

        String userId = paramters.get("id");
        if (!StringUtil.isEmpty(userId)) {
            UserService userService = new UserService();
            User user = userService.getUserById(Integer.parseInt(userId));
            if (user != null) {
                modelAndView.addRequestParameter(Const.PARAM_USER, user);
            }
        }
        modelAndView.setView(EDIT_PAGE);
        modelAndView.setRedirect(false);
        return modelAndView;
    }

    public ModelAndView save(Map<String, Object> sessions, Map<String, String> paramters) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        String userName = paramters.get("userName");
        String password = paramters.get("password");
        user.setUserName(userName);
        user.setUserPassword(password);

        UserService userService = new UserService();
        String userId = paramters.get("userId");
        if (!StringUtil.isEmpty(userId)) {
            user.setUserId(Integer.parseInt(userId));
            userService.update(user);
        } else {
            userService.create(user);
        }
        modelAndView.setView(paramters.get(Const.PARAM_CONTEXT_PATH) + Const.USERS_SELVLET);
        return modelAndView;
    }

    public ModelAndView login(Map<String, Object> sessions, Map<String, String> paramters) throws IOException, ServletException {
        ModelAndView modelAndView = new ModelAndView();

        if (sessions.get(Const.PARAM_USER) != null) {
            modelAndView.setView(paramters.get(Const.PARAM_CONTEXT_PATH) + Const.USERS_SELVLET);
        } else {
            String goPage = paramters.get("go");
            if (!StringUtil.isEmpty(goPage)) {
                modelAndView.addRequestParameter("go", goPage);
            }
            modelAndView.setView(Const.LOGIN_PAGE);
            modelAndView.setRedirect(false);
        }
        return modelAndView;
    }

    public ModelAndView saveLogin(Map<String, Object> sessions, Map<String, String> paramters) throws IOException, ServletException {
        ModelAndView modelAndView = new ModelAndView();

        UserService userService = new UserService();
        try {
            User user = userService.login(paramters.get("userName"), paramters.get("password"));
            if (user == null) {
                modelAndView.addRequestParameter(Const.PARAM_LOGIN_ERROR, "userName or password is wrong.");
                modelAndView.setView(Const.LOGIN_PAGE);
                modelAndView.setRedirect(false);
            } else {
                modelAndView.addRequestSession(Const.PARAM_USER, user);
                String goPage = paramters.get("go");
                if (!StringUtil.isEmpty(goPage)) {
                    modelAndView.setView(paramters.get(Const.PARAM_CONTEXT_PATH) + "/" + goPage);
                } else {
                    modelAndView.setView(paramters.get(Const.PARAM_CONTEXT_PATH) + Const.USERS_SELVLET);
                }
            }
        } catch (ParameterException e) {
            modelAndView.addRequestParameter(Const.PARAM_REQUIRED, e.getErrorMsg());
            modelAndView.setView(Const.LOGIN_PAGE);
            modelAndView.setRedirect(false);
        }
        return modelAndView;
    }

}
