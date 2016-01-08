package mx.evin.apps.words;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;

import mx.evin.apps.words.model.entities.Technology;
import mx.evin.apps.words.model.entities.UserTechnology;
import mx.evin.apps.words.viewmodel.LoginHelper;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_ = "MainActivityTAG_";
    private ParseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Parse.enableLocalDatastore(this);

        ParseObject.registerSubclass(Technology.class);
        ParseObject.registerSubclass(UserTechnology.class);

        Parse.initialize(this);

        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();

        LoginHelper.loginSequence(this);
    }

    public void userReady() {
        user = ParseUser.getCurrentUser();
        Log.d(TAG_, user.getUsername());
    }

    @Override
    protected void onStop() {
        ParseUser.logOut();
        super.onStop();
    }
}
