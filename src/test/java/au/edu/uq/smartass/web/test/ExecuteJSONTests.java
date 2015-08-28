package au.edu.uq.smartass.web.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hp.hpl.jena.util.FileManager;

public class ExecuteJSONTests extends ReadPageTest {
	JSONArray tests;
	JSONObject variables;

	class VariableNotFoundException extends RuntimeException {
		String varName;
		
		public VariableNotFoundException(String varName) {
			this.varName = varName;
		}
		
		@Override
		public String toString() {
			return "Variable not found: " + varName;
		}
	}
	
	public ExecuteJSONTests(String name, JSONObject tests, JSONObject variables, JSONObject globals) throws JSONException {
		super(name);
		this.tests = tests.getJSONArray("tests");
		initVars(variables, globals);
	}

	public ExecuteJSONTests(String name, HeadlessHtmlRendererContext rcontext, 
			JSONObject tests, JSONObject variables, JSONObject globals) throws JSONException {
		super(name, rcontext, 0);
		this.tests = tests.getJSONArray("tests");
		initVars(variables, globals);
	}
	
	private void initVars(JSONObject variables, JSONObject globals) throws JSONException {
		this.variables = resolveVariables(variables, globals);
		Iterator<String> keys = globals.keys();
		try {
			while(keys.hasNext()) {
				String key = keys.next();
				this.variables.put(key, globals.getString(key));
			}
		} catch(JSONException e) {}
	}
	
	
	private JSONObject resolveVariables(JSONObject obj, JSONObject vars) throws JSONException {
		JSONObject resolved = new JSONObject();
		Iterator<String> keys = obj.keys(); 
		while(keys.hasNext()) {
			String key = keys.next();
			String value = obj.getString(key);
			if(value.charAt(0)=='$') //variable
				try {
					value = vars.getString(value);
				} catch(JSONException e) {
					throw new VariableNotFoundException(value);
				}
			resolved.put(key, value);
		}
		return resolved;
	}

	@Override
	protected void execute() {
		int good = 0, bad = 0;
		for(int i=0; i<tests.length(); i++) 
			try {
				url = composeUrl(tests.getJSONObject(i));
				System.out.print("" + (i+1) + " " + url);
				super.execute();
				System.out.print(" " + getHttpResponse());
				if(getHttpResponse()==200 || getHttpResponse()==302) {
					good++;
					System.out.println(" OK");
				} else {
					System.out.println(" FAILED");
					bad++;
				}
					
			} catch(JSONException e) {
				System.err.println("Error while composing url for the test " + i);
			}
		System.out.println("Total: " + tests.length() + ", Success: " + good + ", Failed: " + bad);
	}
	
	private String composeUrl(JSONObject test) throws JSONException {
		String url = test.getString("url");
		try {
			JSONObject resolved_params = resolveVariables(test.getJSONObject("parameters"), variables);
			Iterator<String> keys = resolved_params.keys();
			String params = "";
			while(keys.hasNext()) {
				String key = keys.next();
				if(params.length()>0)
					params = params + "&";
				params = params + key + "=" + resolved_params.getString(key);
			}
			url = url + "?" + params;
		} catch (JSONException e) {} //it's completely valid if url has no parameters
		return url;
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws JSONException 
	 */
	public static void main(String[] args) throws IOException, JSONException {
		File json_file = new File(args[0]);
		byte[] buff = new byte[1000];
		StringBuffer sb = new StringBuffer();
		FileInputStream fs = new FileInputStream(json_file);
		try {
			while(fs.read(buff)>0)
				sb.append(new String(buff));
		} finally {
			fs.close();
		}
		JSONObject tests = new JSONObject(sb.toString());
		
		sb.delete(0, sb.length());
		fs = new FileInputStream(new File(json_file.getParentFile(), tests.getString("variables")));
		try {
			while(fs.read(buff)>0)
				sb.append(new String(buff));
		} finally {
			fs.close();
		}
		JSONObject vars = new JSONObject(sb.toString());
		
		sb.delete(0, sb.length());
		fs = new FileInputStream(new File(json_file.getParentFile(), tests.getString("globals")));
		try {
			while(fs.read(buff)>0)
				sb.append(new String(buff));
		} finally {
			fs.close();
		}
		JSONObject globs = new JSONObject(sb.toString());
		
		Logger logger = Logger.getLogger(HeadlessHtmlRendererContext.class.getName());
		logger.setLevel(Level.OFF);
		ExecuteJSONTests exec = new ExecuteJSONTests("validator", tests, vars, globs);
		exec.run();
	}


}
