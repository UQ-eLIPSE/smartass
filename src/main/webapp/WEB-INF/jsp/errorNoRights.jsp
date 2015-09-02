<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
<head>
	<%@include file="header.jsp.inc" %>
	<title>Smart Assignments | Error</title>
</head>
<body>
    <table width="100%">
	    <tr class="header"><td>
			<h3>Error!</h3>
		</td></tr>
		<tr><td>
			You are either not logged in or have no rights to <c:out value="${description}"/>.
		</td></tr>
		<tr><td>
			<a href="index.htm">[return to homepage]</a>
			<a href="login.htm">[log in]</a>
			<a href="register.htm">[register]</a>
		</td></tr>
	</table>
</body>
<head>
	<%@include file="header.jsp.inc" %>
	<title>Smart Assignments | Error</title>
</head>
</html>