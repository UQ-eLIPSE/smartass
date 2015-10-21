<%@page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
	<%@include file="../jsp/header.jsp.inc" %>
	<title>Smart Assignments Repository |
		<c:if test="${mode=='new'}">
			Add New Template | Step 4: Resolve Missing Metadata | File
		</c:if>
		<c:if test="${mode=='edit'}">
			Edit template | Add File
		</c:if>
	</title>
    <script type="text/javascript">
    	function uploadFieldChange() {
        	var txt = document.getElementById("fileMultipart").value;
        	var pos = txt.lastIndexOf("/");
        	if(pos>=0) txt = txt.substr(pos+1);
        	pos = txt.lastIndexOf("\\");
        	if(pos>=0) txt = txt.substr(pos+1);
        	document.getElementById("file.name").value = txt;
    	}
    </script>
</head>
<body>
	<form:form modelAttribute="item"  enctype="multipart/form-data" method="POST">
		<table>
			<tr class="header"><td colspan=3><h2>
				<c:if test="${mode=='new'}">
					Step 4: Resolve Missing Metadata -
				</c:if> 
				<c:if test="${empty item.file.name}">
					Add Template File
				</c:if>
				<c:if test="${!empty item.file.name}">
					Resolve Missing File 
				</c:if>
				<c:if test="${mode=='new'}">
					- <c:out value="${template.filesResolver.importingItemNo}"/>/<c:out value="${template.filesResolver.importingItemCount}"/>
				</c:if>
			</h2>
			</td></tr>
			<tr><td> <b>Possible solutions:</b> </td></tr>
			<c:if test="${!empty item.file.name}">
				<tr class="header"><td>
					<h3><form:radiobutton path="solution" value="0"/> Add new file</h3>
				</td></tr>
				<tr><td>
					<table>
						<tr>
							<td><b>* Upload file:</b></td>
							<td><input size="50" type="file" name="fileMultipart" id="fileMultipart" onchange="uploadFieldChange()"/></td>
						</tr>
						<tr>
							<td><b>* Name:</b></td>
							<td><form:input size="50" path="file.name"/></td>
						</tr>
						<tr>
							<td><b>Description:</b></td>
							<td><form:input size="50" path="file.description"/></td>
						</tr>
					</table>
				</td></tr>
			</c:if>

			<c:if test="${(!empty item.file.name) && (!empty suggestions)}"> 
				<tr class="header"><td><h3>Select from suggested</h3></td></tr>
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
				</td></tr>
 			</c:if> 

				<tr class="header"><td><h3>Search in the database</h3></td></tr>
				<tr><td>
			      Search by file name: 
			      <form:input path="search" maxlength="128" id="search" title="Search by file name"/>
				  <input type=submit class="button" name="_eventId_search" value="Search">
				</td></tr>

				<c:if test="${!empty found}">
					<tr><td>
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

				<tr><td>
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
				</td></tr>
			<c:if test="${empty item.file.name}">
				<tr class="header"><td><h3><form:radiobutton path="solution" value="0"/>Add new file</h3></td></tr>
				<tr><td>
					<table>
						<tr>
							<td><b>* Upload file:</b></td>
							<td><input  size="50" type="file" id="fileMultipart" name="fileMultipart" onchange="uploadFieldChange()" /></td>
						</tr>
						<tr>
							<td><b>* Name:</b></td>
							<td><form:input  size="50" path="file.name"/></td>
						</tr>
						<tr>
							<td><b>Description:</b></td>
							<td><form:input  size="50" path="file.description"/></td>
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