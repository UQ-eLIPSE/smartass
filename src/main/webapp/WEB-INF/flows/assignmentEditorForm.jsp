<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>

  <head>
    <%@include file="../jsp/header.jsp.inc" %>
    <title>Smart Assignments | Assignment Editor</title>


    <style type='text/css'>
        table.clear { border: 0px; padding: 0px }
        td.assignment-selector {padding-bottom: 5px;}
        
        #titleIndicator {
            font-size: x-large;
        }

        .spin-anim {
            animation: spin 1000ms infinite linear;
        }

        @keyframes spin {
            from {
                transform: rotate(0deg);
            }

            to {
                transform: rotate(359deg);
            }
        }
    </style>

  </head>


  <body onload="setControlsState('<c:out value="${template.selectedRow.kind}"/>', '<c:out value="${template.selectedRow.parent.kind}"/>')">
     <div class="container">

        <h2>Assignment Editor</h2>

        <form:form modelAttribute="template">

          <div class="form-group has-feedback">
              <label for="assignmentTitle">Assignment Title:</label>
              <input type="text" value="${template.assignmentTitle}" class="form-control" name="assignmentTitle" id="assignmentTitle">
              <i class="glyphicon glyphicon-remove form-control-feedback" id="titleIndicator"></i>
          </div>

            <div class="form-group">
              <table width="100%">


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
                    <table class='question-list clear'>
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
                  <td class="question-list-btns">
                    <!-- <button type="submit" class="btn" name="_eventId_view" value="View code">View Code</button> -->
                    <!-- <button type="button" class="btn" name="download" value="Download"
                      onclick="window.location.href = 'download-assignment.htm?execution=<c:out value="${flowExecutionKey}"/>'">Download</button> -->
                    <c:if test="${!canExecute}">
                      <button type="submit" class="btn" name="_eventId_execute" value="Execute" disabled="disabled">Execute</button>
                    </c:if>
                    <c:if test="${canExecute}">
                      <button type="submit" class="btn background-secondary-color" name="_eventId_execute" value="Execute">Execute</button>
                    </c:if>
                    <button style="width:125" type="submit" class="btn" name="_eventId_clear" value="New assignment">New Assignment</button>
                    <!-- <button type="submit" class="btn" name="_eventId_upload" value="Upload">Upload</button> -->
                    <c:if test="${(!empty user.name) && user.editAssignmentsRight}">
                      <button type="submit" class="btn" name="_eventId_save" value="Save">Save</button>
                    </c:if>
                    <button type="submit" class="btn" name="_eventId_finish" value="Exit">Exit</button>
                    <!-- <br> <form:checkbox path="decorateWithLatex" /> Decorate assignment constructions with LaTeX -->
                  </td>
                  <td></td>
                </tr>
              </table>
            </div>
        </form:form>
        </div>

        <script src="/js/assignment_title.js"></script>

        <script type="text/javascript">
            function setControlsState(kind, parent_kind) {
                document.getElementById("addRepeat").disabled = ('section'==kind || 'section'==parent_kind);
                document.getElementById("addQuestion").disabled = ('section'==kind || 'section'==parent_kind);
                document.getElementById("edit").disabled = ('call'==kind || ''==kind || 'repeat-end'==kind);
                document.getElementById("delete").disabled = ('repeat-end'==kind || 'section'==kind || 'section-end'==kind);
            }

            // Used in the assignment title class
            var postUrl = window.location.origin + "${flowExecutionUrl}";
            assignmentTitle.init(postUrl);

        </script>
    <script src="/js/process_question_list.js"></script>
    <%@include file="../jsp/footer.jsp.inc" %>
  </body>

</html>
