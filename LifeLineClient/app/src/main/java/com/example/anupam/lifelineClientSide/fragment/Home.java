package com.example.anupam.lifelineClientSide.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anupam.lifelineClientSide.DriverFindingSendingNotifications;

import com.example.anupam.lifelineClientSide.R;
import com.skyfishjy.library.RippleBackground;

import org.json.JSONObject;
import org.w3c.dom.Text;


public class Home extends Fragment implements View.OnClickListener {

    public Home() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        ImageView emergency = (ImageView)v.findViewById(R.id.save_button);
        emergency.setOnClickListener(this);

        final RippleBackground rippleBackground=(RippleBackground)v.findViewById(R.id.content);
        rippleBackground.startRippleAnimation();

        return v;
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(getContext(), DriverFindingSendingNotifications.class);
        startActivity(i);

    }
}
