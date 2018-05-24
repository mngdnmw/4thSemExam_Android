package mafioso.so.so.android.BLL.WeatherService;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import mafioso.so.so.android.R;

public class WeatherService extends AsyncTask<String, Void, JSONObject> {

    public IAsyncResponse delegate = null;

    /** --- Reference to the application context. --- */
    Context mContext;

    /** --- Tag for debug purposes. --- */
    static String TAG = "SOSOMAFIOSO::WEATHER";

    /** --- Reference to URL for weather API. --- */
    private String OPEN_WEATHER_MAP_URL;

    /** --- Reference to the API key for the weather API. --- */
    private String OPEN_WEATHER_MAP_API;

    public WeatherService(IAsyncResponse asyncResponse, Context context) {
        delegate = asyncResponse;

        mContext = context;

        OPEN_WEATHER_MAP_URL = mContext.getResources().getString(R.string.WEATHER_API_URL);
        OPEN_WEATHER_MAP_API = mContext.getResources().getString(R.string.weather_key);
    }

    /**
     * Set icon depending on weather conditions.
     * @param actualId
     * @param sunrise
     * @param sunset
     * @return
     */
    private static String setWeatherIcon(int actualId, long sunrise, long sunset){
        Log.d(TAG, "setWeatherIcon: Run");
        int id = actualId / 100;
        Log.d(TAG, "setWeatherIcon: " +actualId);
        String icon = "";
        if(actualId == 800){
            long currentTime = new Date().getTime();
            if(currentTime>=sunrise && currentTime<sunset) {
                icon = "&#xf00d;";
            } else {
                icon = "&#xf02e;";
            }
        } else {
            switch(id) {
                case 2 : icon = "&#xf01e;";
                    break;
                case 3 : icon = "&#xf01c;";
                    break;
                case 5 : icon = "&#xf019;";
                    break;
                case 6 : icon = "&#xf01b;";
                    break;
                case 7 : icon = "&#xf014;";
                    break;
                case 8 : icon = "&#xf013;";
                    break;
            }
            Log.d(TAG, "setWeatherIcon: " + icon);
        }
        return icon;
    }

    @Override
    protected JSONObject doInBackground(String... params) {

        JSONObject jsonWeather = null;
        try {
            jsonWeather = getWeatherJSON(params[0], params[1]);
        } catch (Exception e) {
            Log.d(TAG, "Cannot process JSON results", e);
        }


        return jsonWeather;
    }

    /**
     * Map and return JSON object data once retrieved.
     * @param json
     * Retrieved JSON object.
     */
    @Override
    protected void onPostExecute(JSONObject json) {
        try {
            if(json != null){
                JSONObject details = json.getJSONArray("weather").getJSONObject(0);
                JSONObject main = json.getJSONObject("main");
                DateFormat df = DateFormat.getDateTimeInstance();


                String city = json.getString("name").toUpperCase(Locale.US) + ", " + json.getJSONObject("sys").getString("country");
                String description = details.getString("description").toUpperCase(Locale.US);
                Log.d(TAG, "onPostExecute: " + description);
                String temperature = String.format("%.2f", main.getDouble("temp"))+ "°";
                String humidity = main.getString("humidity") + "%";
                String pressure = main.getString("pressure") + " hPa";
                String updatedOn = df.format(new Date(json.getLong("dt")*1000));

                String iconText = setWeatherIcon(details.getInt("id"),
                        json.getJSONObject("sys").getLong("sunrise") * 1000,
                        json.getJSONObject("sys").getLong("sunset") * 1000);

                delegate.processFinish(city, description, temperature, humidity, pressure, updatedOn, iconText);

            }
        } catch (JSONException e) {

        }



    }

    /**
     * Attempts to retrieve weather information for the given location.
     * @param lat
     * Latitude of position.
     * @param lon
     * Longitude of position.
     * @return
     * JSON object containing weather information.
     */
    public JSONObject getWeatherJSON(String lat, String lon){
        try {
            URL url = new URL(String.format(OPEN_WEATHER_MAP_URL, lat, lon));
            HttpURLConnection connection =
                    (HttpURLConnection)url.openConnection();

            connection.addRequestProperty("x-api-key", OPEN_WEATHER_MAP_API);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String tmp="";
            while((tmp=reader.readLine())!=null)
                json.append(tmp).append("\n");
            reader.close();

            JSONObject data = new JSONObject(json.toString());

            // This value will be 404 if the request was not successful
            if(data.getInt("cod") != 200){
                return null;
            }

            return data;
        }catch(Exception e){
            return null;
        }
    }
}