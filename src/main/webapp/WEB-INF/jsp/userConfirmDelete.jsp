<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<%@include file="header.jsp.inc" %>
	<title>Smart Assignments Repository | Delete Assignment</title> 
</head>
<body>
<c:if test="${(!empty user.name) && user.adminRight}">
	<form action="user-delete.htm">
		<table>
			<tr class="header"><td colspan="2">
				<h2>Confirm User Deletion</h2>
			</td></tr>
			<c:if test="${!empty item}">
				<c:if test="${item.id!=1}">
					<input type="hidden" name="id" value="<c:out value="${item.id}"/>">
					<tr>
					    <td><b>User's name:</b></td>
					    <td><c:out value="${item.name}" /></td>
					</tr>
					<tr>
					    <td><b>Full name:</b></td>
					    <td><c:out value="${item.fullname}" /></td>
					</tr>
					<tr>
					    <td><b>Where I am from:</b></td>
					    <td><c:out value="${item.place}" /></td>
					</tr>
					<tr>
					    <td><b>About myself:</b></td>
					    <td><c:out value="${item.description}" /></td>
					</tr>
					<tr><td colspan="2">
						<input type="checkbox" name="confirmed" value="yes">Yes, I really want to delete this user.<br><br>
					</td></tr>
			
					<tr class="header">
						<td><input type="submit" class="button" name="delete" value="Delete"></td>
						<td><input type="button" class="button" name="cancel" value="Cancel" onclick="history.go(-1);"></td>
					</tr>
				</c:if>
				<c:if test="${item.id==1}">
					<tr><td><b>You can not delete Administrator!</b></td></tr>
					<tr><td><a href="index.htm">[return to assignments list]</a>
				</c:if>
			</c:if>
			<c:if test="${empty item}">
				<tr><td>User does not exists.</td></tr>
				<tr><td><a href="index.htm">[return to assignments list]</a>
			</c:if>
		</table>
	</form>
</c:if>
<c:if test="${(empty user.name) || !user.adminRight}">
	<b>To delete a user you need to log in as a user with appropriate rights!</b><br><br>
	<a href="login.htm">[log in]</a> <a href="index.htm">[return to homepage]</a>
</c:if>
</body>
<head>
	<%@include file="header.jsp.inc" %>
	<title>Smart Assignments Repository | Delete Assignment</title> 
</head>
</html>