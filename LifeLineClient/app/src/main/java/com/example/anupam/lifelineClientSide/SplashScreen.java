package com.example.anupam.lifelineClientSide;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);
       // getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                SharedPreferences myPrefs = getSharedPreferences("userInfo",MODE_PRIVATE);
                String username = myPrefs.getString("username",null);
                String password = myPrefs.getString("password",null);

                if (username != null && password != null ) {
                    //username and password are present, do your stuff

                    Intent i = new Intent(SplashScreen.this,MainScreen.class);
                    i.putExtra("username_temp",username);
                    startActivity(i);

                    // close this activity
                    finish();
                }
                else {
                    Intent i = new Intent(SplashScreen.this, Login_activity.class);
                    startActivity(i);

                    // close this activity
                    finish();
                }
            }
        }, 4000);

    }
}
