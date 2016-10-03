<%@page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
	<%@include file="../jsp/header.jsp.inc" %>
	<title>Assignments Composer | Create New Assignment</title>
</head>
<body>
    <div class="container">
        <form:form modelAttribute="items" method="POST">
            <div class="form-group">
                <h2>Assignments Composer - Create New Assignment</h2>
                <span class="error">
                    <form:errors path="*" />
                </span>
                <c:set var="i" value="0"/>
                <c:forEach items="${items.items}" var="it">
                        <label><c:out value="${it.title}"/></label>
                        <c:if test="${it.type=='I'}">
                            <form:input cssClass="form-control" path="items[${i}].value" size="10"/>
                        </c:if>
                        <c:if test="${it.type=='L'}">
                            <br>
                            <form:input cssClass="form-control" path="items[${i}].value" size="120"/>
                        </c:if>
                        <c:if test="${it.type=='T'}">
                            <br>
                            <form:textarea cssClass="form-control" path="items[${i}].value" cols="80" rows="5"/>
                        </c:if>
                        <c:set var="i" value="${i+1}"/>
                        <span class="error">
                            <form:errors path="items" />
                        </span>
                </c:forEach>
            </div>

            <button style="width:125" type="submit" class="btn btn-warning pull-right" name="_eventId_ok" value="Next">Next <span class="glyphicon glyphicon-arrow-right"></span></button>
            <!-- <input style="width:125" type="submit" class="button" name="_eventId_cancel" value="Cancel"/> -->
        </form:form>
    </div>
</body>
</html>