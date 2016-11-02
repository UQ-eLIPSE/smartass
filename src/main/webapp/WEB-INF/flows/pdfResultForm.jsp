<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
	<%@include file="../jsp/header.jsp.inc" %>
	<title>Smart Assignments | Assignment Editor | Execution Results and Pdf Files</title> 
</head>
<body>
    <div class="container">
	<form method="post">
	    <div class="form-group">
		<h2>Assignment Execution Results and Pdf Files</h2>   
		<table>
			<tr class="header"><td><h3>Latex results:</h3></td></tr>
			<tr><td><a href=<c:out value="download/${resultPath}/questions.tex"/> target="_blank">Questions</a></td></tr>
			
			<tr><td><a href=<c:out value="download/${resultPath}/solutions.tex"/> target="_blank">Solutions</a></td></tr>
			
			<tr><td><a href=<c:out value="download/${resultPath}/answers.tex"/> target="_blank">Answers</a></td></tr>
	
			<tr class="header"><td><h3>PDF files:</h3></td></tr>
			<tr><td><a href=<c:out value="download/${resultPath}/questions.pdf"/> target="_blank">Questions</a></td></tr>
			<tr><td><a href=<c:out value="download/${resultPath}/solutions.pdf"/> target="_blank">Solutions</a></td></tr>
			<tr><td><a href=<c:out value="download/${resultPath}/answers.pdf"/> target="_blank">Answers</a><br></td></tr>

			<tr class="header"><br><td></td></tr>
			<tr><td><a href=<c:out value="download/${resultPath}/assignments.zip"/>>Download all as .zip archive</a></td></tr>
			
			<tr class="header"><br><td></td></tr>
			<tr><td>
				To save a file without opening - right click and choose "Save target"<br>
				<br><b>Attention! Execution results will be deleted from the server after you return to assignment editor or after 30 minutes since its creation.</b></td></tr>
			<tr><td><br>
				<input style="width:125" type="submit" class="btn" name="_eventId_cancel" value="Back to editor"/>
				<input style="width:125" type="submit" class="btn" name="_eventId_new" value="New assignment"/>
				<input style="width:125" type="submit" class="btn" name="_eventId_finish" value="Exit"/>
			</td></tr>
		</table>
		</div>
        </form>
    </div>
</body>
</html>
