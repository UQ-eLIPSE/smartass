<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
	<%@include file="../jsp/header.jsp.inc" %>
	<script type="text/javascript">
            function onLoad() {
                if (document.getElementById("edRepeatsNum").value > 0) {
                    document.getElementById("btOk").disabled = false;
                } else {
                    document.getElementById("btOk").disabled = true;
                }
            }

            function onChangeRepeatsNum(e) {
                if (e.which) {
                    keynum = e.which;
                }

                keychar = String.fromCharCode(keynum);

                if (document.getElementById("edRepeatsNum").value>0 || keychar!="0") {
                    document.getElementById("btOk").disabled = false;
                } else {
                    document.getElementById("btOk").disabled = true;
                }
            }
	</script>
</head>
<body onload="onLoad()">
    <div class="container">
        <form:form method="post" modelAttribute="item">
            <div class="form-group">
                <label>Number of repeats</label>
                <form:input cssClass="form-control" id="edRepeatsNum" path="repeatsNum" onkeypress="onChangeRepeatsNum(event)" onchange="onChangeRepeatsNum()" />
            </div>

                <c:if test="${isNew}">
                    <c:set var="i" value="0"/>
                    <c:forEach items="${item.addons}" var="it">
                        <div class="form-group">
                            <label><c:out value="${it.title}"/></label>
                            <form:textarea cssClass="form-control" path="addons[${i}].value" cols="80" rows="5"/>
                            <c:set var="i" value="${i+1}"/>
                        </div>
                    </c:forEach>
                </c:if>
                <div class="pull-right">
                    <input style="width:125" type="submit" class="btn background-secondary-color" id="btOk" name="_eventId_ok" value="OK"/>
                    <input style="width:125" type="submit" class="btn" name="_eventId_cancel" value="Cancel"/>
                </div>
        </form:form>
	</div>
</body>
</html>
