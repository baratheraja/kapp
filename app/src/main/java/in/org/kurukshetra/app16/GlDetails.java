package in.org.kurukshetra.app16;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.pushbots.push.Pushbots;

import in.org.kurukshetra.app16.app.MyApplication;

public class GlDetails extends AppCompatActivity {

    TextView glDetails;
    ImageView imageView;
    String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gl_details2);

        title = getIntent().getStringExtra("Title");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(title);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        glDetails = (TextView) findViewById(R.id.gl_details);
        imageView = (ImageView) findViewById(R.id.header_picture);

        switch (title) {
            case "Angelo Vermeulen":
                imageView.setImageResource(R.drawable.vermeulen_big);
                glDetails.setText(R.string.angelo);
                break;
            case "Abhas Mitra":
                imageView.setImageResource(R.drawable.abhasmitra);
                glDetails.setText(R.string.abhas);
                break;
            case "Arjun Shetty":
                imageView.setImageResource(R.drawable.gl3);
                glDetails.setText(R.string.arjun);
                break;
            case "Chiragh Dewan":
                imageView.setImageResource(R.drawable.chiragh);
                glDetails.setText(R.string.chirag);
                break;
            case "Dr. Seshagiri Rao":
                imageView.setImageResource(R.drawable.sesha);
                glDetails.setText(R.string.sesha);
                break;
            case "Hemanth Kumar Guruswamy":
                imageView.setImageResource(R.drawable.hemanth);
                glDetails.setText(R.string.hemanth);
                break;
            case "Girish Mathrubootham":
                imageView.setImageResource(R.drawable.girish);
                glDetails.setText(R.string.girish);
                break;
            case "Masha Nazeem":
                imageView.setImageResource(R.drawable.masha);
                glDetails.setText(R.string.masha);
                break;
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
        MyApplication.getInstance().trackScreenView("GL " + title);
        Pushbots.sharedInstance().tag("GL "+ title);
    }
}
