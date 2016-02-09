package fr.vmarchaud.shareeat.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.util.IOUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import fr.vmarchaud.shareeat.Core;


public class HttpDefaultClient {

	public static final Logger logger = Core.getLogger();
	public static final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

	private HttpURLConnection createUrlConnection(URL url) throws IOException {
		logger.debug("Opening connection to " + url);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setConnectTimeout(1000);
		connection.setReadTimeout(1000);
		connection.setUseCaches(false);
		return connection;
	}

	public JsonObject makeRequest(URL url, Object input) {
		String jsonResult;
		try {
			jsonResult = input == null ? performGetRequest(url) : performPostRequest(url, gson.toJson(input), "application/json");
			JsonObject result = gson.fromJson(jsonResult, JsonObject.class);
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String performPostRequest(URL url, String post, String contentType) throws IOException {

		HttpURLConnection connection = createUrlConnection(url);
		byte[] postAsBytes = post.getBytes("UTF-8");

		connection.setRequestProperty("Content-Type", contentType + "; charset=utf-8");
		connection.setRequestProperty("Content-Length", "" + postAsBytes.length);
		connection.setDoOutput(true);

		logger.debug("Writing POST data to " + url + ": " + post);
		logger.debug("Reading data from " + url);

		InputStream inputStream = null;
		try {
			inputStream = connection.getInputStream();
			String result = IOUtils.toString(new InputStreamReader(inputStream));
			logger.debug("Successful read, server response was " + connection.getResponseCode());
			logger.debug("Response: " + result);
			return result;
		} catch (IOException e) {
			inputStream = connection.getErrorStream();
			if (inputStream != null) {
				logger.debug("Reading error page from " + url);
				String result = IOUtils.toString(new InputStreamReader(inputStream));
				logger.debug("Successful read, server response was " + connection.getResponseCode());
				logger.debug("Response: " + result);
				return result;
			}
			logger.debug("Request failed : ", e);
			throw e;
		} finally {
			inputStream.close();
		}
	}

	private String performGetRequest(URL url) throws IOException {
		HttpURLConnection connection = createUrlConnection(url);

		logger.debug("Reading data from " + url);
		InputStream inputStream = null;
		try {
			inputStream = connection.getInputStream();
			String result = IOUtils.toString(new InputStreamReader(inputStream));
			logger.debug("Successful read, server response was " + connection.getResponseCode());
			logger.debug("Response: " + result);
			return result;
		} catch (IOException e) {
			inputStream = connection.getErrorStream();
			if (inputStream != null) {
				logger.debug("Reading error page from " + url);
				String result = IOUtils.toString(new InputStreamReader(inputStream));
				logger.debug("Successful read, server response was " + connection.getResponseCode());
				logger.debug("Response: " + result);
				return result;
			}
			logger.debug("Request failed ", e);
			throw e;
		} finally {

			inputStream.close();
		}
	}
}
