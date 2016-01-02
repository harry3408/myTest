package com.harry.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.harry.common.Const;
import com.harry.model.User;
import com.harry.service.UserService;

@WebServlet("/users")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String USER_LIST_JSP = "/WEB-INF/jsp/list.jsp";

    public UserServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = new UserService();
        List<User> users = userService.getAllUsers();
        request.setAttribute(Const.PARAM_USER_LIST, users);
        request.getRequestDispatcher(USER_LIST_JSP).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
