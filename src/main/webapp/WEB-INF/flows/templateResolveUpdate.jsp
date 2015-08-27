<%@page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
	<%@include file="../jsp/header.jsp.inc" %>
	<title>Smart Assignments Repository | 
	<c:if test="${mode=='new'}">
		Add New Template | Step 4: Resolve Missing Metadata | Update
	</c:if>	
	<c:if test="${mode=='edit'}">
		Edit Template | Add Update
	</c:if>	
	</title>
</head>
<body>
	<form:form method="POST" modelAttribute="item">
		<table>
			<tr class="header"><td colspan=2><h2>
				<c:if test="${mode=='new'}">
					Step 4: Resolve Missing Metadata -
				</c:if>	
				Add Template Update
				<c:if test="${mode=='new'}">
					<c:out value="${template.updatesResolver.importingItemNo}"/>/<c:out value="${template.updatesResolver.importingItemCount}"/>
				</c:if>			
			</h2></td></tr>
			<tr><td> <b>Possible solutions:</b> </td></tr>
			<tr class="header"><td colspan="2">
				<h3>Add New Update</h3>
			</td></tr>
			<tr><td>
				<table>
					<tr>
						<td><b>* Update date:</b></td>
						<td><form:input path="update.updateDate" /> (date format is yyyy-mm-dd)</td>
					</tr>
					<tr>
						<c:if test="${mode=='new'}">
							<td><b>* Update author:</b></td>
						</c:if>
						<td><c:out value="${item.update.author.name}" /></td>
					</tr>
					<tr>
						<td><b>Comment:</b></td>
						<td><form:textarea rows="5" cols="70" path="update.comment"/></td>
					</tr>
				</table>
			</td></tr>

			<c:if test="${mode=='edit'}">
				<tr class="header"><td colspan="2"><h3>Search update author in the database</h3></td></tr>
				<tr><td>
			      Search by author name: 
			      <form:input path="search" maxlength="128" id="search" title="Search by author name"/>
				  <input type=submit class="button" name="_eventId_search" value="Search">
				</td></tr>

				<c:if test="${!empty found}">
					<tr><td colspan="2">
						<c:if test="${mode=='new'}">
							<input style="width:125" type="submit" class="button" name="_eventId_ok" value="Next"/>
							<input style="width:125" type="button" class="button" value="Back" onclick="history.go(-1);"/>
						</c:if>
				   		<c:if test="${mode=='edit'}">
							<input style="width:125" type="submit" class="button" name="_eventId_ok" value="Ok"/>
						</c:if>
						<input style="width:125" type="submit" class="button" name="_eventId_cancel" value="Cancel"/>
					</td></tr>
				</c:if>

				<%@include file="pages.inc.jsp" %>

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

				<%@include file="pages.inc.jsp" %>

				<tr class="header"><td colspan="2"><h3><form:radiobutton path="solution" value="0"/>Add new author</h3></td></tr>
				<tr><td>
					<table>
						<tr>
							<td><b>* Name:</b></td>
							<td><form:input path="update.author.name"/></td>
						</tr>
						<tr>
							<td><b>Description:</b></td>
							<td><form:input path="update.author.description"/></td>
						</tr>
					</table>
				</td></tr>
			</c:if>

			<tr><td class="error">
				<form:errors path="*" />
			</td></tr>
		</table>		
   
		<c:if test="${mode=='new'}">
			<input style="width:125" type="submit" class="button" name="_eventId_ok" value="Next"/>
			<input style="width:125" type="button" class="button" value="Back" onclick="history.go(-1);"/>
		</c:if>
		<c:if test="${mode=='edit'}">
			<input style="width:125" type="submit" class="button" name="_eventId_ok" value="Ok"/>
		</c:if>
		<input style="width:125" type="submit" class="button" name="_eventId_cancel" value="Cancel"/>
	</form:form>
</body></html>