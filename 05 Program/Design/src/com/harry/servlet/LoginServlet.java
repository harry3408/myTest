package com.harry.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.harry.common.Const;
import com.harry.exception.ParameterException;
import com.harry.model.User;
import com.harry.service.UserService;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String LOGIN_PAGE = "/WEB-INF/jsp/login.jsp";
    private static final String PARAM_USERS = "/users";

    public LoginServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(LOGIN_PAGE).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = new UserService();
        try {
            User user = userService.login(request.getParameter("userName"), request.getParameter("password"));
            if(user == null) {
                request.setAttribute(Const.PARAM_LOGIN_ERROR, "userName or password is wrong.");
                request.getRequestDispatcher(LOGIN_PAGE).forward(request, response);
            } else {
                HttpSession session = request.getSession();
                session.setAttribute(Const.PARAM_USER, user);
                response.sendRedirect(request.getContextPath() + PARAM_USERS);
            }
        } catch (ParameterException e) {
            request.setAttribute(Const.PARAM_REQUIRED, e.getErrorMsg());
            request.getRequestDispatcher(LOGIN_PAGE).forward(request, response);
        }
    }
}
