package in.org.kurukshetra.app16.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by AkilAdeshwar on 03-01-2016.
 */
public class K16AuthenticatorService extends Service {

    // Instance field that stores the authenticator object
    private K16Authenticator mAuthenticator;

    @Override
    public void onCreate() {
        // Create a new authenticator object
        mAuthenticator = new K16Authenticator(this);
    }

    /*
     * When the system binds to this Service to make the RPC call
     * return the authenticator's IBinder.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
    
}
