package au.edu.uq.smartass.web.composer;

import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

public class EditSelectedAction {
	public Event execute(AssignmentConstruct template) throws Exception {
//		AbstractTemplateConstruction row = template.getRow(template.getSelectedIndex());
//		String kind = row.getKind();
//		if(kind.equals("text") || kind.equals("repeat") || kind.equals("multi"))
//			;		
//		else
//			return new Event(this, "cancel");
////		context.getFlowScope().put(row.getKind(), row);
//		return new Event(this, row.getKind());
		return new Event(this, "cancel");
	}
}
