package com.example.sandeep.mallfinder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sandeep on 9/12/15.
 */
public class Places {

    /** Receives a JSON object and returns a list */
    public List<HashMap<String,String>> parse(JSONObject jsonObject){
        JSONArray jsonArray = null;
        try{
            /** Retrieves all the elements in the 'googlePlacesJson' Object*/
            jsonArray = jsonObject.getJSONArray("results");
        }catch(JSONException e){
            e.printStackTrace();
        }
        /**Invoking getPlaces with a array of Json Object where each object represents a place*/
        return getPlaces(jsonArray);
    }


    private List<HashMap<String, String>> getPlaces(JSONArray jsonArray){
        int placesCount = jsonArray.length();
        List<HashMap<String, String>> placesList = new ArrayList<HashMap<String, String>>();
        HashMap<String,String > placeMap = null;

        /**Taking each place and adds to list object*/
        for(int i=0; i<placesCount;i++){
            try{
                /**Call getPlace with place JSON object to parse the place */
                placeMap = getPlace((JSONObject) jsonArray.get(i));
                placesList.add(placeMap);
            }catch(JSONException e){
                e.printStackTrace();
            }
        }
        return placesList;
    }

    private HashMap<String ,String> getPlace(JSONObject googlePlaceJson){
        HashMap<String, String> googlePlaceMap = new HashMap<String,String>();
        String placeName = "NA";
        String vicinity = "NA";
        String latitude = "";
        String longitude = "";
        String reference = "";

        try{
            /**Retrieving place name if available*/
            if(!googlePlaceJson.isNull("name")){
                placeName = googlePlaceJson.getString("name");
            }
            if(!googlePlaceJson.isNull("vicinity")){
                vicinity = googlePlaceJson.getString("vicinity");
            }
            latitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lng");
            reference = googlePlaceJson.getString("reference");

            googlePlaceMap.put("place_name", placeName);
            googlePlaceMap.put("vicinity",vicinity);
            googlePlaceMap.put("lat",latitude);
            googlePlaceMap.put("lng",longitude);
            googlePlaceMap.put("reference",reference);
        }catch(JSONException e){
            e.printStackTrace();
        }
        return googlePlaceMap;
    }
}
