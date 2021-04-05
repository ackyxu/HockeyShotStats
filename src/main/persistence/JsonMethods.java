package persistence;

//Requires that the classes in model has a method to convert the fields to a JSON Object
public interface JsonMethods {

    //REQUIRE: Class that implements this method must return JSONArray or JSONObject
    //EFFECTS: converts a Class Object into a JSONObject or JSONArray
    Object toJson();



}
