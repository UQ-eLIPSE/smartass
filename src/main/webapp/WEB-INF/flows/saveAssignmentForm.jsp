<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
	<%@include file="../jsp/header.jsp.inc" %>
	<title>Smart Assignments | Editor | Save Assignment</title> 
</head>
    <body>
        <div class="container">
            <c:if test="${(!empty user.name) && user.editAssignmentsRight}">
                <form:form modelAttribute="template" >
                    <div class="form-group">
                            <label>* Assignment Name:</label>
                            <form:input cssClass="form-control" size="50" path="name" />
                            <label>Description:</label>
                            <form:textarea cssClass="form-control" rows="5" cols="70" path="description" />
        <!--
                            <label>Tags:</label>
                            <form:input cssClass="form-control" size="50" path="tags" />
                     -->
                            <label>Fields marked with "*" are required.</label>

                            <div class="error">
                                <form:errors path="*" />
                            </div>

                            <input type="submit" class="btn" name="_eventId_ok" value="Save changes">
                            <input type="button" class="btn" name="_eventId_cancel" value="Cancel" onclick="history.go(-1);">
                    </div>
                </form:form>
            </c:if>
            <c:if test="${(empty user.name) || !user.editRepositoryRight}">
                <b>To edit author you need to log in as a user with appropriate rights!</b><br><br>
                <a href="login.htm">[log in]</a> <a href="index.htm">return to homepage</a>
            </c:if>
        </div>
    </body>
</html>