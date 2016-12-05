<%@page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
	<%@include file="header.jsp.inc" %>
	<title>Smart Assignments Repository | Template Details</title> 
</head>
<body>
    <div class="container">
<!--	<input class="btn btn-warning"style="width:125" type="button" class="button" name="back" value="Back" onclick="history.go(-1)" > -->


        <h2>View Template</h2>

        <div class="panel panel-default"> 
            <div class="panel-heading">
                <h3 class="panel-title">Overview</h3>
            </div>

            <div class="panel-body">
                <div class="form-group">
                    <label for="template_name">Template name:</label>
                    <p id="template_name">${template.name}</p>
                </div>

                <div class="form-group">
                    <label for="keywords">Keywords</label>
                    <p id="keywords">${template.keywords}</p>
                </div>

                <div class="form-group">
                    <label for="created">Created (date format is yyyy-mm-dd)</label>
                    <p id="created">${template.dtcreated}</p>
                </div>

                <div class="form-group">
                    <label for="uploaded">Uploaded</label>
                    <p id="uploaded">${template.dtuploaded}</p>
                </div>

                <div class="form-group">
                    <label for="description">Description</label>
                </div>
            </div>
        </div>

        <div class="panel panel-default"> 
            <div class="panel-heading">
                <h3 class="panel-title">Categories</h3>
            </div>

            <div class="panel-body">

                <ul class="list-group">
                    <c:set var="i" value="0"/>
                    <c:forEach items="${template.classifications}" var="m">
                            <li class="list-group-item">
                                <c:out value="${m.fullName}"/>
                                <c:if test="${classCount>1}">
                                    <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                                </a>
                                </c:if>
                            </li>
                        <c:set var="i" value="${i+1}"/>
                    </c:forEach>
                </ul>

            </div>
        </div>

        <div class="panel panel-default"> 
            <div class="panel-heading">
                <h3 class="panel-title">Author</h3>
            </div>

            <div class="panel-body">
                <dl class="dl-horizontal">
                    <dt>Author Name</dt>
                    <dd><c:out value="${template.author.name}"/></dd>

                    <dt>Description</dt>
                    <dd><c:out value="${template.author.description}"/></dd>
                </dl>

            </div>
        </div>

        <c:if test="${!empty template.modules}">
            <div class="panel panel-default"> 
                <div class="panel-heading">
                    <h3 class="panel-title">Modules</h3>
                </div>

                <div class="panel-body">
                    <ul class="list-group">
                        
                        <c:set var="i" value="0"/>
                        <c:forEach items="${template.modules}" var="m">
                            <li class="list-group-item">
                                <dl class="dl-horizontal">
                                    <dt>Module Name</dt>
                                    <dd>${m.name}</dd>

                                    <dt>Package</dt>
                                    <dd>${m.modulePackage}</dd>

                                    <dt>Parameters</dt>
                                    <dd>${m.parameters}</dd>

                                    <dt>Description</dt>
                                    <dd>${m.description}</dd>
                                </dl>
                            </li>
                            <c:set var="i" value="${i+1}"/>
                        </c:forEach>
                    </ul>


                </div>
            </div>
        </c:if>

        <c:if test="${!empty template.files}">
            <div class="panel panel-default"> 
                <div class="panel-heading">
                    <h3 class="panel-title">Files</h3>
                </div>

                <div class="panel-body">
                    <ul class="list-group">
                    <c:set var="i" value="0"/>
                    <c:forEach items="${template.files}" var="m">
                        <li class="list-group-item">
                            <dl class="dl-horizontal">
                                <dt>File name</dt>
                                <dd>${m.name}</dd>

                                <dt>Description</dt>
                                <dd>${m.description}</dd>
                            </dl>
                        </li>

                        <c:set var="i" value="${i+1}"/>
                    </c:forEach>
                    </ul>

                </div>
            </div>
        </c:if>

        <c:if test="${!empty template.updates}">
            <div class="panel panel-default"> 
                <div class="panel-heading">
                    <h3 class="panel-title">Updates</h3>
                </div>

                <div class="panel-body">
                    <ul class="list-group">
                    <c:set var="i" value="0"/>
                    <c:forEach items="${template.updates}" var="m">
                        <li class="list-group-item">
                            <dl class="dl-horizontal">
                                <dt>Date</dt>
                                <dd>${m.updateDate}</dd>

                                <dt>Author</dt>
                                <dd>${m.author.name}</dd>

                                <dt>Comment</dt>
                                <dd>${m.comment}</dd>
                            </dl>
                        </li>

                        <c:set var="i" value="${i+1}"/>
                    </c:forEach>
                    </ul>

                </div>
            </div>
        </c:if>

        <div class="panel panel-default"> 
            <div class="panel-heading">
                <h3 class="panel-title">Examples</h3>
            </div>

            <div class="panel-body">
                <ul class="list-group">
                    <li class="list-group-item" style="line-height: 2.5;">
                        <b>Questions:</b> ${template.questions}
                    </li>

                    <li class="list-group-item" style="line-height: 2.5;">
                        <b>Solutions:</b> ${template.solutions}
                    </li>

                    <li class="list-group-item" style="line-height: 2.5;">
                        <b>Short Answers:</b> ${template.shortanswers}
                    </li>
                </ul>

            </div>
        </div>


	</div>

</body>
</html>
