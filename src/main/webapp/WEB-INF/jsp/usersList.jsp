<%@ page contentType="text/html" %></b>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
  <head>
	<%@include file="header.jsp.inc" %>
  	<title>Smart Assignments | Users</title>
  </head>
  <body>
    <h2>Smart Assignments - Users</h2>
    <table width="100%"><tr  class="menu"><td colspan=2>
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
		    			(<c:out value="${user.fullname}"/>)
		    		</c:if></b>
		    	</td>
		    </c:if>
	    </tr><tr class="selected-menu"><td class="selected-menu" colspan=5></td></tr></table>
    </td></tr><tr>

    <td valign="top" width="200" class="menu">
	    <table>
	    	<tr><td >
				<a href="index.htm">[recent templates]</a>
			</td></tr>
	    	<tr><td>
				<a href="index.htm?mode=browse">[browse assignments]</a>
			</td></tr>
	    	<tr><td class="selected-menu">
				[users]
	    	<tr><td>
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
	    	<tr><td><hr><a href="contacts.htm">[contacts]</a></td></tr>
	    </table>
    </td>
    
    <td valign="top">
	    <h3>Users</h3>
	    <table width="95%" cellspacing="0">
	    <tr class="header">
	    	<th>Login</th>
	    	<th colspan=3>Full name</th>
	    </tr>
	    <c:set var="rownumber" value="0"/>
	    <c:forEach items="${users}" var="u">
	    	<c:if test="${(rownumber % 2) == 1}">
	    		<tr class="row-dark">
	    	</c:if>
	    	<c:if test="${(rownumber % 2) == 0}">
	    		<tr class="row-light">
	    	</c:if>
	    		<td><c:out value="${u.name}"/></td>
	    		<td>
				    <c:out value="${u.fullname}"/> </a>
				</td>
	    		<td align="right" colspan=2>
	    			<a href="userdetails.htm?id=<c:out value="${u.id}"/>">[user details]</a> 
	    			<a href="index.htm?mode=user&id=<c:out value="${u.id}"/>">[assignments]</a> 
	    			<c:if test="${user.adminRight}">
	    				<a href=<c:out value="adminuser.htm?userid=${u.id}"/>>[edit]</a>
						<c:if test="${u.id!=1}">
	    					<a href=<c:out value="user-delete-confirm.htm?id=${u.id}"/>>[delete]</a>
						</c:if>
	    			</c:if>
	    		</td>
	    	</tr>
	    <c:set var="rownumber" value="${rownumber+1}"/>
	    </c:forEach>
	    </table>
    </td>
    </tr></table>
</body>
<head>
	<%@include file="header.jsp.inc" %>
</head>
</html>
