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
    <table width="100%"><tr class="menu">
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
	      <div class="panel panel-default">
	        <div class="panel-heading background-primary-color white clearfix">
		        <form action="repository-classs.htm">
		            <div class="form-group">
		                <label>Search by category name:</label>
		            <input  class="form-control" maxlength="128" id="search" name="search" size=55 title="Search by category name" value="<c:out value="${lastsearch}"/>"> <br>
		            <input class="btn btn-warning pull-right" type="submit" value="Search">
		            <input  type="hidden" name="parentid" value="<c:out value="${parentid}"/>">
		            </div>
		        </form>
            </div>
            <div class="panel-body">
		        <table class="table" width="95%" cellspacing="0">
		        <thead>
		    	    <th>Name</th>
		    	    <th align="right">
					<c:if test="${!empty parent}">
						<a href="repository-classs.htm?parentid=0">Top Level</a>
					</c:if>
				    <c:url var="url" value="/repository-class.htm" >
				  		<c:param name="parentid" value="${parent.id}" />
					</c:url>
				    <a class="pull-right" href="<c:out value="${url}"/>"> New </a>
		    	</th>
		        </thead>

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
						    <a href="<c:out value="${url}"/>">Sub-categories | </a>
						</c:if>
					    <c:url var="url" value="/repository-class-delete-confirm.htm" >
					  		<c:param name="parentid" value="${parentid}" />
						</c:url>
					    <a href="<c:out value="${url}"/>&id=<c:out value="${i.id}"/>">Delete</a>
		    		</td>
		    	</tr>
		    <c:set var="rownumber" value="${rownumber+1}"/>
		    </c:forEach>
		    </table>
	    </c:if>
		<c:if test="${(empty user.name) || !user.editRepositoryRight}">
			<b>To edit categories you need to log in as a user with appropriate rights!</b><br><br>
			<a href="login.htm?url=repository-classs.htm">Log in</a> <a href="index.htm">Return to Homepage</a>
	    </c:if>
    </td>
    </tr></table>
            </div>
        </div>
    </div>
	<%@include file="footer.jsp.inc" %>
</body>
</html>
