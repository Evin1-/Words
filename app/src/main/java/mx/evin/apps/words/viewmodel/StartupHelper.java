package mx.evin.apps.words.viewmodel;

import com.parse.ParseUser;

import mx.evin.apps.words.model.entities.Technology;
import mx.evin.apps.words.model.scripts.CreateObject;

/**
 * Created by evin on 1/8/16.
 */
public class StartupHelper {

    public static void createTechnologies() {
        CreateObject.getCreateTechnology("Android");
        CreateObject.getCreateTechnology("iOS");
        CreateObject.getCreateTechnology("SharePoint");
        CreateObject.getCreateTechnology("Management");
    }

    public void createUserTechnologies(ParseUser user, Technology technology){
        CreateObject.getCreateUserTechnology(user, technology);
    }
}
