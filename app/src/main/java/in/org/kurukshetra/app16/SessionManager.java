package in.org.kurukshetra.app16;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences pref;

    Editor editor;

    Context _context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "AndroidHivePref";

    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_TKID = "tkid";
    public static final String KEY_TOKEN    = "token";
    public static final String KEY_AUTH_TOKEN = "authentication_token";
    public static final String KEY_PROVIDER = "provider";

    public static final String KEY_ID = "id";

    public static final String KEY_KID = "Kid";
    public static final String KEY_NAME = "name";

    public static final String KEY_SA_ID = "sa_id";

    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();

    }


    public void createLoginSession(String token,String authentication_token,String id,String kid,
                                   String name, String sa_id,String provider){


        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_TOKEN, token);
        editor.putString(KEY_AUTH_TOKEN,authentication_token);
        editor.putString(KEY_ID,id);
        editor.putString(KEY_KID, kid);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_SA_ID, sa_id);
        editor.putString(KEY_PROVIDER,provider);
        editor.commit();
    }

    public void checkLogin(){
        if(!this.isLoggedIn()){
       /*     Intent i = new Intent(_context, HomeActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);*/
        }
    }


    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_ID, pref.getString(KEY_ID,null));
        user.put(KEY_KID, pref.getString(KEY_KID,null));
        return user;
    }

    public String getKid(){
        return pref.getString(KEY_KID,null);
    }
    public void logoutUser(){
        editor.clear();
        editor.commit();
       // Intent i = new Intent(_context, HomeActivity.class);
        //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //_context.startActivity(i);
    }
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void setProvider(String provider){
        editor.putString(KEY_PROVIDER,provider);
    }

    public void setTkid(String tkid) {
        editor.putString(KEY_TKID,tkid);
    }
    public String getTkid(){
        return pref.getString(KEY_TKID,null);
    }
    public String getProvider(){
        return pref.getString(KEY_PROVIDER,null);
    }

    public String getUserName() {
        return pref.getString(KEY_NAME,null);
    }

    public String getKeyToken() {
        return pref.getString(KEY_TOKEN,null);
    }
    public String getAuthToken() {
        return pref.getString(KEY_AUTH_TOKEN,null);
    }

}
