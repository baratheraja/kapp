package in.org.kurukshetra.app16.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import in.org.kurukshetra.app16.HomeActivity;
import in.org.kurukshetra.app16.R;

/**
 * Created by AkilAdeshwar on 03-01-2016.
 */

//
//Work to done during sync is implemented in this class.
//
public class K16SyncAdapter extends AbstractThreadedSyncAdapter {

    public static final int SYNC_INTERVAL = 60 * 180;
    public static final int SYNC_FLEXTIME = SYNC_INTERVAL/3;
    public static final String TAG = K16SyncAdapter.class.getSimpleName();

    Context context;

    public K16SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        this.context = context;
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Log.i(TAG, "Sync With Server Started..");
        syncWithServer();
        Log.i(TAG, "Sync With Server Complete. Data up to date.");
    }


    public void syncWithServer() {

        for (int i = 0; i < HomeActivity.ASSET_SUB_DIR.length; i++) {

            String[] files  = getFilesFromSubDir(HomeActivity.ASSET_SUB_DIR[i]);

	        for (String file : files) downloadAndUpdateFile (HomeActivity.ASSET_SUB_DIR[i], file);
        }
    }

    public String[] getFilesFromSubDir(String subDir){
        AssetManager assetManager = context.getAssets();
        String[] files;
        try {
            files = assetManager.list(subDir);
        }
        catch (Exception e){
            files = null;
        }
        return files;
    }

    public void downloadAndUpdateFile(String subDir, String fileName){


        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String BASE_URL = "http://cms.kurukshetra.org.in";
        String jsonResult =  null;

        try {
            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendPath(subDir)
                    .appendPath(fileName)
                    .build();

            URL url = new URL(builtUri.toString());

            Log.i(TAG, "Requesting: " + url.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder ();
            if (inputStream == null) {
                // Nothing to do.
                return;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append (line).append ("\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                Log.i(TAG, "Status: server down");
                return;
            }
            jsonResult = buffer.toString();

            Log.i(TAG, subDir +" --> " + fileName +" \n" + jsonResult);
        } catch (IOException e) {
            Log.e(TAG, "mError ", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(TAG, "Error closing stream", e);
                }
            }
        }
        if(jsonResult!=null) {
            updateFile(subDir, fileName, jsonResult);
        }
        else{
            Log.e(TAG,"network error");
        }
    }


    public void updateFile(String subDir,String fileName,String json) {

        try {
            File oldFile = new File(Environment.getExternalStorageDirectory() + "/k16/" + subDir, fileName);
            oldFile.delete();


            File newFile = new File(Environment.getExternalStorageDirectory() + "/k16/" + subDir, fileName);

            PrintWriter out = new PrintWriter(newFile);
            out.write(json);
            out.close();

            Log.i(TAG,"File Update Completed: "+subDir+" -> "+fileName);
        }
        catch (Exception e){
            Log.i(TAG,e.toString());
        }
    }

    public static void initializeSyncAdapter(Context context) {
        getSyncAccount(context);
    }

    public static void syncImmediately(Context context) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context),
                context.getString(R.string.content_authority), bundle);
    }


    public static Account getSyncAccount(Context context) {
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account(
                context.getString(R.string.app_name), context.getString(R.string.sync_account_type));

        // If the password doesn't exist, the account doesn't exist
        if ( null == accountManager.getPassword(newAccount) ) {

        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call ContentResolver.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */

            onAccountCreated(newAccount, context);
        }
        return newAccount;
    }

    private static void onAccountCreated(Account newAccount, Context context) {
        /*
         * Since we've created an account
         */
        K16SyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);

        /*
         * Without calling setSyncAutomatically, our periodic sync will not be enabled.
         */

        ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority), true);

        /*
         * Finally, let's do a sync to get things started
         */
        syncImmediately(context);
    }

    public static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {

        Account account = getSyncAccount(context);
        String authority = context.getString(R.string.content_authority);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // we can enable inexact timers in our periodic sync
            SyncRequest request = new SyncRequest.Builder().
                    syncPeriodic(syncInterval, flexTime).
                    setSyncAdapter(account, authority).
                    setExtras(new Bundle()).build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account,
                    authority, new Bundle(), syncInterval);
        }

    }

}
