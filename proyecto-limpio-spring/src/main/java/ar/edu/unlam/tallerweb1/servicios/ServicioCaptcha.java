package ar.edu.unlam.tallerweb1.servicios;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
 
import javax.net.ssl.HttpsURLConnection;
 
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;

public class ServicioCaptcha {
	private static final Logger logger = Logger.getLogger(ServicioCaptcha.class);
    public static final String url = "https://www.google.com/recaptcha/api/siteverify";
    private final static String USER_AGENT = "Mozilla/5.0";
 
    public static boolean verify(String secretKey, String gRecaptchaResponse) {
        if (gRecaptchaResponse == null || "".equals(gRecaptchaResponse)) {
            return false;
        }
        try{
            URL obj = new URL(url);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
 
            // add request header
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
 
            String postParams = "secret=" + secretKey + "&response=" + gRecaptchaResponse;
 
            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();
 
            int responseCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            JSONObject jsonObject = new JSONObject(response.toString());
            return jsonObject.getBoolean("success");
        }catch(Exception e){
            logger.info(e);
            return false;
        }
    }
}
