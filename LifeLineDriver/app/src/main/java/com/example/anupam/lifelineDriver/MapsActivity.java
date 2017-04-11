package com.example.anupam.lifelineDriver;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuAdapter;
import android.text.LoginFilter;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationChangeListener {

    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    LatLng myPosition;
    String userId = "";
    Timer myTimer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        SharedPreferences settings = getSharedPreferences("userInfo", MODE_PRIVATE);

        userId = settings.getString("username","");

        final long period = 1000;
        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {

                try {
                    Log.d("anupam","requesting for coord");

                    runOnUiThread(new Runnable() {
                        public void run() {
                       //
                            //     Toast.makeText(activity, "Hello", Toast.LENGTH_SHORT).show();
                            JSONObject jsonObject = new JSONObject();
                            HttpAsyncTask4 st = null;

                            st = new HttpAsyncTask4(MapsActivity.this,mMap);
                            st.execute("http://"+getString(R.string.InternetProtocol)+"/lifeline/receive_user_cord_for_driver.php");

                        }
                    });

                }
                catch (Exception jse){
                    Log.d("anupam","some exception arised- "+jse.toString()+jse.getMessage());
                }
            }
        }, 0, period);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        mMap.setMyLocationEnabled(true);

        // Getting LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Creating a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Getting the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        // Getting Current Location
        Location location = locationManager.getLastKnownLocation(provider);

        if (location != null) {
            // Getting latitude of the current location
            double latitude = location.getLatitude();

            // Getting longitude of the current location
            double longitude = location.getLongitude();
            Log.d("anupam",String.valueOf(latitude));
            // Creating a LatLng object for the current location
            LatLng latLng = new LatLng(latitude, longitude);

            myPosition = new LatLng(latitude, longitude);

            googleMap.addMarker(new MarkerOptions().position(myPosition).title("Start"));
        }



        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
       // CameraUpdateFactory.zoomTo(15);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(myPosition));
        mMap.animateCamera( CameraUpdateFactory.zoomTo( 16.2f ) );
        mMap.setOnMyLocationChangeListener(this);
    }

    @Override
    public void onMyLocationChange(Location location) {

            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude()))
                    .title("My Location"));

            /* ..and Animate camera to center on that location !
             * (the reason for we created this custom Location Source !) */
            mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));

        JSONObject jsonObject = new JSONObject();
        HttpAsyncTask3 ss = null;

        try {
            Log.d("asa","first");
            ss = new HttpAsyncTask3(this,userId,String.valueOf(location.getLatitude()),String.valueOf(location.getLongitude()));
            ss.execute("http://"+getString(R.string.InternetProtocol)+"/lifeline/set_driver_cord.php");
            // Thread.sleep(2000);
            Log.d("asa","first");
        }catch (Exception jse){

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myTimer.cancel();
    }
}

class HttpAsyncTask4 extends AsyncTask<String, Void, String> {
    String result="";
    MapsActivity context;
    GoogleMap mMap;
    public HttpAsyncTask4(MapsActivity context, GoogleMap mMap) {
        this.context = context;
        this.mMap = mMap;
    }
    public String getResult(){
        return this.result;
    }
    ProgressDialog PD;

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
        PD = new ProgressDialog(this.context);
        PD.setTitle("Please Wait..");
        PD.setMessage("Loading...");
        PD.setCancelable(false);
        PD.show();
        PD.dismiss();
    }
    @Override
    protected String doInBackground(String... urls) {
        InputStream inputStream = null;
//        result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(urls[0]);
            String json = "";
            JSONObject jsonObject = new JSONObject();

    //        jsonObject.accumulate("username", username);
      //      jsonObject.accumulate("password", password);

            json = jsonObject.toString();
            StringEntity se = new StringEntity(json);

            httpPost.setEntity(se);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");


            HttpResponse httpResponse = httpclient.execute(httpPost);

            inputStream = httpResponse.getEntity().getContent();
            if (inputStream != null)
                this.result = convertInputStreamToString(inputStream);
            else
                this.result = "Did not work!";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.result;
    }


    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        return result;


    }
    @Override
    protected void onPostExecute(String result) {
      //  PD.dismiss();
        Log.d("result","must be here");
        Log.d("anupam",result);
        String[] words = result.split("/");
        double latitude = Double.parseDouble(words[0]);
        double longitude = Double.parseDouble(words[1]);

        LatLng myPosition = new LatLng(latitude, longitude);

        mMap.addMarker(new MarkerOptions().position(myPosition).title("UserCord"));

    }
}
class HttpAsyncTask3 extends AsyncTask<String, Void, String> {
    String userId;
    String latitude;
    String longitude;

    String result="";
    MapsActivity context;

    public HttpAsyncTask3(MapsActivity context,String userId, String lat, String lon) {
        this.context = context;
        this.userId = userId;
        this.latitude = lat;
        this.longitude = lon;
    }
    ProgressDialog PD;

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
        PD = new ProgressDialog(this.context);
        PD.setTitle("Please Wait..");
        PD.setMessage("Loading...");
        PD.setCancelable(false);
        PD.show();
        PD.dismiss();
    }
    @Override
    protected String doInBackground(String... urls) {
        InputStream inputStream = null;
//        result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(urls[0]);
            String json = "";
            JSONObject jsonObject = new JSONObject();
            Log.d("anupam",userId+" userid ");
            Log.d("anupam",latitude+" lat");
            Log.d("anupam",longitude+" long");
            jsonObject.accumulate("username", userId);
            jsonObject.accumulate("latitude", latitude);
            jsonObject.accumulate("longitude", longitude);

            json = jsonObject.toString();
            StringEntity se = new StringEntity(json);

            httpPost.setEntity(se);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");


            HttpResponse httpResponse = httpclient.execute(httpPost);

            inputStream = httpResponse.getEntity().getContent();
            if (inputStream != null)
                this.result = convertInputStreamToString(inputStream);
            else
                this.result = "Did not work!";
            Log.d("anupam",this.result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.result;
    }


    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        return result;


    }

    @Override
    protected void onPostExecute(String result) {
        //PD.dismiss();
        // Login_activity la = new Login_activity();

        if(result.charAt(0)=='s'){
        //    context.registration_successful(context);
        }
        else{
          //  context.wrong(context);
        }
    }
}