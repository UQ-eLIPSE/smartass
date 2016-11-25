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

      <h2>Recent Assignments</h2>
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
            <form action="index.htm?mode=browse" method="post">
              <div class="form-group">
                <label class="white">Search by assignment name:</label><br>
                <input class="form-control" maxlength=128 name=byname size=55 title="Search by assignment name" value="<c:out value="${assignments_byname}"/>"> <br>
                <label class="white">Search by user:</label><br>
                <input class="form-control" maxlength=128 name=byuser size=55 title="Search by user" value="<c:out value="${assignments_byuser}"/>"> <br>
                <!-- 			                <label class="white">Search by tags:</label><br>
                <input class="form-control" maxlength=128 name=bytags size=55 title="Search by tags" value="<c:out value="${assignments_bytags}"/>"> <br> -->
                <button class="btn btn-warning pull-right" type=submit value="Search"><span class="glyphicon glyphicon-search"></span> Search</button>
              </div>
            </form>
          </c:if>
        </div>

        <div class="panel-body">
          <table width="95%" cellspacing="0" class="table">

            <c:if test="${(mode=='recent') or (mode=='user') or (mode=='my') or (mode=='browse')}">
              <tr>
                <td colspan=3>
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
                </td>
              </tr>
            </c:if>

            <thead>
              <tr class="header">
                <th>Date</th>
                <th>Name</th>
                <th colspan=2>Author</th>
              </tr>
            </thead>

            <c:set var="rownumber" value="0"/>
            <c:forEach items="${assignments}" var="t">
              <tr>
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

            <c:if test="${(mode=='recent') or (mode=='user') or (mode=='my') or (mode=='browse')}">
              <tr>
                <td colspan=3>
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
                </td>
              </tr>
            </c:if>
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
              </div>
            </c:if>
          </div>
        </div>
        </div>
        <%@include file="../jsp/footer.jsp.inc" %>
      </body>
    </html>