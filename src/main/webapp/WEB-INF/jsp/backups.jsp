<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
  <head>
	<%@include file="header.jsp.inc" %>
  	<title>Smart Assignments | Backups</title>
  </head>
  <body>
    <h2>Smart Assignments - Backups</h2>
    <table width="100%">
		<tr class="header"><td colspan=2>
			<%@include file="top.inc.jsp" %>
	    </td></tr>
	<tr><td valign="top" width="150" class="menu">
	    <table>
	    	<c:if test="${!empty user}">
		    	<tr><td>
			    	<a href="repository-authors.htm">[authors]</a></td></tr>
		    	<tr><td>
					<a href="repository-classs.htm?parentid=0">[categories]</a>
				</td></tr>
		    	<tr><td>
					<a href="repository-modules.htm">[modules]</a>
				</td></tr>
		    	<tr><td>
					<a href="repository-files.htm">[files]</a>
				</td></tr>
		    	<tr><td><hr></td></tr>
			</c:if>
	    	<tr><td><a href="repository.htm">[templates]</a></td></tr>
	    	<c:if test="${!empty user && user.adminRight}">
		    	<tr><td><hr></td></tr>
		    	<tr><td class="selected-menu">[backups]</td></tr>
			</c:if>
	    </table>
    </td>
    <td valign="top">
		<c:if test="${(!empty user.name) && user.adminRight}">

		    <table width="95%" cellspacing="0">

		    <c:set var="rownumber" value="0"/>
		    <c:forEach items="${items}" var="i">
		    	<c:if test="${(rownumber % 2) == 1}">
		    		<tr class="row-dark">
		    	</c:if>
		    	<c:if test="${(rownumber % 2) == 0}">
		    		<tr class="row-light">
		    	</c:if>
		    		<td width="30%">
					    <a href="<c:out value="backup-file.htm?name=${i}"/>"> <c:out value="${i}"/> </a>
					</td>
		    	</tr>
		    <c:set var="rownumber" value="${rownumber+1}"/>
		    </c:forEach>

		    </table>
	    </c:if>
		<c:if test="${(empty user.name) || !user.adminRight}">
			<b>To download backups you need to log in as a user with appropriate rights!</b><br><br>
			<a href="login.htm">[log in]</a> <a href="index.htm">[return to homepage]</a>
	    </c:if>
    </td>
    </tr></table>
  </body>
<head>
	<%@include file="header.jsp.inc" %>
</head>
</html>
