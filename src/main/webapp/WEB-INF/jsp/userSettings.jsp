<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
	<%@include file="header.jsp.inc" %>
<title>Edit user settings</title> 
<script type="text/javascript">
</script>
</head>
<body>
    <div class="container">
        <c:if test="${!empty user.name}">
            <form:form modelAttribute="editUser" >
                <div class="form-group">
			        <h3>Edit user settings</h3>

		            <label>User name:</label>
		            <c:out value="${user.name}" />
                    <br />

			        <label>* E-mail:</label>
		            <form:input cssClass="form-control" size="40" path="email" /><form:errors path="email" />

		            <label>Full name:</label>
		            <form:input cssClass="form-control" size="80" path="fullname" />

		            <label>Where I am from:</label>
		            <form:input cssClass="form-control" size="80" path="place" />

		            <label>About myself:</label>
		            <form:textarea cssClass="form-control" rows="5" cols="70" path="description" />

		            <label>Rows per page in lists:</label>
		    	    <form:select cssClass="form-control" path="rowsPerPage" >
					    <form:option value="10"/>
					    <form:option value="20"/>
					    <form:option value="50"/>
					    <form:option value="100"/>
				    </form:select>

			        Fields marked with "*" is required fields.<br><br>

			        <h3>User rights</h3>

			        <ul>
		                <li>
		                    <label>Edit assignments:</label>
				            <c:if test="${user.editAssignmentsRight}">
					            YES
				            </c:if>
				            <c:if test="${!user.editAssignmentsRight}">
					            NO
				            </c:if>
			            </li>

		                <li>
		                    <label>Edit repository:</label>
				            <c:if test="${user.editRepositoryRight}">
					            YES
				            </c:if>
				            <c:if test="${!user.editRepositoryRight}">
					            NO
				            </c:if>
			            </li>

		                <li>
		                    <label>Administrator:</label>
				            <c:if test="${user.adminRight}">
					            YES
				            </c:if>
				            <c:if test="${!user.adminRight}">
					            NO
				            </c:if>
				        </li>
                    </ul>

			        <a href="user-edit-password.htm">Change password</a>

			        <div class="pull-right">
			            <input type="submit" class="btn btn-warning" name="save" value="Save changes">
			            <input type="button" class="btn" name="cancel" value="Cancel" onclick="history.go(-1);">
			        </div>
                </form:form>
            </c:if>
            <c:if test="${empty user.name}">
	            <b>You should be logged in to edit your settings!</b><br><br>
	            <a href="login.htm">[log in]</a> <a href="index.htm">Return to Homepage</a>
            </c:if>
        </div>
    </div>
	<%@include file="footer.jsp.inc" %>
</body>
</html>