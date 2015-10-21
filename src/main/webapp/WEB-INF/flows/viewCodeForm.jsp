<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<head>
	<title>Assignment Editor | Code Preview</title>
	<%@include file="../jsp/header.jsp.inc" %>
	<script  type="text/javascript">
		
	</script>
</head>
<html><body onload="save.disabled = true;">
	<form method="post">
		<table>
			<tr class="header">
				<td><h2>Assignment Editor - Code Preview</h2></td>
				<td><a href="help.htm?context=ass-code" target="_blank">[help]</a></td>
			</tr>
			<tr><td colspan="2">
				<textarea name="code" style="height:90%;width:99%;" rows=30
					onkeypress="save.disabled = false;" onchange="save.disabled = false;"><c:out value="${code}"/></textarea>   
			</td></tr>
			<tr class="header"><td colspan="2">
				<input type="button" class="button" name="download" value="Download" 
					onclick="window.location.href = 'download-assignment.htm?execution=<c:out value="${flowExecutionKey}"/>'"/>
				<input style="width:125" type="submit" class="button" name="_eventId_execute" value="Execute"/>
				<input type="submit" class="button" name="_eventId_interactive" value="Interactive Editor (BETA)"/>
				<input style="width:125" type="submit" class="button" name="_eventId_save" id='save' value="Save"/>
				<input style="width:125" type="submit" class="button" name="_eventId_cancel" value="Cancel"/>
			</td></tr>
		</table>
	</form>
</body></html>