package com.example.anupam.lifelineClientSide;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.DonutProgress;

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
import java.sql.Driver;

public class DriverFindingSendingNotifications extends Activity implements View.OnClickListener {
    Context context;
CountDownTimer cdt;
    ProgressDialog PD;
    Button request_cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_driver_finding_sending_notifications);

        PD = new ProgressDialog(DriverFindingSendingNotifications.this);
        PD.setMessage("Searching for Available Ambulances...");
        PD.setCancelable(false);
        context = getApplicationContext();
        request_cancel = (Button)findViewById(R.id.request_cancel);
        request_cancel.setOnClickListener(this);
        final DonutProgress dp = (DonutProgress)findViewById(R.id.donut_progress);

        final Handler h = new Handler(){
            @Override
            public void handleMessage(Message msg){
                if(msg.what == -1){
                    PD.show();
                }
                else if(msg.what == 0){
                    PD.setMessage("A nearby ambulance has been found for you !!");
                }
                else if(msg.what == 1 ){
                    PD.setMessage("Sending notifications to your dear ones");
                }
                else if(msg.what == 2 ){
                    PD.setMessage("Establishing connection with the ambulance");
                }

            }
        };


        cdt = new CountDownTimer(5000, 1) {
            public void onTick(long millisUntilFinished) {
                Log.d("anupam", String.valueOf(millisUntilFinished));
                dp.setProgress((float) (millisUntilFinished*1.0/1000f));
                String text = String.format("%.1f",(float) (millisUntilFinished*1.0/1000f) );
                dp.setText(String.valueOf(text)+" sec");
            }

            public void onFinish() {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            SharedPreferences settings = getSharedPreferences("userInfo", MODE_PRIVATE);

                            String userId = settings.getString("username","");

                            JSONObject jsonObject = new JSONObject();
                            HttpAsyncTask6 st = null;
                            st = new HttpAsyncTask6(DriverFindingSendingNotifications.this,userId);
                            st.execute("http://" + getString(R.string.InternetProtocol) + "/lifeline/receive_request_user.php");

                            Thread.sleep(300);
                            h.sendEmptyMessage(-1);
                            Thread.sleep(4000);
                            h.sendEmptyMessage(0);
                            Thread.sleep(2500);
                            h.sendEmptyMessage(1);
                            Thread.sleep(4000);
                            h.sendEmptyMessage(2);
                            Thread.sleep(5000);
                            //  PD.setMessage("Establishing connection with the ambulance");
                        }   catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Intent i = new Intent(DriverFindingSendingNotifications.this,MapsActivity.class);
                        startActivity(i);
                        PD.dismiss();
                        finish();

                    }
                }).start();;
            }

        }.start();



    }

    @Override
    public void onClick(View v) {

        Toast.makeText(context,"Next time please think before you make a request !!",Toast.LENGTH_SHORT).show();
        cdt.cancel();
        finish();
    }
}

class HttpAsyncTask6 extends AsyncTask<String, Void, String> {
    String userId;
    String latitude;
    String longitude;

    String result="";
    DriverFindingSendingNotifications context;

    public HttpAsyncTask6(DriverFindingSendingNotifications context,String userId) {
        this.context = context;
        this.userId = userId;
    }
    ProgressDialog PD;

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
//        PD = new ProgressDialog(this.context);
  //      PD.setTitle("Please Wait..");
    //    PD.setMessage("Loading...");
     //   PD.setCancelable(false);
      //  PD.show();
       // PD.dismiss();
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
            jsonObject.accumulate("username", userId);

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

    }
}