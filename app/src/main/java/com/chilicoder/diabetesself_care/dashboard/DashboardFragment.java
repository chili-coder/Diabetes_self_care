package com.chilicoder.diabetesself_care.dashboard;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chilicoder.diabetesself_care.DeveloperActivity;
import com.chilicoder.diabetesself_care.InfoActivity;
import com.chilicoder.diabetesself_care.R;
import com.chilicoder.diabetesself_care.blood.BloodActivity;
import com.chilicoder.diabetesself_care.followup.FollowupActivity;
import com.chilicoder.diabetesself_care.footcare.FootActivity;
import com.chilicoder.diabetesself_care.tobacco.TobaccoActivity;


public class DashboardFragment extends Fragment {


    public DashboardFragment() {
        // Required empty public constructor
    }


    private CardView followupCard,bloodCard,tabaccoCard,footCard,infoCard,developerCard;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     View  v= inflater.inflate(R.layout.fragment_dashboard, container, false);

     followupCard=v.findViewById(R.id.followup_card);
     bloodCard=v.findViewById(R.id.blood_card);
     tabaccoCard=v.findViewById(R.id.tabacco_card);
     footCard=v.findViewById(R.id.foot_card);
     infoCard=v.findViewById(R.id.info_card);
     developerCard=v.findViewById(R.id.developer_card);


     infoCard.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             startActivity(new Intent(getActivity(), InfoActivity.class));
         }
     });

        developerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), DeveloperActivity.class));
            }
        });

        tabaccoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), TobaccoActivity.class));
            }
        });

        followupCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), FollowupActivity.class));
            }
        });
        bloodCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), BloodActivity.class));
            }
        });

        footCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), FootActivity.class));
            }
        });




     return  v;

    }
}