<%@page contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <%@include file="../jsp/header.jsp.inc" %>
    <title>Smart Assignments Repository | Add New Template | Step 2: Review Metadata</title> 
</head>
<body>
    <form:form modelAttribute="premeta">   
        <table>
            <tr class="header"><td colspan=3>
                <h2>Step 2: Review Metadata</h2> 
            </td></tr>
            <c:if test="${empty premeta}">
                <tr>
                    <td colspan=2><b>No metadata found in this template! All metadata-related steps will be skipped.</b></td>
                    <input style="width:125" type="submit" class="button" name="_eventId_skip" value="Next"/>
                </tr>
                </table>
                <br>
            </c:if>
            <c:if test="${!empty premeta}">
 
                <tr>
                    <td><b>* Template name:</b></td>
                    <td><form:input size="70" path="name"/></td>
                </tr>
                <tr>
                    <td><b>Keywords:</b></td>
                    <td><form:input size="70" path="keywords"/></td>
                </tr>
                <tr>
                    <td><b>Created:</b></td>
                    <td><form:input size="70" path="dtcreated"/> (date format is yyyy-mm-dd)</td>
                </tr> 
                <tr>
                    <td><b>Description:</b></td>
                    <td><form:textarea rows="5" cols="70" path="description" /></td>
                </tr>

                <c:if test="${!empty premeta.author}">
                    <tr class="header"><td colspan=3>
                        <h3>Author</h3> 
                    </td></tr>
                    <tr>
                        <td><b>* Author's name:</b></td>
                        <td><form:input size="70" path="author[0]"/></td>
                    </tr>
                    <tr>
                        <td><b>Description:</b></td>
                        <td><form:textarea rows="4" cols="70" path="author[1]" /></td>
                    </tr>
                </c:if>

                <c:if test="${!empty premeta.modules}">
                    <tr class="header"><td colspan=3>
                        <h3>Modules</h3> 
                    </td></tr>
                    <c:set var="i" value="0"/>
                    <c:forEach items="${premeta.modules}" var="m">
                        <c:if test="${(i % 2) == 1}">
                            <tr class="row-dark">
                        </c:if>
                        <c:if test="${(i % 2) == 0}">
                            <tr class="row-light">
                        </c:if>
                            <td><b>* Module:</b></td>
                            <td><form:input size="70" path="modules[${i}][0]"/></td>
                        </tr>
                        <c:if test="${(i % 2) == 1}">
                            <tr class="row-dark">
                        </c:if>
                        <c:if test="${(i % 2) == 0}">
                            <tr class="row-light">
                        </c:if>
                            <td><b>Parameters:</b></td>
                            <td><form:textarea rows="4" cols="70" path="modules[${i}][3]" /></td>
                        </tr>
                        <c:if test="${(i % 2) == 1}">
                            <tr class="row-dark">
                        </c:if>
                        <c:if test="${(i % 2) == 0}">
                            <tr class="row-light">
                        </c:if>
                            <td><b>Description:</b></td>
                            <td><form:textarea rows="4" cols="70" path="modules[${i}][2]" /></td>
                        </tr>
                        <c:set var="i" value="${i+1}"/>
                    </c:forEach>
                </c:if>

                <c:if test="${!empty premeta.files}">
                    <tr class="header"><td colspan=3>
                        <h3>Files</h3> 
                    </td></tr>
                    <c:set var="i" value="0"/>
                    <c:forEach items="${premeta.files}" var="m">
                        <c:if test="${(i % 2) == 1}">
                            <tr class="row-dark">
                        </c:if>
                        <c:if test="${(i % 2) == 0}">
                            <tr class="row-light">
                        </c:if>
                            <td><b>* File name:</b></td>
                            <td><form:input size="70" path="files[${i}][0]"/></td>
                        </tr>
                        <c:if test="${(i % 2) == 1}">
                            <tr class="row-dark">
                        </c:if>
                        <c:if test="${(i % 2) == 0}">
                            <tr class="row-light">
                        </c:if>
                            <td><b>Description:</b></td>
                            <td><form:textarea rows="4" cols="70" path="files[${i}][1]" /></td>
                        </tr>
                        <c:set var="i" value="${i+1}"/>
                    </c:forEach>
                </c:if>

                <c:if test="${!empty premeta.updates}">
                    <tr class="header"><td colspan=3>
                        <h3>Updates</h3> 
                    </td></tr>
                    <c:set var="i" value="0"/>
                    <c:forEach items="${premeta.updates}" var="m">
                        <c:if test="${(i % 2) == 1}">
                            <tr class="row-dark">
                        </c:if>
                        <c:if test="${(i % 2) == 0}">
                            <tr class="row-light">
                        </c:if>
                            <td><b>* Date:</b></td>
                            <td><form:input path="updates[${i}][0]"/></td>
                        </tr> 
                        <c:if test="${(i % 2) == 1}">
                            <tr class="row-dark">
                        </c:if> 
                        <c:if test="${(i % 2) == 0}">
                            <tr class="row-light">
                        </c:if>
                            <td><b>* Author:</b></td>
                            <td><form:input size="70" path="updates[${i}][1]"/></td>
                        </tr>
                        <c:if test="${(i % 2) == 1}">
                            <tr class="row-dark">
                        </c:if>
                        <c:if test="${(i % 2) == 0}">
                            <tr class="row-light">
                        </c:if>
                            <td><b>Description:</b></td>
                            <td><form:textarea rows="4" cols="70" path="updates[${i}][2]" /></td>
                        </tr> 
                        <c:set var="i" value="${i+1}"/>

                    </c:forEach>
                </c:if>

                <c:if test="${!empty premeta.updAuthors}">
                    <tr class="header"><td colspan=3>
                        <h3>Update's authors</h3> 
                    </td></tr>
                    <c:set var="i" value="0"/>
                    <c:forEach items="${premeta.updAuthors}" var="m">
                        <c:if test="${(i % 2) == 1}">
                            <tr class="row-dark">
                        </c:if>
                        <c:if test="${(i % 2) == 0}">
                            <tr class="row-light">
                        </c:if>
                            <td><b>* Author's name:</b></td>
                            <td><form:input path="updAuthors[${i}][0]"/></td>
                        </tr>
                        <c:if test="${(i % 2) == 1}">
                            <tr class="row-dark">
                        </c:if>
                        <c:if test="${(i % 2) == 0}">
                            <tr class="row-light">
                        </c:if>
                            <td><b>Description:</b></td>
                            <td><form:textarea rows="4" cols="70" path="updAuthors[${i}][1]" /></td>
                        </tr>
                        <c:set var="i" value="${i+1}"/>
                    </c:forEach>
                    
                </c:if>
<!-- 
-->

                <tr><td>
                </td></tr>
                <tr><td colspan=3>
                    <i>* Required fields</i>
                </td></tr>
            </table>
            <br>
    
            <input style="width:125" type="submit" class="button" name="_eventId_ok" value="Next"/>
            </c:if>

        <input style="width:125" type="button" class="button" value="Back" onclick="history.go(-1);"/>
        <input style="width:125" type="submit" class="button" name="_eventId_cancel" value="Cancel"/>
    </form:form>
</body></html>
