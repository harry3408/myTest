<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.harry.common.Const" %>
<%@ page import="com.harry.utils.PropertyUtil" %>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>

<%
      String baseURL = request.getContextPath();
  %>
<link rel="stylesheet" type="text/css" href="<%=PropertyUtil.getStaticUrl() %>/styles/common.css" />
</head>
  <body>
  <%
      Map<String, String> loginError = null;
       String userNameR = "";
       String pwdR = "";
       if(request.getAttribute(Const.PARAM_REQUIRED) != null) {
           loginError = (Map<String, String>)request.getAttribute(Const.PARAM_REQUIRED);
           if(loginError != null) {
       userNameR = loginError.get(Const.PARAM_USER_NAME) == null ? "" : loginError.get(Const.PARAM_USER_NAME);
       pwdR = loginError.get(Const.PARAM_PWD) == null ? "" : loginError.get(Const.PARAM_PWD);
           }
       }
       String error = "";
       if(request.getAttribute(Const.PARAM_LOGIN_ERROR) != null) {
           error = request.getAttribute(Const.PARAM_LOGIN_ERROR).toString();
       }
       String goPage = "";
       if(request.getParameter("go") != null) {
           goPage = request.getParameter("go").toString();
       }
  %>
    <form action="/DesignSpring/page/saveLogin" method="post" class="loginForm">
     <div class="loginDiv">User Name:<input type="text" name="userName" /><label><%=userNameR%></label></div>
     <div class="loginDiv">Password: <input type="password" name="password" /><label><%=pwdR%></label></div>
     <div class="submitDiv"><input type="submit" value="Submit" /></div>
     <label><%= error %></label>
     <input type="hidden" name="go" value="<%=goPage %>" />
    </form>
  </body>
</html>
