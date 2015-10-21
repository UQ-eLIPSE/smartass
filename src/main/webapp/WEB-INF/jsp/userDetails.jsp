<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
	<%@include file="header.jsp.inc" %>
<title>Smart Assignments | User Details</title> 
<script type="text/javascript">
</script>
</head>
<body>
<form:form modelAttribute="editUser" >   
	<table>
		<tr bgcolor="#f0f0f0"><td colspan="2">
			<h3>User Details</h3>
		</td></tr>
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

		<tr bgcolor="#f0f0f0"><td colspan="2">
			<h3>User's Rights</h3>
		</td></tr>
		<tr>
		    <td><b>Edit assignments:</b></td>
		    <td>
				<c:if test="${item.editAssignmentsRight}">
					<b>YES</b>
				</c:if>
				<c:if test="${!item.editAssignmentsRight}">
					<b>NO</b>
				</c:if>
		    </td>
		</tr>
		<tr>
		    <td><b>Edit repository:</b></td>
		    <td>
				<c:if test="${item.editRepositoryRight}">
					<b>YES</b>
				</c:if>
				<c:if test="${!item.editRepositoryRight}">
					<b>NO</b>
				</c:if>
		    </td>
		</tr>
		<tr>
		    <td><b>Administrator:</b></td>
		    <td>
				<c:if test="${item.adminRight}">
					<b>YES</b>
				</c:if>
				<c:if test="${!item.adminRight}">
					<b>NO</b>
				</c:if>
		    </td>
		</tr>

		<tr bgcolor="#f0f0f0">
			<td><input type="button" class="button" name="cancel" value="Back" onclick="history.go(-1);"></td>
		</tr>
	</table>
</form:form>
</body>
<head>
	<%@include file="header.jsp.inc" %>
</head>
</html>