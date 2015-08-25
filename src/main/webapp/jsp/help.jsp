<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
	<title>Smart Assignments | Help | Home page</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/smartass.css" />
</head>
<body>
	<table width="100%">
		<tr class="header"><td><h3>Smart Assignments Help - <c:out value="${title}"/></h3></td></tr>
		<tr>
		    <td><c:out value="${text}" escapeXml="false"/></td>
		</tr>
		<tr>
			<td class="header"><a href="javascript:window.close()">[close]</a></td>
		</tr>
	</table>
</body>
<head>
	<%@include file="header.jsp.inc" %>
</head>
</html>
