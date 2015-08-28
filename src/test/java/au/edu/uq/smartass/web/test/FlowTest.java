package au.edu.uq.smartass.web.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.json.JSONException;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.util.FileManager;

public class FlowTest extends ReadPageTest {
	String baseUri;
	String metaUri;
	String flowUri;

	Model model;

	Property parameterProperty;
	Property nameProperty;
	Property requiredProperty;
	Property typeProperty;
	Property flowClass;
	Property flowState;
	Property transitionProperty;
	Property stateProperty;

	public FlowTest(Model model) {
		super("FlowTest");
		this.model = model;
		baseUri = model.getNsPrefixURI("p");
		metaUri = baseUri + "meta/";
		flowUri = baseUri + "flow/";

		parameterProperty = model.createProperty(metaUri+"hasParameter");
		nameProperty = model.createProperty(metaUri+"name");
		requiredProperty = model.createProperty(metaUri+"required");
		typeProperty = model.createProperty(metaUri+"type");
		flowClass = model.createProperty(metaUri+"flowClass");
		transitionProperty = model.createProperty(metaUri+"type");
		stateProperty = model.createProperty(metaUri+"type");
	}

	public void processFlow(String flowName) {
		
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
		
		FlowTest test = new FlowTest(model);
		
//		try {
//			test.generateTestCases();
//			test.saveTests(new File(args[2]));
//		} catch (JSONException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
