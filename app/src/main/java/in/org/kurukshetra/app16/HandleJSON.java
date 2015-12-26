package in.org.kurukshetra.app16;

/**
 * Created by goku on 19-12-2014.
 */
import android.os.StrictMode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HandleJSON {


    public String title[] = new String[150];
    public boolean result=false;
    public static int count=0;



    HandleJSON()
    {
        int SDK_INT = android.os.Build.VERSION.SDK_INT;

        if (SDK_INT>8){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        // TODO Auto-generated method stub
        String json_url="http://cms.kurukshetra.org.in/updates.json";
        String response_message = "";
        String USER_AGENT = "Mozilla/5.0";
        try {
            URL obj = new URL(json_url);

            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");


            con.setRequestProperty("User-Agent", USER_AGENT);
            int responseCode = con.getResponseCode();
            final StringBuilder json_result=new StringBuilder();

            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                response_message = response.toString();
                System.out.println(response_message);
                JSONArray results = new JSONArray(response_message);
                String id[] = new String[results.length()];
                count=results.length();

                for (int i = 0; i < results.length(); i++) {
                    JSONObject result = results.getJSONObject(i);
                    id[i] = result.getString("id");
                    title[i] = result.getString("title");



                }
                result=true;


            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


}

