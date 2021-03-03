package persistence;

public interface JsonMethods {

    //REQUIRE: Class that implements this method must return JSONArray or JSONObject
    //EFFECTS: return this as JSON object
    Object toJson();



}
