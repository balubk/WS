package net.balubk.flickr.weekly;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;


public class HtmlUtils {

	public static String getUrlContentsAsString(String url) {
		HttpClient client = new HttpClient();
		HttpMethod method = new GetMethod(url);
		method.setFollowRedirects(true);
		method.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
		StringWriter out = new StringWriter();
		try {
			int statusCode = client.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				// error!
				System.err.println("Method failed: " + method.getStatusLine());
				return null;
			}
			InputStream in = method.getResponseBodyAsStream();
			IOUtils.copy(in, out);
			return out.toString();
		} catch (HttpException e) {
			System.err.println("Fatal protocol violation: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Fatal transport error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			// Release the connection.
			method.releaseConnection();
			IOUtils.closeQuietly(out);
		}
		return null;
	}
}
