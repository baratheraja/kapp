package in.org.kurukshetra.app16;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import in.org.kurukshetra.app16.app.MyApplication;

public class GlDetails extends AppCompatActivity {

    TextView glDetails;
    ImageView imageView;
    String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gl_details2);
        Intent intent = getIntent();
        title = intent.getStringExtra("Title");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(title);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        glDetails = (TextView) findViewById(R.id.gl_details);
        imageView = (ImageView) findViewById(R.id.header_picture);
        if(title.equals("Angelo Vermeulen")){
            imageView.setImageResource(R.drawable.vermeulen_big);
            String details = "<b>Crew commander of MARS Simulation program funded by NASA.</b> Has given numerous <b>TED</b> talks internationally about venturing into space and establishing life in other planets.\n" +
                    "<b>Biomodd</b> is a well known project started by him in which ecosystems and computers coexist. On the most basic level, Biomodd creates symbiotic relationships between plants and computers. It is already in place in Athens, Ohio, Philippines, New Zealand, Belgium, Chile, and UK.\n" +
                    "He has other well known achievements and awards to his name.<br><br><b>Date: Feb 6,2016 </b>";
            glDetails.setText(Html.fromHtml(details));
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.getInstance().trackScreenView("GL "+ title);
    }
}
