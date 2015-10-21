<%@page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
	<%@include file="../jsp/header.jsp.inc" %>
	<title>Smart Assignments Repository | Add New Template | Step 3: Select Metadata to Import</title> 
</head>
<body>
	<form:form modelAttribute="template">   
		<table>
			<tr class="header"><td colspan=3>
				<h2>Step 5: Save Data into the Repository</h2>
			</td></tr>
			<tr>
				<td><b>Template name:</b></td>
				<td><c:out value="${template.name}"/></td>
			</tr>
			<tr>
				<td><b>Keywords:</b></td>
				<td><c:out value="${template.keywords}"/></td>
			</tr>
			<tr>
				<td><b>Created:</b></td>
				<td><c:out value="${template.dtcreatedStr}"/></td>
			</tr>
			<tr>
				<td><b>Description:</b></td>
				<td><c:out value="${template.description}"/></td>
			</tr>
			
			<tr class="header"><td colspan=3>
				<h3>Category</h3> 
			</td></tr>
			<tr>
				<td><b>Template category:</b></td>
				<td colspan=2><c:out value="${template.classifications[0].fullName}"/></td>
			</tr>

			<tr class="header"><td colspan=3>
				<h3>Author</h3> 
			</td></tr>
			<tr>
				<td><b>Author's name:</b></td>
				<td><c:out value="${template.author.name}"/></td>
			</tr>
			<tr>
				<td><b>Description:</b></td>
				<td><c:out value="${template.author.description}"/></td>
			</tr>

			<c:if test="${!empty template.modules}">
				<tr class="header"><td colspan=3>
					<h3>Modules</h3> 
				</td></tr>
			    <c:set var="i" value="0"/>
				<c:forEach items="${template.modules}" var="m">
			    	<c:if test="${(i % 2) == 1}">
			    		<tr class="row-dark">
			    	</c:if>
			    	<c:if test="${(i % 2) == 0}">
			    		<tr class="row-light">
			    	</c:if>
						<td><b>Module's name:</b></td>
						<td><c:out value="${m.name}"/></td>
					</tr>
			    	<c:if test="${(i % 2) == 1}">
			    		<tr class="row-dark">
			    	</c:if>
			    	<c:if test="${(i % 2) == 0}">
			    		<tr class="row-light">
			    	</c:if>
						<td><b>Package:</b></td>
						<td><c:out value="${m.modulePackage}"/></td>
					</tr>
			    	<c:if test="${(i % 2) == 1}">
			    		<tr class="row-dark">
			    	</c:if>
			    	<c:if test="${(i % 2) == 0}">
			    		<tr class="row-light">
			    	</c:if>
					    <td><b>Parameters:</b></td>
						<td><c:out value="${m.parameters}"/></td>
					</tr>
			    	<c:if test="${(i % 2) == 1}">
			    		<tr class="row-dark">
			    	</c:if>
			    	<c:if test="${(i % 2) == 0}">
			    		<tr class="row-light">
			    	</c:if>
					    <td><b>Description:</b></td>
						<td><c:out value="${m.description}"/></td>
					</tr>
				    <c:set var="i" value="${i+1}"/>
				</c:forEach>
			</c:if>

			<c:if test="${!empty template.files}">
				<tr class="header"><td colspan=3>
					<h3>Files</h3> 
				</td></tr>
			    <c:set var="i" value="0"/>
				<c:forEach items="${template.files}" var="m">
			    	<c:if test="${(i % 2) == 1}">
			    		<tr class="row-dark">
			    	</c:if>
			    	<c:if test="${(i % 2) == 0}">
			    		<tr class="row-light">
			    	</c:if>
						<td><b>File name:</b></td>
						<td><c:out value="${m.name}"/></td>
					</tr>
			    	<c:if test="${(i % 2) == 1}">
			    		<tr class="row-dark">
			    	</c:if>
			    	<c:if test="${(i % 2) == 0}">
			    		<tr class="row-light">
			    	</c:if>
					    <td><b>Description:</b></td>
						<td><c:out value="${m.description}"/></td>
					</tr>
				    <c:set var="i" value="${i+1}"/>
				</c:forEach>
			</c:if>

			<c:if test="${!empty template.updates}">
				<tr class="header"><td colspan=3>
					<h3>Updates</h3> 
				</td></tr>
			    <c:set var="i" value="0"/>
				<c:forEach items="${template.updates}" var="m">
				    	<c:if test="${(i % 2) == 1}">
			    		<tr class="row-dark">
			    	</c:if>
			    	<c:if test="${(i % 2) == 0}">
			    		<tr class="row-light">
			    	</c:if>
						<td><b>Date:</b></td>
						<td><fmt:formatDate pattern="yyyy-MM-dd" value="${m.updateDate}"/></td>
					</tr> 
			    	<c:if test="${(i % 2) == 1}">
			    		<tr class="row-dark">
			    	</c:if> 
			    	<c:if test="${(i % 2) == 0}">
			    		<tr class="row-light">
			    	</c:if>
						<td><b>Author:</b></td>
						<td><c:out value="${m.author.name}"/></td>
					</tr>
				    	<c:if test="${(i % 2) == 1}">
			    		<tr class="row-dark">
			    	</c:if>
			    	<c:if test="${(i % 2) == 0}">
			    		<tr class="row-light">
			    	</c:if>
					    <td><b>Comment:</b></td>
						<td><c:out value="${m.comment}"/></td>
					</tr> 

				</c:forEach>
			</c:if>

			<c:if test="${!empty template.updAuthorsList}">
				<tr class="header"><td colspan=3>
					<h3>Update Authors</h3> 
				</td></tr>
			    <c:set var="i" value="0"/>
				<c:forEach items="${template.updAuthorsList}" var="m">
			    	<c:if test="${(i % 2) == 1}">
			    		<tr class="row-dark">
			    	</c:if>
			    	<c:if test="${(i % 2) == 0}">
			    		<tr class="row-light">
			    	</c:if>
						<td><b>Author's name:</b></td>
						<td><c:out value="${m.name}"/></td>
					</tr>
			    	<c:if test="${(i % 2) == 1}">
			    		<tr class="row-dark">
			    	</c:if>
			    	<c:if test="${(i % 2) == 0}">
			    		<tr class="row-light">
			    	</c:if>
					    <td><b>Description:</b></td>
					    <td><c:out value="${m.description}"/></td>
					</tr>
				    <c:set var="i" value="${i+1}"/>
				</c:forEach>
			</c:if>

			<tr class="header">
				<td colspan=2><h3>Examples</h3> </td>
			</tr>
			<tr>
				<td><b>Questions:</b></td>
				<td><c:out value="${template.questions}"/></td>
			</tr>
			<tr>
				<td><b>Solutions:</b></td>
				<td><c:out value="${template.solutions}"/></td>
			</tr>
			<tr>
				<td><b>Short answers:</b></td>
				<td><c:out value="${template.shortanswers}"/></td>
			</tr>

			<tr><td>
			</td></tr>
			<tr><td colspan=3>
				<i>* Required fields</i>
			</td></tr>
		</table>
		<br>

		<input style="width:125" type="submit" class="button" name="_eventId_ok" value="Save"/>
		<input style="width:125" type="submit" class="button" name="_eventId_edit" value="Save&Edit"/>
		<input style="width:125" type="button" class="button" value="Back" onclick="history.go(-1);"/>
		<input style="width:125" type="submit" class="button" name="_eventId_cancel" value="Cancel"/>
	</form:form>
</body></html>