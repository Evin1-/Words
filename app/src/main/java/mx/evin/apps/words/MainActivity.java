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
import android.widget.ProgressBar;

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

/**
 * Word's Main Activity.
 * The first activity loaded after the launcher call: handles the initial setup
 * of third party libraries, asks the {@link MainVM} class to start
 * observing this activity which handles the database retrieval from the cloud and UI updates,
 * retrieves the main data from the logged in user and system context and draws the concept's
 * information into the screen.
 * @author evin
 * @version %I%, %G%
 */
public class MainActivity extends AppCompatActivity {
    //TODO Set transparent background
    //TODO Optimize imports
    //TODO Sharing and opening on push notification
    //TODO Change layout on landscape
    //TODO Select technology at first
    //TODO Create login screen
    //TODO Add push notifications magic
    //TODO Add analytics
    //TODO Search by package
    //TODO Add back button
    //TODO Set max lines in docs TextView
    //TODO Create details view
    //TODO Service that updates db
    //TODO Add type of concept (class, interface, etc)
    //TODO Finish documentation
    //TODO Scroll to the top after new link

    /**
     * Tag used in logging.
     */
    private static final String TAG_ = "MainActivityTAG_";

    /**
     * The last term found in the system that the current user has visited.
     */
    private static final String LAST_TERM_KEY_ = Constants.LAST_TERM_KEY;

    /**
     * Google API key used to do the Custom Google Search retrieved from the app's meta-data
     */
    private static String GOOGLE_API_KEY;

    /**
     * Google custom key created for the Custom Search Engine retrieved from the app's meta-data
     */
    private static String GOOGLE_CUSTOM_SEARCH_KEY;

    /**
     * Unique Android ID
     */
    private String android_id;

    /**
     * The last technology found in the system that the current user has set.
     * Defaults to Android.
     */
    public static String mTechnology;

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private FrameLayout mMainFrame;
    private SharedPreferences mSharedPref;
    private ActionBar mActionBar;
    public ProgressBar mProgressBar;

    /**
     * Sets the Views and widgets used to its references in the layouts.
     * Retrieves the google keys needed to communicate with the APIs.
     * Configures the ActionBar to handle a DrawerLayout with a set menu.
     * Sets the default technology.
     * Initializes the login sequence with the {@link mx.evin.apps.words.viewmodel.LoginVM Login ViewModel}.
     * Initializes the main third party libraries and starting
     * setup with {@link mx.evin.apps.words.viewmodel.MainVM}
     * Sets the Main Fragment that the user will see
     *
     * @param savedInstanceState Saved bundle of a previous state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainVM.initializeThirdPartyLibs(this);

        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.a_main_drawer);
        mNavigationView = (NavigationView) findViewById(R.id.a_main_nav);
        mMainFrame = (FrameLayout) findViewById(R.id.a_main_frame);
        mProgressBar = (ProgressBar) findViewById(R.id.a_main_progress);

        mSharedPref = getSharedPreferences(Constants.PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        mTechnology = Constants.DEFAULT_TECHNOLOGY;

        if (savedInstanceState != null){
            Log.d(TAG_, "Previous instance found!");
        }

        retrieveGoogleKeys();
        configureActionBar();
        configureProgressBar();
        setTechnology();

        try {
            android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            android_id = Constants.DEFAULT_LOG_IN_USER;
            e.printStackTrace();
        } finally {
            LoginVM.loginSequence(this, android_id);
        }

        MainVM.initializeMain(this);

        setMainFragment();
    }

    /**
     * Simple configuration of the ProgressBar. Set it as Indeterminate
     */
    private void configureProgressBar() {
        mProgressBar.setIndeterminate(true);
    }

    /**
     * Overrides usual behavior to close DrawerLayout if it is open instead
     * of going back (closing application).
     */
    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * {@inheritDoc}
     * @param item {@inheritDoc}
     * @return boolean Returns true if selected item was called in the Drawer's layout otherwise
     * returns parent's logic (true to consume it here).
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Syncs the state of the DrawerLayout. {@inheritDoc}
     * @param savedInstanceState {@inheritDoc}
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    /**
     * Method called usually when a SearchTerm Fragment has returned with a selected Term.
     * Removes all the foreground Fragments. Sets the new MainFragment with the selected term and
     * technology.
     * @param intent {@inheritDoc}
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        removeUnusedFragments();
        setMainFragment();
    }

    /**
     * Removes all foreground Fragments ({@link mx.evin.apps.words.view.fragments.SearchTermFragment
     * SearchTermFragment} | {@link mx.evin.apps.words.view.fragments.SearchTermVoiceFragment
     * SearchTermVoiceFragment | ...}) using the {@link mx.evin.apps.words.viewmodel.utils.Constants
     * Constants} fragment tag).
     */
    private void removeUnusedFragments() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment addTermFragment = fragmentManager.findFragmentByTag(Constants.FRAGMENT_TAG);

