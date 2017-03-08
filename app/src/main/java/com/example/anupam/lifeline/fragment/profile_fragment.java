package com.example.anupam.lifeline.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.anupam.lifeline.R;


@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
public class profile_fragment extends Fragment {
    private Button change_butt;
    private TextView tv;


    public profile_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile_fragment, container, false);
        setView(v);
        setLisner(v);
        return v;
    }

    public void setView(View view)
    {
        change_butt = (Button)view.findViewById(R.id.change_butt);
        tv = (TextView)view.findViewById(R.id.status);

    }
    public  void setLisner(View view)
    {
        change_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("button clicked");
            }
        });
    }

}
