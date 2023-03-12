package com.trip_tracker;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.trip_tracker.util.HttpHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherFragment extends Fragment {
    private String startLat;
    private String startLon;
    private String finishLat;
    private String finishLon;
    private TextView firstCity;
    private TextView secondCity;
    private TextView firstTemp;
    private TextView secondTemp;
    private TextView firstInfo;
    private TextView secondInfo;
    private String description;
    private String locName;
    private double temp;
    private double ftemp;

    public WeatherFragment() {}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inf =  inflater.inflate(R.layout.fragment_weather, container, false);
        startLat = getArguments().getString("startLat");
        startLon = getArguments().getString("startLon");
        finishLat = getArguments().getString("finishLat");
        finishLon = getArguments().getString("finishLon");
        firstCity =  inf.findViewById(R.id.WF_first_loc_name);
        secondCity =  inf.findViewById(R.id.WF_second_loc_name);
        firstTemp =  inf.findViewById(R.id.WF_temp_text);
        secondTemp =  inf.findViewById(R.id.WF__second_temp_text);
        firstInfo =  inf.findViewById(R.id.WF_info_text);
        secondInfo =  inf.findViewById(R.id.WF_second_info_text);
        new getWeather(startLat, startLon, true).execute();
        new getWeather(finishLat, finishLon, false).execute();
        return inf;
    }

    public void setTextViews(String locName, Double temp, Double ftemp, String info, boolean first){
        if(first){
            firstCity.setText(locName);
            firstTemp.setText(getString(R.string.WF_first_temp_text, String.valueOf(temp)));
            firstInfo.setText(getString(R.string.WF_first_info_text, info, String.valueOf(ftemp)));
        }
        else{
            secondCity.setText(locName);
            secondTemp.setText(getString(R.string.WF_second_temp_text, String.valueOf(temp)));
            secondInfo.setText(getString(R.string.WF_second_info_text, info, String.valueOf(ftemp)));
        }
    }




    private class getWeather extends AsyncTask<Void, Void, Void>{
        private String lat;
        private String lon;
        private boolean first;
        public getWeather(String lat,String lon, boolean first ) {
            super();
            this.first = first;
            this.lat = lat;
            this.lon = lon;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();
            String tempUrl = getString(R.string.weather_url, lat,lon);
            String jsonStr = sh.makeServiceCall(tempUrl);
            //Log.e("responsetest", "Response from url: " + jsonStr);
            try {
                JSONObject jsonResponse = new JSONObject(jsonStr);
                JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                description = jsonObjectWeather.getString("description");
                JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                temp = jsonObjectMain.getDouble("temp");
                ftemp = jsonObjectMain.getDouble("feels_like");
                locName = jsonResponse.getString("name");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            setTextViews(locName, temp, ftemp, description, first);
        }
    }
}