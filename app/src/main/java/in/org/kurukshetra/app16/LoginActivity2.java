package in.org.kurukshetra.app16;

/**
 * Created by baratheraja on 12/1/16.
 */

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.pushbots.push.Pushbots;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import in.org.kurukshetra.app16.app.MyApplication;

/**
 * Created by baratheraja on 8/1/16.
 */
public class LoginActivity2 extends Activity implements GoogleApiClient.OnConnectionFailedListener{

    public static final int RC_SIGN_IN = 9001;
    SignInButton gbtnSignIn;
    Button gbtnSignOut;
    LoginButton fbLoginButton;
    AccessTokenTracker accessTokenTracker;
    SessionManager session;
    OnLoginListener mCallback;
    public interface OnLoginListener {
        public void onLogin(int position);
    }

    public LoginActivity2() {
    }

  /*  public static LoginFragment loginInstance() {
        LoginFragment f = new LoginFragment();
        Bundle args = new Bundle();
        args.putString("DUMMY", "dummy");
        f.setArguments(args);
        return f;
    }
    */
    private Network network;
    CallbackManager callbackManager;
    private GoogleApiClient mGoogleApiClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_signin);

        callbackManager = CallbackManager.Factory.create();
        fbLoginButton =  (LoginButton) findViewById(R.id.login_button);
        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e("User ID: "
                        , loginResult.getAccessToken().getUserId()
                        + "\n" +
                        "Auth Token: "
                        + loginResult.getAccessToken().getToken());
                network = new Network(LoginActivity2.this,"facebook");
                network.authFbUser(loginResult);
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity2.this, "Login cancelled", Toast.LENGTH_LONG);

            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(LoginActivity2.this, "Login error", Toast.LENGTH_LONG);
            }
        });

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken,
                                                       AccessToken currentAccessToken) {

                if (currentAccessToken != null) {
                } else {
                    SessionManager session = new SessionManager(LoginActivity2.this);
                    session.logoutUser();
                    //mCallback.onLogin(0);
                    updateUI(false, false);
                    Intent resultIntent = new Intent();
                    setResult(Activity.RESULT_CANCELED, resultIntent);
                    finish();
                    //onBackPressed();
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //Google Login code
        gbtnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);
        gbtnSignOut = (Button) findViewById(R.id.btn_sign_out);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().requestProfile()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        gbtnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        gbtnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

    }

    public void onStart() {
        super.onStart();
        //checking fb sign in
        session= new SessionManager(this);
        String provider = session.getProvider();
        if(mGoogleApiClient != null ){
            mGoogleApiClient.connect();
        }
        //checking fb sign in
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if( accessToken != null){
            updateUI(true,false);
        }
        else if(provider!=null && provider.equals("google")){
            //checking google signin
            updateUI(true,true);
        }
        else {
            updateUI(false,false);
            LoginManager.getInstance().logOut();
        }

    }

    public void onStop(){
        if(mGoogleApiClient != null & mGoogleApiClient.isConnected()){
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onActivityResult(int requestCode, int responseCode,
                                 Intent intent) {
        super.onActivityResult(requestCode, responseCode, intent);
        Log.e("Fragment", "resukt");

        if (requestCode == RC_SIGN_IN) {
            //Google result
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(intent);
            handleSignInResult(result);
        }
        else{
            //Fb result
            callbackManager.onActivityResult(requestCode, responseCode, intent);
        }
    }

    //For google
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.getInstance().trackScreenView("Login/Logout Attempts");
    }

    //Google
    private void handleSignInResult(GoogleSignInResult result) {

        Log.e("Signin G+", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully
            GoogleSignInAccount acct = result.getSignInAccount();
            Log.d("Account Name", acct.getDisplayName() + " " + acct.getIdToken());
            new GPlusAuthToken(acct.getEmail()).execute();
        } else {
            // Signed out
            Toast.makeText(this,"Sign in error",Toast.LENGTH_LONG).show();

        }
    }
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }
    // [END signIn]

    // [START signOut]
    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        if(status.isSuccess()){
                            SessionManager session = new SessionManager(LoginActivity2.this);
                            session.logoutUser();
                            //mCallback.onLogin(0);
                            updateUI(false, true);
                            Intent resultIntent = new Intent();
// TODO Add extras or a data URI to this intent as appropriate.
                            setResult(Activity.RESULT_CANCELED, resultIntent);
                            finish();

                            //onBackPressed();
                        }
                    }
                });
    }

    private void updateUI(boolean isSignedIn,boolean isGoogle) {
        if(isGoogle){
            if (isSignedIn) {
                fbLoginButton.setVisibility(View.GONE);
                gbtnSignIn.setVisibility(View.GONE);
                gbtnSignOut.setVisibility(View.VISIBLE);

            } else {
                fbLoginButton.setVisibility(View.VISIBLE);
                gbtnSignIn.setVisibility(View.VISIBLE);
                gbtnSignOut.setVisibility(View.GONE);
            }
        }
        else{
            if (isSignedIn) {
                gbtnSignIn.setVisibility(View.GONE);
                gbtnSignOut.setVisibility(View.GONE);
            }
            else{
                gbtnSignIn.setVisibility(View.VISIBLE);
            }
        }

    }


    private class GPlusAuthToken extends AsyncTask<Void,Void,String> {

        String email;
        Context context;
        public GPlusAuthToken(String name) {
            this.email = name;
        }

        @Override
        protected String doInBackground(Void... params) {
            String accessToken = null;
            try {
                accessToken = GoogleAuthUtil.getToken(
                        LoginActivity2.this, email
                        , "oauth2:"
                                + Scopes.PLUS_LOGIN + " "
                                + Scopes.PROFILE + " " + Scopes.EMAIL);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (GoogleAuthException e) {
                e.printStackTrace();
            }
//            Log.d("G_ACCESS_TOKEN",accessToken);
            return accessToken;
        }
        @Override
        protected void onPostExecute(String result){
            Message msg = handler.obtainMessage();
            Bundle b =new Bundle();
            b.putString("Result",result);
            msg.setData(b);
            handler.sendMessage(msg);
        }

        private final Handler handler = new Handler(){
            public void handleMessage(Message message){
                network = new Network(LoginActivity2.this,"google");
                String result = message.getData().getString("Result");
                if(result != null)
                    network.authGoogleUser(message.getData().getString("Result"));
                else
                    Toast.makeText(LoginActivity2.this,"Error Signing in with google",Toast.LENGTH_LONG).show();

            }
        };
    }


    public class Network {

        String provider;
        User user;
        Context context;
        public Network(Context context,String provider) {
            this.context = context;
            this.provider = provider;
        }

        public void authFbUser(LoginResult loginResult){
            HashMap<String,String> map = new HashMap<>();
            map.put("access_token",loginResult.getAccessToken().getToken());
            PostEvent postEvent = new PostEvent(map);
            postEvent.execute("http://login.kurukshetra.org.in/auth/facebook");
            // return user;
        }

        public void authGoogleUser(String token){
            HashMap<String, String > map = new HashMap<>();
            map.put("access_token", token);
            PostEvent postEvent = new PostEvent(map);
            postEvent.execute("http://login.kurukshetra.org.in/auth/google");
            // return user;
        }

        public User createUser(String jsonStr) {

            if(!jsonStr.equals("error")) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    user = new User(jsonObj.getString("token"),jsonObj.getString("authentication_token"),
                            jsonObj.getString("id"),jsonObj.getString("kid"),jsonObj.getString("name"),
                            jsonObj.getString("sa_id"),provider);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else {
                user = null;
            }
            return user;
        }

        private class PostEvent extends AsyncTask<String, Void, String> {
            HashMap<String, String> postDataParams;
            private ProgressDialog dialog;
            private PostEvent( HashMap<String, String> postDataParams) {
                this.postDataParams=postDataParams;
                dialog = new ProgressDialog(LoginActivity2.this);
            }

            @Override
            protected void onPreExecute() {
                this.dialog.setMessage("Signing in ...");
                this.dialog.show();
            }

            @Override
            protected String doInBackground(String... urls) {
                String response = "";
                try {

                    String ur = urls[0];
                    URL url = new URL(ur);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));
                    writer.write(getPostDataString(postDataParams));
                    writer.flush();
                    writer.close();
                    os.close();
                    int responseCode=conn.getResponseCode();

                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                        String line;
                        BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        while ((line=br.readLine()) != null) {
                            response+=line;
                        }
                    }
                    else response="error";
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return response;
            }

            @Override
            protected void onPostExecute(String result) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                Log.d("abc", result + "XYZ");
                Message msg = handler.obtainMessage();
                Bundle b =new Bundle();
                b.putString("Result",result);
                msg.setData(b);
                handler.sendMessage(msg);
            }

            private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
                StringBuilder result = new StringBuilder();
                boolean first = true;
                for(Map.Entry<String, String> entry : params.entrySet()){
                    if (first)
                        first = false;
                    else
                        result.append("&");

                    result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                    result.append("=");
                    result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                }
                return result.toString();
            }

            private final Handler handler = new Handler() {

                public void handleMessage(Message msg){

                    SessionManager session= new SessionManager(LoginActivity2.this);
                    String json = msg.getData().getString("Result");
                    User user = createUser(json);
                    if(user != null)
                        session.createLoginSession(user.getToken(),user.getAuthentication_token(),user.getId(),
                                user.getKid(),user.getName(),user.getSa_id(),user.getProvider());
                    else
                        session.setProvider(provider);
                    Log.e("Session", "created login session");
                    Pushbots.sharedInstance().setAlias(user.getKid());
                    Intent resultIntent = new Intent();
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();

                    // mCallback.onLogin(1);
                    //onBackPressed();
                }
            };
        }
    }
}