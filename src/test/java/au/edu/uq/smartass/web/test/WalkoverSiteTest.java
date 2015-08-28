package au.edu.uq.smartass.web.test;

import java.util.Vector;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class WalkoverSiteTest extends ReadPageTest {
	Vector<String> urls;
	String[] block;
	
	public WalkoverSiteTest(String name, HeadlessHtmlRendererContext rcontext,
			int wait, String[] urls, String[] blockUrls) {
		super(name, rcontext, wait);
		this.urls = new Vector<String>();
		for(int i=0; i<urls.length;i++)
			this.urls.add(urls[i]);
		block = blockUrls;
	}

	public WalkoverSiteTest(String name, int wait,
			String[] urls, String[] blockUrls) {
		super(name, wait);
		this.urls = new Vector<String>();
		for(int i=0; i<urls.length;i++)
			this.urls.add(urls[i]);
		block = blockUrls;
	}
	
	@Override
	protected void execute() {
		int i=0;
		while(i<urls.size()) {
			url = urls.get(i);
			super.execute();
			extractAppendUrls();
			i++;
		}
	}

	protected void extractAppendUrls() {
		XPath xpath = XPathFactory.newInstance().newXPath();
        NodeList nodeList;
		try {
			nodeList = (NodeList) xpath.evaluate("html//a", document, XPathConstants.NODESET);
	        int length = nodeList.getLength();
	        for(int i = 0; i < length; i++) {
	            Element element = (Element) nodeList.item(i);
	            String href = element.getAttribute("href");
	            System.out.println("## Anchor # " + i + ": " + href);
	            if(testUrl(href))
	            	for(int j=0; j<urls.size(); j++)
	            		if(urls.get(j).equals(href))
	            			continue;
	            urls.add(href);
	        }
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private boolean testUrl(String url) {
		if(block==null)
			return true;
		for(int i=0; i<block.length; i++)
			if(url.matches(block[i]))
				return false;
		return true;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		WalkoverSiteTest test = new WalkoverSiteTest("test", 0, 
				new String[]{"http://localhost:8180/smartass/index.htm"},
				new String[]{"mailto:(.)*"});
		test.execute();

	}

}
