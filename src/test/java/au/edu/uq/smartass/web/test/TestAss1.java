package au.edu.uq.smartass.web.test;

import java.util.logging.Logger;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.lobobrowser.html.FormInput;
import org.lobobrowser.html.UserAgentContext;
import org.lobobrowser.html.domimpl.HTMLDocumentImpl;
import org.lobobrowser.html.domimpl.HTMLFormElementImpl;
import org.lobobrowser.html.domimpl.HTMLInputElementImpl;
import org.w3c.dom.NodeList;

public class TestAss1 {

	private static final String LOGIN_LOCATION = "http://localhost:8180/smartass/login.htm";
	private static final String EDIT_LOCATION = "http://localhost:8180/smartass/repository-file.htm?id=25";
	private static final String EDIT_FORM_XPATH = "//form[@id='item']";
	private static final Logger logger = Logger.getLogger(TestAss1.class.getName());
	
	public static void main(String[] args) throws Exception {
		UserAgentContext uacontext = new LocalUserAgentContext();
		HeadlessHtmlRendererContext rcontext = new HeadlessHtmlRendererContext(uacontext);

		// First, we navigate to the starting location.
		rcontext.navigate(LOGIN_LOCATION);
		HTMLDocumentImpl startingDoc = rcontext.getCurrentDocument();
		if(startingDoc == null) throw new IllegalStateException("No document available for startup location.");
		XPath xpath = XPathFactory.newInstance().newXPath();
		NodeList nodeList = (NodeList) xpath.evaluate("//form[@id='userLogin']", startingDoc, XPathConstants.NODESET);
		if(nodeList.getLength() == 0) throw new IllegalStateException("Login form not found in the page.");

		HTMLInputElementImpl textInput = (HTMLInputElementImpl) startingDoc.getElementById("name");
		if(textInput == null) throw new IllegalStateException("Did not find a text field named 'name'.");
		textInput.setValue("admin");
		textInput = (HTMLInputElementImpl) startingDoc.getElementById("password");
		if(textInput == null) throw new IllegalStateException("Did not find a text field named 'name'.");
		textInput.setValue("kuku");

		HTMLFormElementImpl form = (HTMLFormElementImpl) nodeList.item(0);
		form.submit(new FormInput[] { });
		HTMLDocumentImpl homePage = rcontext.getCurrentDocument();
		
		for(int i=0;i<500;i++) {
			System.out.println("loop "+i);
			rcontext.navigate(EDIT_LOCATION);
			startingDoc = rcontext.getCurrentDocument();
			if(startingDoc == null) throw new IllegalStateException("No document available for file edit location.");
			xpath = XPathFactory.newInstance().newXPath();
			nodeList = (NodeList) xpath.evaluate(EDIT_FORM_XPATH, startingDoc, XPathConstants.NODESET);
			if(nodeList.getLength() == 0) throw new IllegalStateException("Expected form not found in the page.");
			form = (HTMLFormElementImpl) nodeList.item(0);

			form.submit(new FormInput[] { /*submitInput1, submitInput2 */});
			HTMLDocumentImpl searchResultsDoc = rcontext.getCurrentDocument();
			if(searchResultsDoc == null) throw new IllegalStateException("No document available for search results page.");
		}

		// We now look for the text field where 
		// the search phrase goes.
//		HTMLInputElementImpl textInput = (HTMLInputElementImpl) startingDoc.getElementById(TEXT_FIELD_ID);
//		if(textInput == null) throw new IllegalStateException("Did not find a text field named '" + TEXT_FIELD_ID + "'.");
//		textInput.setValue(SEARCH_PHRASE);

		// We submit the form as if the "submit" image button
		// had been pressed. We expect to get a new document as a result.
//		FormInput submitInput1 = new FormInput("icePage$SearchBoxTop$qkwsubmit.x", "1");
//		FormInput submitInput2 = new FormInput("icePage$SearchBoxTop$qkwsubmit.y", "1");
		
//		// Finally, we print out the search results.
//		NodeList resultList = (NodeList) xpath.evaluate("//a[@class='resultsLink']", searchResultsDoc, XPathConstants.NODESET);
//		int length = resultList.getLength();
//		System.out.println(length + " results found.");
//		for(int i = 0; i < length; i++) {
//			Element element = (Element) resultList.item(i);
//			System.out.println((i + 1) + ". " + element.getTextContent());
//			System.out.println("   [" + element.getAttribute("href") + "]");
//		}
	}
	
}
