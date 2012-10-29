package rwi.distributed.internal.dispatcher.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import rwi.distributed.core.variables.RwiCommunication;

public class Requester {
	
	protected static void sendRequest(String IPAddress, String Port,
			String Servlet, String Parameters, String ReqMethod, String Answer) {
		try {
			URL url = new URL(IPAddress + ":" + Port + "/" + Servlet + "?"
					+ Parameters);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(ReqMethod);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);

			if (ReqMethod.equals(RwiCommunication.REQUESTMETHOD_DELETE)) {
				// send delete request without waiting for an answer(there will
				// be none!)
				conn.connect();
			} else {
				// open a reader to get the answer of called servlet
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(conn.getInputStream()));

				String res = "";
				// catch all lines from the answer
				for (String line; (line = reader.readLine()) != null;) {
					res += line;
				}
				if(Answer!=null){
					Answer = res;
				}
				// finally close the reader
				reader.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	protected static String generateParamter(String parameter, float value) {
		return parameter + "=" + String.valueOf(value) + "&";
	}

	protected static String generateParamter(String parameter, int value) {
		return parameter + "=" + String.valueOf(value) + "&";
	}

}
