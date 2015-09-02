	    <table class="menu"><tr class="menu">
	    	<td> <a href="index.htm">[home]</a></td>
	    	<td class="selected-menu">[repository]</td>
	    	<c:if test="${empty user}">
		    	<td><a href="login.htm">[log in]</a></td>
		    	<td><a href="register.htm">[register]</a></td>
		    	<td><a href="help.htm?context=repo" target="_blank">[help]</a></td>
		    </c:if>
	    	<c:if test="${!empty user}">
		    	<td><a href="edituser.htm">[user settings]</a></td>
		    	<td><a href="logout.htm">[log out]</a></td>
		    	<td><a href="help.htm?context=repo" target="_blank">[help]</a></td>
		    	<td>
		    		Logged in as: <b><c:out value="${user.name}"/>
		    		<c:if test="${!empty user.fullname}">
		    			(<c:out value="${user.fullname}"/>)</b>
		    		</c:if>
		    	</td>
		    </c:if>
	    </tr><tr class="selected-menu"><td class="selected-menu" colspan=5></td></tr></table>
