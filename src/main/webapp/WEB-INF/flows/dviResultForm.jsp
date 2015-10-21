<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html><body>
	<form method="post">   
		<br>

		Latex results:
		<a href=<c:out value="download/${resultPath}/questions.tex"/>>Download questions</a>
		<br>
		<a href=<c:out value="download/${resultPath}/solutions.tex"/>>Download solutions</a>
		<br>
		<a href=<c:out value="download/${resultPath}/answers.tex"/>>Download answers</a>
		<br> <br>
		DVI files:
		<a href=<c:out value="download/${resultPath}/questions.dvi"/>>Download questions</a>
		<br>
		<a href=<c:out value="download/${resultPath}/solutions.dvi"/>>Download solutions</a>
		<br>
		<a href=<c:out value="download/${resultPath}/answers.dvi"/>>Download answers</a>
		<br><br>
		<a href=<c:out value="download/${resultPath}/assignments.zip"/>>Download all as .zip archive</a>
		<br> 
		
		Attention! Execution results will be deleted from server after you return to assignment editor or after 30 minutes since its creation.
		<br>

		<input style="width:125" type="submit" class="button" name="_eventId_cancel" value="Back to editor"/>
		<input style="width:125" type="submit" class="button" name="_eventId_clear" value="New assignment"/>
	</form>
</body></html>