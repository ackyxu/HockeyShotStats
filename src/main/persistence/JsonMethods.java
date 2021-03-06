package persistence;


public interface JsonMethods {

    //REQUIRE: Class that implements this method must return JSONArray or JSONObject
    //EFFECTS: converts a Class Object into a JSONObject or JSONArray
    Object toJson();



}
