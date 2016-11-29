<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<%@include file="header.jsp.inc" %>
  	<title>Smart Assignments | Repository | Authors</title>
</head>
<body>
    <div class="container">
    <h2>Smart Assignments Repository - Authors</h2>
    <table width="100%"><tr class="menu"><td colspan=2>
		<%@include file="top.inc.jsp" %>
    </td></tr><tr>
    <td valign="top" width="150" class="menu">
	    <table>
	    	<c:if test="${!empty user}">
		    	<tr class="selected-menu"><td>[authors]</td></tr>
		    	<tr><td>
					<a href="repository-classs.htm?parentid=0">[categories]</a>
				</td></tr>
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
		      <div class="error">
		      	<c:out value="${errors}"/>
		      </div>
		    <form action="repository-authors.htm">
		      Search by author name: <br>
		      <input maxlength=128 id="search" name="search" size=55 title="Search by author name" value="<c:out value="${lastsearch}"/>"> <br>
		      <input type=submit value="Search">
		    </form>
	
		    <table width="95%" cellspacing="0">
		    
			<c:set var="page_url" value="repository-authors.htm?search=${search}&"/>
			<%@include file="pages.inc.jsp" %>

			<tr class="header">
		    	<th>Name</th>
		    	<th>Description</th>
		    	<th align="right">
				    <a href="repository-author.htm?id=0"> [new] </a>
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
		    		<td width="30%">
					    <c:url var="url" value="/repository-author.htm" >
					  		<c:param name="id" value="${i.id}" />
						</c:url>
					    <a href="<c:out value="${url}"/>"> <c:out value="${i.name}"/> </a>
					</td>
		    		<td><c:out value="${i.description}"/></td>
		    		<td width="10%" align="right">
					    <c:url var="url" value="/repository-author-delete-confirm.htm" >
					  		<c:param name="id" value="${i.id}" />
						</c:url>
					    <a href="<c:out value="${url}"/>">[delete]</a>
		    		</td>
		    	</tr>
		    <c:set var="rownumber" value="${rownumber+1}"/>
		    </c:forEach>

			<c:set var="page_url" value="repository-authors.htm?search=${search}&"/>
			<%@include file="pages.inc.jsp" %>

		    </table>
	    </c:if>
		<c:if test="${(empty user.name) || !user.editRepositoryRight}">
			<b>To edit authors you need to log in as a user with appropriate rights!</b><br><br>
			<a href="login.htm?url=repository-authors.htm">[log in]</a> <a href="index.htm">[return to homepage]</a>
	    </c:if>
    </td>
    </tr></table>
    </div>
	<%@include file="footer.jsp.inc" %>
  </body>
</html>
