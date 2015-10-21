<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
	<%@include file="header.jsp.inc" %>
	<title>Smart Assignments Repository | Import</title> 
</head>
<body>
<c:if test="${(!empty user.name) && user.adminRight}">
<form:form modelAttribute="item"  enctype="multipart/form-data">   
	<table>
		<tr class="header"><td colspan="2">
			<h3>Import Data into the Repository</h3>
		</td></tr>
		<tr>
		    <td>File to upload:</td>
		    <td><input type="file" name="file" size="50"/></td>
		</tr>
		<tr><td colspan=2>
			<br><b>Attention! Use this function very carefully. Incorrect data in file to be imported can lead to data loss and make site unusable!</b>
		</td></tr>
		<tr class="error"><td colspan="2">
			<form:errors path="*" />
		</td></tr>

		<tr class="header">
			<td><input type="submit" class="button" name="save" id="save" value="Import"></td>
			<td><input type="button" class="button" name="cancel" id="cancel" value="Cancel" onclick="history.go(-1);"></td>
		</tr>
	</table>
</form:form>
</c:if>
<c:if test="${(empty user.name) || !user.adminRight}">
	<b>To import external data into the repository you need to log in as a user with appropriate rights!</b><br><br>
	<a href="login.htm">[log in]</a> <a href="index.htm">[return to homepage]</a>
</c:if>
</body>
<head>
	<%@include file="header.jsp.inc" %>
</head>
</html>