package mx.evin.apps.words.viewmodel;

import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;

import mx.evin.apps.words.model.entities.Pack;
import mx.evin.apps.words.model.entities.Technology;
import mx.evin.apps.words.model.queries.Lookups;
import mx.evin.apps.words.model.scripts.CreateObject;

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
        CreateObject.getCreateTechnology("Android");
        CreateObject.getCreateTechnology("iOS");
        CreateObject.getCreateTechnology("SharePoint");
        CreateObject.getCreateTechnology("Management");
    }

    public static void createUserTechnologies(){
        Technology technology;

        technology = Lookups.getTechnology("Android");
        CreateObject.getCreateUserTechnology(mUser, technology);
    }

    public static void createPacks(){
        CreateObject.getCreatePack("java.lang");
        CreateObject.getCreatePack("android.view");
    }

    public static void createTerms(){
        Technology android;
        Pack java_lang, android_view;

        android = Lookups.getTechnology("Android");
        java_lang = Lookups.getPack("java.lang");
        android_view = Lookups.getPack("android.view");

        CreateObject.getCreateTerm("Object", android, java_lang, "1", "2");
        CreateObject.getCreateTerm("View", android, android_view, "3", "4");
        CreateObject.getCreateTerm("ViewGroup", android, android_view, "5", "6");
    }
}
