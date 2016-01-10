package mx.evin.apps.words;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;

import com.parse.ParseUser;

import mx.evin.apps.words.viewmodel.LoginVM;
import mx.evin.apps.words.viewmodel.ParseVM;
import mx.evin.apps.words.viewmodel.StartupVM;

public class MainActivity extends AppCompatActivity {
    //TODO Set transparent background
    //TODO Remove easyLife
    //TODO Optimize imports
    private static final String TAG_ = "MainActivityTAG_";
    private ParseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ParseVM.parseStart(this);
        LoginVM.loginSequence(this);

        easyLife();
    }

    private void easyLife() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.a_main_drawer);
        drawerLayout.openDrawer(Gravity.LEFT);
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
