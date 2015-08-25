<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
	<%@include file="header.jsp.inc" %>
</head>
<body>
<form:form modelAttribute="command" >   
<h2>Edit User Password</h2>
<br>
<table>
	<tr>
	    <td><b>User name:</b></td>
	    <td><c:out value="${user.name}" /></td>
	</tr>
	<tr>
		<td><b>E-mail:</b></td>
	    <td><c:out value="${user.email}" /></td>
	</tr>
	<tr>
	    <td><b>Full name:</b></td>
	    <td><c:out value="${user.fullname}" /></td>
	</tr>
	<tr><td><br></td></tr>
	<tr>
	    <td><b>* Old password:</b></td>
	    <td><form:password path="oldPassword" id="rawpassword1" /></td>
	</tr>
	<tr>
	    <td><b>* New password:</b></td>
	    <td><form:password path="newPassword1" id="rawpassword1" /></td>
	</tr>
	<tr>
	    <td><b>* Retype password:</b></td>
	    <td><form:password path="newPassword2" id="rawpassword2" /></td>
	</tr>
	<tr><td colspan="2">
		Fields marked with "*" is required fields.<br><br>
	</td></tr>
	<tr><td colspan="2">
		<form:errors path="*"/>
	</td></tr>
	<tr>
		<td><input type="submit" class="button" name="ok" value="OK"></td>
		<td><input type="button" class="button" name="cancel" value="Cancel" onclick="history.go(-1);"></td>
	</tr>
</table>
</form:form>
</body></html>