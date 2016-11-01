<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
  <head>
	<%@include file="../jsp/header.jsp.inc" %>
	<title>Smart Assignments</title>
  </head>
  <body>
  <div class="container">
		    <h2>Templates</h2>
	<form:form method="post" modelAttribute="search" name="search" action="composer.htm?execution=${flowExecutionKey}">
	    <div class="form-group">
    <!-- 		<input type="hidden" name="classification"> -->
            <table width="100%">
		<tr><td colspan=2>
		</td></tr>
		<tr>
			<td valign="top" width="200" class="menu">
	    		<table width="100%">
			    	<c:if test="${search.classid==0}">
				    	<tr class="selected-menu"><td>All Templates</td></tr>
				    </c:if>
			    	<c:if test="${search.classid!=0}">
				    	<tr><td><a href="composer.htm?_eventId_search=1&pageNo=0&execution=<c:out value="${flowExecutionKey}"/>&classid=0">All Templates</a></td></tr>
				    </c:if>
			    	<tr><td>
			    		<table>
			    		<c:forEach items="${classs}" var="c">
		    				<c:if test="${c.id==search.topclassid}">
			    				<c:if test="${c.id==search.classid}">
				    				<tr class="selected-menu"><td colspan=2>
			    					<c:out value="${c.name}"/>
				    			</c:if>
			    				<c:if test="${c.id!=search.classid}">
				    				<tr><td colspan=2>
				    				<a href="composer.htm?_eventId_search=1&pageNo=0&execution=<c:out value="${flowExecutionKey}"/>&classid=<c:out value="${c.id}"/>&topclassid=<c:out value="${c.id}"/>">
				    					<c:out value="${c.name}"/>
				    				</a>
				    			</c:if>
				    			</td></tr>
					    		<c:forEach items="${sub_classs}" var="sc">
				    				<c:if test="${sc.id==search.classid}">
					    				<tr class="selected-menu"><td width="20">&nbsp&nbsp</td><td>
				    					<c:out value="${sc.name}"/>
					    			</c:if>
				    				<c:if test="${sc.id!=search.classid}">
					    				<tr><td width="20">&nbsp&nbsp</td><td>
						    			<a href="composer.htm?_eventId_search=1&pageNo=0&execution=<c:out value="${flowExecutionKey}"/>&classid=<c:out value="${sc.id}"/>">
						    				<c:out value="${sc.name}"/>
						    			</a>
						    		</c:if>
						    		</td></tr>
					    		</c:forEach>
				    		</c:if>
		    				<c:if test="${c.id!=search.topclassid}">
			    				<tr><td colspan=2>
				    				<a href="composer.htm?_eventId_search=1&execution=<c:out value="${flowExecutionKey}"/>&classid=<c:out value="${c.id}"/>&topclassid=<c:out value="${c.id}"/>">
				    					<c:out value="${c.name}"/>
				    				</a>
				    		</c:if>
			    			</td></tr>
			    		</c:forEach>
			    		</table>
			    	</td></tr>
			    </table> 
		    </td>
		    <td valign="top">
                <div class="panel panel-default">
                    <div class="panel-heading background-primary-color clearfix">
                        <h3 class="panel-title white">Templates Found</h3>
                    <label class="white">Search by template name:</label>
                    <form:input cssClass="form-control" maxlength="128" path="nameFilter" size="55"/> <br>
                    <label class="white">Search by keywords (use comma or blank as delimiter):</label>
                    <form:input cssClass="form-control" maxlength="160" path="keywordsFilter" size="55"/> <br>
                    <div class="pull-right">
                        <button class="btn white" style="width:125" type="submit" name="_eventId_search" value="Search"><span class="glyphicon glyphicon-search"></span> Search</button>
                        <button class="btn btn-warning" type="submit" name="_eventId_add" value="Add to assignment"><span class="glyphicon glyphicon-plus-sign"></span> Add to Assignments</button>
                    <!-- <input style="width:125" type="submit" name="_eventId_cancel" value="Cancel"/> -->
                    </div>
                </div>

                    <div class="panel-body">
			    <table class="table" width="95%" cellspacing="0">
