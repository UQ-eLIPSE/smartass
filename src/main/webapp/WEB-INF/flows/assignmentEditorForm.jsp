<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>

  <head>
    <%@include file="../jsp/header.jsp.inc" %>
    <title>Smart Assignments | Assignment Editor</title>
    <script type="text/javascript">
      function setControlsState(kind, parent_kind) {
        if(kind=="section" || parent_kind=="section") {
          document.getElementById("addRepeat").disabled = true;
          document.getElementById("addCall").disabled = true;
          document.getElementById("addMulti").disabled = true;
          document.getElementById("addChoice").disabled = true;
          document.getElementById("addSection").disabled = true;
        } else {
          document.getElementById("addRepeat").disabled = false;
          document.getElementById("addCall").disabled = false;
          document.getElementById("addMulti").disabled = false;
          document.getElementById("addSection").disabled = false;
          
          if(kind=="choice" || kind=="call" || kind=="") {
            document.getElementById("edit").disabled = true;
          } else {
            document.getElementById("edit").disabled = false;
          }

          if(kind=="multi" || (kind!="repeat" && parent_kind=="multi")) {
            document.getElementById("addChoice").disabled = false;
          } else {
            document.getElementById("addChoice").disabled = true;
          }
        } 
      }
    </script>
  </head>

  <body onload="setControlsState('<c:out value="${template.selectedRow.kind}"/>', '<c:out value="${template.selectedRow.parent.kind}"/>')">
    <form:form modelAttribute="template">   
      <table width="100%">

        <tr>
          <td class="header" colspan="2"><h2>Assignment Editor</h2></td>
          <td class="header"><a href="help.htm?context=ass-editor" target="_blank">[help]</a></td>
        </tr>

        <tr>
          <td class="header" width="10px">
            <br>
            <input id="addText" style="width:125" type="submit" name="_eventId_addText" value="Add text"/><br>
            <input id="addCall" style="width:125" type="submit" class="button" name="_eventId_addCall" value="Add QUESTION"/><br><hr>
            <input id="addRepeat" style="width:125" type="submit" class="button" name="_eventId_addRepeat" value="Add REPEAT"/><br>
            <input id="addMulti" style="width:125" type="submit" class="button" name="_eventId_addMulti" value="Add MULTI"/><br>
            <input id="addChoice" style="width:125" type="submit" class="button" name="_eventId_addChoice" value="Add CHOICE"/><br>
            <input id="addSection" style="width:125" type="submit" name="_eventId_addSection" value="Add SECTION"/><br>
            <hr>
            <input id="edit" style="width:125" type="submit" class="button" name="_eventId_edit" value="Edit"/><br><hr>
            <input id="delete" style="width:125" type="submit" class="button" name="_eventId_delete" value="Delete"/><br>
          </td>
          <td valign="top" colspan="2">
            <table border=0 frame=0 cellpadding=0>
              <tr>
                <c:set var='last' value='${template.rowCount-1}' />
                <c:set var='lastrow' value='${template.rows[last]}' />
                <td bgcolor='#f0f0f0'>
                  <form:radiobutton path='selectedIndex' value='${last}' onclick='setControlsState("${lastrow.kind}", "${lastrow.parent.kind}")' />
                </td>
                <td>
                  <table border=0 frame=0 cellspacing=0>
                    <tr>
                      <td bgcolor="#f0f0ff" width='<c:out value="${lastrow.level*10}"/>' >
                        <c:if test="${lastrow.kind == 'text'}"><pre></c:if>
                          <c:out value="${lastrow.html}" />
                        <c:if test="${lastrow.kind == 'text'}"></pre></c:if>
                      </td>
                    </tr>
                  </table>
              </tr>
            </table>
          </td>
        </tr>

        <tr class="header">
          <td></td>
          <td>
            <input type="submit" class="button" name="_eventId_view" value="View code" />
            <input type="button" class="button" name="download" value="Download" 
              onclick="window.location.href = 'download-assignment.htm?execution=<c:out value="${flowExecutionKey}"/>'" />
            <c:if test="${!canExecute}">
              <input type="submit" class="button" name="_eventId_execute" value="Execute" disabled="disabled"/>
            </c:if>
            <c:if test="${canExecute}">
              <input type="submit" class="button" name="_eventId_execute" value="Execute"/>
            </c:if>
            <input type="submit" class="button" name="_eventId_clear" value="Clear"/>
            <input style="width:125" type="submit" class="button" name="_eventId_new" value="New assignment"/>
            <input type="submit" class="button" name="_eventId_upload" value="Upload" />
            <c:if test="${(!empty user.name) && user.editAssignmentsRight}">
              <input type="submit" class="button" name="_eventId_save" value="Save" />
            </c:if>
            <input type="submit" class="button" name="_eventId_finish" value="Exit" />
            <br> <form:checkbox path="decorateWithLatex" /> Decorate assignment constructions with LaTeX
          </td>
          <td></td>
        </tr>

      </table>
    </form:form>
  </body>

</html>
