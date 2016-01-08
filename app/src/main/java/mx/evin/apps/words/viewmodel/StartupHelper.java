package mx.evin.apps.words.viewmodel;

import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;

import mx.evin.apps.words.model.entities.Technology;
import mx.evin.apps.words.model.queries.Lookups;
import mx.evin.apps.words.model.scripts.CreateObject;

/**
 * Created by evin on 1/8/16.
 */
public class StartupHelper {

    private static ParseUser mUser;

    static{
        mUser = ParseUser.getCurrentUser();
    }

    public static void firstTimeSetup(){
        createTechnologies();
        createUserTechnologies();
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
}
