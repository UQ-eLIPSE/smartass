<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
<head>
	<%@include file="header.jsp.inc" %>
	<title>Smart Assignments Repository | Delete Template</title> 
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
<form action="repository-template-delete.htm">
	<table>
		<tr class="header"><td colspan="2">
			<h2>Confirm Template Deletion</h2>
		</td></tr>
		<c:if test="${!empty item}">
			<input type="hidden" name="id" value="<c:out value="${item.id}"/>">
			<input type="hidden" name="classid" value="<c:out value="${item.classifications[0].id}"/>">
			<tr>
			    <td width="10"><b>Name:</b></td>
			    <td><c:out value="${item.name}" /></td>
			</tr>
			<tr>
			    <td><b>Description:</b></td>
			    <td><c:out value="${item.description}" /></td>
			</tr>
			<tr><td colspan="2">
				<input type="checkbox" name="confirmed" value="yes" id="cbYes" onclick="onClickConfirmation(event)">Yes, I really want to delete this template.<br><br>
			</td></tr>
	
			<tr class="header">
				<td><input type="submit" class="button" name="save" id="btSave" value="Delete"></td>
				<td><input type="button" class="button" name="cancel" value="Cancel" onclick="history.go(-1);"></td>
			</tr>
		</c:if>
		<c:if test="${empty item}">
			<tr><td>Template does not exists.</td></tr>
			<tr><td><a href="repository.htm">[return to templates list]</a>
		</c:if>
	</table>
</form>
</c:if>
<c:if test="${(empty user.name) || !user.editRepositoryRight}">
	<b>To delete template you need to log in as a user with appropriate rights!</b><br><br>
	<a href="login.htm">[log in]</a> <a href="index.htm">[return to homepage]</a>
</c:if>
</body>
<head>
	<%@include file="header.jsp.inc" %>
</head>
</html>