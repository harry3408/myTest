package com.harry.taglib.block;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import com.harry.common.Const;
import com.harry.model.User;
import com.harry.service.UserService;

public class ListBlock extends BlockAbstract {
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void execute(PageContext pageContext) {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        List<User> users = userService.getAllUsers();
        request.setAttribute(Const.PARAM_USER_LIST, users);
    }
}