<!-- 				<c:out value="${search.pageNum}"/>
				<c:out value="${search.pageNo}"/>
				<c:out value="${search.rowsNum}"/> -->
				<c:if test="${search.pageNum>0}">
					<c:set var="page_url" value="composer.htm?_eventId_search=1&execution=${flowExecutionKey}&classid=${c.id}&topclassid=${c.id}&"/>
					<tr class="pages"><td colspan=2>
						Pages:
						<c:forEach begin="0" end="${search.pageNum}" var="i">
							<c:if test="${i==search.pageNo}">[<c:out value="${i+1}"/>]</c:if>
							<c:if test="${i!=search.pageNo}">
								<a href="<c:out value="${page_url}pageNo=${i}" escapeXml="false"/>">[<c:out value="${i+1}"/>]</a>
							</c:if>
						</c:forEach>
					</td></tr>
				</c:if>

				<c:if test="${!empty search.templates}">				

			    <tr class="header">
			    	<th></th>
			    	<th>Name</th>
			    	<th>Uploaded</th>
			    	<th>Author</th>
			    </tr>
			    <c:set var="rownumber" value="0"/>
			    <c:forEach items="${search.templates}" var="t">
			    	<c:if test="${(rownumber % 2) == 1}">
			    		<tr class="row-dark">
			    	</c:if>
			    	<c:if test="${(rownumber % 2) == 0}">
			    		<tr class="row-light">
			    	</c:if>
						<td>
			    			<form:checkbox path="selectedIds" value="${t.id}" />
						</td>
						<td>
                                                    <p><a href="<c:out value="template.htm?id=${t.id}"/>"> <c:out value="${t.name}"/> </a></p>

                                                    <p><c:out value="${t.description}"/></p>
                                                        <p>
                                                        
                                                            <c:if test="${!empty t.questions || !empty t.solutions || !empty t.shortanswers}">
            
                                                                    Download examples:
                                                                    <c:if test="${!empty t.questions}">
                                                                    <c:url var="url" value="/download.htm" >
                                                                              <c:param name="scope" value="1" />
                                                                              <c:param name="id" value="${t.id}" />
                                                                              <c:param name="kind" value="0" />
                                                                        </c:url>
                                                                    <a href="<c:out value="${url}"/>">[questions]</a>
                                                                    </c:if>
                                                                    <c:if test="${!empty t.solutions}">
                                                                    <c:url var="url" value="/download.htm" >
                                                                              <c:param name="scope" value="1" />
                                                                              <c:param name="id" value="${t.id}" />
                                                                              <c:param name="kind" value="1" />
                                                                        </c:url>
                                                                    <a href="<c:out value="${url}"/>">[solutions]</a>
                                                                    </c:if>
                                                                    <c:if test="${!empty t.shortanswers}">
                                                                    <c:url var="url" value="/download.htm" >
                                                                              <c:param name="scope" value="1" />
                                                                              <c:param name="id" value="${t.id}" />
                                                                              <c:param name="kind" value="2" />
                                                                        </c:url>
                                                                    <a href="<c:out value="${url}"/>">[short answers]</a>
                                                                    </c:if>
                                                            </c:if>
                                                        </p>
                                                    </td>
                                        <td>
                                        <c:out value="${t.dtuploaded}"/>
                                        </td>
			    		<td><c:out value="${t.author.name}"/></td>
			    	</tr>
			    	<c:set var="rownumber" value="${rownumber+1}"/>
			    </c:forEach>

				<c:if test="${search.pageNum>0}">
					<c:set var="page_url" value="composer.htm?_eventId_search=1&execution=${flowExecutionKey}&classid=${c.id}&topclassid=${c.id}&"/>
					<tr class="pages"><td colspan=2>
						Pages:
						<c:forEach begin="0" end="${search.pageNum}" var="i">
							<c:if test="${i==search.pageNo}">[<c:out value="${i+1}"/>]</c:if>
							<c:if test="${i!=search.pageNo}">
								<a href="<c:out value="${page_url}pageNo=${i}" escapeXml="false"/>">[<c:out value="${i+1}"/>]</a>
							</c:if>
						</c:forEach>
					</td></tr>
				</c:if>

				</c:if>
				<c:if test="${(empty search.templates) and ((!empty search.nameFilter) or (!empty search.keywordsFilter))}">				
					<tr><td colspan=3 valign="middle" align="center">
						<b>No matches found</b>
					</td></tr>
				</c:if>
			    </table>
			    </div>
			    </div>
		    </td>
    	</tr>
		</table>
	</form:form>
	</div>
  </body>
</html>
