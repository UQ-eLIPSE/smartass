package au.edu.uq.smartass.web.composer;

import org.springframework.webflow.execution.Action;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;


public class SaveAssignmentAction implements Action {
	public Event execute(RequestContext context) throws Exception {
		
		return new Event(this, "ok");
	}

}
