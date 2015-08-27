<%@page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
	<%@include file="../jsp/header.jsp.inc" %>
	<title>Smart Assignments Repository | Add new template | Step 4: Resolve Missing Metadata | Update Author</title> 
</head>
<body>
	<form:form modelAttribute="item">
		<table>
			<tr class="header"><td colspan=2><h2>
				Stage 4: Resolve Missing Metadata - 
				<c:if test="${empty item.author.name}">
					Add Update Author
				</c:if>
				<c:if test="${!empty item.author.name}">
					Resolve Missing Update Author 
				</c:if>
					- <c:out value="${template.updAuthorsResolver.importingItemNo}"/>/<c:out value="${template.updAuthorsResolver.importingItemCount}"/>
				</h2>
			</td></tr>
			<tr><td> <b>Possible solutions:</b> </td></tr>
			<c:if test="${!empty item.author.name}">
				<tr class="header"><td colspan=2>
					<h3><form:radiobutton path="solution" value="0"/> Add new author</h3>
				</td></tr>
				<tr><td>
					<table>
						<tr>
							<td><b>* Name:</b></td>
							<td><form:input path="author.name"/></td>
						</tr>
						<tr>
							<td><b>Description:</b></td>
							<td><form:input path="author.description"/></td>
						</tr>
					</table>
				</td></tr>
			</c:if>
			<c:if test="${(!empty item.author.name) && (!empty suggestions)}"> 
				<tr class="header"><td colspan=2><h3>Select from suggested</h3></td></tr>
				    <c:forEach items="${suggestions}" var="i">
				    	<c:if test="${(rownumber % 2) == 1}">
				    		<tr class="row-dark">
				    	</c:if>
				    	<c:if test="${(rownumber % 2) == 0}">
				    		<tr class="row-light">
				    	</c:if>
				    		<td width="30%">
							    <form:radiobutton path="solution" value="${i.id}"/>
							    <c:out value="${i.name}"/> </a>
							</td>
				    		<td><c:out value="${i.description}"/></td>
				    	</tr>
				    <c:set var="rownumber" value="${rownumber+1}"/>
				    </c:forEach>
 			</c:if> 
				<tr class="header"><td colspan=2>Search in database</td></tr>
				<tr><td>
			      <h3>Search by author name:</h3> 
			      <form:input path="search" maxlength="128" id="search" title="Search by author name"/>
				  <input type=submit class="button" name="_eventId_search" value="Search">
				</td></tr>
				<tr><td>
					<input style="width:125" type="submit" class="button" name="_eventId_ok" value="Next"/>
					<input style="width:125" type="button" class="button" value="Back" onclick="history.go(-1);"/>
					<input style="width:125" type="submit" class="button" name="_eventId_cancel" value="Cancel"/>
				</td></tr>

			    <c:forEach items="${found}" var="i">
			    	<c:if test="${(rownumber % 2) == 1}">
			    		<tr class="row-dark">
			    	</c:if>
			    	<c:if test="${(rownumber % 2) == 0}">
			    		<tr class="row-light">
			    	</c:if>
			    		<td width="30%">
						    <form:radiobutton path="solution" value="${i.id}"/>
						    <c:out value="${i.name}"/> </a>
						</td>
			    		<td><c:out value="${i.description}"/></td>
			    	</tr>
			    <c:set var="rownumber" value="${rownumber+1}"/>
			    </c:forEach>
			<c:if test="${empty item.author.name}">
				<tr class="header"><td><form:radiobutton path="solution" value="0"/><h3>Add new author</h3></td></tr>
				<tr><td>
					<table>
						<tr>
							<td><b>* Name:</b></td>
							<td><form:input path="author.name"/></td>
						</tr>
						<tr>
							<td><b>Description:</b></td>
							<td><form:input path="author.description"/></td>
						</tr>
					</table>
				</td></tr>
			</c:if>

			<tr><td class="error">
				<form:errors path="*" />
			</td></tr>
		</table>		
   
		<input style="width:125" type="submit" class="button" name="_eventId_ok" value="Next"/>
		<input style="width:125" type="button" class="button" value="Back" onclick="history.go(-1);"/>
		<input style="width:125" type="submit" class="button" name="_eventId_cancel" value="Cancel"/>
	</form:form>
</body></html>