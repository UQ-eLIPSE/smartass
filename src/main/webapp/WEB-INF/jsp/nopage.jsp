<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
	<%@include file="header.jsp.inc" %>
	<meta HTTP-EQUIV="REFRESH" content="15; url=<c:out value="${redirectUri}"/>">
  	<title>Smart Assignments | Page not Found</title>
  </head>
  <body>
    <div class="container">
        <h2>Smart Assignments - Page not Found</h2>
        <table width="100%">
	        <tr>
                <td valign="top">
    	            <c:out escapeXml="false" value="${message}"/> <br><br>
    	            You will be redirected to the <a href="<c:out value="${redirectUri}"/>">homepage</a> in 10 sec.<br>
                </td>
            </tr>
        </table>
    </div>
  </body>
</html>
