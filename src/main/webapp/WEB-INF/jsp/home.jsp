<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<%@include file="header.jsp.inc" %>
  	<title>Smart Assignments</title>
</head>
<body>
<div class="container">
<div class="header">
<h1><img src="https://shire.science.uq.edu.au/SmartAss/donkey-colour.gif" height="80px" width="106px" alt="small donkey">Smart Assignments </h1>
</div>
    
    <h2>Smart Assignments Home</h2>
    <table width="100%"><tr class="menu"><td colspan=2>
	    <table class="menu"><tr class="menu">
	    	<td class="selected-menu">
		    	<b>[home]</b>
	    	</td>
	    	<td><a href="repository.htm">[repository]</a></td>
	    	<c:if test="${empty user}">
		    	<td><a href="login.htm">[log in]</a></td>
		    	<td><a href="register.htm">[register]</a></td>
		    	<td><a href="help.htm?context=home" target="_blank">[help]</a></td>
		    </c:if>
	    	<c:if test="${!empty user}">
		    	<td><a href="edituser.htm">[user settings]</a></td>
		    	<td><a href="logout.htm">[log out]</a></td>
		    	<td><a href="help.htm?context=home" target="_blank">[help]</a></td>
		    	<td>
		    		Logged in as: <b><c:out value="${user.name}"/>
		    		<c:if test="${!empty user.fullname}">
		    			(<c:out value="${user.fullname}"/>)</b>
		    		</c:if>
		    	</td>
		    </c:if>
	    </tr><tr class="selected-menu"><td class="selected-menu" colspan=5></td></tr></table>
    </td></tr><tr>
    <td valign="top" width="200" class="menu">
	    <table>
	    	<c:if test="${!empty mode}">
		    	<tr><td >
					<a href="index.htm">[recent templates]</a>
				</td></tr>
			</c:if>
		    <c:if test="${empty mode}">
				<tr class="selected-menu"><td>
					[recent templates]
				</td></tr>
			</c:if>
	    	<c:if test="${mode!='browse'}">
		    	<tr><td>
					<a href="index.htm?mode=browse">[browse assignments]</a>
				</td></tr>
			</c:if>
	    	<c:if test="${mode=='browse'}">
		    	<tr><td class="selected-menu">
					[browse assignments]
				</td></tr>
			</c:if>
	    	<tr><td>
				<a href="users.htm">[users]</a>
				<hr>
			</td></tr>
	    	<tr>
		    	<c:if test="${mode=='my'}">
			    	<td class="selected-menu">[my assignments]</td>
			    </c:if>
		    	<c:if test="${mode!='my'}">
		    		<td>
			    	<c:if test="${!empty user}">
						<a href="index.htm?mode=my">[my assignments]</a>
					</c:if>
			    	<c:if test="${empty user}">[my assignments]</c:if>
			    	</td>
			    </c:if>
			</tr>
	    	<tr><td>
				<a href="composer.htm">[create new]</a>
			</td></tr>
	    	<tr><td><hr></td></tr>
	    	<tr><td><a href="contacts.htm">[contacts]</a></td></tr>
	    </table>
    </td>
    <td valign="top">
		<c:if test="${mode=='recent' || empty mode}">
	    	<h3>Recent Assignments</h3>
		</c:if>
		<c:if test="${mode=='user'}">
	    	<h3><c:out value="${auser.name}"/>'s Assignments</h3>
		</c:if>
		<c:if test="${mode=='my'}">
	    	<h3>My Assignments</h3>
		</c:if>
		<c:if test="${mode=='browse'}">
	    	<h3> Browse Assignments</h3>
			<form action="index.htm?mode=browse" method="post">
				Search by assignment name:<br>
				<input maxlength=128 name=byname size=55 title="Search by assignment name" value="<c:out value="${assignments_byname}"/>"> <br>
				Search by user:<br>
				<input maxlength=128 name=byuser size=55 title="Search by user" value="<c:out value="${assignments_byuser}"/>"> <br>
