package mx.evin.apps.words;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.parse.ParseUser;

import mx.evin.apps.words.view.fragments.AboutFragment;
import mx.evin.apps.words.view.fragments.HistoryFragment;
import mx.evin.apps.words.view.fragments.MainFragment;
import mx.evin.apps.words.view.fragments.SearchTermFragment;
import mx.evin.apps.words.view.fragments.SearchTermGoogleFragment;
import mx.evin.apps.words.view.fragments.SearchTermVoiceFragment;
import mx.evin.apps.words.view.fragments.StartingFragment;
import mx.evin.apps.words.viewmodel.LoginVM;
import mx.evin.apps.words.viewmodel.MainVM;
import mx.evin.apps.words.viewmodel.async.CustomSearchAsyncTask;
import mx.evin.apps.words.viewmodel.utils.Constants;

public class MainActivity extends AppCompatActivity {
    //TODO Set transparent background
    //TODO Remove easyLife
    //TODO Optimize imports
    //TODO Sharing and opening on push notification
    //TODO Change layout on landscape
    //TODO Select technology at first
    //TODO Make it offline
    //TODO Work with drawer layout options
    //TODO Create login screen
    //TODO Add push notifications magic
    //TODO Add analytics
    //TODO Add crashalytics
    //TODO Search by package
    //TODO Check double item bug in mItems
    //TODO Add back button
    //TODO Set max lines Docs textview and details view
    //TODO Service that updates db
    //TODO Add type of concept (class, interface, etc)

