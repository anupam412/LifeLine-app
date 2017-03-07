package com.example.anupam.lifeline;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Tutorials extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorials);
    }


    public void method_button_click(View v){
        String url="http://www.wikihow.com/Treat-Severe-Bleeding-During-First-Aid";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void method_button_click_two(View v){
        String url="http://www.wikihow.com/Get-Rid-of-Food-Poisoning";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void method_button_click_three(View v){
        String url="http://www.wikihow.com/Get-Rid-of-Food-Poisoning";
        Intent i= new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);

    }

    public void method_button_click_four (View v){
        String url="http://www.wikihow.com/Help-a-Choking-Victim";
        Intent i= new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);}

    public void method_button_click_five (View v){
        String url="http://www.wikihow.com/Stop-a-Nose-Bleed";
        Intent i= new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);}

    public void method_button_click_six (View v){
        String url="http://www.wikihow.com/Save-the-Life-of-a-Person-Suffering-Heat-Stroke";
        Intent i= new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);}

    public void method_button_click_seven (View v){
        String url="http://www.wikihow.com/Treat-a-Burn";
        Intent i= new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);}
}






