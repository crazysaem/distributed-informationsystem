package rwi.infosystem.core.classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import rwi.infosystem.core.variables.RwiCommunication;

public class Requester {
/**
 * This functions sends a http request to a target system. 
 * The Method can be specified.
 *  
 * @param IPAddress		IPAddress of the target
 * @param Port			Port of the target
 * @param Servlet 		Name of the servlet, with "/" in the beginning!
 * @param Parameters 	List of parameters e.g. "id=12&pos=12.3"
 * @param ReqMethod 	POST / GET / DELETE / PUT
 * @param Answer 		if you want the answer of the request insert a String, else null
 */
protected static String sendRequest(String IPAddress, String Port,
		String Servlet, String Parameters, String ReqMethod) {
	try {
		//setup an url connection
		URL url = new URL("http://"+IPAddress + ":" + Port + Servlet + "?" + Parameters);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		//setup the parameters
		conn.setRequestMethod(ReqMethod);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setUseCaches(false);

		if (ReqMethod.equals(RwiCommunication.REQUESTMETHOD_DELETE)) {
			// send delete request without waiting for an answer(there will be none!)
			conn.connect();
			return "deleted";
		} else {
			// open a reader to get the answer of called servlet
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));

			String res = "";
			// catch all lines from the answer
			for (String line; (line = reader.readLine()) != null;) {
				res += line;
			}
			// finally close the reader
			reader.close();
			return res;
		}
	} catch (IOException e) {
		e.printStackTrace();
	}
	return null;

}

	protected static String generateParamter(String parameter, float value) {
		return parameter + "=" + String.valueOf(value) + "&";
	}

	protected static String generateParamter(String parameter, int value) {
		return parameter + "=" + String.valueOf(value) + "&";
	}
	
	protected static String generateParamter(String parameter, String value) {
		return parameter + "=" + value + "&";
	}
	protected static String generateParamter(String parameter, boolean value) {
		if(value)
			return parameter + "=" + "true" + "&";
		else
			return parameter + "=" + "false" + "&";
	}
}
