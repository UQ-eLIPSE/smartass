<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        } else {
          document.getElementById("addRepeat").disabled = false;
          document.getElementById("addCall").disabled = false;
          
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
            <input id="addCall" style="width:125" type="submit" class="button" name="_eventId_addCall" value="Add QUESTION"/><br>
            <hr>
            <input id="addRepeat" style="width:125" type="submit" class="button" name="_eventId_addRepeat" value="Add REPEAT"/><br>
            <hr>
            <input id="edit" style="width:125" type="submit" class="button" name="_eventId_edit" value="Edit"/><br><hr>
            <input id="delete" style="width:125" type="submit" class="button" name="_eventId_delete" value="Delete"/><br>
          </td>
          <td valign="top" colspan="2">

            <!--
                    . Represent Assignments Constituent Components .
              -->
            <table class='clear' border=0 frame=0 cellpadding=0>
              <c:forEach items='${template.rows}' var='item' varStatus='status'>
                <tr>
                  <td class='assignment' bgcolor='#f0f0f0'>
                    <form:radiobutton path='selectedIndex' value='${status.index}' onclick='setControlsState("${item.kind}", "${item.parent.kind}")' />
                  </td>
                  <td>
                    <table class='clear' border=0 frame=0 cellspacing=0>
                      <tr>
                        <td class='assignment' bgcolor="#f0f0ff" >
                          <c:if test="${item.kind == 'text'}"><pre></c:if>
                            <c:out value="${item.html}" />
                          <c:if test="${lastrow.kind == 'text'}"></pre></c:if>
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
              </c:forEach>
            </table>
            <!--     _____     -->

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
