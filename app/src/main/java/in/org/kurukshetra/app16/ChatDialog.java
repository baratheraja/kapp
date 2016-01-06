package in.org.kurukshetra.app16;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bezyapps.floatieslibrary.Floaty;
import com.bezyapps.floatieslibrary.FloatyOrientationListener;

import java.util.ArrayList;

public class ChatDialog extends AppCompatActivity {
	public static boolean active = false;
	public static Activity myDialog;

	ChatAdapter chatAdapter;


	ArrayList<Message> arrayList;
	JSONFile jsonFile;

	Floaty floaty;
	Button button_start, button_stop;
	private static final int NOTIFICATION_ID = 1500;
	public static final int PERMISSION_REQUEST_CODE = 16;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		myDialog = this;
		myDialog.requestWindowFeature (Window.FEATURE_NO_TITLE);

		super.onCreate (savedInstanceState);
		setContentView (R.layout.chathead);

		button_start = (Button) findViewById(R.id.button_start);
		button_stop = (Button) findViewById(R.id.button_stop);
		Intent intent = new Intent(this, HomeActivity.class);
		PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		Notification notification = Floaty.createNotification (this, "k! Chat", "Chat Running", R.drawable.cyclotron, resultPendingIntent);

		ImageView head = new ImageView(this);
		head.setBackgroundResource (R.drawable.cyclotron);

		// Inflate the Views that are to be used as HEAD and BODY of The Window
		//View head = LayoutInflater.from (this).inflate(R.layout.float_head, null);
		// You should not add click listeners to head as it will be overridden, but the purpose of not making head just
		// an ImageView is so you can add multiple views in it, and show and hide the relevant views to notify user etc.
		View body = LayoutInflater.from(this).inflate(R.layout.chat, null);

		arrayList = new ArrayList<> ();

		jsonFile = new JSONFile (getAssets ());

		String[] temp = jsonFile.getStringArray ("help");
		StringBuilder help = new StringBuilder();
		for (String i : temp) help.append ("\n").append (i);

		arrayList.add (new Message ("bot", help.toString ()));

		final ChatAdapter chatAdapter = new ChatAdapter (getApplicationContext (), arrayList);

		final ListView mListView = (ListView) body.findViewById (R.id.chat);
		final EditText editText = (EditText) body.findViewById (R.id.new_msg);
		mListView.setAdapter (chatAdapter);

		body.findViewById(R.id.btn).setOnClickListener (new View.OnClickListener () {

			@Override
			public void onClick (View v) {

				String msg = editText.getEditableText ().toString ();
				editText.setText ("");

				if (msg.replaceAll (" ", "").length () > 0)
					arrayList.add (new Message ("user", msg));
				else return;

				arrayList.add (new Message ("bot", jsonFile.parseMessage (msg)));

				ChatAdapter chatAdapter = new ChatAdapter (getApplicationContext (), arrayList);
				mListView.setAdapter (chatAdapter);
			}
		});

		floaty = Floaty.createInstance(this, head, body, NOTIFICATION_ID, notification, new FloatyOrientationListener () {
			@Override
			public void beforeOrientationChange(Floaty floaty) {
				Toast.makeText(ChatDialog.this, "Orientation Change Start", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void afterOrientationChange(Floaty floaty) {
				Toast.makeText(ChatDialog.this, "Orientation Change End", Toast.LENGTH_SHORT).show();
			}
		});

		button_start.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
					startFloatyForAboveAndroidL();
				else
					floaty.startService();
			}
		});

		button_stop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				floaty.stopService();
			}
		});

	}

	@TargetApi(Build.VERSION_CODES.M)
	public void startFloatyForAboveAndroidL() {
		if (!Settings.canDrawOverlays(this)) {
			Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
					Uri.parse ("package:" + getPackageName ()));
			startActivityForResult(intent, PERMISSION_REQUEST_CODE);
		} else {
			floaty.startService();
		}
	}


	@TargetApi(Build.VERSION_CODES.M)
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PERMISSION_REQUEST_CODE) {
			if (Settings.canDrawOverlays (this)) {
				floaty.startService();
			} else {
				Spanned message = Html.fromHtml ("Please allow this permission, so <b>DexBot</b> can chat with you!");
				Toast.makeText(this, message, Toast.LENGTH_LONG).show();
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		active = true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		active = false;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		active = false;
	}

}