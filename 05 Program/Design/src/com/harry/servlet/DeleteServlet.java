package com.harry.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.harry.service.UserService;

@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String PARAM_USERS = "/users";

    public DeleteServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = new UserService();
        String id = request.getParameter("id");
        userService.delete(Integer.parseInt(id));
        response.sendRedirect(request.getContextPath() + PARAM_USERS);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
