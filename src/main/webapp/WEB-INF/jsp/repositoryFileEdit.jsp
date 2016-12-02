<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
	<%@include file="header.jsp.inc" %>
	<title>Smart Assignments Repository | Edit File</title> 
	<script type="text/javascript">
	function enableUpload()
	{	
			file.disabled = !replaceFile.checked();
	}
	</script>
</head>
<body>
    <div class="container">
        <c:if test="${(!empty user.name) && user.editRepositoryRight}">
            <form:form modelAttribute="item"  enctype="multipart/form-data">
                <div class="form-group">
			        <c:if test="${item.id==0}">
				        <h3>New file</h3>
			        </c:if>
			        <c:if test="${item.id!=0}">
				        <h3>Edit file</h3>
			        </c:if>
		            <label>* Name:</label>
		            <form:input cssClass="form-control" size="50" path="name" />
		            <label>Description:</label>
		            <form:textarea cssClass="form-control" rows="5" cols="70" path="description" />
		            <c:if test="${item.id!=0}">
				        <form:checkbox path="replaceFile" value="1" onchange="enableUpload();"/>
				        Replace the file in the repository with new one.
		            </c:if>
		            <label>File to upload:</label>
		            <input cssClass="form-control" type="file" name="file" size="50"/></td>
			        Note: despite of the name of the file on your system it will be stored under the name entered in the "Name" field.
			        Fields marked with "*" is required fields.<br><br>
			        <form:errors path="*" />

                    <div class="pull-right">
			           <input type="submit" class="btn btn-warning" name="save" id="save" value="Save changes">
			           <input type="button" class="btn" name="cancel" id="cancel" value="Cancel" onclick="history.go(-1);">
			        </div>
			    </div>
            </form:form>
        </c:if>
        <c:if test="${(empty user.name) || !user.editRepositoryRight}">
	        <b>To edit file you need to log in as a user with appropriate rights!</b><br><br>
	        <a href="login.htm">Log in</a> <a href="index.htm">Return to Homepage</a>
        </c:if>
    </div>
	<%@include file="footer.jsp.inc" %>
</body>
</html>