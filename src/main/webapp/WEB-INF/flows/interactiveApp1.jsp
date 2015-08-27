<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" 
	"http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<%@include file="../jsp/header.jsp.inc" %>
	<title>Smart Assignments | >Interactive Editor</title>
<script type="text/javascript">
function setAppletSize()
{	if (navigator.appName=="Netscape")
		document.getElementById("pdfviewer").height=window.innerHeight*0.85;
}
</script>
</head>
<body onload="setAppletSize();">
	<table height="100%" width="100%">
	<tr>
		<td class="header" width="95%"><h2>Interactive Editor</h2></td>
		<td class="header"><a href="help.htm?context=ass-interactive" target="_blank">[help]</a></td>
	</tr>
	<tr><td colspan="2">
		<form:form>   
			<input type="submit" class="button" name="_eventId_save" value="Save results"/>
			<input type="submit" class="button" name="_eventId_edit" value="Save code&return to editor"/>
			<input type="submit" class="button" name="_eventId_reExecute" value="Re-execute"/>
			<input type="submit" class="button" name="_eventId_cancel" value="Cancel"/>
		</form:form>
	</td></tr>
	</table>
 			<applet id="pdfviewer" code="PdfViewerApplet1.class" height="85%" width="100%"  archive="applet/PDFRenderer.jar, applet/PdfViewerApplet.jar" >
        		<param name="name" value="COMPOSER">
        		<param name="node" value="0_1"> 
        		<param name="section" value="QUESTION"> 
        		<param name="debug" value="false"> 
       		</applet> 		
</body>
</html>
