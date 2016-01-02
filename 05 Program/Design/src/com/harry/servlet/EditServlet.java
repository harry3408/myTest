package com.harry.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.harry.common.Const;
import com.harry.model.User;
import com.harry.service.UserService;
import com.harry.utils.StringUtil;

@WebServlet("/edit")
public class EditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String EDIT_PAGE = "/WEB-INF/jsp/edit.jsp";
    private static final String PARAM_USERS = "/users";

    public EditServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("id");
        if (!StringUtil.isEmpty(userId)) {
            UserService userService = new UserService();
            User user = userService.getUserById(Integer.parseInt(userId));
            if (user != null) {
                request.setAttribute(Const.PARAM_USER, user);
            }
        }
        request.getRequestDispatcher(EDIT_PAGE).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = new User();
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        user.setUserName(userName);
        user.setUserPassword(password);

        UserService userService = new UserService();
        String userId = request.getParameter("userId");
        if (!StringUtil.isEmpty(userId)) {
            user.setUserId(Integer.parseInt(userId));
            userService.update(user);
        } else {
            userService.create(user);
        }
        response.sendRedirect(request.getContextPath() + PARAM_USERS);
    }

}
