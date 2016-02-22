<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.harry.model.User" %>
<%@ page import="com.harry.common.Const" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
      String baseURL = request.getContextPath();
  %>
<link rel="stylesheet" type="text/css" href="<%=baseURL %>/static/styles/common.css" />

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<% User user = (User)request.getAttribute(Const.PARAM_USER);
    int userId = user != null ? user.getUserId() : -1;
    String userName = user != null ? user.getUserName() : "";
    String password = user != null ? user.getUserPassword() : "";
%>
    <form action="/DesignSpring/save.action" method="Post">
        <table>
            <tr>
                <td>Id:</td>
                <td><input type="text" name="id" value="<%= userId == -1 ? "" : userId%>" readonly="readonly"/></td>
            </tr>
            <tr>
                <td>userName:</td>
                <td><input type="text" name="userName" value="<%= userName%>"/></td>
            </tr>
            <tr>
                <td>password:</td>
                <td><input type="password" name="userPassword" value="<%= password%>"/></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="submit"/></td>
            </tr>
        </table>
        <input type="hidden" name="userId" value = "<%= userId %>">
        <input type="hidden" name="age" value = "1">
    </form>
</body>
</html>