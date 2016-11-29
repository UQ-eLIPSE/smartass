<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="-1">
<!---<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/smartass.css" />-->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/custom.css" />

<script src="/smartass-dev/js/jquery.min.js"></script>
<script src="/smartass-dev/js/bootstrap.min.js"></script>

<nav class="navbar navbar-default no-radius background-primary-color nav-logo">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand white" href="${pageContext.request.contextPath}/">
              <img src="${pageContext.request.contextPath}/img/uq-smartass-logo.png" />
            </a>

        </div>

        <ul class="nav navbar-nav navbar-right nav-logo-right">
            <li><a href="./index.html" class="white">Home</a></li>
            <li><a href="index.htm" class="white">Assignments</a></li>
            <li><a href="repository.htm" class="white">Repository</a></li>
	    	<c:if test="${empty user}">
                <li><a href="login.htm" class="white">Log In</a></li>
                <li><a href="register.htm" class="white">Register</a></li>
		    </c:if>
	    	<c:if test="${!empty user}">
		    	<li><a href="logout.htm" class="white">Log Out</a></li>
		    </c:if>
		    <c:if test="${!empty user}">
		    <li class="dropdown">
                      <a href="#" class="dropdown-toggle white" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Admin<span class="caret"></span></a>
                      <ul class="dropdown-menu">
		    	        <li><a href="edituser.htm" class="">User Settings</a></li>
		    	        <c:if test="${user.adminRight}">
		    	            <li><a href="repository-authors.htm" class="">Edit Authors</a></li>
		    	            <li><a href="repository-classs.htm?parentid=0" class="">Edit Categories</a></li>
		    	            <li><a href="repository-modules.htm" class="">Edit Modules</a></li>
		    	            <li><a href="repository-files.htm" class="">Edit Files</a></li>
		    	        </c:if>
                      </ul>
                    </li>
		    </c:if>
            <li><a href="index.htm?mode=browse" class="white">Browse Assignments</a></li>
            <li><a href="help.htm?context=ass-new" class="white" target="_blank">Help</a></li>
            <li><a href="./history.html" class="white"><span>History</span></a></li>
            <li><a href="composer.htm" class="white background-secondary-color"><span class="glyphicon glyphicon-plus-sign"></span> Create New</a></li>
        </ul>
    </div>
</nav>
