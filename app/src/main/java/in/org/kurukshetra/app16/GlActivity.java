package in.org.kurukshetra.app16;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.pushbots.push.Pushbots;

import in.org.kurukshetra.app16.app.MyApplication;

public class GlActivity extends AppCompatActivity {

    public void onClick(View view) {
        String gl = view.getContentDescription().toString();

        Intent intent = new Intent(this, GlDetails.class);

        switch (gl) {
            case "angelo":
                intent.putExtra("Title", "Angelo Vermeulen");
                break;
            case "abhas":
                intent.putExtra("Title", "Abhas Mitra");
                break;
            case "arjun":
                intent.putExtra("Title", "Arjun Shetty");
                break;
            case "chirag":
                intent.putExtra("Title", "Chiragh Dewan");
                break;
            case "sesha":
                intent.putExtra("Title", "Dr. Seshagiri Rao");
                break;
            case "hemanth":
                intent.putExtra("Title", "Hemanth Kumar Guruswamy");
                break;
            case "girish":
                intent.putExtra("Title", "Girish Mathrubootham");
                break;
            case "masha":
                intent.putExtra("Title", "Masha Nazeem");
                break;
        }

        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gl_card);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) onBackPressed();

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.getInstance().trackScreenView("GL Page");

        Pushbots.sharedInstance().tag("GL");
    }
}
