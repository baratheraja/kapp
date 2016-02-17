package in.org.kurukshetra.app16;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class Results extends AppCompatActivity {

    // Log tag
    private static final String TAG = Results.class.getSimpleName();

    // Movies json url
    private ProgressDialog pDialog;
    private List<Movie> movieList = new ArrayList<Movie>();
    private ListView listView;
    private CustomListAdapter adapter;
    Button submit;
    EditText kid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.list);
        submit=(Button)findViewById(R.id.submit);
        kid=(EditText)findViewById(R.id.kid);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(kid.getText().toString().equals("")){
                    movieList.clear();
                    Toast.makeText(getApplicationContext(),"Enter a valid k!ID.",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    movieList.clear();
                    new MyAsyncTask().execute(kid.getText().toString());
                }
            }
        });

        adapter = new CustomListAdapter(this, movieList);
        listView.setAdapter(adapter);



    }

    class MyAsyncTask extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog = new ProgressDialog(Results.this);
        InputStream inputStream = null;
        String result = "";

        protected void onPreExecute() {
            progressDialog.setMessage("Downloading your results...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface arg0) {
                    MyAsyncTask.this.cancel(true);
                }
            });
        }

        @Override
        protected String doInBackground(String... params) {

            URL url;
            String response = "";
            try {
                url = new URL("http://techteam.kurukshetra.org.in/app_scripts/get_my_result.php");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);


                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                HashMap postDataParams = new HashMap();
                SessionManager session = new SessionManager(Results.this);
                postDataParams.put("kid", params[0]);
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
                else {
                    response="";

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException{

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

        protected void onPostExecute(String s) {
            //parse JSON data
            try {
                JSONArray jArray = new JSONArray(s);
                if(jArray.length()==0){
                    Toast.makeText(getApplicationContext(),"No records found for this k!ID.",Toast.LENGTH_SHORT).show();
                }
                else {
                    for (int i = 0; i < jArray.length(); i++) {

                        JSONObject jObject = jArray.getJSONObject(i);

                        final String event = jObject.getString("event");
                        final String round = "Round:"+jObject.getString("round");
                        final String position = jObject.getString("position");

                        Movie movie = new Movie();

                        movie.setTitle(event);
                        movie.setYear(position);

                        if (jObject.getString("round").equals("Winners")) {
                            movie.setThumbnailUrl("Winner");
                        }else if (Integer.parseInt(round.toString()) > 1) {
                            Integer t=Integer.parseInt(round.toString())-1;
                            movie.setThumbnailUrl(t.toString());
                        }
                        else
                        {
                            movie.setThumbnailUrl("Participated");
                        }
                        movieList.add(movie);


                    } // End Loop
                }
                this.progressDialog.dismiss();
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                Log.e("JSONException", "Error: " + e.toString());
            } // catch (JSONException e)

        } // protected void onPostExecute(Void v)


    } //class MyAsyncTask extends AsyncTask<String, String, Void>


}

