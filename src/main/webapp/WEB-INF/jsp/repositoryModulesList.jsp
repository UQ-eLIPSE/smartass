<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
	<%@include file="header.jsp.inc" %>
  	<title>Smart Assignments - Repository - Modules</title>
  </head>
  <body>
  <div class="container">
    <h2>Smart Assignments Repository - Modules</h2>
		<c:if test="${(!empty user.name) && user.editRepositoryRight}">
		      <div class="error">
		      	<c:out value="${errors}"/>
		      </div>

		      <div class="panel panel-default">

		        <div class="panel-heading background-primary-color white clearfix">
		            <form action="repository-modules.htm">
		                <div class="form-group">
		                    <label>Search by module name or package:</label>
		                    <input  class="form-control" maxlength=128 id="search" name="search" size=55 title="Search by module name or package" value="<c:out value="${lastsearch}"/>"> <br>
		                    <input class="btn btn-warning pull-right" type=submit value="Search">
		                </div>
		            </form>
                </div>

                <div class="panel-body">
		    <table class="table" width="95%" cellspacing="0">

		    <thead>
		    	<th>Name</th>
		    	<th>Package</th>
		    	<th align="right">
				    <a href="repository-module.htm?id=0"> New </a>
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
		    		<td width="30%">
					    <c:url var="url" value="/repository-module.htm" >
					  		<c:param name="id" value="${i.id}" />
						</c:url>
					    <a href="<c:out value="${url}"/>"> <c:out value="${i.name}"/> </a>
					</td>
		    		<td><c:out value="${i.modulePackage}"/></td>
		    		<td width="10%" align="right">
					    <c:url var="url" value="/repository-module-delete-confirm.htm" >
					  		<c:param name="id" value="${i.id}" />
						</c:url>
					    <a class="pull-right" href="<c:out value="${url}"/>">Delete</a>
		    		</td>
		    	</tr>
		    <c:set var="rownumber" value="${rownumber+1}"/>
		    </c:forEach>

			<c:set var="page_url" value="repository-modules.htm?search=${search}&"/>
			<%@include file="pages.inc.jsp" %>

		    </table>
		    </div>
		    </div>
	    </c:if>
		<c:if test="${(empty user.name) || !user.editRepositoryRight}">
			<b>To edit modules you need to log in as a user with appropriate rights!</b><br><br>
			<a href="login.htm?url=repository-modules.htm">log in</a> <a href="index.htm">return to homepage</a>
	    </c:if>
    </div>
	<%@include file="footer.jsp.inc" %>
</body>
</html>
