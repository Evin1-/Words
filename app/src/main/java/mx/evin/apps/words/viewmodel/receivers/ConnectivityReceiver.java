package mx.evin.apps.words.viewmodel.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class ConnectivityReceiver extends BroadcastReceiver {
    private static final String TAG_ = "ConnectivityTAG_";

    public ConnectivityReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
//        context.getconn
        Log.d(TAG_, "Connected!: " + isWifiConnected(context));
    }

    private boolean isWifiConnected(Context context){
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connManager.getActiveNetworkInfo();

        return  activeNetwork != null && activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
    }
}
