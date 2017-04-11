package com.example.anupam.lifelineDriver;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
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
import java.util.Calendar;

public class signup_activity extends Activity implements View.OnClickListener {

    EditText username;
    Button choosedate;
    EditText firstname;
    EditText lastname;
    RadioGroup gender;
    EditText email;
    EditText contactno;
    EditText password;

    Button submit;

    String usernameS;
    String firstNameS;
    String lastNameS;
    String dateofbirthS;
    String genderS="other";
    String emailS;
    String contactnoS;
    String passwordS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_signup_activity);

        username = (EditText)findViewById(R.id.user_name);
        firstname = (EditText)findViewById(R.id.first_name);
        lastname = (EditText)findViewById(R.id.last_name);
        email = (EditText)findViewById(R.id.email);
        contactno = (EditText)findViewById(R.id.contact_no);
        password = (EditText)findViewById(R.id.password);

        choosedate = (Button)findViewById(R.id.date2);

        submit = (Button)findViewById(R.id.submit_button_final);

        gender = (RadioGroup)findViewById(R.id.genderz);
       // gender.OnCheckedChangeListener(this);

        choosedate.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.date2){

            Calendar calendar = Calendar.getInstance();
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener(){
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            choosedate.setText(dayOfMonth+" /"+(month+1) + " /"+year);
                            dateofbirthS = ""+year+""+(month+1)+""+dayOfMonth;
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                dpd.show();
            }
        }
        else if(v.getId() == R.id.submit_button_final){
            Log.d("asa","first");

            if(!isConnected()){
                Toast.makeText(getBaseContext(),"Please check your internet connection",Toast.LENGTH_SHORT).show();
            }
            else {
                Log.d("asa","first");

                usernameS = username.getText().toString();
                firstNameS = firstname.getText().toString();
                lastNameS = lastname.getText().toString();
                emailS = email.getText().toString();
                contactnoS = contactno.getText().toString();
                passwordS = password.getText().toString();

                JSONObject jsonObject = new JSONObject();
                HttpAsyncTask2 ss = null;

                try {
                    Log.d("asa","first");

                    ss = new HttpAsyncTask2(this,usernameS,firstNameS,lastNameS,dateofbirthS,genderS,emailS,contactnoS,passwordS);
                    ss.execute("http://"+getString(R.string.InternetProtocol)+"/lifeline/register_driver.php");
                    // Thread.sleep(2000);
                    Log.d("asa","first");
                }catch (Exception jse){

                }

            }

        }
    }

    private boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    public void registration_successful(signup_activity context) {

        Toast.makeText(context,"Successfully Registered",Toast.LENGTH_SHORT).show();
        finish();

    }

    public void wrong(signup_activity context) {
        Toast.makeText(context,"Something went wrong,try again after sometime",Toast.LENGTH_SHORT).show();
    }
}


class HttpAsyncTask2 extends AsyncTask<String, Void, String> {
    String username;
    String firstname;
    String lastname;
    String dateofbirth;
    String gender;
    String email;
    String contactno;
    String password;

    String result="";
    signup_activity context;

    public HttpAsyncTask2(signup_activity context,String username, String firstname,String lastname,String dateofbirth,String gender,String email,String contactno,String password) {
        this.username = username;
        this.firstname = firstname;
        this.context = context;
        this.lastname = lastname;
        this.dateofbirth = dateofbirth;
        this.gender = gender;
        this.email  = email;
        this.contactno = contactno;
        this.password = password;
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
            jsonObject.accumulate("firstname", firstname);
            jsonObject.accumulate("lastname", lastname);
            jsonObject.accumulate("dateofbirth",dateofbirth);
            jsonObject.accumulate("gender", gender);
            jsonObject.accumulate("email", email);
            jsonObject.accumulate("contactno", contactno);
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
        PD.dismiss();
        // Login_activity la = new Login_activity();

        if(result.charAt(0)=='s'){
            context.registration_successful(context);
        }
        else{
            context.wrong(context);
        }
    }
}