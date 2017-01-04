<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<%@include file="header.jsp.inc" %>
	<title>Smart Assignments Repository | Delete Assignment</title> 
</head>
<body>
    <div class="container">
<c:if test="${(!empty user.name) && user.editAssignmentsRight && user.id==item.user.id}">
<form action="assignment-delete.htm">
    <div class="form-group">
			<h2>Confirm Assignment Deletion</h2>
		<c:if test="${!empty item}">
			<input type="hidden" name="id" value="<c:out value="${item.id}"/>">
			    <label>Name:</label>
			    <c:out value="${item.name}" /><br />
			    <label>Description:</label>
			    <c:out value="${item.description}" /><br /><br />
				<input cssClass="form-control" type="checkbox" name="confirmed" value="yes"> Yes, I really want to delete this assignment.<br><br>

				<input type="submit" class="btn btn-danger" name="save" value="Delete" />
				<input type="button" class="btn" name="cancel" value="Cancel" onclick="history.go(-1);" />
		</c:if>
		<c:if test="${empty item}">
			Assignment does not exists.
			<a href="index.htm">[return to assignments list]</a>
		</c:if>
		</div>
</form>
</c:if>
<c:if test="${(empty user.name) || !user.editRepositoryRight}">
	<b>To delete an assignment you need to log in as a user with appropriate rights!</b><br><br>
	<a href="login.htm">[log in]</a> <a href="index.htm">[return to homepage]</a>
</c:if>
	</div>
	    <%@include file="footer.jsp.inc" %>
</body>
</html>