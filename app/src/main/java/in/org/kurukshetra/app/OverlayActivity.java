package in.org.kurukshetra.app;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import android.widget.Toast;

import in.org.kurukshetra.app.R;

public class OverlayActivity extends Activity {

    TagView tag;
    CloudView view;
    ImageButton fb,twitter,youtube;
    ArrayList<TagView> tags = new ArrayList<TagView>();
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overlay);
        view =  (CloudView) findViewById(R.id.cloudview);
        initTag("Sponsors");
        initTag("Contacts");
        initTag("About us");
        initTag("Hospitality");
        initTag("Login");
        view.setCloudTags(tags);
       fb=(ImageButton) findViewById(R.id.fb_icon);
       fb.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Uri uri = Uri.parse("https://www.facebook.com/kurkshetra.org.in");
               Intent intent = new Intent(Intent.ACTION_VIEW, uri);
               startActivity(intent);
           }
       });
       twitter = (ImageButton) findViewById(R.id.twitter_icon);
       twitter.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Uri uri = Uri.parse("https://www.twitter.com/kurkshetra_ceg");
               Intent intent = new Intent(Intent.ACTION_VIEW, uri);
               startActivity(intent);
           }
       });
       youtube = (ImageButton) findViewById(R.id.youtube_icon);
       youtube.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Uri uri = Uri.parse("https://www.youtube.com/kurkshetramedia");
               Intent intent = new Intent(Intent.ACTION_VIEW, uri);
               startActivity(intent);
           }
       });
    }

    public void initTag(String name) {

        tag = new TagView(this);
        tag.setText(name);
        tag.setTextColor(Color.WHITE);
       // tag.setBackgroundResource(R.drawable.button);
        tag.setGravity(Gravity.CENTER);
        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        view.addView(tag, p);
        tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TagView vw = (TagView) v;
                if (vw.getText().toString().equals("Hospitality")) {
                    Intent intent = new Intent(OverlayActivity.this,HospiActivity.class);
                    startActivity(intent);
                } else if (vw.getText().toString().equals("Contacts")) {
                    Intent intent = new Intent(OverlayActivity.this,contacts.class);
                    startActivity(intent);
                } else if (vw.getText().toString().equals("About us")) {
                    Intent intent = new Intent(OverlayActivity.this,AboutUsActivity.class);
                    startActivity(intent);
                } else
                    Toast.makeText(OverlayActivity.this, "Item clicked", Toast.LENGTH_SHORT).show();
            }
        });

        tags.add(tag);

    }

}
