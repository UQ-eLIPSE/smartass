<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <%@include file="header.jsp.inc" %>
    <title>Smart Assignments | Repository</title>
</head>
<body>
    <h2>Smart Assignments - Repository</h2>
    <table width="100%"><tr class="menu"><td colspan=2>
        <table class="menu"><tr class="menu">
            <td> <a href="index.htm">[home]</a></td>
            <td class="selected-menu">[repository]</td>
            <c:if test="${empty user}">
                <td><a href="login.htm">[log in]</a></td>
                <td><a href="register.htm">[register]</a></td>
                <td><a href="help.htm?context=repo" target="_blank">[help]</a></td>
            </c:if>
            <c:if test="${!empty user}">
                <td><a href="edituser.htm">[user settings]</a></td>
                <td><a href="logout.htm">[log out]</a></td>
                <td><a href="help.htm?context=repo" target="_blank">[help]</a></td>
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
            <c:if test="${!empty user}">
                <tr><td>
                    <a href="repository-authors.htm">[authors]</a>
                </td></tr>
                <tr><td>
                    <a href="repository-classs.htm">[categories]</a>
                </td></tr>
                <tr><td>
                    <a href="repository-modules.htm">[modules]</a>
                </td></tr>
                <tr><td>
                    <a href="repository-files.htm">[files]</a>
                </td></tr>
                <tr><td><hr></td></tr>
            </c:if>
            <c:if test="${empty selected_class.id}">
                <tr class="selected-menu"><td>[templates]</td></tr>
            </c:if>
            <c:if test="${!empty selected_class.id}">
                <tr><td><a href="repository.htm">[templates]</a></td></tr>
            </c:if>
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
        <form action="repository.htm">
          Search by template name: <br>
          <input maxlength=128 name=templ_filter size=55 title="Search by template name" 
                value="<c:out value="${templ_filter}"/>"> <br>
          Search by keywords (use comma or blank as delimiter):<br>
          <input maxlength=160 name=keyword_filter size=55 title="Search by keywords" 
                value="<c:out value="${keyword_filter}"/>"> <br>
          <input type=submit value="Search">
        </form>

        <table width="95%" cellspacing="0">

        <c:set var="page_url" value="repository.htm?classid=${selected_class.id}&templ_filter=${templ_filter}&keyword_filter=${keyword_filter}&"/>
        <%@include file="pages.inc.jsp" %>
        
        <tr class="header">
            <th>Name</th>
            <th>Uploaded</th>
            <th>Author</th>

            <th width="10%" align="right">
                <c:choose>
                    <c:when test="${!empty user and !empty selected_class}">
                        <a href="repository-template-new.htm?classId=<c:out value="${selected_class.id}" />">[new]</a>
                    </c:when>
                    <c:otherwise>[new]</c:otherwise>
                </c:choose>
            </th>
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
                <td colspan=2><c:out value="${t.author.name}"/></td>
            </tr>
            <c:if test="${(rownumber % 2) == 1}">
            <tr class="row-dark">
            </c:if>
            <c:if test="${(rownumber % 2) == 0}">
            <tr class="row-light">
            </c:if>
                <c:if test="${empty user}"><td colspan=4></c:if>
                <c:if test="${!empty user}"><td colspan=2></c:if>
                    <c:out value="${t.description}"/>
                </td>
                    <c:if test="${!empty user}">
                        <td align="right" width="40%" colspan=2>
                            <a href="repository-template-edit.htm?id=<c:out value="${t.id}"/>">[edit data]</a> 
                            <a href="repository-template-delete-confirm.htm?id=<c:out value="${t.id}"/>">[delete]</a>
                        </td>
                    </c:if>
            </tr>
        <c:set var="rownumber" value="${rownumber+1}"/>
        </c:forEach>

        <c:set var="page_url" value="repository.htm?classid=${selected_class.id}&templ_filter=${templ_filter}&keyword_filter=${keyword_filter}&"/>
        <%@include file="pages.inc.jsp" %>
        
        </table>
    </td>
    </tr></table>
</body>
<head>
    <%@include file="header.jsp.inc" %>
</head>
</html>
