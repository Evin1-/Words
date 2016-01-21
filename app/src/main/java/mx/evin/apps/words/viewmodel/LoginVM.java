package mx.evin.apps.words.viewmodel;

import android.util.Log;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import mx.evin.apps.words.MainActivity;

/**
 * Created by evin on 1/8/16.
 */
public class LoginVM {
    //TODO Login with Facebook, Twitter and Google+
    private static final String TAG_ = "ViewModelTAG_";
    private static String mAndroidId;

    public static void loginSequence(MainActivity activity, String androidId) {
        mAndroidId = androidId;
        ParseUser user = ParseUser.getCurrentUser();
        if (user == null) {
            Log.d(TAG_, "Not logged in");
            Log.d(TAG_, "Starting login sequence...");
            TryLogin(activity);
        } else {
            Log.d(TAG_, "Logged in " + user.getUsername());
        }
    }

    private static void TryLogin(final MainActivity activity) {
        ParseUser.logInInBackground(mAndroidId, "my_pass4", new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    Log.d(TAG_, "Logged in " + user.getUsername());
                } else {
                    Log.d(TAG_, "Error: User NOT logged in");
                    Log.d(TAG_, e.toString());
                    startSignUp(activity);
                }
            }
        });
    }

    private static void startSignUp(final MainActivity activity) {
        final ParseUser user = new ParseUser();
        user.setUsername(mAndroidId);
        user.setPassword("my_pass4");
        user.setEmail(mAndroidId + "@example.com");

        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Log.d(TAG_, "User signed up " + mAndroidId);
                } else {
                    Log.d(TAG_, "Error: User NOT signed up");
                    Log.d(TAG_, e.toString());
                }
            }
        });
    }


}
