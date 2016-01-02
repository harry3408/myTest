<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.List" %>
<%@page import="com.harry.model.User" %>
<%@page import="com.harry.common.Const" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%
      String baseURL = request.getContextPath();
  %>
<link rel="stylesheet" type="text/css" href="<%=baseURL %>/static/styles/common.css" />

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
    function createFun() {
    	<%
    	   String baseURI = request.getContextPath();
        %>
		window.location.href =  "<%=baseURI %>/edit";
	}
</script>
</head>
<body>
    <%
        List<User> userList = (List<User>) request.getAttribute(Const.PARAM_USER_LIST);
    %>
    <table>
      <tr>
        <td>id</td>
        <td>UserName</td>
        <td colspan="2">operation</td>
      </tr>
      <%
      for(User user : userList) {
          %>
           <tr>
             <td><%=user.getUserId() %></td>
             <td><%=user.getUserName() %></td>
             <td><a href="/Design/edit?id=<%=user.getUserId()%>">edit</a></td>
             <td><a href="/Design/delete?id=<%=user.getUserId()%>">delete</a></td>
           </tr>
          <%
      }
      %>
      <tr>
        <td colspan="4">
            <input type="button" class="createButton" id="createButton" value="create" onclick="createFun()"/>
        </td>
      </tr>
    </table>
</body>
</html>