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
			<c:if test="${item.id==0}">
				<h3>New module</h3>
			</c:if>
			<c:if test="${item.id!=0}">
				<h3>Edit module</h3>
			</c:if>
		    <label>* Name:</label>
		    <form:input cssClass="form-control" size="50" path="name" /></td>

		    <label>Package:</label>
		    <form:input cssClass="form-control" size="50" path="modulePackage" />

		    <label>Parameters:</label>
		    <form:textarea cssClass="form-control" rows="8" cols="80" path="parameters" />

		    <label>Description:</label>
		    <form:textarea cssClass="form-control" rows="10" cols="80" path="description" />

			Fields marked with "*" is required fields.<br><br>

			<form:errors path="*" />

		<div class="pull-right">
			<td><input type="submit" class="btn btn-warning" name="save" value="Save changes"></td>
			<td><input type="button" class="btn" name="cancel" value="Cancel" onclick="history.go(-1);"></td>
		</div>
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