package com.udacity.sandwichclub.utils;
import android.util.Log;

import org.json.*;

import com.udacity.sandwichclub.model.Sandwich;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private static final String TAG = JsonUtils.class.getSimpleName();
    public static Sandwich parseSandwichJson(String json) throws JSONException {
        Sandwich sandwich = new Sandwich();
        JSONObject jsonObject=null;

            jsonObject = new JSONObject(json);
            Log.i(TAG,"json="+json);
if (jsonObject != null) {
    JSONObject nameObject = jsonObject.getJSONObject("name");

    String mainName = "";
    String placeOfOrigin = "";
    String description = "";
    String image = "";
    if (nameObject.getString("mainName") != null) {

        mainName = nameObject.getString("mainName");
    }
    sandwich.setMainName(mainName);

    JSONArray AKA = nameObject.getJSONArray("alsoKnownAs");
    sandwich.setAlsoKnownAs(getStringListFromJsonArray(AKA));

    if (jsonObject.getString("placeOfOrigin") != null) {

        placeOfOrigin = jsonObject.getString("placeOfOrigin");
    }

    if (jsonObject.getString("description") != null) {

        description = jsonObject.getString("description");
    }
    if (jsonObject.getString("image") != null) {

        image = jsonObject.getString("image");
    }
    sandwich.setPlaceOfOrigin(placeOfOrigin);
    sandwich.setDescription(description);
    sandwich.setImage(image);

    JSONArray ingArray = jsonObject.getJSONArray("ingredients");
    sandwich.setIngredients(getStringListFromJsonArray(ingArray));

    } else {

     return null;
    }

        return sandwich;
    }


    public static List<String> getStringListFromJsonArray(JSONArray jArray) throws JSONException {
        List<String> results = new ArrayList<String>();
        for (int i = 0; i < jArray.length(); i++) {
            String val = jArray.getString(i);
            results.add(val);
        }
        return results;
    }

}
