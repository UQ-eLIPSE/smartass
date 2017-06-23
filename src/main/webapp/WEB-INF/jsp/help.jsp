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
            <tr class="header"><td><h2>Smart Assignments Help</h2></td></tr>
            <tr><td><p><em>SmartAss Enquiry:</em> Michael Jennings | 07-3365-3255 | <a href="mailto:msj@uq.edu.au">msj@uq.edu.au</a></p></td></tr>
            <tr><td><p><a href="./pdf/SmartAss-QuestionAndSolutionGenerator.pdf">SmartAss: Question and Solution Generator</a> PDF instructions</p></td></tr>
            <tr class="header"><td><h3><c:out value="${title}"/></h3></td></tr>
            <tr>
                <td><c:out value="${text}" escapeXml="false"/></td>
            </tr>
            <tr>
            </tr>
        </table>
    </div>
</body>
</html>
