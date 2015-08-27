<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
	<%@include file="../jsp/header.jsp.inc" %>
	<title>Smart Assignments | Editor | Save Assignment</title> 
</head>
<body>
<c:if test="${(!empty user.name) && user.editAssignmentsRight}">
<form:form modelAttribute="template" >   
	<table>
		<tr>
		    <td><b>* Assignment Name:</b></td>
		    <td><form:input size="50" path="name" />
		</tr>
		<tr>
		    <td>Description:</td>
		    <td><form:textarea rows="5" cols="70" path="description" /></td>
		</tr>
<!-- 		<tr>
		    <td>Tags:</td>
		    <td><form:input size="50" path="tags" />
		</tr> -->
		<tr><td colspan="2">
			Fields marked with "*" is required fields.<br><br>
		</td></tr>

		<tr><td class="error" colspan=2>
			<form:errors path="*" />
		</td></tr>

		<tr class="header">
			<td><input type="submit" class="button" name="_eventId_ok" value="Save changes"></td>
			<td><input type="button" class="button" name="_eventId_cancel" value="Cancel" onclick="history.go(-1);"></td>
		</tr>
	</table>
</form:form>
</c:if>
<c:if test="${(empty user.name) || !user.editRepositoryRight}">
	<b>To edit author you need to log in as a user with appropriate rights!</b><br><br>
	<a href="login.htm">[log in]</a> <a href="index.htm">[return to homepage]</a>
</c:if>
</body></html>