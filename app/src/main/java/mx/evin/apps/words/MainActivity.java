package mx.evin.apps.words;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.parse.ParseUser;

import mx.evin.apps.words.viewmodel.LoginVM;
import mx.evin.apps.words.viewmodel.ParseVM;
import mx.evin.apps.words.viewmodel.StartupVM;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_ = "MainActivityTAG_";
    private ParseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParseVM.parseStart(this);
        LoginVM.loginSequence(this);
    }

    public void userReady() {
        user = ParseUser.getCurrentUser();
        Log.d(TAG_, user.getUsername());

//        StartupVM.firstTimeSetup();
    }

    @Override
    protected void onStop() {
        ParseUser.logOut();
        super.onStop();
    }
}