    private static final String TAG_ = "MainActivityTAG_";
    private static final String LAST_TERM_KEY_ = Constants.LAST_TERM_KEY;
    private static String GOOGLE_API_KEY;
    private static String GOOGLE_CUSTOM_SEARCH_KEY;
    private String android_id;
    public static String mTechnology;

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private FrameLayout mMainFrame;
    private SharedPreferences mSharedPref;
    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainVM.initializeThirdPartyLibs(this);

        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.a_main_drawer);
        mNavigationView = (NavigationView) findViewById(R.id.a_main_nav);
        mMainFrame = (FrameLayout) findViewById(R.id.a_main_frame);

        mSharedPref = getSharedPreferences(Constants.PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        mTechnology = Constants.DEFAULT_TECHNOLOGY;

        retrieveGoogleKeys();
        configureActionBar();
        setTechnology();

        try {
            android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            android_id = "user_123";
            e.printStackTrace();
        } finally {
            LoginVM.loginSequence(this, android_id);
        }

        MainVM.initializeMain();

        setMainFragment();

//        //TODO Remove in production
//        easyLife();

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
        removeUnusedFragments();
        setMainFragment();
    }

    private void removeUnusedFragments() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment addTermFragment = fragmentManager.findFragmentByTag(Constants.FRAGMENT_TAG);

        if (addTermFragment != null)
            fragmentManager.beginTransaction().remove(addTermFragment).commit();
    }

    private void setTechnology() {
//        mActionBar.s
        mTechnology = mSharedPref.getString(Constants.TECHNOLOGY_USED_TAG, Constants.DEFAULT_TECHNOLOGY);
        mActionBar.setSubtitle(mTechnology);
    }

    private void configureActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.a_main_toolbar);
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();

        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setHomeButtonEnabled(true);
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
                    case R.id.nav_home:
                        setMainFragment();
                        break;
                    case R.id.nav_history:
                        setHistoryFragment();
                        break;
                    case R.id.nav_images:
                        setVisibilityImageRecycler();
                        break;
                    case R.id.nav_about:
                        showAbout();
                        break;
                    case R.id.nav_rate:
                        rateApp();
                        break;
                    case R.id.nav_logout:
                        startFinishingActivity();
                        break;
                }

                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void setHistoryFragment() {
        String last_term = mSharedPref.getString(LAST_TERM_KEY_, "--");

        updateDrawerChecks(Constants.DRAWER_CHECK.HISTORY);

        if (last_term.equals("--")){
            getSupportFragmentManager().beginTransaction().replace(mMainFrame.getId(), new StartingFragment()).commit();
        }else {
            getSupportFragmentManager().beginTransaction().replace(mMainFrame.getId(), new HistoryFragment()).commit();
        }
    }

    private void startFinishingActivity() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.a_main_dialog_confirm_exit));
        builder.setPositiveButton(getString(android.R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        builder.setNegativeButton(getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Snackbar snackbar = Snackbar
                        .make(mNavigationView, getString(R.string.a_main_dialog_thanks), Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void rateApp() {
        Uri uri = Uri.parse("market://details?id=" + getBaseContext().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);

        if (Build.VERSION.SDK_INT >= 21){
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        }else {
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        }

        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getBaseContext().getPackageName())));
        }
    }

    private void showAbout() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(new AboutFragment(), Constants.ABOUT_FRAGMENT_TAG).commit();
    }

    private void setVisibilityImageRecycler() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.f_main_images_rv);
        MenuItem imageToggle = mNavigationView.getMenu().findItem(R.id.nav_images);
        if (recyclerView != null){
            if (imageToggle.getTitle().equals(getString(R.string.nav_toggle_images_show))) {
                recyclerView.setVisibility(View.VISIBLE);
                imageToggle.setTitle(R.string.nav_toggle_images_hide);
            }else {
                recyclerView.setVisibility(View.GONE);
                imageToggle.setTitle(R.string.nav_toggle_images_show);
            }
        }
    }

    private void setMainFragment() {
        //TODO Update current fragment instead of creating a new one
        String last_term = mSharedPref.getString(LAST_TERM_KEY_, "--");

        updateDrawerChecks(Constants.DRAWER_CHECK.HOME);

        if (last_term.equals("--")){
            getSupportFragmentManager().beginTransaction().replace(mMainFrame.getId(), new StartingFragment()).commit();
        }else {
            MainVM.refreshCurrentTermById(last_term, this);
            getSupportFragmentManager().beginTransaction().replace(mMainFrame.getId(), new MainFragment()).commit();
        }
    }

    private void updateDrawerChecks(Constants.DRAWER_CHECK drawerCheck) {
        Menu menu = mNavigationView.getMenu();
        MenuItem menuItem;

        switch (drawerCheck){
            case HISTORY:
                menuItem = menu.findItem(R.id.nav_history);
                break;
            default:
                menuItem = menu.findItem(R.id.nav_home);
        }

        menuItem.setChecked(true);
    }

    private void easyLife() {
//        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.a_main_drawer);
//        drawerLayout.openDrawer(GravityCompat.START);
//        findViewById(R.id.a_main_search_type_btn).callOnClick();
//        findViewById(R.id.a_main_search_talk_icon).callOnClick();
//        findViewById(R.id.a_main_search_google_icon).callOnClick();
    }

    private void retrieveGoogleKeys(){
        try {
            ApplicationInfo ai = getPackageManager().getApplicationInfo(this.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            GOOGLE_API_KEY = bundle.getString(Constants.GOOGLE_API_KEY_TAG);
            GOOGLE_CUSTOM_SEARCH_KEY = bundle.getString(Constants.GOOGLE_CUSTOM_SEARCH_KEY_TAG);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG_, "Failed to load meta-data, NameNotFound: " + e.getMessage());
        } catch (NullPointerException e) {
            Log.e(TAG_, "Failed to load meta-data, NullPointer: " + e.getMessage());
        }
    }

    public void addTermGeneric(Constants.TYPE_ADD type_add){
        FragmentManager fm = getSupportFragmentManager();
        Fragment addTermFragment;
        switch (type_add){
            case SPOKEN:
                addTermFragment = new SearchTermVoiceFragment();
                break;
            case GOOGLED:
                addTermFragment = new SearchTermGoogleFragment();
                break;
            default:
                addTermFragment = new SearchTermFragment();
        }
        fm.beginTransaction().add(addTermFragment, Constants.FRAGMENT_TAG).commit();
    }

    public void userReady() {
//        StartupVM.firstTimeSetup();
    }

    public void search_term(View view) {
        addTermGeneric(Constants.TYPE_ADD.WRITTEN);
    }

    public void search_term_voice(View view) {
        addTermGeneric(Constants.TYPE_ADD.SPOKEN);
    }

    public void search_term_google(View view) {
        addTermGeneric(Constants.TYPE_ADD.GOOGLED);
    }

    public void start_custom_google_search(View view) {
        //TODO Remove focus from EditText
        String searchTerm = SearchTermGoogleFragment.searchTerm;
        if (searchTerm.length() > 0){
            SearchTermGoogleFragment.mItems.clear();
            new CustomSearchAsyncTask().execute(SearchTermGoogleFragment.searchTerm, GOOGLE_API_KEY, GOOGLE_CUSTOM_SEARCH_KEY);
        }
    }

    public void start_web_activity(View view) {
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra(Constants.TITLE_WEB_KEY, MainVM.mCurrentTerm.getWords());
        intent.putExtra(Constants.URL_WEB_KEY, MainVM.mCurrentTerm.getUrl());
        startActivity(intent);
    }
}
