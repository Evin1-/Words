package mx.evin.apps.words.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import mx.evin.apps.words.MainActivity;
import mx.evin.apps.words.R;
import mx.evin.apps.words.WebActivity;
import mx.evin.apps.words.model.entities.parse.Img;
import mx.evin.apps.words.model.entities.parse.Pack;
import mx.evin.apps.words.model.entities.parse.Term;
import mx.evin.apps.words.model.entities.parse.TermTerm;
import mx.evin.apps.words.view.fragments.MainFragment;
import mx.evin.apps.words.view.fragments.SearchTermFragment;
import mx.evin.apps.words.view.fragments.SearchTermVoiceFragment;
import mx.evin.apps.words.viewmodel.utils.Constants;
import mx.evin.apps.words.viewmodel.utils.MyTagHandler;

/**
 * Created by evin on 1/10/16.
 */
public class MainVM {
    //TODO Check that 2 offline calls are not at the same time
    private static final String TAG_ = "MainVMTAG_";
    public static ArrayList<Term> mTerms;
    public static ArrayList<Term> termsHistory;
    public static Term mCurrentTerm;
    public static Context mCurrentContext;

    static {
        mTerms = new ArrayList<>();
        termsHistory = new ArrayList<>();
    }

    public static void initializeMain() {
        initializeTerms();
    }

