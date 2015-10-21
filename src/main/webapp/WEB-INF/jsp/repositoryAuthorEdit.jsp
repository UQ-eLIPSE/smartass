<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
	<%@include file="header.jsp.inc" %>
	<title>Smart Assignments Repository | Edit Author</title> 
</head>
<body>
<c:if test="${(!empty user.name) && user.editRepositoryRight}">
<form:form modelAttribute="item" >   
	<table>
		<tr class="header"><td colspan="2">
			<c:if test="${item.id==0}">
				<h3>New author</h3>
			</c:if>
			<c:if test="${item.id!=0}">
				<h3>Edit author</h3>
			</c:if>
		</td></tr>
		<tr>
		    <td><b>* Name:</b></td>
		    <td><form:input size="50" path="name" /></td>
		</tr>
		<tr>
		    <td>Description:</td>
		    <td><form:textarea rows="5" cols="70" path="description" /></td>
		</tr>
		<tr><td colspan="2">
			Fields marked with "*" is required fields.<br><br>
		</td></tr>

		<tr class="error"><td colspan="2">
			<form:errors path="*" />
		</td></tr>

		<tr class="header">
			<td><input type="submit" class="button" name="save" value="Save changes"></td>
			<td><input type="button" class="button" name="cancel" value="Cancel" onclick="history.go(-1);"></td>
		</tr>
	</table>
</form:form>
</c:if>
<c:if test="${(empty user.name) || !user.editRepositoryRight}">
	<b>To edit author you need to log in as a user with appropriate rights!</b><br><br>
	<a href="login.htm">[log in]</a> <a href="index.htm">[return to homepage]</a>
</c:if>
</body>
<head>
	<%@include file="header.jsp.inc" %>
	<title>Smart Assignments Repository | Edit Author</title> 
</head>
</html>