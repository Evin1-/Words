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

        terms.put("RecyclerView", (Term) RowCreator.getCreateTerm("RecyclerView", android, v7_widget, "1", "2",
                "http://developer.android.com/reference/android/support/v7/widget/RecyclerView.html"));

        terms.put("ListView", (Term) RowCreator.getCreateTerm("ListView", android, android_widget, "1", "2",
                "http://developer.android.com/reference/android/widget/ListView.html"));

        terms.put("CardView", (Term) RowCreator.getCreateTerm("CardView", android, v7_widget, "1", "2",
                "http://developer.android.com/reference/android/support/v7/widget/CardView.html"));

        terms.put("Adapter", (Term) RowCreator.getCreateTerm("Adapter", android, android_widget, "1", "2",
                "http://developer.android.com/reference/android/widget/Adapter.html"));

    }

    public static void createTermTerms(){
        RowCreator.getCreateTermTerm(terms.get("Object"), terms.get("View"), 3);
        RowCreator.getCreateTermTerm(terms.get("Object"), terms.get("Context"), 3);
        RowCreator.getCreateTermTerm(terms.get("View"), terms.get("ViewGroup"), 3);
        RowCreator.getCreateTermTerm(terms.get("ViewGroup"), terms.get("FrameLayout"), 3);
        RowCreator.getCreateTermTerm(terms.get("ViewGroup"), terms.get("LinearLayout"), 3);
        RowCreator.getCreateTermTerm(terms.get("ViewGroup"), terms.get("RelativeLayout"), 3);
        RowCreator.getCreateTermTerm(terms.get("ViewGroup"), terms.get("RecyclerView"), 3);
        RowCreator.getCreateTermTerm(terms.get("FrameLayout"), terms.get("LinearLayout"), 3);
        RowCreator.getCreateTermTerm(terms.get("FrameLayout"), terms.get("RelativeLayout"), 3);
        RowCreator.getCreateTermTerm(terms.get("LinearLayout"), terms.get("RelativeLayout"), 3);
        RowCreator.getCreateTermTerm(terms.get("Context"), terms.get("ContextWrapper"), 3);
        RowCreator.getCreateTermTerm(terms.get("RecyclerView"), terms.get("ListView"), 3);
        RowCreator.getCreateTermTerm(terms.get("RecyclerView"), terms.get("CardView"), 3);
        RowCreator.getCreateTermTerm(terms.get("RecyclerView"), terms.get("Adapter"), 3);
        RowCreator.getCreateTermTerm(terms.get("ListView"), terms.get("Adapter"), 3);
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
        RowCreator.getCreateImg("example", "http://usercontent2.hubimg.com/6849417.png",
                "description", 1, terms.get("Object"));
    }

}
