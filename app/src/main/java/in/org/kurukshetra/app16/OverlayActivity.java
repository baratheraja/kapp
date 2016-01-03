package in.org.kurukshetra.app16;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import java.util.ArrayList;

import in.org.kurukshetra.app16.app.MyApplication;

public class OverlayActivity extends Activity {

	TagView tag;
	CloudView view;
	ImageButton fb,twitter,youtube,close;

	ArrayList<TagView> tags = new ArrayList<>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_overlay);
		final SharedPreferences s;
		final String MyPREFERENCES = "MyPrefs" ;
		s = getSharedPreferences(MyPREFERENCES,MODE_PRIVATE);
		view =  (CloudView) findViewById(R.id.cloudview);
		initTag("Events");
		initTag("Workshops");
		initTag("Xceed");
		initTag("Guest Lectures");
		initTag("Sponsors");
		initTag("Contacts");
		initTag("About us");
		initTag("Hospitality");
		initTag("Student Ambassador");
		initTag("Chat");
		view.setCloudTags(tags);
		fb=(ImageButton) findViewById(R.id.fb_icon);
		fb.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Uri uri = Uri.parse("https://www.facebook.com/kurukshetra.org.in");
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
				finish();
			}
		});
		twitter = (ImageButton) findViewById(R.id.twitter_icon);
		twitter.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Uri uri = Uri.parse("https://www.twitter.com/kurukshetra_ceg");
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
				finish();
			}
		});
		youtube = (ImageButton) findViewById(R.id.youtube_icon);
		youtube.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Uri uri = Uri.parse("https://www.youtube.com/kurukshetramedia");
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
				finish();
			}
		});
		close = (ImageButton) findViewById(R.id.close);
		close.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
				finish();
			}
		});
		if(s.getInt("fab",0)==0) {
			SharedPreferences.Editor e = s.edit();
			e.putInt("fab", 1);
			e.commit();
			onCoachMark();
		}

	}
	public void onCoachMark(){

		final Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		dialog.setContentView(R.layout.coach_mark);
		dialog.setCanceledOnTouchOutside(true);
		//for dismissing anywhere you touch
		View masterView = dialog.findViewById(R.id.coach_mark_master_view);
		masterView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dialog.dismiss();
			}
		});
		dialog.show();
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
					Intent intent = new Intent(OverlayActivity.this, HospiActivity.class);
					startActivity(intent);
					finish();
				} else if (vw.getText().toString().equals("Contacts")) {
					Intent intent = new Intent(OverlayActivity.this, contacts.class);
					startActivity(intent);
					finish();
				} else if (vw.getText().toString().equals("About us")) {
					Intent intent = new Intent(OverlayActivity.this, AboutUsActivity.class);
					startActivity(intent);
					finish();
				} else if (vw.getText().toString().equals("Sponsors")) {
					Intent intent = new Intent(OverlayActivity.this, Sponsers.class);
					startActivity(intent);
					finish();
				}else if (vw.getText().toString().equals("Student Ambassador")) {
					Uri uri = Uri.parse("http://m.kurukshetra.org.in/#/sa");
					Intent intent = new Intent(Intent.ACTION_VIEW, uri);
					startActivity(intent);
					finish();
				} else if (vw.getText().toString().equals("Events")) {

					Intent intent = new Intent(OverlayActivity.this, Events.class);
					startActivity(intent);
					finish();
				} else if (vw.getText().toString().equals("Workshops")) {
					Intent intent = new Intent(OverlayActivity.this, Workshops.class);
					startActivity(intent);
					finish();
				} else if (vw.getText().toString().equals("Xceed")) {

					Intent intent = new Intent(OverlayActivity.this, XceedActivity.class);
					startActivity(intent);
					finish();
				} else if (vw.getText().toString().equals("Guest Lectures")) {

					Intent intent = new Intent(OverlayActivity.this, GlActivity.class);
					startActivity(intent);
					finish();
				} else if (vw.getText().toString().equals("Chat")) {

					Intent intent = new Intent(OverlayActivity.this, ChatDialog.class);
					startActivity(intent);
					finish();
				}

			}
		});
		tags.add(tag);
	}
	@Override
	protected void onResume() {
		super.onResume();
		MyApplication.getInstance().trackScreenView("Sphere Menu");
	}

}
