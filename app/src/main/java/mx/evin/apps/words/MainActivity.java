package mx.evin.apps.words;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.parse.ParseUser;

import mx.evin.apps.words.viewmodel.LoginVM;
import mx.evin.apps.words.viewmodel.ParseVM;

public class MainActivity extends AppCompatActivity {
    //TODO Set transparent background
    //TODO Remove easyLife
    //TODO Optimize imports
    //TODO Sharing and opening on push notification
    private static final String TAG_ = "MainActivityTAG_";
    private ParseUser mUser;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureActionBar();

        ParseVM.parseStart(this);
        LoginVM.loginSequence(this);

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

    private void configureActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.a_main_drawer);
        mNavigationView = (NavigationView) findViewById(R.id.a_main_nav);

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

    private void easyLife() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.a_main_drawer);
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void userReady() {
        mUser = ParseUser.getCurrentUser();
        Log.d(TAG_, mUser.getUsername());

//        StartupVM.firstTimeSetup();
    }
}
