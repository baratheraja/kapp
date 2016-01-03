package in.org.kurukshetra.app16;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class ChatDialog extends AppCompatActivity {
	public static boolean active = false;
	public static Activity myDialog;

	ChatAdapter chatAdapter;
	ListView mListView;

	ArrayList<Message> arrayList;
	JSONFile jsonFile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
//		this.requestWindowFeature (Window.FEATURE_NO_TITLE);

		myDialog = this;

		setContentView(R.layout.chat);

		arrayList = new ArrayList<> ();

		jsonFile = new JSONFile (getAssets ());

		String[] temp = jsonFile.getStringArray ("help");
		StringBuilder help = new StringBuilder();
		for (String i : temp) help.append ("\n").append (i);

		arrayList.add (new Message ("bot", help.toString ()));

		chatAdapter = new ChatAdapter (getApplicationContext (), arrayList);

		mListView = (ListView) findViewById (R.id.chat);
		mListView.setAdapter (chatAdapter);

		findViewById(R.id.btn).setOnClickListener (new View.OnClickListener () {

			@Override
			public void onClick (View v) {

				EditText editText = (EditText) findViewById (R.id.new_msg);

				String msg = editText.getEditableText ().toString ();
				editText.setText ("");

				if (msg.replaceAll (" ", "").length () > 0)
					arrayList.add (new Message ("user", msg));
				else return;

				arrayList.add (new Message ("bot", jsonFile.parseMessage (msg)));

				chatAdapter = new ChatAdapter (getApplicationContext (), arrayList);
				mListView.setAdapter (chatAdapter);
			}
		});

		findViewById(R.id.dialog_top).setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick (View v) {
				finish ();
			}
		});
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