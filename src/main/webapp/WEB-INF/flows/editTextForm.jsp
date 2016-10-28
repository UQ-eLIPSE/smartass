<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
    <head>
	    <%@include file="../jsp/header.jsp.inc" %>
    </head>
    <body>
        <div class="container">
	        <form:form method="post" modelAttribute="item">
	            <div class="form-group">
		            <input type="hidden" name="_flowExecutionKey" value="<c:out value="${flowExecutionKey}"/>"/>
		            <form:textarea cssClass="form-control" path="text" rows="10" cols="40"/><br/>
		
		            <input style="width:125" type="submit" class="btn" name="_eventId_ok" value="OK"/>
		            <input style="width:125" type="submit" class="btn" name="_eventId_cancel" value="Cancel"/>
		        </div>
	    </form:form>
    </body>
</html>