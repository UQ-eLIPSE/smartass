<%@page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
	<%@include file="header.jsp.inc" %>
	<title>Smart Assignments Repository | Template Details</title> 
</head>
<body>
	<table>
		<tr class="header"><td colspan=2>
			<h2>Template Details</h2>
		</td>
		<td align="center"><a href="help.htm?context=template" target="_blank">[help]</a></td>
		</tr>
		<tr>
			<td><b>Template name:</b></td>
			<td><c:out value="${template.name}" /></td>
		</tr>
		<tr>
			<td><b>Keywords:</b></td>
			<td><c:out value="${template.keywords}"/></td>
		</tr>
		<tr>
			<td><b>Created:</b></td>
			<td><c:out value="${template.dtcreated}"/></td>
		</tr>
		<tr>
			<td><b>Uploaded:</b></td>
			<td><c:out value="${template.dtuploaded}"/></td>
		</tr>
		<tr>
			<td><b>Description:</b></td>
			<td><c:out value="${template.description}"/></td>
		</tr>
		
		<tr><td colspan=2>
		     <c:url var="url" value="/download.htm" >
		  		<c:param name="scope" value="0" />
		  		<c:param name="id" value="${template.id}" />
			  </c:url>
		      <a href="<c:out value="${url}"/>">[download template]</a>
		      <c:url var="url" value="/view.htm" >
		  		<c:param name="id" value="${template.id}" />
			  </c:url>
		      <a href="<c:out value="${url}"/>">[view template code]</a>
		      <br><br>
		</td></tr>

		<tr class="header">
			<td colspan=2><h3>Categories</h3> </td>
			<td></td>
		</tr>
		<c:forEach items="${template.classifications}" var="m">
			<tr>
				<td colspan=2><c:out value="${m.fullName}"/></td>
				<td></td>
			</tr>
		</c:forEach>

		<c:if test="${!empty template.author}">
			<tr class="header">
				<td colspan=2><h3>Author</h3></td>
				<td></td>
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
				<td></td>
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
					<td></td>
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
				<td></td>
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
					<td>
						<c:url var="url" value="/download.htm" >
			  				<c:param name="scope" value="2" />
			  				<c:param name="id" value="${m.id}" />
						</c:url>
		      			<a href="<c:out value="${url}"/>"> [download] </a>
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
					<td></td>
				</tr>
			    <c:set var="i" value="${i+1}"/>
			</c:forEach>

			<tr class="header"><td colspan=2>
				<h3>Updates</h3> 
				<td></td></tr>
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
					<td></td>
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
				<td></td>
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
				</c:if></td>
			</tr>

	</table>
	<br>

	<input style="width:125" type="button" class="button" name="back" value="Back" onclick="history.go(-1)" >
</body>
<head>
	<%@include file="header.jsp.inc" %>
</head>
</html>