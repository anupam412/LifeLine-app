package com.example.anupam.lifeline;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Anupam on 03-02-2017.
 */
public class MainScreen extends Activity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout dL;
    NavigationView dList;
    ImageButton b;
    TextView personName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_screen);

        SharedPreferences sf = getSharedPreferences("userInfo",MODE_PRIVATE);


        b = (ImageButton)findViewById(R.id.menu_button);
        dL = (DrawerLayout)findViewById(R.id.menu_drawer);
        dList = (NavigationView) findViewById(R.id.nav_view);

        View v = dList.getHeaderView(0);
        personName = (TextView)v.findViewById(R.id.nav_header_textz);
        Bundle extras = getIntent().getExtras();
        personName.setText("Welcome "+extras.getString("username_temp").toString());


        b.setOnClickListener(this);
        dList.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onClick(View v) {
        dL.openDrawer(dList);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getTitle().toString().compareTo("Logout")==0){
            Toast.makeText(getBaseContext(),"You've Successfully logged out",Toast.LENGTH_SHORT).show();
            SharedPreferences sf = getSharedPreferences("userInfo",MODE_PRIVATE);
            SharedPreferences.Editor editor = sf.edit();
            editor.clear();
            editor.apply();
            Intent i = new Intent(MainScreen.this,Login_activity.class);
            startActivity(i);
            finish();
        }
        else if(item.getTitle().toString().compareTo("Share")==0){
            try {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "LIFELINE");
                String sAux = "Check out this cool app recently created by IIT Patna Students and share it with your friends!!\n" +
                        "Regards\n" +
                        "Raghav,Ashish,Anupam\n";
                sAux = sAux + "https://play.google.com/store/apps/details?id=com.patientz.activity&hl=en\n\n";
                i.putExtra(Intent.EXTRA_TEXT, sAux);
                startActivity(Intent.createChooser(i, "choose one"));
            } catch(Exception e) {
                //e.toString();
            }
        }

        else if (item.getTitle().toString().compareTo("Tutorials")==0){
            Intent i = new Intent(MainScreen.this,Tutorials.class);
            startActivity(i);
        }

            return false;
    }


}
