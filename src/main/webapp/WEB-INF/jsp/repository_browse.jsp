<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <%@include file="header.jsp.inc" %>
    <title>Smart Assignments | Repository</title>
</head>
<body>
    <div class="container">
    <h2>Repository</h2>
    <table width="100%"><tr class="menu"><td colspan=2>
        <table class="menu"><tr class="selected-menu"><td class="selected-menu" colspan=5></td></tr></table>
    </td></tr><tr>
    <td valign="top" width="200" class="menu">
        <table>
            <tr><td>
                <table>
                <c:forEach items="${classs}" var="c">
                    <c:if test="${c.id==selected_top_class.id}">
                        <c:if test="${c.id==selected_class.id}">
                            <tr class="selected-menu"><td colspan=2>
                            <c:out value="${c.name}"/>
                        </c:if>
                        <c:if test="${c.id!=selected_class.id}">
                            <tr><td colspan=2>
                            <a href="repository.htm?classid=<c:out value="${c.id}"/>">
                                <c:out value="${c.name}"/>
                            </a>
                        </c:if>
                        </td></tr>
                        <c:forEach items="${sub_classs}" var="sc">
                            <c:if test="${sc.id==selected_class.id}">
                                <tr class="selected-menu"><td width="20">&nbsp&nbsp</td><td>
                                <c:out value="${sc.name}"/>
                            </c:if>
                            <c:if test="${sc.id!=selected_class.id}">
                                <tr><td width="20">&nbsp&nbsp</td><td>
                                <a href="repository.htm?classid=<c:out value="${sc.id}"/>">
                                    <c:out value="${sc.name}"/>
                                </a>
                            </c:if>
                            </td></tr>
                        </c:forEach>
                    </c:if>
                    <c:if test="${c.id!=selected_top_class.id}">
                        <tr><td colspan=2>
                            <a href="repository.htm?classid=<c:out value="${c.id}"/>">
                                <c:out value="${c.name}"/>
                            </a>
                    </c:if>
                    </td></tr>
                </c:forEach>
                </table>
            </td></tr>
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
        <div class="error">
            <c:out value="${errors}"/>
        </div>

    <div class="panel panel-default">
        <div class="panel-heading background-primary-color clearfix">
            <h3 class="panel-title white">Recent Templates</h3>
        <form action="repository.htm">
        <div class="form-group">
          <label class="white">Search by template name:</label>
          <input class="form-control" maxlength=128 name=templ_filter size=55 title="Search by template name"
                value="<c:out value="${templ_filter}"/>"> <br>
          <label class="white">Search by keywords (use comma or blank as delimiter):</label>
          <input class="form-control" maxlength=160 name=keyword_filter size=55 title="Search by keywords"
          value="<c:out value="${keyword_filter}"/>"> <br>

            <label class="white" for="sort">Sort by: </label>
            <select class="form-control" id="sort">
                <option>Name - Ascending</option>
                <option>Name - Descending</option>
                <option>Author Name - Ascending</option>
                <option>Author Name - Descending</option>
                <option>Date - Ascending</option>
                <option>Date - Descending</option>
            </select>

          <button class="btn btn-warning pull-right" type=submit value="Search"><span class="glyphicon glyphicon-search"></span> Search</button>
        </div>
        </form>

        </div>
        <div class="panel-body">
        <table class="table table-striped" width="95%" cellspacing="0">

        <thead>
        <c:set var="page_url" value="repository.htm?classid=${selected_class.id}&templ_filter=${templ_filter}&keyword_filter=${keyword_filter}&"/>
        <%@include file="pages.inc.jsp" %>

        <tr class="header">
            <th>Name</th>
            <th>Uploaded</th>
            <th>Author</th>

            <th width="10%" align="right">
                <c:if test="${!empty user and !empty selected_class}">
                    <a href="repository-template-new.htm?classId=<c:out value="${selected_class.id}" />">[new]</a>
                </c:if>
            </th>
        </tr>
    </thead>

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
                    <p><c:out value="${t.description}"/></p>
                    <c:if test="${!empty user}">
                            <a href="repository-template-edit.htm?id=<c:out value="${t.id}"/>">[edit data]</a>
                            <a href="repository-template-delete-confirm.htm?id=<c:out value="${t.id}"/>">[delete]</a>
                    </c:if>

            <td>
                <p><c:out value="${t.dtuploaded}"/></p>
            </td>

            <td>
                <p><c:out value="${t.author.name}"/></p>
            </td>
        </tr>

        <c:set var="rownumber" value="${rownumber+1}"/>
        </c:forEach>

        <c:set var="page_url" value="repository.htm?classid=${selected_class.id}&templ_filter=${templ_filter}&keyword_filter=${keyword_filter}&"/>
        <%@include file="pages.inc.jsp" %>

        </table>
        </div>
        </div>
    </td>
    </tr></table>
</div>
</body>
</html>