    private static void initializeTerms() {
        ParseQuery<ParseObject> query = new ParseQuery<>("Term");
        query.fromLocalDatastore();
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects == null || objects.size() == 0) {
                        initializeTermsOnline();
                    } else {
                        updateTerms(objects);
                    }
                } else {
                    initializeTermsOnline();
                }
            }
        });
    }

    private static void updateTerms(List<ParseObject> objects) {
        ParseObject.pinAllInBackground(objects);
        for (ParseObject object : objects) {
            notifyAdapters((Term) object);
            object.getParseObject("pack").fetchInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    if (e == null){
                        object.pinInBackground();
                    }
                }
            });
        }
    }

    private static void notifyAdapters(Term term){
        if (term != null){
            mTerms.add(term);
        }
        SearchTermFragment.mAdapter.notifyDataSetChanged();
        SearchTermVoiceFragment.mAdapter.notifyDataSetChanged();
    }

    private static void initializeTermsOnline(){
        ParseQuery<ParseObject> query = new ParseQuery<>("Term");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    updateTerms(objects);
                } else {
                    Log.d(TAG_, "TermsOnline + " + e.toString());
                }
            }
        });
    }

    public static ArrayList<Term> getTerms() {
        return mTerms;
    }

    public static void refreshMainFragment(final Activity activity) {
        TextView textViewDoc = (TextView) activity.findViewById(R.id.f_main_doc_txt);
        final TextView textViewPack = (TextView) activity.findViewById(R.id.f_main_pack_txt);
        TextView textViewTitle = (TextView) activity.findViewById(R.id.f_main_title_txt);
        TextView textViewHierarchy = (TextView) activity.findViewById(R.id.f_main_hierarchy_txt);
        TextView textURL = (TextView) activity.findViewById(R.id.f_main_url_txt);

        refreshRelatedTerms();
        refreshImages();
        refreshHistory();
        refreshShared();

        MainActivity mainActivity = (MainActivity) activity;
        ActionBar actionBar = mainActivity.getSupportActionBar();
        if (actionBar != null)
            actionBar.setSubtitle(MainActivity.mTechnology + " | " + mCurrentTerm.getWords());

        textViewDoc.setText(setTextViewHTML(mCurrentTerm.getDocs(), Constants.TYPE_HTML.BODY));
        textViewDoc.setLinksClickable(true);
        textViewDoc.setMovementMethod(LinkMovementMethod.getInstance());

        textViewHierarchy.setText(setTextViewHTML(mCurrentTerm.getHierarchy(), Constants.TYPE_HTML.HIERARCHY));
        textViewHierarchy.setLinksClickable(true);
        textViewHierarchy.setMovementMethod(LinkMovementMethod.getInstance());

        mCurrentTerm.getPack().fetchIfNeededInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null){
                    Pack pack = ((Pack) object);
                    textViewPack.setText(pack.getName());
                }else {
                    textViewPack.setText(activity.getString(R.string.f_main_package_placeholder));
                }
            }
        });

        textViewTitle.setText(mCurrentTerm.getWords());
        textURL.setText(mCurrentTerm.getUrl());

    }

    private static void refreshShared() {
        SharedPreferences sharedPreferences = mCurrentContext.getSharedPreferences(Constants.PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.LAST_TERM_KEY, mCurrentTerm.getObjectId());
        editor.apply();
    }

    private static void refreshHistory() {
        if (termsHistory != null && termsHistory.size() > 0){
            if (termsHistory.get(termsHistory.size() - 1).equals(mCurrentTerm)){
                return;
            }
        }
        if (termsHistory != null){
            termsHistory.add(mCurrentTerm);
        }
    }

    protected static Spanned setTextViewHTML(String html, Constants.TYPE_HTML type_html) {
        CharSequence sequence = preFormatHTML(html, type_html);
        SpannableStringBuilder strBuilder = new SpannableStringBuilder(sequence);
        URLSpan[] urls = strBuilder.getSpans(0, sequence.length(), URLSpan.class);
        for (URLSpan span : urls) {
            makeLinkClickable(strBuilder, span);
        }
        return strBuilder;
    }

    private static CharSequence preFormatHTML(String html, Constants.TYPE_HTML type_html) {
        if (html == null || html.length() < 1){
            return "";
        }

        if (type_html == Constants.TYPE_HTML.HIERARCHY){
            html = html.replace("</tr>\n    \n\n    <tr>", "<br>");
        }

        return Html.fromHtml(html, null, new MyTagHandler());
    }

    protected static void makeLinkClickable(final SpannableStringBuilder strBuilder, final URLSpan span) {
        final int start = strBuilder.getSpanStart(span);
        final int end = strBuilder.getSpanEnd(span);
        int flags = strBuilder.getSpanFlags(span);
        ClickableSpan clickable = new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);

                int intColor = (Build.VERSION.SDK_INT >= 23) ?
                        ContextCompat.getColor(mCurrentContext, R.color.color_primary_dark) :
                        mCurrentContext.getResources().getColor(R.color.color_primary_dark);

                ds.setUnderlineText(false);
                ds.setColor(intColor);
            }

            public void onClick(View view) {
                char[] aux = new char[end - start];
                strBuilder.getChars(start, end, aux, 0);
                refreshCurrentTermByName(new String(aux), mCurrentContext, span.getURL());

            }
        };
        strBuilder.setSpan(clickable, start, end, flags);
        strBuilder.removeSpan(span);
    }

    private static void refreshRelatedTerms() {
        ParseQuery<ParseObject> query1 = new ParseQuery<>("TermTerm");
        ArrayList<ParseQuery<ParseObject>> parseQueries = new ArrayList<>();
        ParseQuery<ParseObject> query2 = new ParseQuery<>("TermTerm");

        MainFragment.mTerms.clear();

        query1.whereEqualTo("term1", mCurrentTerm);
        query2.whereEqualTo("term2", mCurrentTerm);
        parseQueries.add(query1);
        parseQueries.add(query2);

        ParseQuery<ParseObject> orQuery = ParseQuery.or(parseQueries);
        orQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (ParseObject parseObject : objects) {
                        parseObject.pinInBackground();
                        TermTerm termTerm = (TermTerm) parseObject;
                        termTerm.getTerm1().fetchIfNeededInBackground(new GetCallback<ParseObject>() {
                            @Override
                            public void done(ParseObject object, ParseException e) {
                                if (e == null) {
                                    object.pinInBackground();
                                    Term term = (Term) object;
                                    if (!term.getWords().equals(mCurrentTerm.getWords())) {
                                        MainFragment.mTerms.add(term);
                                        MainFragment.mRelatedTermsAdapter.notifyDataSetChanged();
                                    }
                                } else {
                                    Log.e(TAG_, e.toString());
                                }
                            }
                        });
                        termTerm.getTerm2().fetchIfNeededInBackground(new GetCallback<ParseObject>() {
                            @Override
                            public void done(ParseObject object, ParseException e) {
                                if (e == null) {
                                    object.pinInBackground();
                                    Term term = (Term) object;
                                    if (!term.getWords().equals(mCurrentTerm.getWords())) {
                                        MainFragment.mTerms.add(term);
                                        MainFragment.mRelatedTermsAdapter.notifyDataSetChanged();
                                    }
                                } else {
                                    Log.e(TAG_, e.toString());
                                }
                            }
                        });
                    }
                } else {
                    Log.e(TAG_, e.toString());
                }
            }
        });
    }

    private static void refreshImages() {
        MainFragment.mImgs.clear();

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Img");
        query.whereEqualTo("term", mCurrentTerm);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null){
                    for (ParseObject object : objects){
                        object.pinInBackground();
                        Img image = (Img) object;
                        MainFragment.mImgs.add(image);
                        MainFragment.mImagesTermsAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

    }

    public static void refreshCurrentTermById(final String lastTermId, final Context context) {
        mCurrentContext = context;
        ParseObject query = ParseObject.createWithoutData("Term", lastTermId);
        query.fetchFromLocalDatastoreInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    mCurrentTerm = (Term) object;
                    refreshMainFragment((Activity) mCurrentContext);
                } else {
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Term");
                    query.getInBackground(lastTermId, new GetCallback<ParseObject>() {
                        public void done(ParseObject object, ParseException e) {
                            if (e == null) {
                                object.pinInBackground();
                                mCurrentTerm = (Term) object;
                                refreshMainFragment((Activity) mCurrentContext);
                            }
                        }
                    });
                }
            }
        });
    }

    public static void refreshCurrentTermByName(final String lastTermWords, final Context context, final String url) {
        //TODO What if it finds 2 objects
        mCurrentContext = context;
        ParseQuery<ParseObject> query = new ParseQuery<>("Term");
        query.fromLocalDatastore();
        query.whereEqualTo("words", lastTermWords);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    mCurrentTerm = (Term) object;
                    refreshMainFragment((Activity) mCurrentContext);
                } else {
                    if (lastTermWords.contains(".")) {
                        String last = lastTermWords.substring(lastTermWords.lastIndexOf(".") + 1);
                        refreshCurrentTermByName(last, context, url);
                    } else {
                        try {
                            URL auxURL = new URL(mCurrentTerm.getUrl());
                            String buildURL = auxURL.getProtocol() + "://" + auxURL.getHost() + "/" + url;

                            Intent intent = new Intent(mCurrentContext, WebActivity.class);
                            intent.putExtra(Constants.TITLE_WEB_KEY, lastTermWords);
                            intent.putExtra(Constants.URL_WEB_KEY, buildURL);
                            mCurrentContext.startActivity(intent);
                        } catch (MalformedURLException e1) {
                            Log.e(TAG_, e1.toString());
                        }
                    }
                }
            }
        });
    }

    public static void initializeThirdPartyLibs(MainActivity mainActivity) {
        ParseVM.parseStart(mainActivity);
//        Fabric.with(mainActivity, new Crashlytics());
    }
}
