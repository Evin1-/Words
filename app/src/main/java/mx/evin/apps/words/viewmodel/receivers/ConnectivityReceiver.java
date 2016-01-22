package mx.evin.apps.words.viewmodel.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import mx.evin.apps.words.viewmodel.services.DataService;

public class ConnectivityReceiver extends BroadcastReceiver {
    private static final String TAG_ = "ConnectivityTAG_";
    private static boolean isReceiving = false;

    public ConnectivityReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!isMyServiceRunning() && isWifiConnected(context) && !isReceiving){
            isReceiving = true;
            DataService.isRunning = true;
            Intent broadcastIntent = new Intent(context, DataService.class);
            context.startService(broadcastIntent);
        }
    }

    private boolean isWifiConnected(Context context){
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connManager.getActiveNetworkInfo();

        return  activeNetwork != null && activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
    }

    private boolean isMyServiceRunning() {
        return DataService.isRunning;
    }
}
