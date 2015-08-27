<%@page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
	<%@include file="../jsp/header.jsp.inc" %>
	<title>Smart Assignments Repository | Edit Template | Add a Category to the Template</title> 
</head>
<body>
	<form:form method="POST" modelAttribute="item">
		<table>
			<tr class="header"><td colspan=2><h2>
				Add a Category to the Template 
			</h2></td></tr>
			<tr class="header"><td colspan=2><h3>Search in Database</h3></td></tr>
			<c:if test="${item.parentid!=0}">
				<tr>
	`				<td colspan=2>Parent category: <c:out value="${parent.name}"/></td>
			</tr>
			</c:if>
			<tr>
				<td colspan=2>Search a category:
			    	<form:input path="search" maxlength="128" id="search" title="Search a category"/>
					<input type=submit class="button" name="_eventId_search" value="Search">
				</td>
			</tr>
			<c:if test="${item.parentid!=0}">
				<tr><td colspan=2>
					<a href="repository-template-edit.htm?execution=<c:out value="${flowExecutionKey}"/>&_eventId_goDeep&parentid=0"> [top level] </a>
				</td></tr>
			</c:if>
		    <c:set var="rownumber" value="0"/>
		    <c:forEach items="${found}" var="i">
		    	<c:if test="${(rownumber % 2) == 1}">
		    		<tr class="row-dark">
		    	</c:if>
		    	<c:if test="${(rownumber % 2) == 0}">
		    		<tr class="row-light">
		    	</c:if>
		    		<td>
					    <form:radiobutton path="solution" value="${i.id}"/>
		    			<c:out value="${i.name}"/>
					</td>
		    		<td width="25%" align="right">
		    			<c:if test="${item.parentId==0}">
						    <a href="repository-template-edit.htm?execution=<c:out value="${flowExecutionKey}"/>&_eventId_goDeep&parentid=<c:out value="${i.id}"/>"> [sub-categories] </a>
						</c:if>
		    		</td>
		    	</tr>
		    <c:set var="rownumber" value="${rownumber+1}"/>
		    </c:forEach>

			<tr><td class="error">
				<form:errors path="*" />
			</td></tr>
		</table>		
   
		<input style="width:125" type="submit" class="button" name="_eventId_ok" value="Ok"/>
		<input style="width:125" type="submit" class="button" name="_eventId_back" value="Cancel"/>
	</form:form>
</body></html>