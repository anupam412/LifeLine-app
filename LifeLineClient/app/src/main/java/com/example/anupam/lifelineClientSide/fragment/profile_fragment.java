package com.example.anupam.lifelineClientSide.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.example.anupam.lifelineClientSide.MainScreen;
import com.example.anupam.lifelineClientSide.MapsActivity;
import com.example.anupam.lifelineClientSide.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
public class profile_fragment extends Fragment {

    Activity activity;
    TextView name,age,bloodgroup,willing,gender,contact,mail,friendnetwork;
    public profile_fragment() {
        activity = getActivity();
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile_fragment, container, false);

        name = (TextView)v.findViewById(R.id.my_name);
        age = (TextView)v.findViewById(R.id.my_age);
        bloodgroup = (TextView)v.findViewById(R.id.my_bloodgroup);
        willing = (TextView)v.findViewById(R.id.my_willingness);
        gender = (TextView)v.findViewById(R.id.my_gender);
        contact = (TextView)v.findViewById(R.id.my_number);
        mail = (TextView)v.findViewById(R.id.my_email);
        friendnetwork = (TextView)v.findViewById(R.id.my_friend_network);

        SharedPreferences settings = this.getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        String userId = settings.getString("username","");


        JSONObject jsonObject = new JSONObject();
        HttpAsyncTask5 st = null;
        st = new HttpAsyncTask5(activity,name,age,bloodgroup,willing,gender,contact,mail,friendnetwork,userId);
        st.execute("http://" + getString(R.string.InternetProtocol) + "/lifeline/get_user_profile.php");


        return v;


    }

}

class HttpAsyncTask5 extends AsyncTask<String, Void, String> {
    String result="";
    Activity context;
    Location location;
    String userID;
    TextView name,age,bloodgroup,willing,gender,contact,mail,friendnetwork;


    public HttpAsyncTask5(Activity context, TextView name, TextView age, TextView bloodgroup, TextView willing, TextView gender, TextView contact, TextView mail, TextView friendnetwork, String userId) {
        this.name = name;
        this.age = age;
        this.bloodgroup = bloodgroup;
        this.willing = willing;
        this.gender = gender;
        this.contact = contact;
        this.mail = mail;
        this.friendnetwork = friendnetwork;
        this.context = context;
        this.userID = userId;
    }
    public String getResult(){

        return this.result;

    }
    ProgressDialog PD;

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
    //    PD = new ProgressDialog(context);
     //   PD.setTitle("Refreshing...");
     //   PD.setMessage("Loading...");
      //  PD.setCancelable(false);
      ///  PD.show();
        //PD.dismiss();
    }
    @Override
    protected String doInBackground(String... urls) {
        InputStream inputStream = null;
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(urls[0]);
            String json = "";
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("username", userID);

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
       // PD.dismiss();
        Log.d("result", "must be here");
        Log.d("anupam", result);
        String[] words = result.split("/");

        name.setText(words[0]);
        age.setText(words[1]);
        bloodgroup.setText(words[2]);
        willing.setText(words[3]);
        gender.setText(words[4]);
        contact.setText(words[5]);
        mail.setText(words[6]);
        friendnetwork.setText(words[7]);



    }
}