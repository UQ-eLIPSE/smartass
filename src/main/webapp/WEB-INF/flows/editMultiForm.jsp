<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html><body>
	<form:form method="post" modelAttribute="item">   
		<input type="hidden" name="_flowExecutionKey" value="<c:out value="${flowExecutionKey}"/>"/>
		<table>
		<tr><td>
			<b>MULTI </b><form:input path="choicesCount"/>
		</td></tr>
		</table>
		<input style="width:125" type="submit" class="button" name="_eventId_ok" value="OK"/>
		<input style="width:125" type="submit" class="button" name="_eventId_cancel" value="Cancel"/>
	</form:form>
</body></html>