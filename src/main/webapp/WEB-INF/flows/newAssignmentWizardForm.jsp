<%@page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
	<%@include file="../jsp/header.jsp.inc" %>
	<title>Assignments Composer | Create New Assignment</title>
</head>
<body>
	<form:form modelAttribute="items" method="POST">
		<table>
			<tr class="header">
				<td colspan=2><h2>Assignments Composer - Create New Assignment</h2></td>
				<td align="center"><a href="help.htm?context=ass-new" target="_blank">[help]</a></td>
			</tr>
			<tr><td class="error">
				<form:errors path="*" />
			</td></tr>
			<c:set var="i" value="0"/>
			<c:forEach items="${items.items}" var="it">
				<tr><td>
					<c:out value="${it.title}"/>
					<c:if test="${it.type=='I'}">
						<form:input path="items[${i}].value" size="10"/>
					</c:if> 
					<c:if test="${it.type=='L'}">
						<br>
						<form:input path="items[${i}].value" size="120"/>
					</c:if> 
					<c:if test="${it.type=='T'}">
						<br>
						<form:textarea path="items[${i}].value" cols="80" rows="5"/>
					</c:if> 
					<c:set var="i" value="${i+1}"/>
					<tr><td class="error">
						<form:errors path="items" />
					</td></tr>
				</td></tr>
			</c:forEach>

			<tr><td> </td></tr>
		</table>		
   
		<input style="width:125" type="submit" class="button" name="_eventId_ok" value="Next"/>
		<input style="width:125" type="submit" class="button" name="_eventId_cancel" value="Cancel"/>
	</form:form>
</body></html>