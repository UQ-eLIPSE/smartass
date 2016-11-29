<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
	<%@include file="header.jsp.inc" %>
	<title>Smart Assignments Repository | Edit Module</title> 
</head>
<body>
<div class="container">
<c:if test="${(!empty user.name) && user.editRepositoryRight}">
<form:form modelAttribute="item" >   
	<table>
		<tr class="header"><td colspan="2">
			<c:if test="${item.id==0}">
				<h3>New module</h3>
			</c:if>
			<c:if test="${item.id!=0}">
				<h3>Edit module</h3>
			</c:if>
		</td></tr>
		<tr valign="top">
		    <td><b>* Name:</b></td>
		    <td><form:input size="50" path="name" /></td>
		</tr>
		<tr valign="top">
		    <td>Package:</td>
		    <td><form:input size="50" path="modulePackage" /></td>
		</tr>
		<tr valign="top">
		    <td>Parameters:</td>
		    <td><form:textarea rows="8" cols="80" path="parameters" /></td>
		</tr>
		<tr valign="top">
		    <td>Description:</td>
		    <td><form:textarea rows="10" cols="80" path="description" /></td>
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
	<b>To edit module you need to log in as a user with appropriate rights!</b><br><br>
	<a href="login.htm">[log in]</a> <a href="index.htm">[return to homepage]</a>
</c:if>
</div>
	<%@include file="footer.jsp.inc" %>
</body>
</html>