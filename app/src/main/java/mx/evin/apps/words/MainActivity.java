package mx.evin.apps.words;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.parse.ParseUser;

import mx.evin.apps.words.view.fragments.AddTermFragment;
import mx.evin.apps.words.view.fragments.MainFragment;
import mx.evin.apps.words.view.fragments.StartingFragment;
import mx.evin.apps.words.viewmodel.LoginVM;
import mx.evin.apps.words.viewmodel.MainVM;
import mx.evin.apps.words.viewmodel.ParseVM;
import mx.evin.apps.words.viewmodel.StartupVM;
import mx.evin.apps.words.viewmodel.utils.Constants;

public class MainActivity extends AppCompatActivity {
    //TODO Set transparent background
    //TODO Remove easyLife
    //TODO Optimize imports
    //TODO Sharing and opening on push notification
    //TODO Change layout on landscape
    //TODO Select technology at first
    private static final String TAG_ = "MainActivityTAG_";
    private static final String LAST_TERM_KEY_ = Constants.LAST_TERM_KEY;
    private ParseUser mUser;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private FrameLayout mMainFragment;
    private SharedPreferences mSharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.a_main_drawer);
        mNavigationView = (NavigationView) findViewById(R.id.a_main_nav);
        mMainFragment = (FrameLayout) findViewById(R.id.a_main_frame);
        mSharedPref = getSharedPreferences(Constants.PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);

        configureActionBar();

        ParseVM.parseStart(this);
        LoginVM.loginSequence(this);
        MainVM.initializeMain();

        setMainFragment();

        //TODO Remove in production
        easyLife();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    protected void onStop() {
        ParseUser.logOut();
        super.onStop();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG_, "onNewIntent");
        removeUnusedFragments();
    }

    private void removeUnusedFragments() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        AddTermFragment addTermFragment = (AddTermFragment) fragmentManager.findFragmentByTag(Constants.FRAGMENT_TAG_TYPE);

        if (addTermFragment != null)
            fragmentManager.beginTransaction().remove(addTermFragment).commit();
    }

    private void configureActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_technology:
                        Log.d(TAG_, "Technology");
                        break;
                    case R.id.nav_history:
                        Log.d(TAG_, "History");
                        break;
                }

                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void setMainFragment() {
        String last_term = mSharedPref.getString(LAST_TERM_KEY_, "--");
        if (last_term.equals("--")){
            Log.d(TAG_, "NONE");
            getSupportFragmentManager().beginTransaction().replace(mMainFragment.getId(), new StartingFragment()).commit();
        }else {
            Log.d(TAG_, "SOME");
            getSupportFragmentManager().beginTransaction().replace(mMainFragment.getId(), new MainFragment()).commit();
        }
    }

    private void easyLife() {
//        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.a_main_drawer);
//        drawerLayout.openDrawer(GravityCompat.START);
        findViewById(R.id.a_main_search_type_btn).callOnClick();
    }

    public void addTermType(){
        FragmentManager fm = getSupportFragmentManager();
        AddTermFragment addTermFragment = new AddTermFragment();
        addTermFragment.show(fm, Constants.FRAGMENT_TAG_TYPE);
    }

    public void userReady() {
        mUser = ParseUser.getCurrentUser();
        Log.d(TAG_, mUser.getUsername());

//        StartupVM.firstTimeSetup();
    }

    public void add_term(View view) {
        addTermType();
    }
}
