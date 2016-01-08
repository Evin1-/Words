package mx.evin.apps.words;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import mx.evin.apps.words.model.entities.Technology;
import mx.evin.apps.words.model.entities.UserTechnology;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_ = "MainActivityTAG_";

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

        ParseUser user = new ParseUser();
        user.setUsername("my_name34");
        user.setPassword("my_pass4");
        user.setEmail("email334@example.com");

        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Log.d(TAG_, "User logged in");
                    Technology technology = new Technology();
                    technology.setName("Android");
                    technology.saveInBackground();

                    ParseUser currentUser = ParseUser.getCurrentUser();

                    UserTechnology userTechnology = new UserTechnology();
                    userTechnology.setTechnology(technology);
                    userTechnology.setUser(currentUser);
                    userTechnology.saveInBackground();
                } else {
                    Log.d(TAG_, "Error: User NOT logged in");
                    Log.d(TAG_, e.toString());
                }
            }
        });
    }
}
