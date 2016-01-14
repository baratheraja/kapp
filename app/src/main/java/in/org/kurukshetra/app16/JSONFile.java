package in.org.kurukshetra.app16;

import android.content.res.AssetManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class JSONFile {

	HashMap<String, String[]> stringHashMap;
	HashMap<String, String> queryType;
	AssetManager JSONAssetManager;

	public JSONFile (AssetManager assetManager) {

		stringHashMap = new HashMap<> ();
		queryType = new HashMap<> () ;

		String[] helpStrings = new String[] {
				"Hey! This is DexBot here to help you! <br/> ",
				"<b>You can single tap the chathead to close this chat box </b> <br/> ",
				"<b>You can double tap the chathead to close this chat </b> <br/>",
				"You can use these commands to communicate with me <br/>",
				"event __? - e.g. event Sherlock <br/>",
				"workshop __? - e.g. workshop Creative Coding <br/>",
				"help - To see this help message <br/>"
		};

		queryType.put ("help", "help");
		queryType.put ("about", "description");
		queryType.put ("when", "time");

		stringHashMap.put ("help", helpStrings);

		JSONAssetManager = assetManager;

		initJSONFile ("events");
		initJSONFile ("workshops");
	}

	public void initJSONFile(String type) {

		try {
			BufferedReader br = new BufferedReader (new InputStreamReader (JSONAssetManager.open (type + ".json")));

			StringBuilder builder = new StringBuilder ();
			String JSONString;
			while((JSONString = br.readLine ()) != null) builder.append (JSONString);

			JSONArray categoryJSONArray = new JSONObject (builder.toString ()).getJSONArray (type);

			String[] categories = new String[categoryJSONArray.length ()];

			for(int i = 0; i < categoryJSONArray.length (); i++) {
				categories[i] = categoryJSONArray.getJSONObject (i).getString ("name");
				JSONArray tempArray = categoryJSONArray.getJSONObject (i).getJSONArray (type);

				String[] eventNames = new String[tempArray.length ()];

				for(int j = 0; j < tempArray.length (); j++) {

					ArrayList<String> contentList = new ArrayList<> ();

					JSONObject tempObject = tempArray.getJSONObject (j);
					eventNames[j] = tempObject.getString ("name").toLowerCase ();

					JSONArray tabs = tempObject.getJSONArray ("tabs");

					for(int k = 1; k < tabs.length (); k++) {
						contentList.add (tabs.getJSONObject (k).getString ("title"));
						contentList.add (tabs.getJSONObject (k).getString ("content"));
					}

					String[] description = new String[contentList.size ()];
					contentList.toArray (description);
					stringHashMap.put (type + eventNames[j], description);
				}


				stringHashMap.put (type + categories[i].toLowerCase (), eventNames);
			}

			stringHashMap.put (type + "all", categories);
		}
		catch(IOException ie) {
			Log.d ("JSONFile", "Read error");
		}
		catch (JSONException je) {
			Log.d ("JSONArray", "Malformed JSON");
		}

	}


	public String[] getStringArray(String key) {
		return stringHashMap.get (key);
	}

	public String getQueryType(String key) {
		return queryType.get (key);
	}

	public String parseMessage(String message) {

		String parsedMessage = "Please see 'help' to see the list of commands";

		message = message.toLowerCase ();

		String messageType = message.split ("\\s+")[0];

		String messageBody = message.substring (messageType.length ()).toLowerCase ().trim ();

		if (message.matches ("event(s*)(.*)")) {

			StringBuilder reply = new StringBuilder ();
			String[] wordParts = stringHashMap.get ("events" + messageBody);

			if(wordParts != null) {
				parsedMessage = "";
				for(String i : wordParts) reply.append ("<br/>").append (i);
				parsedMessage = parsedMessage + reply.toString();
			}
		}
		else if(message.matches ("workshop(s*)(.*)")){

			StringBuilder reply = new StringBuilder ();
			String[] wordParts = stringHashMap.get ("workshops" + messageBody);

			if(wordParts != null) {
				parsedMessage = "";
				for(String i : wordParts) reply.append ("<br/>").append (i);
				parsedMessage = parsedMessage + reply.toString();
			}
		}
		else if(message.matches ("help(.*)")) {
			String[] wordParts = stringHashMap.get ("help");
			parsedMessage = "";
			for(String i : wordParts) parsedMessage = parsedMessage + i;
		}

		return parsedMessage;
	}
}
