package com.example.anupam.lifelineDriver.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anupam.lifelineDriver.R;


public class tutorial_fragment extends Fragment {
    private CardView c1,c2,c3,c4,c5,c6,c7;


    public tutorial_fragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tutorial_fragment, container, false);
        setView(v);
        setListner(v);


        return v;


    }

    public void setView(View v)
    {
        c1 = (CardView)v.findViewById(R.id.card_view_one);
        c2 = (CardView)v.findViewById(R.id.card_view_two);
        c3 = (CardView)v.findViewById(R.id.card_view_three);
        c4 = (CardView)v.findViewById(R.id.card_view_four);
        c5 = (CardView)v.findViewById(R.id.card_view_five);
        c6 = (CardView)v.findViewById(R.id.card_view_six);
        c7 = (CardView)v.findViewById(R.id.card_view_seven);
    }

    public void setListner(View view)
    {
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.wikihow.com/Treat-Severe-Bleeding-During-First-Aid";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="http://www.wikihow.com/Get-Rid-of-Food-Poisoning";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });

        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url="http://www.wikihow.com/Get-Rid-of-Food-Poisoning";
                Intent i= new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="http://www.wikihow.com/Help-a-Choking-Victim";
                Intent i= new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });

        c5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String url="http://www.wikihow.com/Stop-a-Nose-Bleed";
            Intent i= new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);

            }
        });

        c6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        String url="http://www.wikihow.com/Save-the-Life-of-a-Person-Suffering-Heat-Stroke";
        Intent i= new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
            }
        });

        c7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        String url="http://www.wikihow.com/Treat-a-Burn";
        Intent i= new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
            }
        });


   }

    }



