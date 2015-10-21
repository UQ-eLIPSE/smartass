<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<%@include file="header.jsp.inc" %>
	<title>Smart Assignments Repository | Delete Assignment</title> 
</head>
<body>
<c:if test="${(!empty user.name) && user.editAssignmentsRight && user.id==item.user.id}">
<form action="assignment-delete.htm">
	<table>
		<tr class="header"><td colspan="2">
			<h2>Confirm Assignment Deletion</h2>
		</td></tr>
		<c:if test="${!empty item}">
			<input type="hidden" name="id" value="<c:out value="${item.id}"/>">
			<tr>
			    <td><b>Name:</b></td>
			    <td><c:out value="${item.name}" /></td>
			</tr>
			<tr>
			    <td><b>Description:</b></td>
			    <td><c:out value="${item.description}" /></td>
			</tr>
			<tr><td colspan="2">
				<input type="checkbox" name="confirmed" value="yes">Yes, I really want to delete this assignment.<br><br>
			</td></tr>
	
			<tr class="header">
				<td><input type="submit" class="button" name="save" value="Delete"></td>
				<td><input type="button" class="button" name="cancel" value="Cancel" onclick="history.go(-1);"></td>
			</tr>
		</c:if>
		<c:if test="${empty item}">
			<tr><td>Assignment does not exists.</td></tr>
			<tr><td><a href="index.htm">[return to assignments list]</a>
		</c:if>
	</table>
</form>
</c:if>
<c:if test="${(empty user.name) || !user.editRepositoryRight}">
	<b>To delete an assignment you need to log in as a user with appropriate rights!</b><br><br>
	<a href="login.htm">[log in]</a> <a href="index.htm">[return to homepage]</a>
</c:if>
</body>
<head>
	<%@include file="header.jsp.inc" %>
	<title>Smart Assignments Repository | Delete Assignment</title> 
</head>
</html>