<!-- 				Search by tags:<br>
				<input maxlength=128 name=bytags size=55 title="Search by tags" value="<c:out value="${assignments_bytags}"/>"> <br> -->
		    	<input type=submit value="Search">
			</form>
		</c:if>
	    <table width="95%" cellspacing="0">

		<c:if test="${(mode=='recent') or (mode=='user') or (mode=='my') or (mode=='browse')}">
			<tr><td colspan=3>
				<c:if test="${mode=='recent'}">
					<c:set var="page_url" value="index.htm?mode=${mode}&"/>
				</c:if>
				<c:if test="${mode=='browse'}">
					<c:set var="page_url" value="index.htm?mode=${mode}&byname=${byname}&byuser=${byuser}&bytags=${bytags}&"/>
				</c:if>
				<c:if test="${mode=='user'}">
					<c:set var="page_url" value="index.htm?mode=${mode}&id=${auser.id}&"/>
				</c:if>
				<c:if test="${mode=='my'}">
					<c:set var="page_url" value="index.htm?mode=${mode}&"/>
				</c:if>
				<%@include file="pages.inc.jsp" %>
			</td></tr>
		</c:if>

	    <tr class="header">
	    	<th>Date</th>
	    	<th>Name</th>
	    	<th colspan=2>Author</th>
	    </tr>
	    <c:set var="rownumber" value="0"/>
	    <c:forEach items="${assignments}" var="t">
	    	<c:if test="${(rownumber % 2) == 1}">
	    		<tr class="row-dark">
	    	</c:if>
	    	<c:if test="${(rownumber % 2) == 0}">
	    		<tr class="row-light">
	    	</c:if>
	    		<td width="15%"><c:out value="${t.dtcreated}"/></td>
	    		<td>
				    <c:out value="${t.name}"/>
				</td>
	    		<td><c:out value="${t.user.name}"/></td>
	    		<td width="15%">
	    			<c:if test="${t.user.id==user.id}">
						 <a href="composer.htm?mode=1&id=<c:out value="${t.id}"/>">[edit]</a>
						 <a href="assignment-delete-confirm.htm?id=<c:out value="${t.id}"/>">[delete]</a>
	    			</c:if>
					<a href="composer.htm?new=1&id=<c:out value="${t.id}"/>">[copy]</a>
				</td>
	    	</tr>
	    	<c:if test="${(rownumber % 2) == 1}">
	    		<tr class="row-dark">
	    	</c:if>
	    	<c:if test="${(rownumber % 2) == 0}">
	    		<tr class="row-light">
	    	</c:if>
	    		<td colspan=3><c:out value="${t.description}"/></td>
	    	</tr>
	    <c:set var="rownumber" value="${rownumber+1}"/>
	    </c:forEach>

		<c:if test="${(mode=='recent') or (mode=='user') or (mode=='my') or (mode=='browse')}">
			<tr><td colspan=3>
				<c:if test="${mode=='recent'}">
					<c:set var="page_url" value="index.htm?mode=${mode}&"/>
				</c:if>
				<c:if test="${mode=='browse'}">
					<c:set var="page_url" value="index.htm?mode=${mode}&byname=${byname}&byuser=${byuser}&bytags=${bytags}&"/>
				</c:if>
				<c:if test="${mode=='user'}">
					<c:set var="page_url" value="index.htm?mode=${mode}&id=${auser.id}&"/>
				</c:if>
				<c:if test="${mode=='my'}">
					<c:set var="page_url" value="index.htm?mode=${mode}&"/>
				</c:if>
				<%@include file="pages.inc.jsp" %>
			</td></tr>
		</c:if>

	    </table>

		<c:if test="${(empty mode)}">
		    <h3>Recent Templates</h3>
		    <table width="95%" cellspacing="0">
		    <tr class="header">
		    	<th>Name</th>
		    	<th>Uploaded</th>
		    	<th>Author</th>
		    </tr>
		    <c:set var="rownumber" value="0"/>
		    <c:forEach items="${templates}" var="t">
		    	<c:if test="${(rownumber % 2) == 1}">
		    		<tr class="row-dark">
		    	</c:if>
		    	<c:if test="${(rownumber % 2) == 0}">
		    		<tr class="row-light">
		    	</c:if>
		    		<td>
					    <c:url var="url" value="/template.htm" >
					  		<c:param name="id" value="${t.id}" />
						</c:url>
					    <a href="<c:out value="${url}"/>"> <c:out value="${t.name}"/> </a>
					</td>
		    		<td><c:out value="${t.dtuploaded}"/></td>
		    		<td><c:out value="${t.author.name}"/></td>
		    	</tr>
		    	<c:if test="${(rownumber % 2) == 1}">
		    		<tr class="row-dark">
		    	</c:if>
		    	<c:if test="${(rownumber % 2) == 0}">
		    		<tr class="row-light">
		    	</c:if>
		    		<td colspan=3><c:out value="${t.description}"/></td>
		    	</tr>
		    <c:set var="rownumber" value="${rownumber+1}"/>
		    </c:forEach>
		    </table>
		</c:if>
    </td>
    </tr>
    </table>
</body>
<head>
	<%@include file="header.jsp.inc" %>
  	<title>Smart Assignments</title>
</head>
</html>
