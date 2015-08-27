<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
<head>
	<%@include file="../jsp/header.jsp.inc" %>
	<title>Smart Assignments Repository | Add new template | Step 5: Upload Template Examples</title> 
</head>
<body>
	<form method="POST" enctype="multipart/form-data">
		<table>
			<tr class="header"><td colspan=2><h2>
				Step 5: Upload Template Examples
			</td></tr>
			<tr>
				<td><b>Questions:</b></td>
				<td><input type="file" name="questions" size="50"/></td>
			</tr>
			<tr>
				<td><b>Solutions:</b></td>
				<td><input type="file" name="solutions" size="50"/></td>
			</tr>
			<tr>
				<td><b>Short answers:</b></td>
				<td><input type="file" name="shortanswers" size="50"/></td>
			</tr>
		</table>		
   
		<c:if test="${mode=='new'}">
			<input style="width:125" type="submit" class="button" name="_eventId_ok" value="Next"/>
			<input style="width:125" type="button" class="button" value="Back" onclick="history.go(-1);"/>
		</c:if>
		<c:if test="${mode=='edit'}">
			<input style="width:125" type="submit" class="button" name="_eventId_ok" value="Ok"/>
		</c:if>
		<input style="width:125" type="submit" class="button" name="_eventId_cancel" value="Cancel"/>
	</form>
</body></html>