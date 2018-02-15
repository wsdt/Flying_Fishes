package yourowngame.com.yourowngame.classes.handler;


import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Iterator;

import yourowngame.com.yourowngame.classes.exceptions.JsonToObjectMapper_Exception;


public abstract class Mapper <OBJ> {
    private static final String TAG = "Mapper";
    // MAPPING METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public JSONObject convertStrToJson(String jsonStr) { //to serialize JsonObj --> just jsonObj.toString()
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonStr); //.replace("\"","\\\"") scheinbar nicht deescapen!!
        } catch(JSONException e) {
            Log.e("convertStrToJson","Could not convert String to Json object. \nString: "+jsonStr);
        }
        if (jsonObject == null) {Log.w(TAG, "convertStrToJson: Returned a NULL-JsonObject!");}
        return jsonObject;
    }

    public String convertJsonToStr(JSONObject jsonObject) {
        //Dummy method for jsonObj.toString();
        return jsonObject.toString();
    }

    //MAPPING SINGLE ROW TO OBJECT - START #############################################
    public OBJ MapJsonToObject(String json) throws JsonToObjectMapper_Exception {
        return MapJsonToObject(this.convertStrToJson(json));
    }

    public abstract OBJ MapJsonToObject(JSONObject json) throws JsonToObjectMapper_Exception;
    //MAPPING SINGLE ROW TO OBJECT - END ###############################################

    //MAPPING SEVERAL ROWS TO OBJECT - START ###########################################
    public ArrayList<OBJ> MapJsonRowsToObject(String json) throws JsonToObjectMapper_Exception {
        return MapJsonRowsToObject(this.convertStrToJson(json));
    }

    public ArrayList<OBJ> MapJsonRowsToObject(JSONObject json) throws JsonToObjectMapper_Exception {
        Iterator<?> keys = json.keys();
        ArrayList<OBJ> all_rows_mapped = new ArrayList<>();

        while (keys.hasNext()) {
            String key = (String) keys.next();
            try {
                if (json.get(key) instanceof JSONObject) {
                    all_rows_mapped.add(MapJsonToObject((JSONObject) json.get(key)));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                throw new JsonToObjectMapper_Exception();
            }
        }
        return all_rows_mapped;
    }
    //MAPPING SEVERAL ROWS TO OBJECT - END ###############################################


    public ArrayList<OBJ> CastArrayListObjToSpecObj(ArrayList list, OBJ class_type) {
        ArrayList<OBJ> new_list = new ArrayList<>();
        for (Object o : list) {
            new_list.add((OBJ) o);
        }
        return new_list;
    }
}
