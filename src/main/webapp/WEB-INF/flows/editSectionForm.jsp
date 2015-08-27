<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html><body>
	<form:form method="post" modelAttribute="item">   
		<table>
		<tr><td>
			<b>SECTION </b>
			<form:select path="sectionName" >
				<form:option value="QUESTION"/>
				<form:option value="SOLUTION"/>
				<form:option value="SHORTANSWER"/>
			</form:select >
		</td></tr>
		</table>
		<input style="width:125" type="submit" class="button" name="_eventId_ok" value="OK"/>
		<input style="width:125" type="submit" class="button" name="_eventId_cancel" value="Cancel"/>
	</form:form>
</body></html>