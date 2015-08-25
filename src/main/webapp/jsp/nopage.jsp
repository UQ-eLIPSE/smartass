<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
  <head>
	<%@include file="header.jsp.inc" %>
	<meta HTTP-EQUIV="REFRESH" content="15; url=<c:out value="${redirectUri}"/>">
  	<title>Smart Assignments | Page not Found</title>
  </head>
  <body>
    <h2>Smart Assignments - Page not Found</h2>
    <table width="100%">
		<tr class="header"><td colspan=2>
		    <table class="menu"><tr class="menu">
		    	<td><a href="<c:out value="${redirectUri}"/>">[home]</a></td>
		    	<td><a href="repository.htm">[repository]</a></td>
		    	<c:if test="${empty user}">
			    	<td><a href="login.htm">[log in]</a></td>
			    	<td><a href="register.htm">[register]</a></td>
			    </c:if>
		    	<c:if test="${!empty user}">
			    	<td><a href="edituser.htm">[user settings]</a></td>
			    	<td><a href="logout.htm">[log out]</a></td>
			    	<td>
			    		Logged in as: <b><c:out value="${user.name}"/>
			    		<c:if test="${!empty user.fullname}">
			    			(<c:out value="${user.fullname}"/>)</b>
			    		</c:if>
			    	</td>
			    </c:if>
		    </tr><tr class="selected-menu"><td class="selected-menu" colspan=4></td></tr></table>
	    </td></tr>
	<tr><td valign="top" width="150" class="menu">
    </td>
    <td valign="top">
    	<c:out escapeXml="false" value="${message}"/> <br><br>
    	You will be redirected to the <a href="<c:out value="${redirectUri}"/>">homepage</a> in 10 sec.<br>
    </td>
    </tr></table>
  </body>
<head>
	<%@include file="header.jsp.inc" %>
</head>
</html>
