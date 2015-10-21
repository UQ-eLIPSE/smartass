<%@page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
	<%@include file="../jsp/header.jsp.inc" %>
	<title>Smart Assignments Repository | Edit Template</title> 
</head>
<body>
	<form:form modelAttribute="template">   
		<table>
			<tr class="header"><td colspan=3>
				<h2>Edit Template</h2>
			</td></tr>
			<tr>
				<td><b>* Template name:</b></td>
				<td><form:input path="name" size="100" /></td>
			</tr>
			<tr><td class="error" colspan="3">
				<form:errors path="*"/>
			</td></tr>
			<tr>
				<td><b>Keywords:</b></td>
				<td><form:input path="keywords" size="100"/></td>
			</tr>
			<tr>
				<td><b>Created:</b></td>
	 			<td><form:input path="dtcreated"/> (date format is yyyy-mm-dd)</td>  
			</tr>
			<tr>
				<td><b>Uploaded:</b></td>
				<td><c:out value="${template.dtuploaded}"/></td>
			</tr>
			<tr>
				<td><b>Description:</b></td>
				<td><form:textarea rows="5" cols="70" path="description"/></td>
			</tr>
			
			<tr class="header">
				<td colspan=2><h3>Categories</h3> </td>
				<td><a href="repository-template-edit.htm?execution=<c:out value="${flowExecutionKey}"/>&_eventId_addClass&parentid=0">[add]</a></td>
			</tr>
		    <c:set var="i" value="0"/>
			<c:forEach items="${template.classifications}" var="m">
				<tr>
					<td colspan=2><c:out value="${m.fullName}"/></td>
					<c:if test="${classCount>1}">
					<td><a href="repository-template-edit.htm?execution=<c:out value="${flowExecutionKey}"/>&_eventId_removeClass&i=<c:out value="${i}"/>">[remove]</a></td>
					</c:if>
				</tr>
			    <c:set var="i" value="${i+1}"/>
			</c:forEach>

			<c:if test="${!empty template.author}">
				<tr class="header">
					<td colspan=2><h3>* Author</h3></td>
					<td><a href="repository-template-edit.htm?execution=<c:out value="${flowExecutionKey}"/>&_eventId_selectAuthor">[select]</a></td>
				</tr>
				<tr>
					<td><b>Author's name:</b></td>
					<td><c:out value="${template.author.name}"/></td>
					<td>
					</td>
				</tr>
				<tr>
					<td><b>Description:</b></td>
					<td><c:out value="${template.author.description}"/></td>
					<td></td>
				</tr>
			</c:if>

			<tr class="header">
				<td colspan=2><h3>Modules</h3></td>
				<td><a href="repository-template-edit.htm?execution=<c:out value="${flowExecutionKey}"/>&_eventId_addModule">[add]</a>
			</tr>
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
					<td><a href="repository-template-edit.htm?execution=<c:out value="${flowExecutionKey}"/>&_eventId_removeModule&i=<c:out value="${i}"/>">[remove]</a></td>
				</tr>
		    	<c:if test="${(i % 2) == 1}">
		    		<tr class="row-dark">
		    	</c:if>
		    	<c:if test="${(i % 2) == 0}">
		    		<tr class="row-light">
		    	</c:if>
					<td><b>Package:</b></td>
					<td colspan=2><c:out value="${m.modulePackage}"/></td>
				</tr>
		    	<c:if test="${(i % 2) == 1}">
		    		<tr class="row-dark">
		    	</c:if>
		    	<c:if test="${(i % 2) == 0}">
		    		<tr class="row-light">
		    	</c:if>
				    <td><b>Parameters:</b></td>
					<td colspan=2><c:out value="${m.parameters}"/></td>
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

			<tr class="header">
				<td colspan=2><h3>Files</h3></td>
				<td><a href="repository-template-edit.htm?execution=<c:out value="${flowExecutionKey}"/>&_eventId_addFile">[add]</a></td>
			</tr>
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
					<td><a href="repository-template-edit.htm?execution=<c:out value="${flowExecutionKey}"/>&_eventId_removeFile&i=<c:out value="${i}"/>">[remove]</a></td>
				</tr>
		    	<c:if test="${(i % 2) == 1}">
		    		<tr class="row-dark">
		    	</c:if>
		    	<c:if test="${(i % 2) == 0}">
		    		<tr class="row-light">
		    	</c:if>
				    <td><b>Description:</b></td>
					<td><c:out value="${m.description}"/></td>
					<td></td>
				</tr>
			    <c:set var="i" value="${i+1}"/>
			</c:forEach>

			<tr class="header"><td colspan=2>
				<h3>Updates</h3> 
				<td><a href="repository-template-edit.htm?execution=<c:out value="${flowExecutionKey}"/>&_eventId_addUpdate">[add]</a>
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
					<td><c:out value="${m.updateDate}"/></td>
					<td><a href="repository-template-edit.htm?execution=<c:out value="${flowExecutionKey}"/>&_eventId_removeUpdate&i=<c:out value="${i}"/>">[remove]</a></td>
				</tr> 
		    	<c:if test="${(i % 2) == 1}">
		    		<tr class="row-dark">
		    	</c:if> 
		    	<c:if test="${(i % 2) == 0}">
		    		<tr class="row-light">
		    	</c:if>
					<td><b>Author:</b></td>
					<td><c:out value="${m.author.name}"/></td>
					<td></td>
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

			<tr class="header">
				<td colspan=2><h3>Examples</h3> </td>
				<td><a href="repository-template-edit.htm?execution=<c:out value="${flowExecutionKey}"/>&_eventId_uploadExamples">[upload]</a></td>
			</tr>
			<tr>
				<td><b>Questions:</b></td>
				<td><c:out value="${template.questions}"/></td>
				<td><c:if test="${!empty template.questions}">
			        <c:url var="url" value="/download.htm" >
			  		  <c:param name="scope" value="1" />
			  		  <c:param name="id" value="${template.id}" />
			  		  <c:param name="kind" value="0" />
				    </c:url>
			        <a href="<c:out value="${url}"/>">[download]</a><br>
			        <c:url var="url" value="/repository-template-edit.htm" >
			  		  <c:param name="execution" value="${flowExecutionKey}" />
			  		  <c:param name="_eventId_deleteExample" value="1" />
			  		  <c:param name="kind" value="0" />
				    </c:url>
			        <a href="<c:out value="${url}"/>">[delete]</a><br>
				</c:if></td>
			</tr>
			<tr>
				<td><b>Solutions:</b></td>
				<td><c:out value="${template.solutions}"/></td>
				<td><c:if test="${!empty template.solutions}">
			        <c:url var="url" value="/download.htm" >
			  		  <c:param name="scope" value="1" />
			  		  <c:param name="id" value="${template.id}" />
			  		  <c:param name="kind" value="1" />
				    </c:url>
			        <a href="<c:out value="${url}"/>">[download]</a><br>
			        <c:url var="url" value="/repository-template-edit.htm" >
			  		  <c:param name="execution" value="${flowExecutionKey}" />
			  		  <c:param name="_eventId_deleteExample" value="1" />
			  		  <c:param name="kind" value="1" />
				    </c:url>
			        <a href="<c:out value="${url}"/>">[delete]</a><br>
				</c:if></td>
			</tr>
			<tr>
				<td><b>Short answers:</b></td>
				<td><c:out value="${template.shortanswers}"/></td>
				<td><c:if test="${!empty template.shortanswers}">
			        <c:url var="url" value="/download.htm" >
			  		  <c:param name="scope" value="1" />
			  		  <c:param name="id" value="${template.id}" />
			  		  <c:param name="kind" value="2" />
				    </c:url>
			        <a href="<c:out value="${url}"/>">[download]</a><br>
			        <c:url var="url" value="/repository-template-edit.htm" >
			  		  <c:param name="execution" value="${flowExecutionKey}" />
			  		  <c:param name="_eventId_deleteExample" value="1" />
			  		  <c:param name="kind" value="2" />
				    </c:url>
			        <a href="<c:out value="${url}"/>">[delete]</a><br>
				</c:if></td>
			</tr>

			<tr><td>
			</td></tr>
			<tr><td colspan=3>
				<i>* Required fields</i>
			</td></tr>
		</table>
		<br>
		<input style="width:125" type="submit" class="button" name="_eventId_save" value="Save"/>
		<input style="width:125" type="submit" class="button" name="_eventId_cancel" value="Cancel"/>
	</form:form>
</body></html>