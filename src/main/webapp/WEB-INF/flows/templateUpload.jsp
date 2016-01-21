<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <%@include file="../jsp/header.jsp.inc" %>
    <title>Smart Assignments Repository | Add New template | Step 1: Upload Template</title> 
</head>
<body>
    <form method="POST" enctype="multipart/form-data">

        <%-- @todo: fix table usage! --%>
        <table>
            <tr class="header"><td colspan=3><h2> Step 1: Upload Template  </h2> </td></tr>
            <tr><td><br></td></tr>
            <tr> <td>File to upload:</td> <td><input type="file" name="file" size="50"/></td> </tr>
            <tr><td><br></td></tr>
        </table>
                            
        <input style="width:125" type="submit" class="button" name="_eventId_ok" value="Next"/>
        <input style="width:125" type="submit" class="button" name="_eventId_cancel" value="Cancel"/>
    </form>
</body></html>
