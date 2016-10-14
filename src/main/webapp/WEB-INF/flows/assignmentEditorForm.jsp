<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>

  <head>
    <%@include file="../jsp/header.jsp.inc" %>
    <title>Smart Assignments | Assignment Editor</title>

    <script type="text/javascript">
      	function setControlsState(kind, parent_kind) {
            document.getElementById("addRepeat").disabled = ('section'==kind || 'section'==parent_kind);
            document.getElementById("addQuestion").disabled = ('section'==kind || 'section'==parent_kind);
	    document.getElementById("edit").disabled = ('call'==kind || ''==kind);
        }
    </script>

    <style type='text/css'>
	table.clear { border: 0px; padding: 0px }
	td.assignment-selector { background-color: #f0f0f0 }
	td.assignment-content { background-color: #f0f0ff }
    </style>

  </head>

  <body onload="setControlsState('<c:out value="${template.selectedRow.kind}"/>', '<c:out value="${template.selectedRow.parent.kind}"/>')">
     <div class="container">
        <form:form modelAttribute="template">
            <div class="form-group">
              <table width="100%">

                <tr>
                  <td class="header" colspan="2"><h2>Assignment Editor</h2></td>
                </tr>

                <tr>
                  <td class="header" width="10px">
                    <br>
                    <input id="addText" style="width:125" type="submit" class="form-control" name="_eventId_addText" value="Add Text"/><br>
                    <input id="addQuestion" style="width:125" type="submit" class="form-control" name="_eventId_addCall" value="Add Question"/><br>
                    <hr>
                    <input id="addRepeat" style="width:125" type="submit" class="form-control" name="_eventId_addRepeat" value="Add Repeat"/><br>
                    <hr>
                    <input id="edit" style="width:125" type="submit" class="form-control" name="_eventId_edit" value="Edit"/><br><hr>
                    <input id="delete" style="width:125" type="submit" class="form-control" name="_eventId_delete" value="Delete"/><br>
                    <input id="clear" style="width:125" type="submit" class="form-control" name="_eventId_clear" value="Delete All"/ ><br>
                    <!-- <button type="submit" class="btn" name="_eventId_clear" value="Clear">Delete All</button> -->
                  </td>
                  <td valign="top" colspan="2">

                    <!--
                            . Represent Assignments Constituent Components .
                      -->
                    <table class='clear'>
                      <c:forEach items='${template.rows}' var='item'>
                        <c:if test="${item.visible}">
                          <tr>
                            <td class='assignment-selector'>
                              <form:radiobutton path='selectedIndex' value='${item.index}' onclick='setControlsState("${item.kind}", "${item.parent.kind}")' />
                            </td>
                            <td>
                              <table class='clear'>
                                <tr>
                                  <td class='assignment-content'>
                                    <c:if test="${item.kind == 'text'}"><pre></c:if>
                                      <c:out value="${item.html}" />
                                    <c:if test="${lastrow.kind == 'text'}"></pre></c:if>
                                  </td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                        </c:if>
                      </c:forEach>
                    </table>
                    <!--     _____     -->

                  </td>
                </tr>

                <tr class="header">
                  <td></td>
                  <td>
                    <!-- <button type="submit" class="btn" name="_eventId_view" value="View code">View Code</button> -->
                    <!-- <button type="button" class="btn" name="download" value="Download"
                      onclick="window.location.href = 'download-assignment.htm?execution=<c:out value="${flowExecutionKey}"/>'">Download</button> -->
                    <c:if test="${!canExecute}">
                      <button type="submit" class="btn" name="_eventId_execute" value="Execute" disabled="disabled">Execute</button>
                    </c:if>
                    <c:if test="${canExecute}">
                      <button type="submit" class="btn" name="_eventId_execute" value="Execute">Execute</button>
                    </c:if>
                    <button style="width:125" type="submit" class="btn" name="_eventId_new" value="New assignment">New Assignment</button>
                    <!-- <button type="submit" class="btn" name="_eventId_upload" value="Upload">Upload</button> -->
                    <c:if test="${(!empty user.name) && user.editAssignmentsRight}">
                      <button type="submit" class="btn" name="_eventId_save" value="Save">Save</button>
                    </c:if>
                    <button type="submit" class="btn" name="_eventId_finish" value="Exit">Exit</button>
                    <br> <form:checkbox path="decorateWithLatex" /> Decorate assignment constructions with LaTeX
                  </td>
                  <td></td>
                </tr>
              </table>
            </div>
        </form:form>
        </div>
  </body>

</html>
