<tr class="pages"><td colspan=2>
	Pages:
	<c:forEach begin="1" end="${page_num}" var="i">
		<c:if test="${i==page_no}">[<c:out value="${i}"/>]</c:if>
		<c:if test="${i!=page_no}">
			<a href="<c:out value="${page_url}page=${i}" escapeXml="false"/>">[<c:out value="${i}"/>]</a>
		</c:if>
	</c:forEach>
</td></tr>
