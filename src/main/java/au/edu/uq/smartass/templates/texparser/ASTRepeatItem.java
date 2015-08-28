package au.edu.uq.smartass.templates.texparser;

import java.util.Set;

import au.edu.uq.smartass.script.Script;

public class ASTRepeatItem extends ComplexNode {

	public ASTRepeatItem() {
		super(0);
	}
	
	@Override
	public RComplexNode execute(Set<String> sections, Script script) {
		return executeChildren(new RComplexNode(this, "RepeatItem"), sections, script);
	}

}
