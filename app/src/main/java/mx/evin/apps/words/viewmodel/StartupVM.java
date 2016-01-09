package mx.evin.apps.words.viewmodel;

import com.parse.ParseUser;

import java.util.HashMap;

import mx.evin.apps.words.model.entities.Pack;
import mx.evin.apps.words.model.entities.Technology;
import mx.evin.apps.words.model.entities.Term;
import mx.evin.apps.words.model.queries.Lookups;
import mx.evin.apps.words.model.scripts.RowCreator;

/**
 * Created by evin on 1/8/16.
 */
public class StartupVM {
    //TODO terms hashmap
    private static ParseUser mUser;

    static{
        mUser = ParseUser.getCurrentUser();
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
    }

    public static void createTerms(){
        Technology android;
        Pack java_lang, android_view;

        android = Lookups.getTechnology("Android");
        java_lang = Lookups.getPack("java.lang");
        android_view = Lookups.getPack("android.view");

        RowCreator.getCreateTerm("Object", android, java_lang, "1", "2");
        RowCreator.getCreateTerm("View", android, android_view, "3", "4");
        RowCreator.getCreateTerm("ViewGroup", android, android_view, "5", "6");
    }

    public static void createTermTerms(){
        Term term1, term2;
        Technology android;
        Pack java_lang, android_view;

        android = Lookups.getTechnology("Android");
        java_lang = Lookups.getPack("java.lang");
        android_view = Lookups.getPack("android.view");

        term1 = Lookups.getTerm("Object", android, java_lang);
        term2 = Lookups.getTerm("ViewGroup", android, android_view);

        RowCreator.getCreateTermTerm(term1, term2, 3);
    }

    public static void createUserTerms(){
        Term term1, term2, term3;
        Technology android;
        Pack java_lang, android_view;

        android = Lookups.getTechnology("Android");
        java_lang = Lookups.getPack("java.lang");
        android_view = Lookups.getPack("android.view");

        term1 = Lookups.getTerm("Object", android, java_lang);
        term2 = Lookups.getTerm("View", android, android_view);
        term3 = Lookups.getTerm("ViewGroup", android, android_view);

        RowCreator.getCreateUserTerm(mUser, term1, 2);
        RowCreator.getCreateUserTerm(mUser, term2, 4);
        RowCreator.getCreateUserTerm(mUser, term3, 0);
    }

    public static void createTermHierarchies() {
        Term term1, term2, term3;
        Technology android;
        Pack java_lang, android_view;

        android = Lookups.getTechnology("Android");
        java_lang = Lookups.getPack("java.lang");
        android_view = Lookups.getPack("android.view");

        term1 = Lookups.getTerm("Object", android, java_lang);
        term2 = Lookups.getTerm("View", android, android_view);
        term3 = Lookups.getTerm("ViewGroup", android, android_view);

        RowCreator.getCreateTermHierarchy(term2, term3);
    }

    public static void createTermImplementations(){
        Term term1, term2, term3;
        Technology android;
        Pack java_lang, android_view;

        android = Lookups.getTechnology("Android");
        java_lang = Lookups.getPack("java.lang");
        android_view = Lookups.getPack("android.view");

        term1 = Lookups.getTerm("Object", android, java_lang);
        term2 = Lookups.getTerm("View", android, android_view);
        term3 = Lookups.getTerm("ViewGroup", android, android_view);

        RowCreator.getCreateTermImplementation(term3, term2);
    }

}
