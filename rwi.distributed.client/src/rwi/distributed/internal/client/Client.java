package rwi.distributed.internal.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import rwi.distributed.core.variables.RwiCommunication;

public class Client {

	private String isaddress = "http://localhost:8080";

	public int register(String message) {
		String res = SendRequest(message, RwiCommunication.REQUESTMETHOD_POST,
				isaddress + RwiCommunication.REGISTER_SERVLET);
		return Integer.parseInt(res);
	}

	public void unregister(String id) {
		SendRequest(id, RwiCommunication.REQUESTMETHOD_POST,
				isaddress + RwiCommunication.REGISTER_SERVLET);
	}

	private String SendRequest(String message, String RequestMethod,
			String UrlTarget) {
		String result = "";
		try {
			URL url = new URL(UrlTarget);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(RequestMethod);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestProperty("Content-Type", "text/html");
			conn.setRequestProperty("Content-Length",
					String.valueOf(message.length()));

			OutputStreamWriter writer = new OutputStreamWriter(
					conn.getOutputStream());
			writer.write(message);
			writer.flush();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));

			for (String line; (line = reader.readLine()) != null;) {
				result += line;
			}
			System.out.println(result);

			writer.close();
			reader.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
