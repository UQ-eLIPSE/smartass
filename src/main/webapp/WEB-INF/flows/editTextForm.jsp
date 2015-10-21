<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html><body>
	<form:form method="post" modelAttribute="item">   
		<input type="hidden" name="_flowExecutionKey" value="<c:out value="${flowExecutionKey}"/>"/>
		<form:textarea path="text" rows="10" cols="40"/><br/>
		
		<input style="width:125" type="submit" class="button" name="_eventId_ok" value="OK"/>
		<input style="width:125" type="submit" class="button" name="_eventId_cancel" value="Cancel"/>
	</form:form>
</body></html>