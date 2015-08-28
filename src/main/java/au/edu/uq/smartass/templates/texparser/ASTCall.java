/* Generated By:JJTree: Do not edit this line. ASTCall.java */

package au.edu.uq.smartass.templates.texparser;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import au.edu.uq.smartass.engine.Engine;
import au.edu.uq.smartass.script.Script;
import au.edu.uq.smartass.templates.TexReader;

public class ASTCall extends SimpleNode {
	Engine engine;
	
	@Override
	public String getCode() {
		return "%%CALL " + getFilename() +"\n";
	}
	
	@Override
	public ResultNode execute(Set<String> sections, Script script) {
		TexReader tr = new TexReader(engine, true);
		try {
			Map<String, String> result;
			tr.loadTemplate(engine.getTemplateStream(getFilename()));
	   		result = tr.execute();
	   		sections.addAll(result.keySet());
	   		return new RCall(this, result);
		} catch(Exception e) {
			//any exception during template reader execution will result  
			//error_str of RCall set to error description
			if(e.getMessage()==null)
				return new RCall(this, e.toString()); //if exception does not contain message do fallback to its toString() value
			return new RCall(this, e.getMessage()); 
		}
	}
	
	public void setEngine(Engine engine) {
		this.engine = engine;
	}
	
	public ASTCall(int id) {
		super(id);
	}
	
	public ASTCall(TexParser p, int id) {
		super(p, id);
	}

	public String getFilename() {
		return text;
	}

	public void setFilename(String filename) {
		this.text = filename;
	}

	public Iterator<SimpleNode>  iterateResultNodes() {
		return null;
	}
}