        if (addTermFragment != null)
            fragmentManager.beginTransaction().remove(addTermFragment).commit();
    }

    /**
     * Sets the current Technology saved in the SharedPreferences.
     * Updates the ActionBar's subtitle with it.
     * Defaults to Android.
     */
    private void setTechnology() {
        mTechnology = mSharedPref.getString(Constants.TECHNOLOGY_USED_TAG, Constants.DEFAULT_TECHNOLOGY);
        mActionBar.setSubtitle(mTechnology);
    }

    /**
     * Configures the action bar with the home (hamburger) button to open the DrawerLayout.
     * Sets the current app toolbar to the created layout toolbar.
     * Creates the listener to watch for menu item clicks.
     */
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

    /**
     * Opens a new {@link mx.evin.apps.words.view.fragments.HistoryFragment HistoryFragment} in the
     * main container ({@link #mMainFrame FrameLayout})
     */
    private void setHistoryFragment() {
        String last_term = mSharedPref.getString(LAST_TERM_KEY_, "--");

        updateDrawerChecks(Constants.DRAWER_CHECK.HISTORY);

        if (last_term.equals("--")) {
            getSupportFragmentManager().beginTransaction().replace(mMainFrame.getId(), new StartingFragment()).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(mMainFrame.getId(), new HistoryFragment()).commit();
        }
    }

    /**
     * Creates alert when user tries to finish Activity from
     * the {@link #mDrawerLayout DrawerLayout}
     */
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

    /**
     * Creates a new Intent when the user tries to rate the app from
     * the {@link #mDrawerLayout DrawerLayout}. It first tries to access the market app in the
     * phone if this fails it will just open a new browser with
     * the Play Store app's name and package information page.
     */
    private void rateApp() {
        Uri uri = Uri.parse("market://details?id=" + getBaseContext().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);

        if (Build.VERSION.SDK_INT >= 21) {
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        } else {
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        }

        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getBaseContext().getPackageName())));
        }
    }

    /**
     * Creates a new {@link AboutFragment}.
     */
    private void showAbout() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(new AboutFragment(), Constants.ABOUT_FRAGMENT_TAG).commit();
    }

    /**
     * Either hides or shows the images after a click in a {@link #mDrawerLayout} menuItem.
     */
    private void setVisibilityImageRecycler() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.f_main_images_rv);
        MenuItem imageToggle = mNavigationView.getMenu().findItem(R.id.nav_images);
        if (recyclerView != null) {
            if (imageToggle.getTitle().equals(getString(R.string.nav_toggle_images_show))) {
                recyclerView.setVisibility(View.VISIBLE);
                imageToggle.setTitle(R.string.nav_toggle_images_hide);
            } else {
                recyclerView.setVisibility(View.GONE);
                imageToggle.setTitle(R.string.nav_toggle_images_show);
            }
        }
    }

    /**
     * Sets the MainFragment that the user will see contained in {@link #mMainFrame}. It will first
     * check if a term has already been navigated and show its info if it has, else, it
     * will show a generic "Get started" layout.
     */
    private void setMainFragment() {
        //TODO Update current fragment instead of creating a new one
        String last_term = mSharedPref.getString(LAST_TERM_KEY_, "--");

        updateDrawerChecks(Constants.DRAWER_CHECK.HOME);

        if (last_term.equals("--")) {
            getSupportFragmentManager().beginTransaction().replace(mMainFrame.getId(), new StartingFragment()).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(mMainFrame.getId(), new MainFragment()).commit();
        }
    }

    /**
     * Update the colored MenuItem of {@link #mNavigationView} depending of the user's current
     * view. Defaults to the Home view.
     * @param drawerCheck Different options to color the item depending on the view. Defined
     *                    in {@link mx.evin.apps.words.viewmodel.utils.Constants.DRAWER_CHECK}.
     */
    private void updateDrawerChecks(Constants.DRAWER_CHECK drawerCheck) {
        Menu menu = mNavigationView.getMenu();
        MenuItem menuItem;

        switch (drawerCheck) {
            case HISTORY:
                menuItem = menu.findItem(R.id.nav_history);
                break;
            default:
                menuItem = menu.findItem(R.id.nav_home);
        }

        menuItem.setChecked(true);
    }

    /**
     * Retrieves the Google keys needed: {@link #GOOGLE_API_KEY} and {@link
     * #GOOGLE_CUSTOM_SEARCH_KEY} from the app's metadata set in the AndroidManifest.
     */
    private void retrieveGoogleKeys() {
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

    /**
     * Creates a new Fragment to search for a term
     * ({@link mx.evin.apps.words.view.fragments.SearchTermFragment
     * SearchTermFragment} | {@link mx.evin.apps.words.view.fragments.SearchTermVoiceFragment
     * SearchTermVoiceFragment | ...}) on the FragmentManager setting it with a tag defined
     * in {@link Constants}
     * @param type_add The type of searching it was called. Defined
     *                 in {@link mx.evin.apps.words.viewmodel.utils.Constants.TYPE_ADD}
     */
    public void addTermGeneric(Constants.TYPE_ADD type_add) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment addTermFragment;
        switch (type_add) {
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

    /**
     * Search for a term from the local/remote database filtering its values with an EditText.
     * It creates a new {@link SearchTermFragment}.
     * @param view The View that created the event. Usually the Search a Term... button, the pen
     *             in the bottom menu or the "Getting started" message.
     */
    public void search_term(View view) {
        addTermGeneric(Constants.TYPE_ADD.WRITTEN);
    }

    /**
     * Search for a term from the local/remote database filtering its values with a SpeechRecognition
     * engine defined in {@link mx.evin.apps.words.viewmodel.utils.VoiceRecognizer}.
     * It creates a new {@link SearchTermVoiceFragment}.
     * @param view The View that created the event. Usually the microphone button at
     *             the bottom menu.
     */
    public void search_term_voice(View view) {
        addTermGeneric(Constants.TYPE_ADD.SPOKEN);
    }

    /**
     * Search for a term with a Custom Google Search. Using {@link #GOOGLE_CUSTOM_SEARCH_KEY}
     * and {@link #GOOGLE_API_KEY} to make a Retrofit call to the results. It creates a
     * new {@link SearchTermGoogleFragment}.
     * @param view The View that created the event. Usually the magnifier button at
     *             the bottom menu.
     * @see <a href= "https://developers.google.com/custom-search/docs/overview"/>Google's
     * Custom Search docs</a>
     */
    public void search_term_google(View view) {
        addTermGeneric(Constants.TYPE_ADD.GOOGLED);
    }

    /**
     * Creates a new AsyncTask defined in {@link CustomSearchAsyncTask} when a user has
     * called a Custom Google Search from {@link SearchTermGoogleFragment}.
     * @param view The View that created the event. Usually the "Search" button
     *             at {@link SearchTermGoogleFragment}.
     */
    public void start_custom_google_search(View view) {
        //TODO Remove focus from EditText
        String searchTerm = SearchTermGoogleFragment.searchTerm;
        if (searchTerm.length() > 0) {
            SearchTermGoogleFragment.mItems.clear();
            new CustomSearchAsyncTask().execute(SearchTermGoogleFragment.searchTerm, GOOGLE_API_KEY, GOOGLE_CUSTOM_SEARCH_KEY);
        }
    }

    /**
     * Creates a new Intent to open a term in a WebView contained in {@link WebActivity}.
     * It passes the main url and its title.
     * @param view The View that created the event. Usually a CardView in
     *             a {@link SearchTermGoogleFragment} or a link
     *             in the docs TextView from the {@link MainFragment}.
     */
    public void start_web_activity(View view) {
        Intent intent = new Intent(this, WebActivity.class);

        intent.putExtra(Constants.TITLE_WEB_KEY, MainVM.mCurrentTerm.getWords());
        intent.putExtra(Constants.URL_WEB_KEY, MainVM.mCurrentTerm.getUrl());
        startActivity(intent);
    }
}
