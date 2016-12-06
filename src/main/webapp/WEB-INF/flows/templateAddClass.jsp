<%@page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
	<%@include file="../jsp/header.jsp.inc" %>
	<title>Smart Assignments Repository | Edit Template | Add a Category to the Template</title> 
</head>
<body>
    <div class="container">
        <form:form method="POST" modelAttribute="item">

            <h2>Add Category</h2>

            <div class="panel panel-default">
                <div class="panel-heading">Search in Database</div>
                <div class="panel-body">
                    <c:if test="${item.parentid!=0}">
                        <p>Parent category: <b><c:out value="${parent.name}"/></b></p>
                    </c:if>

                    <div class="form-group">
                        <label for="nameSearch">Search a category</label>
                        <div class="input-group">
                            <form:input path="search" maxlength="128" id="nameSearch" cssClass="form-control"/>
                            <span class="input-group-btn">
                                <input type=submit class="btn btn-primary" name="_eventId_search" value="Search">
                            </span>

                        </div>

                        <br>
                        <c:if test="${item.parentid!=0}">
                                <a class="btn btn-default" href="repository-template-edit.htm?execution=<c:out value="${flowExecutionKey}"/>&_eventId_goDeep&parentid=0">Top level</a>
                        </c:if>
                    </div>
                </div>
            </div>

            <ul class="list-group">
                <c:forEach items="${found}" var="i">
                    <li class="list-group-item" style="line-height: 2.5;">
                        <form:radiobutton path="solution" value="${i.id}"/>
                        <c:out value="${i.name}"/>

                        <c:if test="${item.parentId==0}">
                            <a class="pull-right btn btn-primary" href="repository-template-edit.htm?execution=<c:out value="${flowExecutionKey}"/>&_eventId_goDeep&parentid=<c:out value="${i.id}"/>">Sub-categories</a>
                        </c:if>
                    </li>
                </c:forEach>
            </ul>

            <input style="width:125" type="submit" class="button btn btn-primary" name="_eventId_ok" value="Ok"/>
            <input style="width:125" type="submit" class="button btn btn-default" name="_eventId_back" value="Cancel"/>

        </form:form>
    </div>
</body></html>
