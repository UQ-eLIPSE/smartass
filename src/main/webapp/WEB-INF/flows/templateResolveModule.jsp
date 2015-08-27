<%@page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
	<%@include file="../jsp/header.jsp.inc" %>
	<title>Smart Assignments Repository | 
	<c:if test="${mode=='new'}">
		Add New Template | Step 4: Resolve Missing Metadata | Module
	</c:if>
	<c:if test="${mode=='edit'}">
		Edit Template | Add Module
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
				<c:if test="${empty item.module.name}">
					Add Template Module
				</c:if>
				<c:if test="${!empty item.module.name}">
					Resolve Missing Module
				</c:if>
				<c:if test="${mode=='new'}">
					- <c:out value="${template.modulesResolver.importingItemNo}"/>/<c:out value="${template.modulesResolver.importingItemCount}"/>
				</c:if>
			</h2></td></tr>
			<tr><td> <b>Possible solutions:</b> </td></tr>
			<c:if test="${!empty item.module.name}">
				<tr class="header"><td colspan="2">
					<h3><form:radiobutton path="solution" value="0"/> Add New Module</h3>
				</td></tr>
				<tr><td colspan="2">
					<table>
						<tr>
							<td><b>* Module name:</b></td>
							<td><form:input path="module.name" size="50"/></td>
						</tr>
						<tr>
							<td><b>Module package:</b></td>
							<td><form:input path="module.modulePackage"  size="50"/></td>
						</tr>
						<tr>
							<td><b>Module parameters:</b></td>
							<td><form:textarea  rows="6" cols="56" path="module.parameters" /></td>
						</tr>
						<tr>
							<td><b>Description:</b></td>
							<td><form:textarea path="module.description" cols="56" rows="4"/></td>
						</tr>
					</table>
				</td></tr>
			</c:if>
			<c:if test="${(!empty item.module.name) && (!empty suggestions)}"> 
				<tr class="header"><td colspan="2"><h3>Select from suggested</h3></td></tr>
				    <c:forEach items="${suggestions}" var="i">
				    	<c:if test="${(rownumber % 2) == 1}">
				    		<tr class="row-dark">
				    	</c:if>
				    	<c:if test="${(rownumber % 2) == 0}">
				    		<tr class="row-light">
				    	</c:if>
				    		<td width="30%">
							    <form:radiobutton path="solution" value="${i.id}"/>
						    	<c:if test="${!empty i.modulePackage}">
						    		<c:out value="${i.modulePackage}"/>.
					    		</c:if>
							    <c:out value="${i.name}"/> </a>
							</td>
				    		<td><c:out value="${i.description}"/></td>
				    	</tr>
				    <c:set var="rownumber" value="${rownumber+1}"/>
				    </c:forEach>
 			</c:if> 
				<tr class="header"><td colspan="2"><h3>Search in Database</h3></td></tr>
				<tr><td colspan="2">
			      Search by  name: 
			      <form:input path="search" maxlength="128" id="search" title="Search by file name" size="40"/>
				  <input type="submit" class="button" name="_eventId_search" value="Search">
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
						    	<c:if test="${!empty i.modulePackage}">
						    		<c:out value="${i.modulePackage}"/>.
					    		</c:if>
							    <c:out value="${i.name}"/> </a>
							</td>
				    		<td><c:out value="${i.description}"/></td>
				    	</tr>
				    <c:set var="rownumber" value="${rownumber+1}"/>
				    </c:forEach>

				<%@include file="pages.inc.jsp" %>

			<c:if test="${empty item.module.name}">
				<tr class="header"><td colspan="2"><h3><form:radiobutton path="solution" value="0"/>Add New Module</h3></td></tr>
				<tr><td>
					<table>
						<tr>
							<td><b>* Module name:</b></td>
							<td><form:input path="module.name" size="50" /></td>
						</tr>
						<tr>
							<td><b>Module package:</b></td>
							<td><form:input path="module.modulePackage" size="50" /></td>
						</tr>
						<tr>
							<td><b>Module parameters:</b></td>
							<td><form:textarea  rows="6" cols="70" path="module.parameters" /></td>
						</tr>
						<tr>
							<td><b>Description:</b></td>
							<td><form:textarea path="module.description" cols="56" rows="4"/></td>
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