package au.edu.uq.smartass.web.test;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lobobrowser.html.*;
import org.lobobrowser.html.domimpl.*;
import org.lobobrowser.html.parser.DocumentBuilderImpl;
import org.lobobrowser.html.parser.InputSourceImpl;


/**
 * The simple implementation of non-visual renderer context for Cobra HTML Parser.
 *
 */
public class HeadlessHtmlRendererContext extends AbstractHtmlRendererContext {

	private static final Logger logger = Logger.getLogger(HeadlessHtmlRendererContext.class.getName());

	private final UserAgentContext uacontext;
	private HTMLDocumentImpl document;
	private String cookie = "";
	private int responseCode;
	
	public HeadlessHtmlRendererContext(final UserAgentContext uacontext) {
		this.uacontext = uacontext;
	}
	
	public HTMLDocumentImpl getCurrentDocument() {
		return this.document;
	}

	@Override
	public boolean isImageLoadingEnabled() {
		// We don't need to load images.
		return false;
	}
	
	@Override
	public UserAgentContext getUserAgentContext() {
		// For consistency, this should return the 
		// same UserAgentContext that was used to
		// parse the document.
		return this.uacontext;
	}

	public void navigate(String urlOrPath) {
		try {
			// We implement a convenience navigate() method
			// that is based on submitForm().
			URL url;
			if(document!=null)
				url = org.lobobrowser.util.Urls.guessURL(document.getDocumentURL(), urlOrPath);
			else
				url = org.lobobrowser.util.Urls.guessURL(urlOrPath);
			this.submitForm("GET", url, "_this", null, null);
		} catch(java.net.MalformedURLException mfu) {
			logger.log(Level.WARNING, "navigate()", mfu);
		}
	}
	
	@Override
	public void submitForm(String method, URL action, String target, String enctype, FormInput[] formInputs) {
		// This is the code that does form submission.
		try {
			final String actualMethod = method.toUpperCase();
			URL resolvedURL;
			if("GET".equals(actualMethod) && formInputs != null) {
				boolean firstParam = true;
				StringBuffer newUrlBuffer = new StringBuffer(action.toExternalForm());
				if(action.getQuery() == null) {
					newUrlBuffer.append("?");
				}
				else {
					newUrlBuffer.append("&");
				}
				for(int i = 0; i < formInputs.length; i++) {
					FormInput parameter = formInputs[i];
					String name = parameter.getName();
					String encName = URLEncoder.encode(name, "UTF-8");
					if(parameter.isText()) {
						if(firstParam) {
							firstParam = false;
						}
						else {
							newUrlBuffer.append("&");
						}
						String valueStr = parameter.getTextValue();
						String encValue = URLEncoder.encode(valueStr, "UTF-8");
						newUrlBuffer.append(encName);
						newUrlBuffer.append("=");
						newUrlBuffer.append(encValue);
					}
				}	
				resolvedURL = new java.net.URL(newUrlBuffer.toString());
			}
			else {
				resolvedURL = action;
			}
			URL urlForLoading;
			if(resolvedURL.getProtocol().equals("file")) {
				// Remove query so it works.
				try {
					String ref = action.getRef();
					String refText = ref == null || ref.length() == 0 ? "" : "#" + ref;
					urlForLoading = new URL(resolvedURL.getProtocol(), action.getHost(), action.getPort(), action.getPath() + refText);
				} catch(java.net.MalformedURLException throwable) {
					urlForLoading = action;
				}
			}
			else {
				urlForLoading = resolvedURL;
			}
			
			// Using potentially different URL for loading.
			boolean isPost = "POST".equals(actualMethod);
			URLConnection connection = urlForLoading.openConnection();
			connection.setRequestProperty("User-Agent", getUserAgentContext().getUserAgent());
			connection.setRequestProperty("Cookie", cookie);
			HttpURLConnection hc = null;
			if (connection instanceof HttpURLConnection) {
				hc = (HttpURLConnection) connection;
				hc.setRequestMethod(actualMethod);
				// Do not follow redirects
				hc.setInstanceFollowRedirects(false);
			}
			if(isPost) {
				connection.setDoOutput(true);
				ByteArrayOutputStream bufOut = new ByteArrayOutputStream();
				boolean firstParam = true;
				if(formInputs != null) {
					for(int i = 0; i < formInputs.length; i++) {
						FormInput parameter = formInputs[i];
						String name = parameter.getName();
						String encName = URLEncoder.encode(name, "UTF-8");
						if(parameter.isText()) {
							if(firstParam) {
								firstParam = false;
							}
							else {
								bufOut.write((byte) '&');				    					
							}
							String valueStr = parameter.getTextValue();
							String encValue = URLEncoder.encode(valueStr, "UTF-8");
							bufOut.write(encName.getBytes("UTF-8"));
							bufOut.write((byte) '=');
							bufOut.write(encValue.getBytes("UTF-8"));
						}
					}
				}
				byte[] postContent = bufOut.toByteArray();
				if(connection instanceof HttpURLConnection) {
					((HttpURLConnection) connection).setFixedLengthStreamingMode(postContent.length);
				}
				connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				OutputStream postOut = connection.getOutputStream();
				postOut.write(postContent);
				postOut.flush();
			}					
			if(hc != null) {
				// We handle redirects.
				int respCode = hc.getResponseCode();
				if(respCode == HttpURLConnection.HTTP_MOVED_PERM || respCode == HttpURLConnection.HTTP_MOVED_TEMP) {
					String location = hc.getHeaderField("Location");
					URL newAction = new URL(action, location);
					// Recurse
					this.submitForm("GET", newAction, target, null, null);
					//if redirect returned HTTP_OK, remember initial response code
					//otherwise responseCode = code set by recursive call of submitForm()
					if(responseCode==HttpURLConnection.HTTP_OK)
						responseCode = respCode;
					return;
				}
				//remember http response code from server
				responseCode = respCode;
			}
			String cook = hc.getHeaderField("Set-Cookie");
			if(cook!=null)
				cookie = cook;
			InputStream in = connection.getInputStream();
			try {
				InputStream bin = new BufferedInputStream(in, 8192);
				String actualURI = urlForLoading.toExternalForm();
				// Note that DocumentBuilderImpl needs to be
				// constructed by passing both a UserAgentContext
				// and an HtmlRendererContext in this case, so
				// that form.submit() can take effect.
				DocumentBuilderImpl builder = new DocumentBuilderImpl(this.uacontext, this);
				String charset = org.lobobrowser.util.Urls.getCharset(connection);
				InputSourceImpl is = new InputSourceImpl(bin, actualURI, charset);
				this.document = (HTMLDocumentImpl) builder.parse(is);
			} finally { 
				in.close();
			}
		} catch(Exception err) {
			this.document = null;
			logger.log(Level.WARNING, "submitForm()", err);
		}
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public int getResponceCode() {
		return responseCode;
	}
}
