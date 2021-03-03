package persistence;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class JsonReaders {

    protected String parseString(JSONObject json, String key) {
        String string;
        try {
            string = json.getString(key);
        } catch (JSONException e) {
            return " ";
        }

        return string;
    }

    protected Integer parseInteger(JSONObject json, String key) {
        Integer num;
        try {
            num = json.getInt(key);
        } catch (JSONException e) {
            return null;
        }

        return num;
    }

    protected JSONObject getObject(JSONObject json, String key) {

        try {
            return json.getJSONObject(key);

        } catch (JSONException e) {
            return new JSONObject();
        }
    }


}
