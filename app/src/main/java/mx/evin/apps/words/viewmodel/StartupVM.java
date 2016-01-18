package mx.evin.apps.words.viewmodel;

import com.parse.ParseUser;

import java.util.HashMap;

import mx.evin.apps.words.model.entities.parse.Pack;
import mx.evin.apps.words.model.entities.parse.Technology;
import mx.evin.apps.words.model.entities.parse.Term;
import mx.evin.apps.words.model.queries.Lookups;
import mx.evin.apps.words.model.scripts.RowCreator;

/**
 * Created by evin on 1/8/16.
 */
public class StartupVM {
    //TODO Remove this from project
    private static ParseUser mUser;
    private static HashMap<String, Term> terms;

    static{
        mUser = ParseUser.getCurrentUser();
        terms = new HashMap<>();
    }

    public static void firstTimeSetup(){
        createTechnologies();
        createUserTechnologies();
        createPacks();
        createTerms();
        createTermTerms();
        createUserTerms();
        createTermHierarchies();
        createTermImplementations();
        createImgs();
    }

    public static void createTechnologies() {
        RowCreator.getCreateTechnology("Android");
        RowCreator.getCreateTechnology("iOS");
        RowCreator.getCreateTechnology("SharePoint");
        RowCreator.getCreateTechnology("Management");
    }

    public static void createUserTechnologies(){
        Technology technology;

        technology = Lookups.getTechnology("Android");
        RowCreator.getCreateUserTechnology(mUser, technology);
    }

    public static void createPacks(){
        RowCreator.getCreatePack("java.lang");
        RowCreator.getCreatePack("android.view");
        RowCreator.getCreatePack("android.support.v7.widget");
        RowCreator.getCreatePack("android.widget");
        RowCreator.getCreatePack("android.content");
    }

    public static void createTerms(){
        Technology android;
        Pack java_lang, android_view, v7_widget, android_widget, android_content;

        android = Lookups.getTechnology("Android");
        java_lang = Lookups.getPack("java.lang");
        android_view = Lookups.getPack("android.view");
        v7_widget = Lookups.getPack("android.support.v7.widget");
        android_widget = Lookups.getPack("android.widget");
        android_content = Lookups.getPack("android.support.v7.widget");

        terms.put("Object", (Term) RowCreator.getCreateTerm("Object", android, java_lang, "1", "2",
                "http://developer.android.com/reference/java/lang/Object.html"));

        terms.put("View", (Term) RowCreator.getCreateTerm("View", android, android_view, "1", "2",
                "http://developer.android.com/reference/android/view/View.html"));

        terms.put("ViewGroup", (Term) RowCreator.getCreateTerm("ViewGroup", android, android_view, "1", "2",
                "http://developer.android.com/reference/android/view/ViewGroup.html"));

        terms.put("RecyclerView", (Term) RowCreator.getCreateTerm("RecyclerView", android, v7_widget, "1", "2",
                "http://developer.android.com/reference/android/support/v7/widget/RecyclerView.html"));

        terms.put("FrameLayout", (Term) RowCreator.getCreateTerm("FrameLayout", android, android_widget, "1", "2",
                "http://developer.android.com/reference/android/widget/FrameLayout.html"));

        terms.put("LinearLayout", (Term) RowCreator.getCreateTerm("LinearLayout", android, android_widget, "1", "2",
                "http://developer.android.com/reference/android/widget/LinearLayout.html"));

        terms.put("RelativeLayout", (Term) RowCreator.getCreateTerm("RelativeLayout", android, android_widget, "1", "2",
                "http://developer.android.com/reference/android/widget/RelativeLayout.html"));

        terms.put("Context", (Term) RowCreator.getCreateTerm("Context", android, android_content, "1", "2",
                "http://developer.android.com/reference/android/content/Context.html"));

        terms.put("ContextWrapper", (Term) RowCreator.getCreateTerm("ContextWrapper", android, android_content, "1", "2",
                "http://developer.android.com/reference/android/content/ContextWrapper.html"));

    }

    public static void createTermTerms(){
        Term term1 = terms.get("Object");
        Term term2 = terms.get("View");
        Term term3 = terms.get("ViewGroup");

        RowCreator.getCreateTermTerm(term1, term2, 3);
    }

    public static void createUserTerms(){
        Term term1 = terms.get("Object");
        Term term2 = terms.get("View");
        Term term3 = terms.get("ViewGroup");

        RowCreator.getCreateUserTerm(mUser, term1, 2);
        RowCreator.getCreateUserTerm(mUser, term2, 4);
        RowCreator.getCreateUserTerm(mUser, term3, 0);
    }

    public static void createTermHierarchies() {
        Term term1 = terms.get("Object");
        Term term2 = terms.get("View");
        Term term3 = terms.get("ViewGroup");

        RowCreator.getCreateTermHierarchy(term2, term3);
    }

    public static void createTermImplementations(){
        Term term1 = terms.get("Object");
        Term term2 = terms.get("View");
        Term term3 = terms.get("ViewGroup");

        RowCreator.getCreateTermImplementation(term3, term2);
    }

    public static void createImgs(){
        Term term1 = terms.get("Object");
        Term term2 = terms.get("View");
        Term term3 = terms.get("ViewGroup");

        RowCreator.getCreateImg("example", "https://goo.gl/OI4XQ4", "description", 1, term1);
    }

}
