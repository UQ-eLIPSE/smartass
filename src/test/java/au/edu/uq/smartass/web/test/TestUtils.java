package au.edu.uq.smartass.web.test;

import org.lobobrowser.html.domimpl.HTMLDocumentImpl;

public class TestUtils {
	public static HTMLDocumentImpl navigate(HeadlessHtmlRendererContext rcontext, String location) {
		rcontext.navigate(location);
		return rcontext.getCurrentDocument();
	}
}
