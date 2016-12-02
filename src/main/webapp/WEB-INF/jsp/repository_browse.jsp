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
            <h3 class="panel-title white">Search Templates</h3>
            <input id="nameFilter" class="form-control input-lg" placeholder="Search..."> <br>
        </div>

        <form action="repository.htm">
        <div class="form-group">
        </div>
        </form>

        <div class="panel-body">
        <table class="table table-bordered" id="repo-table" width="95%">

        <thead>
        <c:set var="page_url" value="repository.htm?classid=${selected_class.id}&templ_filter=${templ_filter}&keyword_filter=${keyword_filter}&"/>
        <!-- <%@include file="pages.inc.jsp" %> -->

        <tr class="header">
            <th style="cursor: pointer" id="name-header"><a>Name</a> <span id="name-icon" aria-hidden="true"></span></th>
            <th style="cursor: pointer; min-width: 120px;" id="uploaded-header"><a>Uploaded</a> <span id="uploaded-icon" aria-hidden="true"></span></th>
            <th style="cursor: pointer" id="author-header"><a>Author</a> <span id="author-icon" aria-hidden="true"></span></th>

            <c:if test="${!empty user and !empty selected_class}">
                <th width="10%" align="right">
                    <a href="repository-template-new.htm?classId=<c:out value="${selected_class.id}" />">[new]</a>
                </th>
            </c:if>
        </tr>
    </thead>

            <c:set var="rownumber" value="0"/>
            <c:forEach items="${templates}" var="t">
                <tr class="table-row">
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
        <!-- <%@include file="pages.inc.jsp" %> -->

            </table>
          </div>
        </div>
      </td>
    </tr></table>
</div>
        </div>

<script src="/smartass-dev/js/template_table.js"></script>
<script>
    template_table.init('#repo-table', 'repository');
</script>
    <%@include file="../jsp/footer.jsp.inc" %>
</body>
</html>
