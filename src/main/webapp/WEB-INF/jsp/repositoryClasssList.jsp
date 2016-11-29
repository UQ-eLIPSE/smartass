<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
	<%@include file="header.jsp.inc" %>
  	<title>Smart Assignments - Repository - Categories</title>
  </head>
  <body>
  <div class="container">
    <h2>Smart Assignments Repository - Categories</h2>
    <table width="100%"><tr class="menu"><td colspan=2>
		<%@include file="top.inc.jsp" %>
    </td></tr><tr>
    <td valign="top" width="150" class="menu">
	    <table>
	    	<c:if test="${!empty user}">
		    	<tr><td><a href="repository-authors.htm">[authors]</a></td></tr>
		    	<tr class="selected-menu"><td>[categories]</td></tr>
		    	<tr><td>
					<a href="repository-modules.htm">[modules]</a>
				</td></tr>
		    	<tr><td>
					<a href="repository-files.htm">[files]</a>
				</td></tr>
		    	<tr><td><hr></td></tr>
			</c:if>
	    	<tr><td><a href="repository.htm">[templates]</a></td></tr>
	    	<c:if test="${!empty user && user.adminRight}">
		    	<tr><td><hr></td></tr>
		    	<tr><td>
					<a href="repository-export.htm?all=1">[export]</a>
				</td></tr>
		    	<tr><td>
					<a href="repository-import.htm">[import]</a>
				</td></tr>
		    	<tr><td>
					<a href="backups.htm">[backups]</a>
				</td></tr>
			</c:if>
	    </table>
    </td>
    <td valign="top">
		<c:if test="${(!empty user.name) && user.editRepositoryRight}">
			<c:if test="${!empty parent}">
				<b>Parent:</b>
				<c:out value="${parent.name}" />
				<br><br>
			</c:if>
	      <div class="error">
	      	<c:out value="${errors}"/>
	      </div>
		    <form action="repository-classs.htm">
		      Search by category name: <br>
		      <input maxlength="128" id="search" name="search" size=55 title="Search by category name" value="<c:out value="${lastsearch}"/>"> <br>
		      <input type="submit" value="Search">
		      <input type="hidden" name="parentid" value="<c:out value="${parentid}"/>">
		    </form>
	
		    <table width="95%" cellspacing="0">
		    <tr class="header">
		    	<th>Name</th>
		    	<th align="right">
					<c:if test="${!empty parent}">
						<a href="repository-classs.htm?parentid=0">[top level]</a>
					</c:if>
				    <c:url var="url" value="/repository-class.htm" >
				  		<c:param name="parentid" value="${parent.id}" />
					</c:url>
				    <a href="<c:out value="${url}"/>"> [new] </a>
		    	</th>
		    </tr>
		    <c:set var="rownumber" value="0"/>
		    <c:forEach items="${items}" var="i">
		    	<c:if test="${(rownumber % 2) == 1}">
		    		<tr class="row-dark">
		    	</c:if>
		    	<c:if test="${(rownumber % 2) == 0}">
		    		<tr class="row-light">
		    	</c:if>
		    		<td>
					    <c:url var="url" value="/repository-class.htm" >
					  		<c:param name="parentid" value="${i.parentModel.id}" />
						</c:url>
					    <a href="<c:out value="${url}"/>&id=<c:out value="${i.id}"/>"> <c:out value="${i.name}"/> </a>
					</td>
		    		<td width="25%" align="right">
		    			<c:if test="${parentid==0}">
						    <c:url var="url" value="/repository-classs.htm" >
						  		<c:param name="parentid" value="${i.id}" />
							</c:url>
						    <a href="<c:out value="${url}"/>">[sub-categories]</a>
						</c:if>
					    <c:url var="url" value="/repository-class-delete-confirm.htm" >
					  		<c:param name="parentid" value="${parentid}" />
						</c:url>
					    <a href="<c:out value="${url}"/>&id=<c:out value="${i.id}"/>">[delete]</a>
		    		</td>
		    	</tr>
		    <c:set var="rownumber" value="${rownumber+1}"/>
		    </c:forEach>
		    </table>
	    </c:if>
		<c:if test="${(empty user.name) || !user.editRepositoryRight}">
			<b>To edit categories you need to log in as a user with appropriate rights!</b><br><br>
			<a href="login.htm?url=repository-classs.htm">[log in]</a> <a href="index.htm">[return to homepage]</a>
	    </c:if>
    </td>
    </tr></table>
    </div>
	<%@include file="footer.jsp.inc" %>
</body>
</html>
