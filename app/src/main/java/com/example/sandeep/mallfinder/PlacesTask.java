package com.example.sandeep.mallfinder;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by sandeep on 8/12/15.
 * Class to download Google Place details
 */
public class PlacesTask extends AsyncTask<Object,Integer, String>{
    String googlePlacesData = null;
    GoogleMap gMap;

    // Invoked by execute method of this object
    @Override
    protected String doInBackground(Object... inputObj){
        try{
            gMap = (GoogleMap) inputObj[0];
            String googlePlacesUrl = (String) inputObj[1];
            googlePlacesData = downloadUrl(googlePlacesUrl);

        }catch(Exception e){
            Log.d("Background Task:Getting Places data", e.toString());
        }
        Log.d("Google Places Data is",googlePlacesData);
        return googlePlacesData;
    }

    // Executed after the complete execution of doInBackground method.
    @Override
    protected void onPostExecute(String result){
        ParserTask parserTask = new ParserTask();
        Object[] toPass = new Object[2];
        toPass[0] = gMap;
        toPass[1] = result;
        parserTask.execute(toPass);
    }

    private String downloadUrl (String strUrl) throws IOException{
        String data ="";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url =   new URL(strUrl);
            //Creating an HTTP connection to communicate with URL
            urlConnection = (HttpURLConnection) url.openConnection();

            //Connecting to url
            urlConnection.connect();

            //reading data from url
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();
            String line="";

            while( (line = br.readLine()) != null){
                sb.append(line);
            }
            data = sb.toString();
            br.close();

        }catch(Exception e){
            Log.d("Exception while downloading url",e.toString());
        }finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
}
