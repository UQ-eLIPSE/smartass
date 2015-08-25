<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
<head>
	<%@include file="header.jsp.inc" %>
	<title>Smart Assignments Repository | Delete Category</title> 
	<script type="text/javascript">
		function onLoad() {
			if(document.getElementById("cbYes").checked)
				document.getElementById("btSave").disabled = false;
			else
				document.getElementById("btSave").disabled = true;
		}
		function onClickConfirmation(e) {
			if(window.event) // IE
			  {
			  keynum = e.keyCode;
			  }
			else if(e.which) // Netscape/Firefox/Opera
			  {
			  keynum = e.which;
			  }
			keychar = String.fromCharCode(keynum);
			
			if(document.getElementById("cbYes").checked)
				document.getElementById("btSave").disabled = false;
			else
				document.getElementById("btSave").disabled = true;
		}
	</script>
</head>
<body onload="onLoad()">
<c:if test="${(!empty user.name) && user.editRepositoryRight}">
<form action="repository-class-delete.htm">
	<table>
		<tr class="header"><td colspan="2">
			<h3>Confirm category deletion</h3>
		</td></tr>
		<c:if test="${!empty item}">
			<input type="hidden" name="id" value="<c:out value="${item.id}"/>">
			<input type="hidden" name="parentid" value="<c:out value="${item.parentModel.id}"/>">
			<c:if test="${!empty item.parentModel}">
				<tr>
				    <td><b>Parent:</b></td>
				    <td><c:out value="${item.parentModel.name}" /></td>
				</tr>
			</c:if>
			<tr>
			    <td><b>Name:</b></td>
			    <td><c:out value="${item.name}" /></td>
			</tr>
			<tr>
			    <td><b>Description:</b></td>
			    <td><c:out value="${item.description}" /></td>
			</tr>
			<tr><td colspan="2">
				<input type="checkbox" name="confirmed" id="cbYes" value="yes" onclick="onClickConfirmation(event)">Yes, I really want to delete this category.<br><br>
			</td></tr>
	
			<tr class="header">
				<td><input type="submit" class="button" name="save" value="Delete" id="btSave"></td>
				<td><input type="button" class="button" name="cancel" value="Cancel" onclick="history.go(-1);"></td>
			</tr>
		</c:if>
		<c:if test="${empty item}">
			<tr><td>Category does not exists.</td></tr>
			<tr><td><a href="repository-classs.htm">[return to clasifications list]</a>
		</c:if>
	</table>
</form>
</c:if>
<c:if test="${(empty user.name) || !user.editRepositoryRight}">
	<b>To delete a category you need to log in as a user with appropriate rights!</b><br><br>
	<a href="login.htm">[log in]</a> <a href="index.htm">[return to homepage]</a>
</c:if>
</body>
<head>
	<%@include file="header.jsp.inc" %>
</head>
</html>