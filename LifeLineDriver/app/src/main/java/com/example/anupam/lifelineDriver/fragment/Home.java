package com.example.anupam.lifelineDriver.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.anupam.lifelineDriver.MainScreen;
import com.example.anupam.lifelineDriver.MapsActivity;
import com.example.anupam.lifelineDriver.R;
import com.race604.drawable.wave.WaveDrawable;

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
import java.util.Timer;
import java.util.TimerTask;


public class Home extends Fragment implements CompoundButton.OnCheckedChangeListener {
    Timer myTimer;
    String userId;
    Activity activity;
    public Home() {
        this.activity = getActivity();
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        Switch sw =(Switch)v.findViewById(R.id.switch1);
        sw.setOnCheckedChangeListener(this);

        SharedPreferences settings = this.getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);

         userId = settings.getString("username","");

        ImageView imageView2 = (ImageView) v.findViewById(R.id.image2);
        WaveDrawable chromeWave = new WaveDrawable(getContext(),R.drawable.heart);
        chromeWave.setWaveAmplitude(70);
        chromeWave.setWaveSpeed(50);
        chromeWave.setWaveLength(1600);

        chromeWave.setIndeterminate(true);


        imageView2.setImageDrawable(chromeWave);

        TextView bt = (TextView)v.findViewById(R.id.blinking_text);
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(600); //You can manage the blinking time with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        bt.startAnimation(anim);


        final long period = 2000;
        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {

                JSONObject jsonObject1 = new JSONObject();
                HttpAsyncTask7 ss = null;
                try {
                    ss = new HttpAsyncTask7(activity, userId);
                    ss.execute("http://"+getString(R.string.InternetProtocol)+"/lifeline/a.php");

                } catch (Exception jse) {
                    Log.d("anupam", jse.getMessage() + jse.toString());
                }
            }

        },0,period);
        return v;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


        JSONObject jsonObject1 = new JSONObject();
        HttpAsyncTask8 ss = null;
        try {
            ss = new HttpAsyncTask8(activity, userId,String.valueOf(isChecked));
            ss.execute("http://"+getString(R.string.InternetProtocol)+"/lifeline/set_driver_available.php");

        } catch (Exception jse) {
            Log.d("anupam", jse.getMessage() + jse.toString());
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        myTimer.cancel();
    }
}



class HttpAsyncTask8 extends AsyncTask<String, Void, String> {
    String userId;
    String boolstatus;
    String result="";
    Context context;

    public HttpAsyncTask8(Context context, String userId,String boolstatus) {

        this.boolstatus = boolstatus;
        this.context = context;
        this.userId = userId;

    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
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
            //Log.d("anupam",this.userId+" RealDriverid ");
            jsonObject.accumulate("drivername", this.userId);
            if(boolstatus.compareTo("false")==0)
                 jsonObject.accumulate("availability","0");
            else{
                jsonObject.accumulate("availability","1");
            }

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

       // if(result.charAt(0)=='s'){
            //    context.registration_successful(context);
      //  }
      //  else{
            //  context.wrong(context);
        //}
    }
}
class HttpAsyncTask7 extends AsyncTask<String, Void, String> {
    String userId;

    String result="";
    Context context;

    public HttpAsyncTask7(Context context, String userId) {

        this.context = context;
        this.userId = userId;

    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
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
            jsonObject.accumulate("drivername", userId);

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

        if(result.charAt(0)=='s'){
            //    context.registration_successful(context);
        }
        else{
            //  context.wrong(context);
        }
    }
}