<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.harry.common.Const" %>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
</head>
  <body>
  <%Map<String, String> loginError = null;
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
  %>
    <form action="login" method="post">
     User Name:<input type="text" name="userName" /><label><%=userNameR%></label><br />
     Password: <input type="password" name="password" /><label><%=pwdR%></label><br />
     <input type="submit" value="Submit" /> <br />
     <label><%= error %></label>
    </form>
  </body>
</html>
