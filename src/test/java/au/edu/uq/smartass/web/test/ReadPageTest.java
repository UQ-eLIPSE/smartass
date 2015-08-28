package au.edu.uq.smartass.web.test;

import org.lobobrowser.html.UserAgentContext;
import org.lobobrowser.html.domimpl.HTMLDocumentImpl;

public class ReadPageTest extends Test {
	protected String url;
	protected HTMLDocumentImpl document;
	private UserAgentContext uacontext;
	private HeadlessHtmlRendererContext rcontext;
	private int httpResponse;

	private int wait;

	public ReadPageTest(String name) {
		super(name);
		uacontext = new LocalUserAgentContext();
		rcontext = new HeadlessHtmlRendererContext(uacontext);
	}
	
	public ReadPageTest(String name, int wait) {
		super(name);
		this.wait = wait;
		uacontext = new LocalUserAgentContext();
		rcontext = new HeadlessHtmlRendererContext(uacontext);
	}

	public ReadPageTest(String name, HeadlessHtmlRendererContext rcontext, int wait) {
		super(name);
		this.wait = wait;
		this.rcontext = rcontext;
		uacontext = rcontext.getUserAgentContext();
	}

	public ReadPageTest(String name, String url, int wait) {
		super(name);
		this.wait = wait;
		setUrl(url);
		uacontext = new LocalUserAgentContext();
		rcontext = new HeadlessHtmlRendererContext(uacontext);
	}

	@Override
	protected void beforeExecute() {
		try {
			Thread.sleep(wait);
		} catch (InterruptedException e) {}
	}
	
	protected void analyseResult() {
		if(document == null)
			testOutcome = RESULT_FAILED;
		else
			testOutcome = RESULT_COMPLETE;
	}
	
	@Override
	protected void execute() {
		rcontext.navigate(url);
		document = rcontext.getCurrentDocument();
		httpResponse = rcontext.getResponceCode();
		analyseResult();
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getUrl() {
		return url;
	}
	
	public int getHttpResponse() {
		return httpResponse;
	}
	
	public void print() {
		System.out.println("Test " + getName() + " complete in " + ticks + "ms with result: " + 
				rcontext.getResponceCode() + ", cookie: " + rcontext.getCookie());
	}
}
