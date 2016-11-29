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
	<table>
		<tr class="header"><td colspan="2">
			<c:if test="${item.id==0}">
				<h3>New file</h3>
			</c:if>
			<c:if test="${item.id!=0}">
				<h3>Edit file</h3>
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
		<c:if test="${item.id!=0}">
			<tr><td colspan=2>
				<form:checkbox path="replaceFile" value="1" onchange="enableUpload();"/> 
				Replace the file in the repository with new one.
			</td></tr>
		</c:if>
		<tr>
		    <td>File to upload:</td>
		    <td><input type="file" name="file" size="50"/></td>
		</tr>
		<tr><td colspan=2>
			Note: despite of the name of the file on your system it will be stored under the name entered in the "Name" field.
		</td></tr>
		<tr><td colspan="2">
			Fields marked with "*" is required fields.<br><br>
		</td></tr>
		<tr class="error"><td colspan="2">
			<form:errors path="*" />
		</td></tr>

		<tr class="header">
			<td><input type="submit" class="button" name="save" id="save" value="Save changes"></td>
			<td><input type="button" class="button" name="cancel" id="cancel" value="Cancel" onclick="history.go(-1);"></td>
		</tr>
	</table>
</form:form>
</c:if>
<c:if test="${(empty user.name) || !user.editRepositoryRight}">
	<b>To edit file you need to log in as a user with appropriate rights!</b><br><br>
	<a href="login.htm">[log in]</a> <a href="index.htm">[return to homepage]</a>
</c:if>
    </div>
	<%@include file="footer.jsp.inc" %>
</body>
</html>