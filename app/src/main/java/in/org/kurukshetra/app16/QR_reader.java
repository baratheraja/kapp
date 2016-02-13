package in.org.kurukshetra.app16;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.zxing.Result;
import com.pushbots.push.Pushbots;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import in.org.kurukshetra.app16.app.MyApplication;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QR_reader extends AppCompatActivity implements ZXingScannerView.ResultHandler,CollegeChooserFragment.OnCollegeListener{
    private ZXingScannerView z;
    LinearLayout lv;
    Result result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_reader);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Pushbots.sharedInstance().tag("Projects");
        z=new ZXingScannerView(this);
        lv=(LinearLayout)findViewById(R.id.lv);
        lv.addView(z);
        z.startCamera();
    }

    public void onPause()
    {
        super.onPause();
        z.stopCamera();
    }


    @Override
    protected void onResume()
    {

        super.onResume();
        z.setResultHandler(this);
        z.startCamera();
        MyApplication.getInstance().trackScreenView("Projects");
    }

    public void send(String a,String b,String c)
    {
        class asyncc extends AsyncTask<String,Void,respo>
        {

            @Override
            protected respo doInBackground(String... params) {
                String uss=params[0];
                String clg = params[2];
                respo r=new respo();
                String link="http://test2014.kurukshetra.org.in/test.php";
                String data;
                SessionManager session = new SessionManager(QR_reader.this);
                try {
                    data = URLEncoder.encode("qid", "UTF-8") + "=" + URLEncoder.encode(uss, "UTF-8");
                    data += "&" + URLEncoder.encode("kid", "UTF-8") + "=" + URLEncoder.encode(session.getKid(), "UTF-8");
                    data += "&" + URLEncoder.encode("college", "UTF-8") + "=" + URLEncoder.encode(clg, "UTF-8");
                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();
                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write( data );
                    wr.flush();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while((line = reader.readLine()) != null)
                    {
                        sb.append(line);
                        break;
                    }
                    Log.d("Yeah:", sb.toString());
                    r.xc=sb.toString();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return r;
            }


            protected void onPostExecute(respo r){

                lv.removeAllViewsInLayout();
                finish();
                Toast.makeText(getApplicationContext(), "Scanned Successfully " + result.getText() , Toast.LENGTH_SHORT).show();
            }
        }
        asyncc aaa=new asyncc();
        aaa.execute(a, b, c);

    }

    @Override
    public void handleResult(Result result) {
        this.result = result;
        CollegeChooserFragment fragment = CollegeChooserFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment.show(fragmentManager, "Enter College");

    }

    @Override
    public void onCollege(String college) {
        send(result.getText().toString(), result.getText().toString(), college);
        z.stopCamera();

    }

    public class respo
    {
        String xc;
    }

}
