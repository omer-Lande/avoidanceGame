package com.example.caravoidancegame;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;

import com.example.caravoidancegame.Fragments.ListFragments;
import com.example.caravoidancegame.Fragments.MapFragment;
import com.example.caravoidancegame.Iterfaces.StepCallBack;
import com.google.android.material.imageview.ShapeableImageView;



public class ScoreboardActivity extends AppCompatActivity {

    private ListFragments listFragments;
    private ShapeableImageView returnIMG;
    private MapFragment mapFragment;

    private StepCallBack StepCallBack = new StepCallBack() {
        @Override
        public void setMapLocation(double lat, double lon, String name) {
            mapFragment.setMapLocation(lat,lon,name);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        initFragments();
        findViews();
        returnIMG.setOnClickListener(v->{
            Intent openingIntent = new Intent(this, MenuActivity.class);
            startActivity(openingIntent);
            finish();
        });
    }

    private void initFragments() {
        listFragments = new ListFragments();
        mapFragment = new MapFragment();
        listFragments.setCallBack_list(StepCallBack);
        getSupportFragmentManager().beginTransaction().add(R.id.main_LST_players,listFragments).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.maps,mapFragment).commit();

    }

    public void findViews(){
        returnIMG = findViewById(R.id.IMG_return);
    }
}
