package in.org.kurukshetra.app16;

import android.annotation.TargetApi;
import android.app.Activity;
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

import java.util.ArrayList;

public class ChatDialog extends AppCompatActivity {
	public static boolean active = false;
	public static Activity myDialog;

	ChatAdapter chatAdapter;


	ArrayList<Message> arrayList;
	JSONFile jsonFile;

	Floaty floaty;
	Button button_start, button_stop;
	public static final int PERMISSION_REQUEST_CODE = 16;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		myDialog = this;
		myDialog.requestWindowFeature (Window.FEATURE_NO_TITLE);

		super.onCreate (savedInstanceState);
		setContentView (R.layout.chathead);

		button_start = (Button) findViewById(R.id.button_start);
		button_stop = (Button) findViewById(R.id.button_stop);

		ImageView head = new ImageView(this);
		head.setBackgroundResource (R.drawable.chat_head);

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

		floaty = Floaty.createInstance(this, head, body);
		button_start.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
					startFloatyForAboveAndroidL();
				else{

					floaty.startService();
						updateUI(floaty.getIsActive());
				}

			}
		});
		updateUI(floaty.getIsActive());
		button_stop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				floaty.stopService();
				updateUI(floaty.getIsActive());
			}
		});

	}

	@Override
	protected void onStart() {
		super.onStart();
		updateUI(floaty.getIsActive());
	}

	@TargetApi(Build.VERSION_CODES.M)
	public void startFloatyForAboveAndroidL() {
		if (!Settings.canDrawOverlays(this)) {
			Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
					Uri.parse ("package:" + getPackageName ()));
			startActivityForResult(intent, PERMISSION_REQUEST_CODE);
		} else {
			floaty.startService();
			updateUI(floaty.getIsActive());
		}
	}


	public void updateUI(Boolean active){
		if(active){
			button_start.setVisibility(View.INVISIBLE);
			button_stop.setVisibility(View.VISIBLE);
		}
		else {

			button_start.setVisibility(View.VISIBLE);
			button_stop.setVisibility(View.INVISIBLE);
		}
	}

	@TargetApi(Build.VERSION_CODES.M)
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PERMISSION_REQUEST_CODE) {
			if (Settings.canDrawOverlays (this)) {
				floaty.startService();
				updateUI(floaty.getIsActive());
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
		updateUI(floaty.getIsActive());
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