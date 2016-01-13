package in.org.kurukshetra.app16.workshopreg;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import  in.org.kurukshetra.app16.SessionManager;

public class Network2 {

    public interface RegisterCallback {
        void call(String tid);
    }

    private RegisterCallback callerActivity;

    Context context;
    public Network2(Context context) {
        this.context = context;
        callerActivity = (RegisterCallback)(context);
    }

    public void registerWorkshop(WorkshopRegistration registration) {
        HashMap<String ,String> hashMap = new HashMap<>();
        hashMap.put("saId",registration.getSa_Id());
        hashMap.put("sa",registration.isSa().toString());
        hashMap.put("wa",registration.isWh().toString());
        hashMap.put("other",registration.isOther().toString());
        hashMap.put("otherDesc",registration.getOther_detail());
        hashMap.put("web",registration.isWeb().toString());
        hashMap.put("fb",registration.isFb().toString());
        hashMap.put("users",getJSON(registration.getWorkshopUsers()));
        hashMap.put("id",registration.getWork_id());
        SessionManager session= new SessionManager(context);
        hashMap.put("provider",session.getProvider());
        new PostEvent(hashMap).execute("http://login.kurukshetra.org.in/api/workshop/register");
    }

    public String getJSON(List<WorkshopUser> users){

        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(users);
        return json;
        //return null;
    }

    private class PostEvent extends AsyncTask<String, Void, String> {
        HashMap<String, String> postDataParams;
        private ProgressDialog dialog;
        private PostEvent( HashMap<String, String> postDataParams) {
            this.postDataParams=postDataParams;
            dialog = new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Registering ...");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            try {

                String ur = urls[0];
                URL url = new URL(ur);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                SessionManager session= new SessionManager(context);
                String auth = "Base "+session.getKeyToken();
                conn.setRequestProperty("Authorization",auth);
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));
                writer.flush();
                writer.close();
                os.close();
                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    while ((line=br.readLine()) != null) {
                        response+=line;
                    }
                }
                else response="error";
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            Log.d("abc", result + "XYZ");
            Message msg = handler.obtainMessage();
            Bundle b =new Bundle();
            b.putString("Result",result);
            msg.setData(b);
            handler.sendMessage(msg);
        }

        private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
            StringBuilder result = new StringBuilder();
            boolean first = true;
            for(Map.Entry<String, String> entry : params.entrySet()){
                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
            return result.toString();
        }

        private final Handler handler = new Handler() {

            public void handleMessage(Message msg){

                SessionManager session= new SessionManager(context);
                String json = msg.getData().getString("Result");
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    String transaction_id = jsonObject.getString("transaction_id");
                    callerActivity.call(transaction_id);
                } catch (JSONException e) {

                }
            }
        };
    }
}