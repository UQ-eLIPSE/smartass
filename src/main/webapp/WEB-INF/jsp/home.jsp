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
      </div>

      <h2>Assignments</h2>
      <div class="panel panel-default">
        <div class="panel-heading background-primary-color clearfix">
          <c:if test="${mode=='recent' || empty mode}">
            <h3 class="panel-title white">Assignments</h3>
          </c:if>

          <c:if test="${mode=='user'}">
            <h3 class="panel-title white"><c:out value="${auser.name}"/>'s Assignments</h3>
          </c:if>

          <c:if test="${mode=='my'}">
            <h3 class="panel-title white">My Assignments</h3>
          </c:if>

          <c:if test="${mode=='browse'}">
            <h3 class="panel-title white">Browse Assignments</h3>
              <div class="form-group">
                <input id="nameFilter" class="form-control input-lg" placeholder="Search..." />
                <!-- <button class="btn btn-warning pull-right" type=submit value="Search"><span class="glyphicon glyphicon-search"></span> Search</button> -->
              </div>
          </c:if>
        </div>

        <div class="panel-body">
          <table width="95%" class="table table-bordered" id="assignment-table">

            <thead>
              <tr class="header">
                <th style="cursor: pointer" id="uploaded-header">
                    <a>Date</a>
                    <span id="uploaded-icon" aria-hidden="true"></span>
                </th>
                <th style="cursor: pointer" id="name-header">
                    <a>Name</a>
                    <span id="name-icon" aria-hidden="true"></span>
                </th>
                <th style="cursor: pointer" id="author-header" colspan=2>
                    <a>Author</a>
                    <span id="author-icon" aria-hidden="true"></span>
                </th>
              </tr>
            </thead>

            <c:set var="rownumber" value="0"/>
            <c:forEach items="${assignments}" var="t">
              <tr class="table-row">
                <td width="15%"><c:out value="${t.dtcreated}"/></td>
                <td>
                  <c:out value="${t.name}"/>
                </td>
                <td>
                  <c:out value="${t.user.name}"/>
                </td>
                <td width="15%">
                  <c:if test="${t.user.id==user.id}">
                    <a href="composer.htm?mode=1&id=<c:out value="${t.id}"/>">edit</a>
                    <a href="assignment-delete-confirm.htm?id=<c:out value="${t.id}"/>">delete</a>
                  </c:if>
                  <a href="composer.htm?new=1&id=<c:out value="${t.id}"/>">copy</a>
                </td>
              </tr>

              <c:set var="rownumber" value="${rownumber+1}"/>
            </c:forEach>

          </table>
        </div>

      <div class="panel panel-default">
        <c:if test="${(empty mode)}">
          <div class="panel-heading background-primary-color">
            <h3 class="panel-title white">Templates</h3>
          </div>
          <div class="panel-body">
            <table width="95%" cellspacing="0" class="table table-striped">
              <thead>
                <tr class="header">
                  <th>Name</th>
                  <th>Uploaded</th>
                  <th>Author</th>
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
              </div>
            </c:if>
          </div>
        </div>
        </div>
        <%@include file="../jsp/footer.jsp.inc" %>

        <script src="/js/template_table.js"></script>
        <script>
            template_table.init('#assignment-table', 'assignments');
        </script>
      </body>
    </html>
