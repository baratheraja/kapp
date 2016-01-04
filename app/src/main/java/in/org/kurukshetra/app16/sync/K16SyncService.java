package in.org.kurukshetra.app16.sync;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class K16SyncService extends Service {

    private static final Object sSyncAdapterLock = new Object();
    private static K16SyncAdapter sK16SyncAdapter = null;

    @Override
    public void onCreate() {
        Log.d("K16SyncService", "onCreate - K16SyncService");
        synchronized (sSyncAdapterLock) {
            if (sK16SyncAdapter == null) {
                sK16SyncAdapter = new K16SyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sK16SyncAdapter.getSyncAdapterBinder();
    }


}