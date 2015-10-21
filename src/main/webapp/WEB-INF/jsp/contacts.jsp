<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<%@include file="header.jsp.inc" %>
  	<title>Smart Assignments</title>
</head>
<body>
    <h2>Smart Assignments Home</h2>
    <table width="100%"><tr class="menu"><td colspan=2>
	    <table class="menu"><tr class="menu">
	    	<td class="selected-menu"><b>[home]</b></td>
	    	<td><a href="repository.htm">[repository]</a></td>
	    	<c:if test="${empty user}">
		    	<td><a href="login.htm">[log in]</a></td>
		    	<td><a href="register.htm">[register]</a></td>
		    	<td><a href="help.htm?context=home" target="_blank">[help]</a></td>
		    </c:if>
	    	<c:if test="${!empty user}">
		    	<td><a href="edituser.htm">[user settings]</a></td>
		    	<td><a href="logout.htm">[log out]</a></td>
		    	<td><a href="help.htm?context=home" target="_blank">[help]</a></td>
		    	<td>
		    		Logged in as: <b><c:out value="${user.name}"/>
		    		<c:if test="${!empty user.fullname}">
		    			(<c:out value="${user.fullname}"/>)</b>
		    		</c:if>
		    	</td>
		    </c:if>
	    </tr><tr class="selected-menu"><td class="selected-menu" colspan=5></td></tr></table>
    </td></tr><tr>
    <td valign="top" width="200" class="menu">
	    <table>
	    	<tr><td >
				<a href="index.htm?mode=recent">[recent templates]</a>
			</td></tr>
	    	<tr><td>
				<a href="index.htm?mode=browse">[browse assignments]</a>
			</td></tr>
	    	<tr><td>
				<a href="users.htm">[users]</a>
				<hr>
			</td></tr>
	    	<tr><td>
		    	<c:if test="${!empty user}">
					<a href="index.htm?mode=my">[my assignments]</a>
				</c:if>
		    	<c:if test="${empty user}">[my assignments]</c:if>
			</td></tr>
	    	<tr><td>
				<a href="composer.htm">[create new]</a>
			</td></tr>
	    	<tr><td><hr></td></tr>
	    	<tr><td class="selected-menu">[contacts]</td></tr>
	    </table>
    </td>
    <td valign="top">
    	 Please, send your constructive comments, suggestions or bug reports to  <a href="mailto:smart.assignments@maths.uq.edu.au">smart.assignments@maths.uq.edu.au</a>.
    </td>
    </tr>
    </table>
</body>
<head>
	<%@include file="header.jsp.inc" %>
  	<title>Smart Assignments</title>
</head>
</html>