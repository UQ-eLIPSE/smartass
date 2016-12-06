<%@page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
	<%@include file="../jsp/header.jsp.inc" %>
	<title>Smart Assignments Repository | Edit Template</title> 
</head>
<body>
    <div class="container">

	<form:form modelAttribute="template">   

        <h2>Edit Template</h2>

        <div class="panel panel-default"> 
            <div class="panel-heading">
                <h3 class="panel-title">Overview</h3>
            </div>

            <div class="panel-body">
                <div class="form-group">
                    <label for="template_name">* Template name:</label>
                    <form:input path="name" id="template_name" cssClass="form-control"/>
                    <div class="error">
                        <form:errors path="*"/>
                    </div>
                </div>

                <div class="form-group">
                    <label for="keywords">Keywords</label>
                    <form:input path="keywords" id="keywords" cssClass="form-control"/>
                </div>

                <div class="form-group">
                    <label for="created">Created (date format is yyyy-mm-dd)</label>
                    <form:input path="dtcreated" id="created" cssClass="form-control"/>
                </div>

                <div class="form-group">
                    <label for="uploaded">Uploaded</label>
                    <p id="uploaded" class="form-control-static">${template.dtuploaded}</p>
                </div>

                <div class="form-group">
                    <label for="description">Description</label>
                    <form:textarea path="description" id="description" cssClass="form-control"/>
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
                                <a class="badge" href="repository-template-edit.htm?execution=<c:out value="${flowExecutionKey}"/>&_eventId_removeClass&i=<c:out value="${i}"/>">
                                    <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                                </a>
                                </c:if>
                            </li>
                        <c:set var="i" value="${i+1}"/>
                    </c:forEach>
                </ul>

                <a class="btn btn-success" role="button" href="repository-template-edit.htm?execution=<c:out value="${flowExecutionKey}"/>&_eventId_addClass&parentid=0">Add</a>
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

                <a class="btn btn-default" href="repository-template-edit.htm?execution=<c:out value="${flowExecutionKey}"/>&_eventId_selectAuthor">Change</a>
            </div>
        </div>

        <div class="panel panel-default"> 
            <div class="panel-heading">
                <h3 class="panel-title">Modules</h3>
            </div>

            <div class="panel-body">
                <ul class="list-group">
                    
                    <c:set var="i" value="0"/>
                    <c:forEach items="${template.modules}" var="m">
                        <li class="list-group-item">
                            <a class="btn btn-danger badge" href="repository-template-edit.htm?execution=<c:out value="${flowExecutionKey}"/>&_eventId_removeModule&i=<c:out value="${i}"/>">Remove</a>
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

                <a class="btn btn-success" href="repository-template-edit.htm?execution=<c:out value="${flowExecutionKey}"/>&_eventId_addModule">Add</a>


            </div>
        </div>

        <div class="panel panel-default"> 
            <div class="panel-heading">
                <h3 class="panel-title">Files</h3>
            </div>

            <div class="panel-body">
                <ul class="list-group">
                <c:set var="i" value="0"/>
                <c:forEach items="${template.files}" var="m">
                    <li class="list-group-item">
                        <a class="badge btn btn-danger" href="repository-template-edit.htm?execution=<c:out value="${flowExecutionKey}"/>&_eventId_removeFile&i=<c:out value="${i}"/>">remove</a>
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

                <a class="btn btn-success" href="repository-template-edit.htm?execution=<c:out value="${flowExecutionKey}"/>&_eventId_addFile">Add</a>
            </div>
        </div>

        <div class="panel panel-default"> 
            <div class="panel-heading">
                <h3 class="panel-title">Updates</h3>
            </div>

            <div class="panel-body">
                <ul class="list-group">
                <c:set var="i" value="0"/>
                <c:forEach items="${template.updates}" var="m">
                    <li class="list-group-item">
                        <a class="badge btn btn-danger" href="repository-template-edit.htm?execution=<c:out value="${flowExecutionKey}"/>&_eventId_removeUpdate&i=<c:out value="${i}"/>">remove</a>
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

                <a class="btn btn-success" href="repository-template-edit.htm?execution=<c:out value="${flowExecutionKey}"/>&_eventId_addUpdate">Add</a>
            </div>
        </div>

        <div class="panel panel-default"> 
            <div class="panel-heading">
                <h3 class="panel-title">Examples</h3>
            </div>

            <div class="panel-body">
                <ul class="list-group">
                    <li class="list-group-item" style="line-height: 2.5;">
                        <b>Questions:</b> ${template.questions}
                        <div class="pull-right">
                            <c:if test="${!empty template.questions}">
                            <c:url var="url" value="/download.htm" >
                                <c:param name="scope" value="1" />
                                <c:param name="id" value="${template.id}" />
                                <c:param name="kind" value="0" />
                            </c:url>
                            <a class="btn btn-default" href="<c:out value="${url}"/>">download</a>
                            <c:url var="url" value="/repository-template-edit.htm" >
                                <c:param name="execution" value="${flowExecutionKey}" />
                                <c:param name="_eventId_deleteExample" value="1" />
                                <c:param name="kind" value="0" />
                            </c:url>
                            <a class="btn btn-danger" href="<c:out value="${url}"/>">delete</a>
                            </c:if>
                        </div>
                    </li>

                    <li class="list-group-item" style="line-height: 2.5;">
                        <b>Solutions:</b> ${template.solutions}
                        <div class="pull-right">
                            <c:if test="${!empty template.solutions}">
                            <c:url var="url" value="/download.htm" >
                                <c:param name="scope" value="1" />
                                <c:param name="id" value="${template.id}" />
                                <c:param name="kind" value="1" />
                            </c:url>
                            <a class="btn btn-default" href="<c:out value="${url}"/>">download</a>
                            <c:url var="url" value="/repository-template-edit.htm" >
                                <c:param name="execution" value="${flowExecutionKey}" />
                                <c:param name="_eventId_deleteExample" value="1" />
                                <c:param name="kind" value="1" />
                            </c:url>
                            <a class="btn btn-danger" href="<c:out value="${url}"/>">delete</a>
                            </c:if>
                        </div>
                    </li>

                    <li class="list-group-item" style="line-height: 2.5;">
                        <b>Short Answers:</b> ${template.shortanswers}
                        <div class="pull-right">
                            <c:if test="${!empty template.shortanswers}">
                            <c:url var="url" value="/download.htm" >
                                <c:param name="scope" value="1" />
                                <c:param name="id" value="${template.id}" />
                                <c:param name="kind" value="2" />
                            </c:url>
                            <a class="btn btn-default" href="<c:out value="${url}"/>">download</a>
                            <c:url var="url" value="/repository-template-edit.htm" >
                                <c:param name="execution" value="${flowExecutionKey}" />
                                <c:param name="_eventId_deleteExample" value="1" />
                                <c:param name="kind" value="2" />
                            </c:url>
                            <a class="btn btn-danger" href="<c:out value="${url}"/>">delete</a>
                            </c:if>
                        </div>
                    </li>
                </ul>

                <a class="btn btn-success" href="repository-template-edit.htm?execution=<c:out value="${flowExecutionKey}"/>&_eventId_uploadExamples">Add</a>
            </div>
        </div>

		<input style="width:125" type="submit" class="button btn btn-primary" name="_eventId_save" value="Save"/>
		<input style="width:125" type="submit" class="button btn btn-default" name="_eventId_cancel" value="Cancel"/>
	</form:form>
    </div>
</body></html>
