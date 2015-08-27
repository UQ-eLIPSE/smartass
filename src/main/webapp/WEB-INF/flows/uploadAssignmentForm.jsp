<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html><body>
	<form:form method="POST" enctype="multipart/form-data">
		Upload file: <input type="file" name="file" size="50"/>					
		<input style="width:125" type="submit" class="button" name="_eventId_ok" value="OK"/>
		<input style="width:125" type="submit" class="button" name="_eventId_cancel" value="Cancel"/>
	</form:form>
</body></html>