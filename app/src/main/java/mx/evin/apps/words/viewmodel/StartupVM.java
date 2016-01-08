package mx.evin.apps.words.viewmodel;

import com.parse.ParseUser;

import mx.evin.apps.words.model.entities.Pack;
import mx.evin.apps.words.model.entities.Technology;
import mx.evin.apps.words.model.queries.Lookups;
import mx.evin.apps.words.model.scripts.Creator;

/**
 * Created by evin on 1/8/16.
 */
public class StartupVM {
    private static ParseUser mUser;

    static{
        mUser = ParseUser.getCurrentUser();
    }

    public static void firstTimeSetup(){
        createTechnologies();
        createUserTechnologies();
        createPacks();
        createTerms();
    }

    public static void createTechnologies() {
        Creator.getCreateTechnology("Android");
        Creator.getCreateTechnology("iOS");
        Creator.getCreateTechnology("SharePoint");
        Creator.getCreateTechnology("Management");
    }

    public static void createUserTechnologies(){
        Technology technology;

        technology = Lookups.getTechnology("Android");
        Creator.getCreateUserTechnology(mUser, technology);
    }

    public static void createPacks(){
        Creator.getCreatePack("java.lang");
        Creator.getCreatePack("android.view");
    }

    public static void createTerms(){
        Technology android;
        Pack java_lang, android_view;

        android = Lookups.getTechnology("Android");
        java_lang = Lookups.getPack("java.lang");
        android_view = Lookups.getPack("android.view");

        Creator.getCreateTerm("Object", android, java_lang, "1", "2");
        Creator.getCreateTerm("View", android, android_view, "3", "4");
        Creator.getCreateTerm("ViewGroup", android, android_view, "5", "6");
    }
}
