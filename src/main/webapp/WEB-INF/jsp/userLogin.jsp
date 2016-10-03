<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
	<title>User login - Smart Assignments</title>
	<%@include file="header.jsp.inc" %>
</head>
<body>
    <div class="container">
		<h3>Log in to Smart Assignments site</h3>
        <form:form modelAttribute="userLogin" >
	    <div class="form-group">
		    <label>User login:</label>
		    <form:input cssClass="form-control"  path="name" />
		    <label>Password:</label>
		    <form:password cssClass="form-control" path="password" />
		    <form:errors path="*" />
		</div>
        <button type="submit" class="btn btn-warning pull-right" name="register" value="Log in"><span class="glyphicon glyphicon-log-in"></span> Log in</button>
        </form:form>
    </div>
</body>
</html>
