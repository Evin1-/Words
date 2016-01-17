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
    }

    public static void createTerms(){
        Technology android;
        Pack java_lang, android_view, v7_widget;

        android = Lookups.getTechnology("Android");
        java_lang = Lookups.getPack("java.lang");
        android_view = Lookups.getPack("android.view");
        v7_widget = Lookups.getPack("android.support.v7.widget");

        terms.put("Object", (Term) RowCreator.getCreateTerm("Object", android, java_lang,
                "Class Object is the root of the class hierarchy. Every class has Object as a superclass. " +
                        "All objects, including arrays, implement the methods of this class.",
                "https://docs.oracle.com/javase/7/docs/api/java/lang/Object.html"));

        terms.put("View", (Term) RowCreator.getCreateTerm("View", android, android_view,
                "This class represents the basic building block for user interface components. " +
                        "A View occupies a rectangular area on the screen and is responsible for drawing and event handling. " +
                        "View is the base class for widgets, which are used to create interactive UI components (buttons, text fields, etc.). " +
                        "The ViewGroup subclass is the base class for layouts, which are invisible containers that hold other Views (or other ViewGroups) and define their layout properties.",
                "http://developer.android.com/reference/android/view/View.html"));

        terms.put("ViewGroup", (Term) RowCreator.getCreateTerm("ViewGroup", android, android_view,
                "A ViewGroup is a special view that can contain other views (called children.) " +
                        "The view group is the base class for layouts and views containers. " +
                        "This class also defines the ViewGroup.LayoutParams class which serves as the base class for layouts parameters.",
                "http://developer.android.com/reference/android/view/ViewGroup.html"));

        terms.put("RecyclerView", (Term) RowCreator.getCreateTerm("RecyclerView", android, v7_widget,
                "A flexible view for providing a limited window into a large data set. \n" +
                        "-Adapter: A subclass of RecyclerView.Adapter responsible for providing views that represent items in a data set.\n" +
                        "-Position: The position of a data item within an Adapter.\n" +
                        "-Index: The index of an attached child view as used in a call to getChildAt(int). Contrast with Position.\n" +
                        "-Binding: The process of preparing a child view to display data corresponding to a position within the adapter.\n" +
                        "-Recycle (view): A view previously used to display data for a specific adapter position may be placed in a cache for later reuse to display the same type of data again later. This can drastically improve performance by skipping initial layout inflation or construction.\n" +
                        "-Scrap (view): A child view that has entered into a temporarily detached state during layout. Scrap views may be reused without becoming fully detached from the parent RecyclerView, either unmodified if no rebinding is required or modified by the adapter if the view was considered dirty.\n" +
                        "-Dirty (view): A child view that must be rebound by the adapter before being displayed.",
                "http://developer.android.com/reference/android/support/v7/widget/RecyclerView.html"));
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
