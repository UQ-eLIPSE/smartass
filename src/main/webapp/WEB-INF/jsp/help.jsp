<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
	<title>Smart Assignments | Help | Home page</title>
	<%@include file="../jsp/header.jsp.inc" %>
</head>
<body>
    <div class="container">
        <table width="100%">
            <tr class="header"><td><h3>Smart Assignments Help - <c:out value="${title}"/></h3></td></tr>
            <tr>
                <td><c:out value="${text}" escapeXml="false"/></td>
            </tr>
            <tr>
            </tr>
        </table>
    </div>
</body>
</html>
