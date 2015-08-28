/**
 * 
 */
package au.edu.uq.smartass.web.test;

import org.lobobrowser.html.test.SimpleUserAgentContext;

class LocalUserAgentContext extends SimpleUserAgentContext {
	@Override
	public boolean isScriptingEnabled() {
		// We don't need Javascript for this.
		return false;
	}

	@Override
	public boolean isExternalCSSEnabled() {
		// We don't need to load external CSS documents.
		return false;
	}

	@Override
	public String getUserAgent() {
		return "Mozilla/4.0 (compatible; MSIE 6.0;) Smart Assignments Test Engine";
	}		
}