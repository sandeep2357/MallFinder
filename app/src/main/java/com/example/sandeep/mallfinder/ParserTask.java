package com.example.sandeep.mallfinder;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by sandeep on 9/12/15.
 * Class to parse the Google place details in JSON format.
 * To parse http result json object and show the places in map.
 */
public class ParserTask extends AsyncTask<Object, Integer, List<HashMap<String,String>>> {

    JSONObject googlePlacesJson;
    GoogleMap googleMap;

    @Override
    protected List<HashMap<String,String>> doInBackground(Object... inputObj){
        List<HashMap<String,String>> googlePlacesList = null;
        Places googlePlacesJsonParser = new Places();
        try{
            googleMap = (GoogleMap) inputObj[0];
            googlePlacesJson = new JSONObject((String) inputObj[1]);
            googlePlacesList = googlePlacesJsonParser.parse(googlePlacesJson);
        }catch(Exception e){
            Log.d("Error in Parsing Task",e.toString());
        }
        return googlePlacesList;
    }

    @Override
    protected void onPostExecute(List<HashMap<String,String>> placeDetails){
        googleMap.clear();
        for(int i=0;i<placeDetails.size();i++){
            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String,String>  googlePlace = placeDetails.get(i);
            double lat = Double.parseDouble(googlePlace.get("lat"));
            double lng = Double.parseDouble(googlePlace.get("lng"));
            String placeName = googlePlace.get("place_name");
            String vicinity = googlePlace.get("vicinity");

            LatLng latlng = new LatLng(lat, lng);
            markerOptions.position(latlng);
            markerOptions.title(placeName + " - " + vicinity);
            googleMap.addMarker(markerOptions);
        }
    }
}
