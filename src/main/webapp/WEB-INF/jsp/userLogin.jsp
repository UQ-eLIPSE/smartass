<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
	<title>User login - Smart Assignments</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/smartass.css" />
</head>
<body>
<form:form modelAttribute="userLogin" >   
	<table>
		<tr class="header"><td colspan=2><h3>Log in to Smart Assignments site</h3></td></tr>
		<tr><td colspan=2>Please, enter your username and password</td></tr>
		<tr>
		    <td><b>User login:</b></td>
		    <td><form:input size="40" path="name" /></td>
		</tr>
		<tr>
		    <td><b>Password:</b></td>
		    <td><form:password path="password" /></td>
		</tr>
		<tr><form:errors path="*" /></tr>
		<tr>
			<td><input type="submit" class="button" name="register" value="Log in"></td>
			<td><input type="button" class="button" name="cancel" value="Cancel" onclick="history.go(-1);"></td>
		</tr>
	</table>
</form:form>
</body>
<head>
	<%@include file="header.jsp.inc" %>
</head>
</html>
