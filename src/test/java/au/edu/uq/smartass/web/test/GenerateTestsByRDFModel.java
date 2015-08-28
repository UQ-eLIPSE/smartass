package au.edu.uq.smartass.web.test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.xml.SimplePropertyNamespaceHandler;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Selector;
import com.hp.hpl.jena.rdf.model.SimpleSelector;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.util.FileManager;

public class GenerateTestsByRDFModel  {
	//static final String baseUri = "http://localhost:8180/smartass/meta/";
	
	Property parameterClass;
	Property nameProperty;
	Property requiredProperty;
	Property typeProperty;
	String baseUri;
	String metaUri;
	Model model;
	List<Page> pages;
	Map<String, String> vars;
	JSONArray tests;

	public class PageParameter {
		String name;
		String type;
		boolean required;
		Page page;
		
		public PageParameter(String name, String type, boolean required) {
			this.name = name;
			this.type = type;
			this.required = required;
		}
		
		public PageParameter(Resource rdf) {
			name = rdf.getProperty(nameProperty).getString();
			type = rdf.getProperty(typeProperty).getResource().getLocalName();
			required = rdf.getProperty(requiredProperty).getBoolean();
		}
	}
	
	static public class Page {
		String name;
		List<PageParameter> parameters;
		
		public Page(String name) {
			this.name = name;
			parameters = new ArrayList<PageParameter>();
		}
		
		public void addParameter(PageParameter par) {
			parameters.add(par);
			par.page = this;
		}
	}
	

	public GenerateTestsByRDFModel(Model model) {
		this.model = model;
		baseUri = model.getNsPrefixURI("p");
		metaUri = baseUri + "meta/";

		parameterClass = model.createProperty(metaUri+"hasParameter");
		nameProperty = model.createProperty(metaUri+"name");
		requiredProperty = model.createProperty(metaUri+"required");
		typeProperty = model.createProperty(metaUri+"type");

		vars = new HashMap<String, String>();
	}
	
	public void generateTestCases() throws JSONException {
		String qs = 
			"PREFIX rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n " +
			"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n" +
			"PREFIX p: <" + baseUri + "> \n" +
			"PREFIX : <" + metaUri + "> \n" +
			"SELECT ?page \n" +
			"WHERE { \n" +
			"?page rdf:type :Page .\n" +
			"}"
			;
		Query query = QueryFactory.create(qs);
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		try {
			pages = new ArrayList<Page>();
			ResultSet results = qe.execSelect();
			while(results.hasNext()) {
				QuerySolution solution = (QuerySolution) results.next();
				Resource ppage = solution.getResource("page");
				System.out.println(ppage.getLocalName());
				Page page = new Page(ppage.getLocalName()); 
				StmtIterator iter = ppage.listProperties(parameterClass);
				while(iter.hasNext()) {
					Statement pparam = (Statement) iter.next();
					PageParameter param = new PageParameter(pparam.getResource());
					System.out.println(
							param.name + " " +
							param.type + " " +
							param.required
							);
					page.addParameter(param);
					pages.add(page);
				}
			}
//			ResultSetFormatter.out(System.out, results, query);
		} finally {
			qe.close();
		}

		tests = new JSONArray();
		for(Page p: pages) 
			addTests(p, baseUri);
		JSONObject jsvars = new JSONObject(vars); 
		System.out.println(tests.toString());
		System.out.println(vars.toString());
	}

	private ResultSet queryTestCases(Page page, List<PageParameter> params) {
		String qs = 
			"PREFIX rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n " +
			"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n" +
			"PREFIX p: <" + baseUri + "> \n" +
			"PREFIX : <" + metaUri + "> \n" +
			"SELECT ?test \n" +
			"WHERE { \n" +
			"?test :page \"" + page.name + "\".\n" ;
		for(PageParameter p: params) {
			qs = qs + "?test :hasParameter ?par .\n" +
					"?par :name \"" + p.name + "\".\n";
		}
		return null;
	}
	
