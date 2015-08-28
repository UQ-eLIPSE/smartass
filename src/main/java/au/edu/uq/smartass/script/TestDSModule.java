package au.edu.uq.smartass.script;

import au.edu.uq.smartass.engine.Engine;
import au.edu.uq.smartass.maths.MathsModule;

public class TestDSModule extends MathsModule {
	String[] params;

	public TestDSModule(Engine engine) {
		super(engine);
		params = new String[]{};
	}

	public TestDSModule(Engine engine, String[] params) {
		super(engine, params);
		this.params = params;
	}
	
	@Override
	public String getSection(String name) {
		String s = "";
		for(int i=0;i<params.length;i++) {
			s = s + params[i] + " ";
			System.out.println("param["+Integer.toString(i) + "]:" + params[i]);
		}
		return s;
	}

}
