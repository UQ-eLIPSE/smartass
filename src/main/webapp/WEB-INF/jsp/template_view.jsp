<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
	<%@include file="header.jsp.inc" %>
	<title>Template <c:out value="${template_name}"/> - Smart Assignments</title>
  </head>
  <body>
    <pre>
<c:out value="${content}"/>      
	 </pre>
  </body>
<head>
	<%@include file="header.jsp.inc" %>
</head>
</html>
