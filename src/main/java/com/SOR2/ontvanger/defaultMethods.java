package com.SOR2.ontvanger;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.wicket.ajax.json.JSONException;
import org.apache.wicket.ajax.json.JSONObject;

/**
 * In deze klasse bevinden zich een aantal klas onafhankelijke methoden die wij
 * misschien op meerdere plekken zouden willen implementeren.
 *
 * @author Mark
 * @version 0.1.0
 *
 */
abstract class defaultMethods {

	/**
	 * De methode die de benodigde variabelen en instanties neerzet om een get
	 * uit te voeren
	 *
	 * @param url
	 *            Een String wat een URL voorsteld.
	 *
	 * @throws IOException
	 *
	 * @returns het JSONObject waarin de response van de url verwerkt zit
	 */
	public static JSONObject readJsonFromUrl(String url) throws IOException,
			JSONException {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is,
					Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			is.close();
		}
	}

	/**
	 * De methode die de benodigde variabelen en instanties neerzet om een get
	 * uit te voeren
	 *
	 * @param rd
	 *            Een Reader object waarmee het mogelijk is een url response uit
	 *            te lezen
	 *
	 * @throws IOException
	 *
	 * @returns een String object dat de response bevat
	 */
	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	/**
	 * De methode om een Post mee uit te voeren de methode maakt eerste een
	 * connectie aan en geeft hem daarna de benodigde configuratie.
	 *
	 * @param targetURL
	 *            Een String waarin de url opgeslagen zit
	 * 
	 * @param urlParameters
	 *            Een String waarin de te versturen variablen staan
	 *
	 * @returns het JSONObject waarin de response van de url verwerkt zit
	 */
	public static String excutePost(String targetURL, String urlParameters) {
		URL url;
		HttpURLConnection connection = null;
		try {
			// Create connection
			url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");

			connection.setRequestProperty("Content-Length",
					"" + Integer.toString(urlParameters.getBytes().length));
			connection.setRequestProperty("Content-Language", "en-US");

			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Send request
			DataOutputStream wr = new DataOutputStream(
					connection.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();

			// Get Response
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			return response.toString();
			// vangt fouten op tijdens het configuren en uitvoeren van de post.
			// Deze foutmelding is alomvattend.
		} catch (Exception e) {

			e.printStackTrace();
			return null;

		} finally {
			// wanneer de connectie klaar is zowel de optimale als de negative
			// situatie
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

}
