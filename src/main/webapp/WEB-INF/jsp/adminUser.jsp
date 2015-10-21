<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
	<%@include file="header.jsp.inc" %>
	<title>Smart Assignments | Edit User Settings</title> 
</head>
<body>
<c:if test="${!empty user.name}">
<form:form modelAttribute="editUser" >   
	<table>
		<tr bgcolor="#f0f0f0"><td colspan="2">
			<h3>Edit User Settings</h3>
		</td></tr>
		<tr>
		    <td><b>User name:</b></td>
		    <td><b><c:out value="${editUser.name}" /></b></td>
		</tr>
		<tr>
			<td><b>* E-mail:</b></td>
		    <td><form:input size="40" path="email" /><form:errors path="email" /></td>
		</tr>
		<tr>
		    <td>Full name:</td>
		    <td><form:input size="80" path="fullname" /></td>
		</tr>
		<tr>
		    <td>Where I am from:</td>
		    <td><form:input size="80" path="place" /></td>
		</tr>
		<tr>
		    <td>About myself:</td>
		    <td><form:textarea rows="5" cols="70" path="description" /></td>
		</tr>
		<tr>
		    <td>Rows per page in lists:</td>
		    <td>
		    	<form:select path="rowsPerPage" >
					<form:option value="10"/>
					<form:option value="20"/>
					<form:option value="50"/>
					<form:option value="100"/>
				</form:select>
			</td>
		</tr>
		<tr><td colspan="2">
			Fields marked with "*" is required fields.<br><br>
		</td></tr>

		<tr bgcolor="#f0f0f0"><td colspan="2">
			<h3>User rights</h3>
		</td></tr>
		<tr>
		    <td>Edit assignments:</td>
		    <td><form:checkbox path="editAssignmentsRight"/></td>
		</tr>
		<tr>
		    <td>Edit repository:</td>
		    <td><form:checkbox path="editRepositoryRight"/></td>
		</tr>
		<tr>
		    <td>Administrator:</td>
		    <td><form:checkbox path="adminRight"/></td>
		</tr>

		<tr bgcolor="#f0f0f0"><td colspan="2">
		</td></tr>
		<tr><td colspan="2">
			<a href="editpasswd.htm">Change password</a>
		</td></tr>
		<tr bgcolor="#f0f0f0">
			<td><input type="submit" class="button" name="save" value="Save changes"></td>
			<td><input type="button" class="button" name="cancel" value="Cancel" onclick="history.go(-1);"></td>
		</tr>
	</table>
</form:form>
</c:if>
<c:if test="${empty user.name}">
	<b>You should be logged in to edit your settings!</b><br><br>
	<a href="login.htm">[log in]</a> <a href="undex.htm">[return to homepage]</a>
</c:if>
</body>
<head>
	<%@include file="header.jsp.inc" %>
	<title>Smart Assignments | Edit User Settings</title> 
</head>
</html>