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

	public JSONFile (AssetManager assetManager) {

		stringHashMap = new HashMap<> ();
		queryType = new HashMap<> () ;

		String[] helpStrings = new String[] {
				"Hey! This is DexBot here to help you! <br/> You can use these commands to communicate with me <br/>",
				"when is ___? - e.g. when coding <br/>",
				"about __? - e.g. about categories <br/>",
				"help - To see this help message <br/>"
		};

		queryType.put ("help", "help");
		queryType.put ("about", "description");
		queryType.put ("when", "time");

		stringHashMap.put ("help", helpStrings);

		initJSONFile (assetManager);

	}

	public void initJSONFile(AssetManager assetManager) {

		try {
			BufferedReader br = new BufferedReader (new InputStreamReader (assetManager.open ("events.json")));

			StringBuilder builder = new StringBuilder ();
			String JSONString;
			while((JSONString = br.readLine ()) != null) builder.append (JSONString);

			JSONArray categoryJSONArray = new JSONObject (builder.toString ()).getJSONArray ("events");

			String[] categories = new String[categoryJSONArray.length ()];

			for(int i = 0; i < categoryJSONArray.length (); i++) {
				categories[i] = categoryJSONArray.getJSONObject (i).getString ("name");
				JSONArray tempArray = categoryJSONArray.getJSONObject (i).getJSONArray ("events");

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
					stringHashMap.put (eventNames[j], description);
				}
				stringHashMap.put (categories[i].toLowerCase (), eventNames);
			}

			stringHashMap.put ("categories", categories);
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

	@SuppressWarnings ("unused")
	public String getQueryType(String key) {
		return queryType.get (key);
	}

	public String parseMessage(String message) {

		String parsedMessage = "Please see 'help' to see the list of commands";

		message = message.toLowerCase ();

		String messageType = message.split ("\\s+")[0];

		String messageBody = message.substring (messageType.length ()).toLowerCase ().trim ();

		if (message.matches ("about(.*)")) {
			if(stringHashMap.get (messageBody) != null) {
				parsedMessage = "";
				String[] wordParts = stringHashMap.get (messageBody);
				for(String i : wordParts) parsedMessage = parsedMessage + "<br/>" + i;
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
