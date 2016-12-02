<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<%@include file="header.jsp.inc" %>
	<title>Smart Assignments Repository - Delete module</title> 
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
<div class="container">
<c:if test="${(!empty user.name) && user.editRepositoryRight}">
<form action="repository-module-delete.htm">
			<h3>Confirm module deletion</h3>
		<c:if test="${!empty item}">
			<input type="hidden" name="id" value="<c:out value="${item.id}"/>">
			<p>
			    <h4>Name:</h4>
			    <c:out value="${item.name}" />
            </p>

            <p>
			    <h4>Package:</h4>
			    <c:out value="${item.modulePackage}" />
            </p>

            <p>
			    <h4>Parameters:</h4>
			    <c:out value="${item.parameters}" />
            </p>

            <p>
			    <h4>Description:</h4>
			    <c:out value="${item.description}" />
            </p>
			    <div class="checkbox">
			        <label>
				        <input type="checkbox" name="confirmed" id="cbYes" value="yes" onclick="onClickConfirmation(event)">
				        Yes, I really want to delete this module.
				    </label>
				</div>

			<div class="pull-right">
				<td><input type="submit" class="btn btn-danger" name="save" id="btSave" value="Delete"></td>
				<td><input type="button" class="btn" name="cancel" value="Cancel" onclick="history.go(-1);"></td>
			</div>

		</c:if>

		<c:if test="${empty item}">
			<label>Author does not exists.</label>
			<a href="Repository-authors.htm">Return to Authors list</a>
		</c:if>
</form>
</c:if>
<c:if test="${(empty user.name) || !user.editRepositoryRight}">
	<b>To delete module you need to log in as a user with appropriate rights!</b><br><br>
	<a href="login.htm">[log in]</a> <a href="index.htm">[return to homepage]</a>
</c:if>
</div>
	<%@include file="footer.jsp.inc" %>
</body>
</html>