	private void addTests(Page page, String baseUrl) throws JSONException {

		//split page parameters to required and optional parts
		List<PageParameter> required = new ArrayList<PageParameter>();
		List<PageParameter> optional = new ArrayList<PageParameter>();
		for(PageParameter par: page.parameters) 
			if(par.required)
				required.add(par);
			else
				optional.add(par);
		
		Map<String, String> srequired = new HashMap<String, String>();

		//test for URL without parameters
		JSONObject test = new JSONObject();
		test.put("page", page.name);
		test.put("url", baseUrl+page.name+".htm");
		tests.put(test);
		if(required.size()>0) {
			test.put("result", "ERROR"); //if there are required parameters, test result should be an error  

			//create a set of erroneous test with all valid but one missed parameters
			if(required.size()>1)  
				for(PageParameter par: required) {
					Map<String, String> pars = new HashMap<String, String>();
					for(PageParameter par1: required)
						if(par1!=par)
							pars.put(par1.name, composeValidValueVar(par1));
					tests.put(createJSONTest(page.name, "ERROR", pars));

					srequired.put(par.name, composeValidValueVar(par));
				}
			else
				srequired .put(required.get(0).name, composeValidValueVar(required.get(0)));

			//minimal valid parameters set
			tests.put(createJSONTest(page.name, "OK", srequired));
		} else
			test.put("result", "OK"); //otherwise result should be OK

		for(PageParameter par : page.parameters) {
			Map<String, String> params = new HashMap<String, String>();
			params.putAll(srequired);

			if(par.type.equals("Integer") || par.type.equals("Id")) {
				params.put(par.name, createIntegerValue(par, -1));
				tests.put(createJSONTest(page.name, "SOFTERR", params));
				params.put(par.name, createIntegerValue(par, 0));
				tests.put(createJSONTest(page.name, "SOFTERR", params));
				if(par.type.equals("Id")) {
					params.put(par.name, composeAbsentIdVar(par));
					tests.put(createJSONTest(page.name, "SOFTERR", params));
				}
				params.put(par.name, "$ENORMOUS_INTEGER");
				tests.put(createJSONTest(page.name, "SOFTERR", params));
				params.put(par.name, "$STRING_IN_INTEGER");
				tests.put(createJSONTest(page.name, "ERROR", params));
			}  
			params.put(par.name, composeValidValueVar(par));
			tests.put(createJSONTest(page.name, "OK", params));
		}
	}
	
	private JSONObject createJSONTest(String pageName, String result, Map<String, String> params) throws JSONException {
		JSONObject test = new JSONObject();
		test.put("page", pageName);
		test.put("url", baseUri+pageName+".htm");
		test.put("result", "OK");   
		test.put("parameters", new JSONObject(params));
		return test;
	}
	
	private String composeValidValueVar(PageParameter parameter) {
		String par_var = "$VALID__"+parameter.page.name+"__"+parameter.name;
		if(vars.get(par_var)==null)
			if(parameter.type.equals("Integer"))
				vars.put(par_var, "$VALID_INTEGER");
			else
				vars.put(par_var, "$VALID_STRING");
		return par_var;
	}
	
	private String composeAbsentIdVar(PageParameter parameter) {
		String par_var = "$ABSENT__" + parameter.page.name + "__" + parameter.name;
		if(vars.get(par_var)==null)
			vars.put(par_var, "$VALID_ID");
		return par_var;
	}
	
	private String createIntegerValue(PageParameter parameter, int value) {
		return Integer.toString(value);
	}
	
	public void saveTests(File path) throws JSONException, IOException {
		String name = path.getName();
		String vars_name = name + "_variables";
		String globals_name = name + "_globals";
		System.out.println(path.toString());
		File dir = path.getParentFile();

		JSONObject t = new JSONObject();
		t.put("tests", tests);
		t.put("variables", vars_name);
		t.put("globals", globals_name);
		path.createNewFile();
		FileOutputStream out = new FileOutputStream(path);
		try {
			out.write(t.toString(1).getBytes());
		} finally {
			out.close();
		}
		
		Set<String> newkeys = vars.keySet();
		File vars_file = new File(dir, vars_name);
		try {
			InputStream in = new FileInputStream(vars_file);
			BufferedInputStream bin = new BufferedInputStream(in);
			StringBuffer sb = new StringBuffer();
			try {
				byte[] buff = new byte[1024];
				int count = 0;
				while((count=bin.read(buff))>0)
					sb.append(new String(buff));
			} finally {
				bin.close();
			}
			t = new JSONObject(sb.toString());
			Iterator<String> keys = t.keys();
			while(keys.hasNext()) {
				String key = keys.next();
				newkeys.remove(key);
			}
		} catch (FileNotFoundException e) {	
			t = new JSONObject();
		}

		for(String k: newkeys)
			t.put(k, vars.get(k));
		
		out = new FileOutputStream(vars_file);
		try {
			out.write(t.toString(1).getBytes());
		} finally {
			out.close();
		}
		
		
	}
	

	/**
	 * @param args
	 * @throws JSONException 
	 */
	public static void main(String[] args) {
		InputStream in = FileManager.get().open(args[0]);
		Model model = ModelFactory.createDefaultModel();
		model.read(in, null, "N3");
		in = FileManager.get().open(args[1]);
		Model data_model = ModelFactory.createDefaultModel();;
		data_model.read(in, null, "N3");
		model.add(data_model);
		System.out.println(model.toString());
		
		GenerateTestsByRDFModel test = new GenerateTestsByRDFModel(model);
		try {
			test.generateTestCases();
			test.saveTests(new File(args[2]));
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
