package com.harry.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.harry.common.AppContext;
import com.harry.common.Const;
import com.harry.exception.ParameterException;
import com.harry.model.User;
import com.harry.service.UserService;
import com.harry.utils.StringUtil;

@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

//    public ModelAndView users(Map<String, Object> sessions, Map<String, String> paramters) throws IOException, ServletException {
//        ModelAndView modelAndView = new ModelAndView();
//
//        List<User> users = userService.getAllUsers();
//        modelAndView.addRequestParameter(Const.PARAM_USER_LIST, users);
//        modelAndView.setView("sucess");
//        return modelAndView;
//    }
//
//    public ModelAndView delete(Map<String, Object> sessions, Map<String, String> paramters) throws IOException {
//        ModelAndView modelAndView = new ModelAndView();
//
//        String id = paramters.get("id");
//        userService.delete(Integer.parseInt(id));
//        modelAndView.setView("sucess");
//        System.out.println("hello1");
//        return modelAndView;
//    }
//
//    public ModelAndView edit(Map<String, Object> sessions, Map<String, String> paramters) throws IOException, ServletException {
//        ModelAndView modelAndView = new ModelAndView();
//
//        String userId = paramters.get("id");
//        if (!StringUtil.isEmpty(userId)) {
//            User user = userService.getUserById(Integer.parseInt(userId));
//            if (user != null) {
//                modelAndView.addRequestParameter(Const.PARAM_USER, user);
//            }
//        }
//        modelAndView.setView("sucess");
//        return modelAndView;
//    }
//
//    public ModelAndView save(Map<String, Object> sessions, Map<String, String> paramters) throws IOException {
//        ModelAndView modelAndView = new ModelAndView();
//        User user = new User();
//        String userName = paramters.get("userName");
//        String password = paramters.get("password");
//        user.setUserName(userName);
//        user.setUserPassword(password);
//
//        String userId = paramters.get("userId");
//        if (!StringUtil.isEmpty(userId)) {
//            user.setUserId(Integer.parseInt(userId));
//            userService.update(user);
//        } else {
//            userService.create(user);
//        }
//        modelAndView.setView("sucess");
//        return modelAndView;
//    }

    @RequestMapping("login.action")
    public ModelAndView login(Map<String, Object> sessions, Map<String, String> paramters) throws IOException, ServletException {
        ModelAndView modelAndView = new ModelAndView();

        AppContext appcontext = AppContext.getAppContext();
        HttpServletRequest request = (HttpServletRequest) appcontext.getObject(Const.APP_CONTEXT_REQUEST);

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
//            if (user == null) {
//                modelAndView.addRequestParameter(Const.PARAM_LOGIN_ERROR, "userName or password is wrong.");
//                modelAndView.setView("error");
//            } else {
//                modelAndView.addRequestSession(Const.PARAM_USER, user);
//                String goPage = paramters.get("go");
//                if (!StringUtil.isEmpty(goPage)) {
//                    modelAndView.setView(goPage);
//                } else {
//                    modelAndView.setView("sucess");
//                }
//            }
        } catch (ParameterException e) {
//            modelAndView.addRequestParameter(Const.PARAM_REQUIRED, e.getErrorMsg());
//            modelAndView.setView("error");
        }
        return modelAndView;
    }

}
