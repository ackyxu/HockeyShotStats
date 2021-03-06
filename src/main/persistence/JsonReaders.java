package persistence;

import org.json.JSONException;
import org.json.JSONObject;


//The catch for JSONExceptions are needed because JSON are a semi-structured data stored; there are certain cases/time
//where certain Key/JSONObject does not exist as a sub Object (example, not all LiveData will contain player
//information, such as events indicating a match started)
//Convert JSON files to Classes
public abstract class JsonReaders {

    //EFFECT: Parse a JSONObject to a string; if JSONException is caught, return empty string
    protected String parseString(JSONObject json, String key) {
        String string;
        try {
            string = json.getString(key);
        } catch (JSONException e) {
            return " ";
        }

        return string;
    }

    //EFFECT: Parse a JSONObject to a Integer; if JSONException is caught, return null
    protected Integer parseInteger(JSONObject json, String key) {
        Integer num;
        try {
            num = json.getInt(key);
        } catch (JSONException e) {
            return null;
        }

        return num;
    }

    //EFFECT: get a JSONObjects;if JSONException is caught, return a empty JSONObject
    protected JSONObject getObject(JSONObject json, String key) {

        try {
            return json.getJSONObject(key);

        } catch (JSONException e) {
            return new JSONObject();
        }
    }


}
