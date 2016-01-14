package in.org.kurukshetra.app16;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;

import java.util.ArrayList;

import in.org.kurukshetra.app16.app.MyApplication;

public class OverlayActivity extends AppCompatActivity{

    TagView tag;
    CloudView view;
    ImageButton fb,twitter,youtube,close,share,key,kico;
    TextView login,kid;
    RelativeLayout hidden;
    LoginFragment loginFragment;
    ArrayList<TagView> tags = new ArrayList<>();

    public void updateUI(){
        SessionManager session= new SessionManager(this);
        if(session.isLoggedIn()){
            hidden.setVisibility(View.VISIBLE);
            kid.setText(session.getKid());
            login.setText(session.getUserName());
        }
        else {
            hidden.setVisibility(View.GONE);
            login.setText("LOGIN");
        }
    }
    public void onClickLogin(){
        Intent intent = new Intent(this,LoginActivity2.class);
        startActivityForResult(intent, 101);
    }
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_overlay);
       final SharedPreferences s;
       final String MyPREFERENCES = "MyPrefs";
       s=getSharedPreferences(MyPREFERENCES,MODE_PRIVATE);
        view =  (CloudView) findViewById(R.id.cloudview);
        initTag("Sponsors");
        initTag("Contacts");
        initTag("About us");
        initTag("Hospitality");
        initTag("Student Ambassador");
        view.setCloudTags(tags);
       loginFragment = LoginFragment.loginInstance();
       final FragmentManager manager = getSupportFragmentManager();
       login= (TextView) findViewById(R.id.login);
       kid = (TextView) findViewById(R.id.kid);
       key = (ImageButton) findViewById(R.id.key);
       kico = (ImageButton) findViewById(R.id.kid_ico);
       hidden = (RelativeLayout) findViewById(R.id.hidden);
       login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               onClickLogin();
              // loginFragment.show(manager, "LOGIN");
           }
       });
       key.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               onClickLogin();
               //loginFragment.show(manager,"LOGIN");
           }
       });

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
       share = (ImageButton) findViewById(R.id.share_icon);
       share.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Intent intent2=new Intent(Intent.ACTION_SEND);
               String text="https://play.google.com/store/apps/details?id=in.org.kurukshetra.app16";
               intent2.setType("text/plain");
               intent2.putExtra(Intent.EXTRA_TEXT, "Check out the offical k!app in Google Playstore " + text);
               startActivity(Intent.createChooser(intent2, "Share via . . ."));

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
       updateUI();
   }

    @Override
    protected void onStart() {
        super.onStart();
        updateUI();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == LoginFragment.RC_SIGN_IN){
       //     LoginFragment fragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.loginFragment);
            loginFragment.onActivityResult(requestCode, resultCode, data);
        }
        else if(requestCode == 101){
            if(resultCode == Activity.RESULT_OK){
                Toast.makeText(this,"Login Successful",Toast.LENGTH_LONG).show();
            }
            else if(resultCode == Activity.RESULT_CANCELED){
                Toast.makeText(this,"Logout Successful",Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this,"Error",Toast.LENGTH_LONG).show();
            }
            updateUI();
        }
    }


}
