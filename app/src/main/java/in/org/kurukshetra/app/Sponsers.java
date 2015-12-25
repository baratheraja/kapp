package in.org.kurukshetra.app;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by goku on 18-12-2014.
 */
public class Sponsers extends ActionBarActivity {


    private ListView sponser;
    private Toolbar toolbar;
    ArrayList name = new ArrayList();
    ArrayList url = new ArrayList();
    public static final String My_Pref = "Mypreferences";
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Activity.CONNECTIVITY_SERVICE);
        setContentView(R.layout.sponsers_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Previous Sponsors");

        sponser = (ListView) findViewById(R.id.sponser_list);
        pref = getSharedPreferences(My_Pref, 0);
        SharedPreferences.Editor ed = pref.edit();


        final CustomList adapter = new
                CustomList(Sponsers.this, name, url);

        sponser.setAdapter(adapter);

        int SDK_INT = android.os.Build.VERSION.SDK_INT;

        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        // TODO Auto-generated method stub
        String json_url = "http://techteam.kurukshetra.org.in/sponsors.json";
        String response_message = "";
        String USER_AGENT = "Mozilla/5.0";
        try {
            URL obj = new URL(json_url);

            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");


            con.setRequestProperty("User-Agent", USER_AGENT);
            int responseCode = con.getResponseCode();
            final StringBuilder json_result = new StringBuilder();

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


                adapter.notifyDataSetChanged();
                ed.putInt("size", results.length());


                for (int i = 0; i < results.length(); i++) {
                    JSONObject result = results.getJSONObject(i);


                    name.add(result.getString("name"));
                    url.add(result.getString("uri"));

                    ed.remove("name" + i);
                    ed.remove("url" + i);
                    ed.putString("name" + i, name.get(i).toString());
                    ed.putString("url" + i, url.get(i).toString());
                    ed.commit();
                    adapter.notifyDataSetChanged();


                }


            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        name.clear();
        url.clear();


        int i = pref.getInt("size", 0);
        for (int j = 0; j < i; j++) {
            name.add(pref.getString("name" + j, ""));
            url.add(pref.getString("url" + j, ""));
            adapter.notifyDataSetChanged();
        }

    }


    public class CustomList extends ArrayAdapter<String> {
        private final Activity context;


        private int lastPosition = -1;


        public CustomList(Activity context,
                          ArrayList name, ArrayList url) {
            super(context, R.layout.sponser_list_item, name);
            this.context = context;


        }

        @Override
        public View getView(final int position, View view, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            final View rowView = inflater.inflate(R.layout.sponser_list_item, null, true);
            TextView txtTitle = (TextView) rowView.findViewById(R.id.sponser_name);
            SmartImageView logos = (SmartImageView) rowView.findViewById(R.id.sponser_logo);
            txtTitle.setText((CharSequence) name.get(position));
           

            lastPosition = position;

            logos.setImageUrl("" + url.get(position) + "");


            return rowView;
        }
    }
}