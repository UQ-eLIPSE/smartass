<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
	<%@include file="header.jsp.inc" %>
	<title>Smart Assignments | Register New User</title> 
	<script type="text/javascript">
	function validatePassword()
	{	
	 	rawpasswd1 = document.getElementById("rawpassword1").value;
		rawpasswd2 = document.getElementById("rawpassword2").value;
		if(rawpasswd1==rawpasswd2) {
			document.getElementById("password").value=document.getElementById("rawpassword1").value;
			return true;
		} else 
			alert("Passwords is defferent!");
		return false;
	}
	</script>
</head>
<body>
<form:form modelAttribute="newUser" >   
<h2>Register a New User</h2>
<br>
<table>
	<tr>
	    <td><b>* User name:</b></td>
	    <td><form:input size="40" path="name" /><form:errors path="name" /></td>
	</tr>
	<tr>
		<td><b>* E-mail:</b></td>
	    <td><form:input size="40" path="email" /><form:errors path="email" /></td>
	</tr>
	<tr>
	    <td><b>* Password:</b></td>
	    <td><form:password path="password1" id="rawpassword1" /><form:errors path="password1" /></td>
	</tr>
	<tr>
	    <td><b>* Retype password:</b></td>
	    <td><form:password path="password2" id="rawpassword2" /></td>
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
	<tr><td colspan="2">
		Fields marked with "*" is required fields.<br><br>
	</td></tr>
	<tr>
		<td><input type="submit" class="button" name="register" value="Register"></td>
		<td><input type="button" class="button" name="cancel" value="Cancel" onclick="history.go(-1);"></td>
	</tr>
</table>
</form:form>
</body>
<head>
	<%@include file="header.jsp.inc" %>
	<title>Smart Assignments | Register New User</title> 
</head>
</html>