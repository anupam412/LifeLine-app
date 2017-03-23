package com.example.anupam.lifeline;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

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

public class Login_activity extends Activity implements View.OnClickListener {

    Button login,forgot_password,signup;
    EditText username;
    EditText password;
    CheckBox rememberMe;
    String UserId,Pswd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login_activity);


        login  = (Button)findViewById(R.id.login_button);
        signup = (Button)findViewById(R.id.signUP_button);

        username = (EditText)findViewById(R.id.editText3);
        password = (EditText)findViewById(R.id.editText4);

        login.setOnClickListener(this);
        signup.setOnClickListener(this);

        rememberMe = (CheckBox)findViewById(R.id.checkbox1);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.login_button) {
            if(!isConnected()){
                Toast.makeText(getBaseContext(),"Please check your internet connection",Toast.LENGTH_SHORT).show();
            }
            IsUserValid();

        }
        else if(v.getId() == R.id.signUP_button){
            Intent i = new Intent(Login_activity.this,signup_activity.class);
            startActivity(i);

        }
    }

    private void saveInfo(Login_activity context,String username,String password,boolean rememberMe) {

        if(rememberMe) {

            SharedPreferences sharedpref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpref.edit();

            editor.putString("username", username);
            editor.putString("password", password);

            editor.apply();
        }
    }

    private void IsUserValid(){
        JSONObject jsonObject = new JSONObject();
        HttpAsyncTask1 ss = null;
        UserId = username.getText().toString();
        Pswd = password.getText().toString();
        try {
            Log.d("asa","first");
            ss = new HttpAsyncTask1(username.getText().toString(),password.getText().toString(),this,rememberMe.isChecked());
            ss.execute("http://172.16.178.74/lifeline/login.php");
           // Thread.sleep(2000);
            Log.d("asa","first");
        }catch (Exception jse){

        }

    }

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
        }

    public void startNewActivity(Login_activity context,String username,String password,boolean rememberMe) {
        Log.d("fuck","offfff");

        saveInfo(context,username,password,rememberMe);
        Log.d("fuck","offfff");

        Intent i = new Intent(context,MainScreen.class);
        i.putExtra("username_temp",username);
        startActivity(i);
        finish();
    }

    public void wrongDATA(Login_activity context) {
        Toast.makeText(context," Wrong credentials",Toast.LENGTH_SHORT).show();
    }
}

class HttpAsyncTask1 extends AsyncTask<String, Void, String> {
    String username;
    String password;
    boolean checkedStatus;
    String result="";
    Login_activity context;
    public HttpAsyncTask1(String text, String text1,Login_activity context,boolean checkedStatus) {
        this.username = text;
        this.password = text1;
        this.context = context;
        this.checkedStatus  = checkedStatus;
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

            jsonObject.accumulate("username", username);
            jsonObject.accumulate("password", password);

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
            //Log.d("anupam",this.result);
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
        PD.dismiss();
        // result.trim();
       // Login_activity la = new Login_activity();
        Log.d("anu",result+""+result.equals("success")+" "+result.length());
        if(result.charAt(0) == 's'){
            Log.d("fuck","if");

            this.context.startNewActivity(context,this.username,this.password,this.checkedStatus);
            Log.d("fuck","offfff");

        }
        else{
           Log.d("fuck","else ");

            this.context.wrongDATA(context);
        }
    }
}
/*
package com.hmkcode.android;
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import com.hmkcode.android.vo.Person;
 
public class MainActivity extends Activity implements OnClickListener {
 
    TextView tvIsConnected;
    EditText etName,etCountry,etTwitter;
    Button btnPost;
 
    Person person;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
        // get reference to the views
        tvIsConnected = (TextView) findViewById(R.id.tvIsConnected);
        etName = (EditText) findViewById(R.id.etName);
        etCountry = (EditText) findViewById(R.id.etCountry);
        etTwitter = (EditText) findViewById(R.id.etTwitter);
        btnPost = (Button) findViewById(R.id.btnPost);
 
        // check if you are connected or not
        if(isConnected()){
            tvIsConnected.setBackgroundColor(0xFF00CC00);
            tvIsConnected.setText("You are conncted");
        }
        else{
            tvIsConnected.setText("You are NOT conncted");
        }
 
        // add click listener to Button "POST"
        btnPost.setOnClickListener(this);
 
    }
 
    public static String POST(String url, Person person){
        InputStream inputStream = null;
        String result = "";
        try {
 
            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
 
            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);
 
            String json = "";
 
            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("name", person.getName());
            jsonObject.accumulate("country", person.getCountry());
            jsonObject.accumulate("twitter", person.getTwitter());
 
            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();
 
            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);
 
            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);
 
            // 6. set httpPost Entity
            httpPost.setEntity(se);
 
            // 7. Set some headers to inform server about the type of the content   
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
 
            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);
 
            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
 
            // 10. convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
 
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
 
        // 11. return result
        return result;
    }
 
    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected())
                return true;
            else
                return false;    
    }
    @Override
    public void onClick(View view) {
 
        switch(view.getId()){
            case R.id.btnPost:
                if(!validate())
                    Toast.makeText(getBaseContext(), "Enter some data!", Toast.LENGTH_LONG).show();
                // call AsynTask to perform network operation on separate thread
                new HttpAsyncTask().execute("http://hmkcode.appspot.com/jsonservlet");
            break;
        }
 
    }
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
 
            person = new Person();
            person.setName(etName.getText().toString());
            person.setCountry(etCountry.getText().toString());
            person.setTwitter(etTwitter.getText().toString());
 
            return POST(urls[0],person);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
       }
    }
 
    private boolean validate(){
        if(etName.getText().toString().trim().equals(""))
            return false;
        else if(etCountry.getText().toString().trim().equals(""))
            return false;
        else if(etTwitter.getText().toString().trim().equals(""))
            return false;
        else
            return true;    
    }
    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
 
        inputStream.close();
        return result;
 
    }   
}
 */