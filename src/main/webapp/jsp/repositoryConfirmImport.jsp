<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
	<title>Smart Assignments | Repository | Import Errors</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/smartass.css" />
</head>
<body>
	<h2>Smart Assignments | Repository | Import Errors</h2>
	<form:form commandName="item">
	   	<input type="submit" name="selected" value="Import selected">
   		<input type="submit" name="all" value="Import all">
   		<input type="submit" name="cancel" value="Cancel">

		<c:set var="rownumber" value="0"/>
	    <c:set var="errors" value="${item.errors}"/>
		<table width="90%">
			<c:forEach items="${errors}" var="e">
				<c:if test="${e.errorLevel==3}">
			    	<c:if test="${(rownumber % 2) == 1}">
			    		<tr class="row-dark">
			    	</c:if>
			    	<c:if test="${(rownumber % 2) == 0}">
			    		<tr class="row-light">
			    	</c:if>
			    	<td><table>
						<tr class="error"><td>
							<b><c:out value="${e.templateName}"/></b>
							There are file(s) that present in the template's metadata but does not exists in the package:
						</td></tr>
						<c:forEach items="${e.files}" var="f">
							<tr class="error"><td>
								<c:out value="${f.name}"/>
							</td></tr>
						</c:forEach>
						<c:if test="${e.noQuestions}">
							<tr><td>
								Template has not a questions example.
							</td></tr>
						</c:if>
						<c:if test="${e.noSolutions}">
							<tr><td>
								Template has not a solutions example.
							</td></tr>
						</c:if>
						<c:if test="${e.noShortanswers}">
							<tr"><td>
								Template has not a short answers example.
							</td></tr>
						</c:if>
						<c:if test="${e.templateExists}">
							<tr class="error"><td>
								Template already exists in the repository.
							</td></tr>
						</c:if>
					</table></td></tr>
				    <c:set var="rownumber" value="${rownumber+1}"/>
				</c:if>
			</c:forEach>
			<c:forEach items="${errors}" var="e">
				<c:if test="${e.errorLevel==2}">
			    	<c:if test="${(rownumber % 2) == 1}">
			    		<tr class="row-dark">
			    	</c:if>
			    	<c:if test="${(rownumber % 2) == 0}">
			    		<tr class="row-light">
			    	</c:if>
			    	<td><table>
						<tr><td>
							<b><c:out value="${e.templateName}"/></b>
						</td></tr>
						<c:if test="${e.noQuestions}">
							<tr><td>
								Template has not a questions example.
							</td></tr>
						</c:if>
						<c:if test="${e.noSolutions}">
							<tr><td>
								Template has not a solutions example.
							</td></tr>
						</c:if>
						<c:if test="${e.noShortanswers}">
							<tr"><td>
								Template has not a short answers example.
							</td></tr>
						</c:if>
						<c:if test="${e.templateExists}">
							<tr class="error"><td>
								Template already exists in the repository.
							</td></tr>
						</c:if>
					</table></td></tr>
				    <c:set var="rownumber" value="${rownumber+1}"/>
				</c:if>
			</c:forEach>
			<c:forEach items="${errors}" var="e">
				<c:if test="${e.errorLevel==1}">
			    	<c:if test="${(rownumber % 2) == 1}">
			    		<tr class="row-dark">
			    	</c:if>
			    	<c:if test="${(rownumber % 2) == 0}">
			    		<tr class="row-light">
			    	</c:if>
			    	<td>
				    	<form:checkbox path="selectedIds" value="${e.templateName}" />
				    </td>
			    	<td><table>
						<tr><td>
							<b><c:out value="${e.templateName}"/></b>
						</td></tr>
						<c:if test="${e.templateExists}">
							<tr><td>
								Template already exists in the repository.
							</td></tr>
						</c:if>
					</table></td></tr>
				    <c:set var="rownumber" value="${rownumber+1}"/>
				</c:if>
			</c:forEach>
		</table>

    	<input type="submit" name="selected" value="Import selected">
    	<input type="submit" name="all" value="Import all">
    	<input type="submit" name="cancel" value="Cancel">
	</form:form>
</body>
<head>
	<%@include file="header.jsp.inc" %>
</head>
</html>
