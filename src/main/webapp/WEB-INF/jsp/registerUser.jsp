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
<div class="container">
    <form:form modelAttribute="newUser" >
    <h2>Register a New User</h2>
    <br>
        <div class="form-group">
    	    <label>* User name:</label>
    	    <form:input cssClass="form-control" size="40" path="name" /><form:errors path="name" />

    		<label>* E-mail:</label>
    	    <form:input cssClass="form-control" size="40" path="email" /><form:errors path="email" />

    	    <label>* Password:</label>
    	    <form:password cssClass="form-control" path="password1" id="rawpassword1" /><form:errors path="password1" />

    	    <label>* Retype password:</label>
    	    <form:password cssClass="form-control" path="password2" id="rawpassword2" />

    	    <label>Full name:</label>
    	    <form:input cssClass="form-control" size="80" path="fullname" />

    	    <label>Where I am from:</label>
    	    <form:input cssClass="form-control" size="80" path="place" />

    	    <label>About myself:</label>
    	    <form:textarea cssClass="form-control" rows="5" cols="70" path="description" />
    		<span>Fields marked with "*" is required fields.</span>
    	</div>
    		<input type="submit" class="btn btn-warning pull-right" name="register" value="Register"></td>
    </form:form>
    </div>
</body>
</html>