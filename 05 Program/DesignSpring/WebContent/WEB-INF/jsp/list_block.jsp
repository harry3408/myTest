
<%@page import="java.util.List"%>
<%@page import="com.harry.model.User"%>
<%@page import="com.harry.common.Const"%>

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
	    for (User user : userList) {
	%>
	<tr>
		<td><%=user.getUserId()%></td>
		<td><%=user.getUserName()%></td>
		<td><a href="/DesignSpring/page/edit/<%=user.getUserId()%>">edit</a></td>
		<td><a href="/DesignSpring/page/delete/<%=user.getUserId()%>">delete</a></td>
	</tr>
	<%
	    }
	%>
	<tr>
		<td colspan="4"><input type="button" class="createButton"
			id="createButton" value="create" onclick="createFun()" /></td>
	</tr>
</table>