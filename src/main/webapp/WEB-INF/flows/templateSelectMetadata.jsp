<%@page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
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
				<h2>Step 3: Select Metadata to Import</h2>
			</td></tr>
			<tr>
				<td><b>* Template's name:</b></td>
				<td><form:input path="name" size="50"/></td>
			</tr>
			<tr>
				<td><b>Keywords:</b></td>
				<td><form:input path="keywords" size="50"/></td>
			</tr>
			<tr>
 				<td><b>Created:</b></td>
				<td><form:input path="dtcreatedStr" size="50" /> (date format is yyyy-mm-dd)</td>
			</tr>
			<tr>
				<td><b>Description:</b></td>
				<td><form:textarea rows="5" cols="50" path="description"/></td>
			</tr> 
			
			<tr class="header"><td colspan=3>
				<h3>Category</h3> 
			</td></tr>
			<tr>
				<td><b>Template category:</b></td>
				<td colspan=2><c:out value="${template.classifications[0].fullName}"/></td>
			</tr>

			<c:if test="${!empty template.author}">
				<tr class="header"><td colspan=3>
					<h3>* Author</h3> 
				</td></tr>
				<tr>
					<td><b>Author's name:</b></td>
					<td><c:out value="${template.author.name}"/></td>
					<td>
						<c:if test="${template.author.id!=0}">FOUND IN THE DATABASE</c:if>
						<c:if test="${template.author.id==0}">NOT IN THE DATABASE</c:if>
					</td>
				</tr>
				<tr>
					<td><b>Description:</b></td>
					<td><c:out value="${template.author.description}"/></td>
					<td><form:checkbox path="importAuthor"/> import</td>
				</tr>
			</c:if>

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
						<td>
							<c:if test="${m.id!=0}">FOUND IN THE DATABASE</c:if>
							<c:if test="${m.id==0}">NOT IN THE DATABASE</c:if>
						</td>
					</tr>
			    	<c:if test="${(i % 2) == 1}">
			    		<tr class="row-dark">
			    	</c:if>
			    	<c:if test="${(i % 2) == 0}">
			    		<tr class="row-light">
			    	</c:if>
						<td><b>Package:</b></td>
						<td><c:out value="${m.modulePackage}"/></td>
						<td><form:checkbox path="modulesResolver.imports[${i}]"/> import</td>
					</tr>
			    	<c:if test="${(i % 2) == 1}">
			    		<tr class="row-dark">
			    	</c:if>
			    	<c:if test="${(i % 2) == 0}">
			    		<tr class="row-light">
			    	</c:if>
					    <td><b>Parameters:</b></td>
						<td colspan="2"><c:out value="${m.parameters}"/></td>
					</tr>
			    	<c:if test="${(i % 2) == 1}">
			    		<tr class="row-dark">
			    	</c:if>
			    	<c:if test="${(i % 2) == 0}">
			    		<tr class="row-light">
			    	</c:if>
					    <td><b>Description:</b></td>
						<td colspan=2><c:out value="${m.description}"/></td>
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
						<td>
							<c:if test="${m.id!=0}">FOUND IN THE DATABASE</c:if>
							<c:if test="${m.id==0}">NOT IN THE DATABASE</c:if>
						</td>
					</tr>
			    	<c:if test="${(i % 2) == 1}">
			    		<tr class="row-dark">
			    	</c:if>
			    	<c:if test="${(i % 2) == 0}">
			    		<tr class="row-light">
			    	</c:if>
					    <td><b>Description:</b></td>
						<td><c:out value="${m.description}"/></td>
						<td><form:checkbox path="filesResolver.imports[${i}]"/> import</td>
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
						<td colspan="2"><fmt:formatDate pattern="yyyy-MM-dd" value="${m.updateDate}"/></td>
					</tr> 
			    	<c:if test="${(i % 2) == 1}">
			    		<tr class="row-dark">
			    	</c:if> 
			    	<c:if test="${(i % 2) == 0}">
			    		<tr class="row-light">
			    	</c:if>
						<td><b>Author:</b></td>
						<td><c:out value="${m.author.name}"/></td>
						<td><form:checkbox path="updatesResolver.imports[${i}]"/> import</td>
					</tr>
				    	<c:if test="${(i % 2) == 1}">
			    		<tr class="row-dark">
			    	</c:if>
			    	<c:if test="${(i % 2) == 0}">
			    		<tr class="row-light">
			    	</c:if>
					    <td><b>Comment:</b></td>
						<td colspan="2"><c:out value="${m.comment}"/></td>
					</tr> 
				    <c:set var="i" value="${i+1}"/>

				</c:forEach>
			</c:if>

			<c:if test="${!empty template.updAuthorsList}">
				<tr class="header"><td colspan=3>
					<h3>Update's authors</h3> 
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
						<td>
							<c:if test="${m.id!=0}">FOUND IN THE DATABASE</c:if>
							<c:if test="${m.id==0}">NOT IN THE DATABASE</c:if>
						</td>
					</tr>
			    	<c:if test="${(i % 2) == 1}">
			    		<tr class="row-dark">
			    	</c:if>
			    	<c:if test="${(i % 2) == 0}">
			    		<tr class="row-light">
			    	</c:if>
					    <td><b>Description:</b></td>
					    <td><c:out value="${m.description}"/></td>
						<td><form:checkbox path="updAuthorsResolver.imports[${i}]"/> import</td>
					</tr>
				    <c:set var="i" value="${i+1}"/>
				</c:forEach>
			</c:if>
			<tr><td>
			</td></tr>
			<tr><td colspan=3>
				<i>* Required fields</i>
			</td></tr>

			<tr><td class="error" colspan="3">
				<form:errors path="*" />
			</td></tr>
		</table>
		<br>

		<input style="width:125" type="submit" class="button" name="_eventId_ok" value="Next"/>
		<input style="width:125" type="button" class="button" value="Back" onclick="history.go(-1);"/>
		<input style="width:125" type="submit" class="button" name="_eventId_cancel" value="Cancel"/>
	</form:form>
</body></html>