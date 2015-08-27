				<c:if test="${mode=='edit'}">
					<c:set var="page_url" value="repository-template-edit.htm?_eventId_page=1&execution=${flowExecutionKey}&"/>
				</c:if>
				<c:if test="${mode=='new'}">
					<c:set var="page_url" value="repository-template-new.htm?_eventId_page=1&execution=${flowExecutionKey}&"/>
				</c:if>
				<c:if test="${item.pageNum>0}">
					<tr class="pages"><td colspan=2>
						Pages:
						<c:forEach begin="0" end="${item.pageNum}" var="i">
							<c:if test="${i==item.pageNo}">[<c:out value="${i+1}"/>]</c:if>
							<c:if test="${i!=item.pageNo}">
								<a href="<c:out value="${page_url}pageNo=${i}" escapeXml="false"/>">[<c:out value="${i+1}"/>]</a>
							</c:if>
						</c:forEach>
					</td></tr>
				</c:if>

